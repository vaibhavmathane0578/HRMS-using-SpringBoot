package com.hr.manage.validator;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hr.manage.constant.ApplicationResponseCode;
import com.hr.manage.constant.Constants;
import com.hr.manage.constant.ValidationUtils;
import com.hr.manage.payload.AdminPayload;
import com.hr.manage.payload.DepartmentPayload;
import com.hr.manage.payload.EmployeePayload;
import com.hr.manage.payload.LeavePayload;
import com.hr.manage.payload.ResponseDetails;

public class Validator {


	public static ResponseEntity<Object> isValidAdminPayload(AdminPayload adminPayload) {
		ResponseDetails passwordValidationMessage = ValidationUtils
				.isValidPassword(adminPayload.getPassword());
		if (ValidationUtils.isInvalidName(adminPayload.getFirstName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(
					new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.FIRSTNAME_INVALID));
		} else if (ValidationUtils.isInvalidName(adminPayload.getLastName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(
					new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.LASTNAME_INVALID));
		} else if (ValidationUtils.isInvalidContactNumber(adminPayload.getContactNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDetails(
					ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.CONTACT_NUMBER_INVALID));
		} else if (ValidationUtils.isInvalidEmail(adminPayload.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.EMAIL_INVALID));
		} else if (passwordValidationMessage != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, passwordValidationMessage.getResponseMessage()));
		}
		return null;

	}



	// EmployeePayloadValidation

	public static ResponseEntity<Object> isValidEmployeePayload(EmployeePayload employeePayload) {
		ResponseDetails passwordValidationMessage = ValidationUtils
				.isValidPassword(employeePayload.getPassword());
		if (ValidationUtils.isInvalidName(employeePayload.getFirstName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(
					new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.FIRSTNAME_INVALID));
		} else if (ValidationUtils.isInvalidName(employeePayload.getLastName())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(
					new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.LASTNAME_INVALID));
		} else if (ValidationUtils.isInvalidGender(employeePayload.getGender())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.GENDER_INVALID));
		} else if (ValidationUtils.isInvalidContactNumber(employeePayload.getContactNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDetails(
					ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.CONTACT_NUMBER_INVALID));
		} else if (ValidationUtils.isInvalidEmail(employeePayload.getOfficialEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.EMAIL_INVALID));
		} else if (ValidationUtils.isInvalidEmail(employeePayload.getPersonalEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.EMAIL_INVALID));
		} else if (ValidationUtils.isInvalidAddress(employeePayload.getPermanentAddress())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.ADDRESS_INVALID));
		} else if (ValidationUtils.isInvalidAddress(employeePayload.getTemporaryAddress())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.ADDRESS_INVALID));
		} else if (passwordValidationMessage != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, passwordValidationMessage.getResponseMessage()));
		} else if (ValidationUtils.isInvalidHighestQualification(employeePayload.getHighestQualification())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDetails(
					ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.HIGHEST_QUALIFICATION_INVALID));
		} else if (ValidationUtils.isInvalidDesignation(employeePayload.getDesignation())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(
					new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.DESIGNATION_INVALID));
		}
		// If all validations pass, return null to indicate successful validation.
		return null;
	}



	public static ResponseEntity<Object> isValidDepartmentPayload(DepartmentPayload departmentPayload) {
		
		return null;
	}



	public static ResponseEntity<Object> isValidLeavePayload(LeavePayload leavePayload) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static boolean isOtpValid(String storedOtp, String enteredOtp, LocalDateTime creationTime, int otpValidityDurationInMinutes) {
        // Check if the OTPs match
        boolean otpMatches = storedOtp.equals(enteredOtp);

        // Check if the OTP is still valid based on the creation time and validity duration
        boolean isOtpStillValid = isWithinValidityDuration(creationTime, otpValidityDurationInMinutes);

        return otpMatches && isOtpStillValid;
    }

    private static boolean isWithinValidityDuration(LocalDateTime creationTime, int otpValidityDurationInMinutes) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = creationTime.plusMinutes(otpValidityDurationInMinutes);
        return currentTime.isBefore(expirationTime);
    }
}
