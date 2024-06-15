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
import com.hr.manage.entity.Department;
import com.hr.manage.payload.DepartmentPayload;
import com.hr.manage.payload.ResponseDetails;
import com.hr.manage.repository.DepartmentRepository;
import com.hr.manage.validator.Validator;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Transactional
	public ResponseEntity<Object> createDepartment(DepartmentPayload departmentPayload) {
		if (departmentPayload.getId() == null) {
			Department response = departmentRepository.getByDepartment(departmentPayload.getDepartmentName());
			if (Objects.nonNull(response)) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(new ResponseDetails(ApplicationResponseCode.RESOURCE_CONFLICT,
								MessageFormat.format(Constants.RESOURCE_ALREADY_EXISTS, EntityTypes.DEPARTMENT,
										Constants.DEPTARTMENT, departmentPayload.getDepartmentName())));
			}

			ResponseEntity<Object> validationResponse = Validator.isValidDepartmentPayload(departmentPayload);
			if (validationResponse != null) {
				return validationResponse;
			}
			Department dept = new Department();
			dept.setId(EntityTypes.DEPARTMENT.idPrefix().concat(UUID.randomUUID().toString()));
			dept.setDepartmentName(departmentPayload.getDepartmentName());
			dept.setCreatedAt(LocalDateTime.now().toString());
			return ResponseEntity.ok(departmentRepository.save(dept));
		} else {
			return null;
		}

	}
	
	public ResponseEntity<Object> getDepartment(String id) {
		Department response = departmentRepository.findById(id);
		if (Objects.nonNull(response)) {
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
						MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.DEPARTMENT.title(), id)));
	}
	
	@Transactional
	public ResponseEntity<Object> updateDepartment(DepartmentPayload departmentPayload) {
		String id = departmentPayload.getId();
		Department response = departmentRepository.findById(id);

		if (Objects.isNull(response)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
							MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.DEPARTMENT.title(), id)));
		}

		ResponseEntity<Object> validationResponse = Validator.isValidDepartmentPayload(departmentPayload);
		if (validationResponse != null) {
			return validationResponse;
		}

		Department dept = new Department();
		dept.setId(id);
		dept.setDepartmentName(departmentPayload.getDepartmentName());
		dept.setCreatedAt(departmentPayload.getCreatedAt());
		dept.setUpdatedAt(LocalDateTime.now().toString());
		return ResponseEntity.ok(departmentRepository.save(dept));

	}

	// DeleteAdminProcess
	@Transactional
	public ResponseEntity<Object> deleteDepartment(String id) {

		Department response = departmentRepository.findById(id);
		if (Objects.isNull(response)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
							MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.ADMIN.title(), id)));
		}
		response.setDeleted(true);
		departmentRepository.save(response);
		return ResponseEntity.ok(new ResponseDetails(ApplicationResponseCode.SUCCESS,
				MessageFormat.format(Constants.RESOURCE_DELETED, EntityTypes.DEPARTMENT.title(), id)));

	}

	// BulkDeleteAdminProcess
	@Transactional
	public ResponseEntity<Object> bulkDelete(List<String> ids) {
		List<Department> response = departmentRepository.getDepartmentsByIds(ids);
		for (Department dept : response) {
			dept.setDeleted(true);
			departmentRepository.save(dept);

		}
		return ResponseEntity.ok(new ResponseDetails(ApplicationResponseCode.SUCCESS,
				MessageFormat.format(Constants.RESOURCES_DELETED, EntityTypes.DEPARTMENT.title())));
	}
	

}
