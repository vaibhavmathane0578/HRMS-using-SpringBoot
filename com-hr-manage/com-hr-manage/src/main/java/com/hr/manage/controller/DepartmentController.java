package com.hr.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.manage.payload.DepartmentPayload;
import com.hr.manage.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
	@Autowired
    private DepartmentService departmentService;
	
	@PostMapping("/create")
	public ResponseEntity<Object> createAdmin(@RequestBody DepartmentPayload departmentPayload){
		return departmentService.createDepartment(departmentPayload);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getDepartment(@PathVariable String id){
		return departmentService.getDepartment(id);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Object> updateDepartment(@RequestBody DepartmentPayload departmentPayload){
		return departmentService.updateDepartment(departmentPayload);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteAdmin(@PathVariable String id) {
		return departmentService.deleteDepartment(id);
	}
	
	@DeleteMapping("/bulk-delete")
	public ResponseEntity<Object> bulkDelete(@RequestBody List<String> ids) {
		return departmentService.bulkDelete(ids);
	}
}
