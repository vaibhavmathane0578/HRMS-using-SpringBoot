package com.hr.manage.service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hr.manage.constant.ApplicationResponseCode;
import com.hr.manage.constant.Constants;
import com.hr.manage.constant.EntityTypes;
import com.hr.manage.entity.Admin;
import com.hr.manage.payload.AdminPayload;
import com.hr.manage.payload.LoginPayload;
import com.hr.manage.payload.ResponseDetails;
import com.hr.manage.repository.AdminRepository;
import com.hr.manage.security.EncryptionUtils;
import com.hr.manage.validator.Validator;
@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;
	 
	 private final EncryptionUtils encryptionUtils = new EncryptionUtils();

	@Transactional
	public ResponseEntity<Object> createAdmin(AdminPayload adminPayload) {
		if (adminPayload.getId() == null) {
			Admin response = adminRepository.getByEmail(adminPayload.getEmail());
			if (Objects.nonNull(response)) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(new ResponseDetails(ApplicationResponseCode.RESOURCE_CONFLICT,
								MessageFormat.format(Constants.RESOURCE_ALREADY_EXISTS, EntityTypes.ADMIN,
										Constants.EMAIL, adminPayload.getEmail())));
			}

			ResponseEntity<Object> validationResponse = Validator.isValidAdminPayload(adminPayload);
			if (validationResponse != null) {
				return validationResponse;
			}
			Admin admin = new Admin();
			admin.setId(Constants.ADMIN_ID_PREFIX.concat(UUID.randomUUID().toString()));
			admin.setFirstName(adminPayload.getFirstName());
			admin.setLastName(adminPayload.getLastName());
			admin.setContactNumber(adminPayload.getContactNumber());
			admin.setEmail(adminPayload.getEmail());
			String encryptedPassword = encryptionUtils.encrypt(adminPayload.getPassword(), "yourSecretKey");
			admin.setPassword(encryptedPassword);
			admin.setCreatedAt(LocalDateTime.now().toString());
			return ResponseEntity.ok(adminRepository.save(admin));
		}else {
		return null;}
	}

	// GetAdminProcess
	public ResponseEntity<Object> getAdmin(String id) {
		Admin response = adminRepository.findById(id);
		if (Objects.nonNull(response)) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
						MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.ADMIN, id)));
	}

	@Transactional
	public ResponseEntity<Object> updateAdmin(AdminPayload adminPayload) {
		String id = adminPayload.getId();
		Admin response = adminRepository.findById(id);

		if (Objects.isNull(response)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
							MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.ADMIN, id)));
		}

		ResponseEntity<Object> validationResponse = Validator.isValidAdminPayload(adminPayload);
		if (validationResponse != null) {
			return validationResponse;
		}

		Admin admin = new Admin();
		admin.setId(id);
		admin.setFirstName(adminPayload.getFirstName());
		admin.setLastName(adminPayload.getLastName());
		admin.setContactNumber(adminPayload.getContactNumber());
		admin.setEmail(adminPayload.getEmail());
		String encryptedPassword = encryptionUtils.encrypt(adminPayload.getPassword(), "yourSecretKey");
		admin.setPassword(encryptedPassword);
		admin.setCreatedAt(adminPayload.getCreatedAt());
		admin.setUpdatedAt(LocalDateTime.now().toString());
		return ResponseEntity.ok(adminRepository.save(admin));

	}

	// DeleteAdminProcess
	@Transactional
	public ResponseEntity<Object> deleteAdmin(String id) {

		Admin response = adminRepository.findById(id);
		if (Objects.isNull(response)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
							MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.ADMIN.title(), id)));
		}
		response.setDeleted(true);
		adminRepository.save(response);
		return ResponseEntity.ok(new ResponseDetails(ApplicationResponseCode.SUCCESS,
				MessageFormat.format(Constants.RESOURCE_DELETED, EntityTypes.ADMIN.title(), id)));

	}

	// BulkDeleteAdminProcess
	@Transactional
	public ResponseEntity<Object> bulkDelete(List<String> ids) {
		List<Admin> response = adminRepository.getAdminByIds(ids);
		for (Admin admin : response) {
			admin.setDeleted(true);
			adminRepository.save(admin);

		}
		return ResponseEntity.ok(new ResponseDetails(ApplicationResponseCode.SUCCESS,
				MessageFormat.format(Constants.RESOURCES_DELETED, EntityTypes.ADMIN.title())));
	}

	@Transactional
	public ResponseEntity<Object> authenticateUser(LoginPayload loginPayload) {
		
			Admin admin = adminRepository.getAdminByEmail(loginPayload);
			if (Objects.nonNull(admin)) {
				String decryptedPassword = encryptionUtils.decrypt(admin.getPassword(), "yourSecretKey");
				if (decryptedPassword.equals(loginPayload.getPassword())) {
	                return ResponseEntity.ok().body(new ResponseDetails(ApplicationResponseCode.SUCCESS, Constants.VALID_CREDENTIALS));
	            } else {
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                        .body(new ResponseDetails(ApplicationResponseCode.UNAUTHORIZED, Constants.INVALID_CREDENTIALS));
	            }
	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                    .body(new ResponseDetails(ApplicationResponseCode.UNAUTHORIZED, Constants.INVALID_CREDENTIALS));
	        }
	    
		
		}

}
