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
import com.hr.manage.payload.AdminPayload;
import com.hr.manage.payload.LoginPayload;
import com.hr.manage.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@PostMapping("/create")
	public ResponseEntity<Object> createAdmin(@RequestBody AdminPayload adminPayload){
		return adminService.createAdmin(adminPayload);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getAdmin(@PathVariable String id){
		return adminService.getAdmin(id);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Object> updateAdmin(@RequestBody AdminPayload adminPayload){
		return adminService.updateAdmin(adminPayload);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteAdmin(@PathVariable String id) {
		return adminService.deleteAdmin(id);
	}
	
	@DeleteMapping("/bulk-delete")
	public ResponseEntity<Object> bulkDelete(@RequestBody List<String> ids) {
		return adminService.bulkDelete(ids);
	}
	
	@PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginPayload loginPayload) {
        return adminService.authenticateUser(loginPayload);
    }
	
}

