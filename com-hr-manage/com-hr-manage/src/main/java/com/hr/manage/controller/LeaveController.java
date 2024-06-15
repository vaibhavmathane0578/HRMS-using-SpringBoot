package com.hr.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.manage.payload.LeavePayload;
import com.hr.manage.service.LeaveService;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    // Endpoint to apply for leave
    @PostMapping("/apply")
    public ResponseEntity<Object> applyForLeave(@RequestBody LeavePayload leavePayload) {
        // Perform validation if needed
        return leaveService.applyForLeave(leavePayload);
    }

    // Endpoint to get leave details by leaveId
    @GetMapping("/{leaveId}")
    public ResponseEntity<Object> getLeaveDetails(@PathVariable String leaveId) {
        return leaveService.getLeaveByLeaveId(leaveId);
    }

    // Endpoint to approve leave
    @PostMapping("/approve/{leaveId}")
    public ResponseEntity<Object> approveLeave(@PathVariable String leaveId) {
        return leaveService.approveLeave(leaveId);
    }

    // Endpoint to reject leave
    @PostMapping("/reject/{leaveId}")
    public ResponseEntity<Object> rejectLeave(@PathVariable String leaveId) {
        return leaveService.rejectLeave(leaveId);
    }

    // Endpoint to delete leave by leaveId
    @DeleteMapping("/delete/{leaveId}")
    public ResponseEntity<Object> deleteLeave(@PathVariable String leaveId) {
        return leaveService.deleteLeave(leaveId);
    }
    
    @DeleteMapping("/bulkDelete")
    public ResponseEntity<Object> bulkDeleteLeaves(@RequestBody List<String> leaveIds) {
        return leaveService.bulkDeleteLeaves(leaveIds);
    }
}

