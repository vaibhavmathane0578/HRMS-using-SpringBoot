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

import com.hr.manage.payload.EmployeePayload;
import com.hr.manage.payload.LoginPayload;
import com.hr.manage.payload.OtpVerificationPayload;
import com.hr.manage.service.EmployeeService;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@PostMapping("/create")
	public ResponseEntity<Object> createEmployee(@RequestBody EmployeePayload employeePayload){
		return employeeService.createEmployee(employeePayload);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getEmployee(@PathVariable String id){
		return employeeService.getEmployee(id);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Object> updateEmployee(@RequestBody EmployeePayload employeePayload){
		return employeeService.updateEmployee(employeePayload);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable String id) {
		return employeeService.deleteEmployee(id);
	}
	
	@DeleteMapping("/bulk-delete")
	public ResponseEntity<Object> bulkDelete(@RequestBody List<String> ids) {
		return employeeService.bulkDelete(ids);
	}
	
	@PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginPayload loginPayload) {
        return employeeService.authenticateUser(loginPayload);
    }
	@PostMapping("/verify-otp")
	public ResponseEntity<Object> verifyOtp(@RequestBody OtpVerificationPayload otpVerificationPayload) {
	    return employeeService.verifyOtp(otpVerificationPayload);
	}
}
