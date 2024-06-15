package com.hr.manage.service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hr.manage.constant.ApplicationResponseCode;
import com.hr.manage.constant.Constants;
import com.hr.manage.constant.EntityTypes;
import com.hr.manage.constant.OTPGenerator;
import com.hr.manage.entity.Employee;
import com.hr.manage.payload.EmployeePayload;
import com.hr.manage.payload.LoginPayload;
import com.hr.manage.payload.OtpVerificationPayload;
import com.hr.manage.payload.ResponseDetails;
import com.hr.manage.repository.EmployeeRepository;
import com.hr.manage.security.EncryptionUtils;
import com.hr.manage.validator.Validator;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	private final EncryptionUtils encryptionUtils = new EncryptionUtils();
	private final Map<String, OtpInfo> otpStorage = new HashMap<>();

	// EmployeeCreateProcess
	@Transactional
	public ResponseEntity<Object> createEmployee(EmployeePayload employeePayload) {

		// Check if an employee with the same email already exists
		Employee response = employeeRepository.getByEmail(employeePayload.getOfficialEmail());
		if (Objects.nonNull(response)) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ResponseDetails(ApplicationResponseCode.RESOURCE_CONFLICT,
							MessageFormat.format(Constants.RESOURCE_ALREADY_EXISTS, EntityTypes.EMPLOYEE,
									Constants.EMAIL, employeePayload.getOfficialEmail())));
		}

		ResponseEntity<Object> validationResponse = Validator.isValidEmployeePayload(employeePayload);
		if (validationResponse != null) {
			return validationResponse; // Return the validation error response.
		}

		Employee employee = new Employee();
		employee.setId(EntityTypes.EMPLOYEE.idPrefix().concat(UUID.randomUUID().toString()));
		employee.setFirstName(employeePayload.getFirstName());
		employee.setLastName(employeePayload.getLastName());
		employee.setGender(employeePayload.getGender());
		employee.setContactNumber(employeePayload.getContactNumber());
		employee.setOfficialEmail(employeePayload.getOfficialEmail());
		employee.setPersonalEmail(employeePayload.getPersonalEmail());
		employee.setPermanentAddress(employeePayload.getPermanentAddress());
		employee.setTemporaryAddress(employeePayload.getTemporaryAddress());
		String encryptedPassword = encryptionUtils.encrypt(employeePayload.getPassword(), "yourSecretKey");
		employee.setPassword(encryptedPassword);
		employee.setHighestQualification(employeePayload.getHighestQualification());
		employee.setDesignation(employeePayload.getDesignation());
		employee.setCreatedAt(LocalDateTime.now().toString());
		return ResponseEntity.ok(employeeRepository.save(employee));
	}

	// GetEmployeeProcess
	public ResponseEntity<Object> getEmployee(String id) {
		Employee response = employeeRepository.findById(id);
		if (Objects.nonNull(response)) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
						MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.EMPLOYEE.title(), id)));

	}

	// UpdateEmployeeProcess
	@Transactional
	public ResponseEntity<Object> updateEmployee(EmployeePayload employeePayload) {
		String id = employeePayload.getId();
		Employee response = employeeRepository.findById(id);

		if (Objects.isNull(response)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
							MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.EMPLOYEE, id)));
		}

		ResponseEntity<Object> validationResponse = Validator.isValidEmployeePayload(employeePayload);
		if (validationResponse != null) {
			return validationResponse;
		}

		Employee employee = new Employee();
		employee.setId(id);
		employee.setFirstName(employeePayload.getFirstName());
		employee.setLastName(employeePayload.getLastName());
		employee.setGender(employeePayload.getGender());
		employee.setContactNumber(employeePayload.getContactNumber());
		employee.setOfficialEmail(employeePayload.getOfficialEmail());
		employee.setPersonalEmail(employeePayload.getPersonalEmail());
		employee.setPermanentAddress(employeePayload.getPermanentAddress());
		employee.setTemporaryAddress(employeePayload.getTemporaryAddress());
		String encryptedPassword = encryptionUtils.encrypt(employeePayload.getPassword(), "yourSecretKey");
		employee.setPassword(encryptedPassword);
		employee.setHighestQualification(employeePayload.getHighestQualification());
		employee.setDesignation(employeePayload.getDesignation());
		employee.setCreatedAt(employeePayload.getCreatedAt());
		employee.setUpdatedAt(LocalDateTime.now().toString());

		// Save the updated employee entity
		return ResponseEntity.ok(employeeRepository.save(employee));
	}

	// DeleteEmployeeProcess
	@Transactional
	public ResponseEntity<Object> deleteEmployee(String id) {

		Employee response = employeeRepository.findById(id);
		if (Objects.isNull(response)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
							MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.EMPLOYEE.title(), id)));
		}
		response.setDeleted(true);
		employeeRepository.save(response);
		return ResponseEntity.ok(new ResponseDetails(ApplicationResponseCode.SUCCESS,
				MessageFormat.format(Constants.RESOURCE_DELETED, EntityTypes.EMPLOYEE.title(), id)));

	}

	// EmployeeProcess
	@Transactional
	public ResponseEntity<Object> bulkDelete(List<String> ids) {
		List<Employee> response = employeeRepository.getEmployeeByIds(ids);
		for (Employee employee : response) {
			employee.setDeleted(true);
			employeeRepository.save(employee);

		}
		return ResponseEntity.ok(new ResponseDetails(ApplicationResponseCode.SUCCESS,
				MessageFormat.format(Constants.RESOURCES_DELETED, EntityTypes.EMPLOYEE.title())));
	}

	@Transactional
	public ResponseEntity<Object> authenticateUser(LoginPayload loginPayload) {
	    try {
	        Employee employee = employeeRepository.getEmployeeByOfficialEmail(loginPayload);
	        if (Objects.nonNull(employee)) {
                String decryptedPassword = encryptionUtils.decrypt(employee.getPassword(), "yourSecretKey");
                if (decryptedPassword.equals(loginPayload.getPassword())) {
                    // Send OTP through email
                    String otp = OTPGenerator.generateOtp();
                    sendOtpByEmail(employee.getOfficialEmail(), otp);
                 // Assuming otpStorage is a Map<String, OtpInfo>
                    otpStorage.put(employee.getOfficialEmail(), new OtpInfo(otp));
                    return ResponseEntity.ok().body(new ResponseDetails(ApplicationResponseCode.SUCCESS,
                            Constants.VALID_CREDENTIALS));
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(new ResponseDetails(ApplicationResponseCode.UNAUTHORIZED,
                                    Constants.INVALID_CREDENTIALS));
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ResponseDetails(ApplicationResponseCode.UNAUTHORIZED,
                                Constants.INVALID_CREDENTIALS));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDetails(ApplicationResponseCode.UNAUTHORIZED, Constants.INVALID_CREDENTIALS));
        }
    }
	 public ResponseEntity<Object> verifyOtp(OtpVerificationPayload otpVerificationPayload) {
	        String officialEmail = otpVerificationPayload.getOfficialEmail();
	        String enteredOtp = otpVerificationPayload.getEnteredOtp();

	        // Check if entered OTP is in the set
	        if (otpStorage.containsKey(officialEmail)) {
	            OtpInfo otpInfo = otpStorage.get(officialEmail);
	            
	            // Use OtpValidator to check if the OTP is valid
	            if (Validator.isOtpValid(otpInfo.getOtp(), enteredOtp, otpInfo.getCreationTime(), 5)) {
	                otpStorage.remove(officialEmail);
	                return ResponseEntity.ok().body(new ResponseDetails(ApplicationResponseCode.SUCCESS,
	                        Constants.OTP_VERIFIED));
	            } else {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                        .body(new ResponseDetails(ApplicationResponseCode.UNAUTHORIZED,
	                                Constants.INVALID_OTP));
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body(new ResponseDetails(ApplicationResponseCode.UNAUTHORIZED,
	                            Constants.INVALID_OTP));
	        }
	    }

 

	// Send OTP through email
    private void sendOtpByEmail(String recipientEmail, String otp) {
        String from = "HRManagement<dev.testverify.mail@gmail.com>";
        String host = "smtp.gmail.com"; // Gmail SMTP server
        String username = "vaibhav.s.mathane@gmail.com"; // Your Gmail username
        String password = "iyutppphcepzeshc"; // Your Gmail password

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Get the default Session object
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(from));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            // Set Subject: header field
            message.setSubject("OTP for HR Management Application");

            // Now set the actual message
            message.setText("Welcome to HR Management Application. Your OTP for login is: " + otp);

            // Send message
            Transport.send(message);
            System.out.println("Sent OTP successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
	
    private static class OtpInfo {
        private String otp;
        private LocalDateTime creationTime;

        public OtpInfo(String otp) {
            this.otp = otp;
            this.creationTime = LocalDateTime.now();
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getCreationTime() {
            return creationTime;
        }
    }
	
	
	
}
