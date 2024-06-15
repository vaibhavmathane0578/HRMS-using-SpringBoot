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
import com.hr.manage.entity.Employee;
import com.hr.manage.entity.Leave;
import com.hr.manage.payload.LeavePayload;
import com.hr.manage.payload.ResponseDetails;
import com.hr.manage.repository.EmployeeRepository;
import com.hr.manage.repository.LeaveRepository;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Leave application process
    @Transactional
    public ResponseEntity<Object> applyForLeave(LeavePayload leavePayload) {
        try {
            // Retrieve employee details by employeeId
            Employee employee = employeeRepository.findById(leavePayload.getEmployeeId());
            if (Objects.isNull(employee)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
                                MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.EMPLOYEE.title(),
                                        leavePayload.getEmployeeId())));
            }

            // Create a new leave instance
            Leave leave = new Leave();
            leave.setLeaveId(EntityTypes.LEAVE.idPrefix().concat(UUID.randomUUID().toString()));
            leave.setEmployee(employee);
            leave.setStartDate(leavePayload.getStartDate());
            leave.setEndDate(leavePayload.getEndDate());
            leave.setLeaveType(leavePayload.getLeaveType());
            leave.setStatus(Constants.LEAVE_STATUS_PENDING); // Initial status is set to Pending
            leave.setCreatedAt(LocalDateTime.now().toString());

            // Save the leave application
            leaveRepository.save(leave);

            return ResponseEntity.ok(new ResponseDetails(ApplicationResponseCode.SUCCESS,
                    MessageFormat.format(Constants.LEAVE_APPLIED_SUCCESSFULLY, leave.getLeaveId())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDetails(ApplicationResponseCode.INTERNAL_SERVER_ERROR,
                            Constants.INTERNAL_SERVER_ERROR));
        }
    }

    // Retrieve leave details by leaveId
    public ResponseEntity<Object> getLeaveByLeaveId(String leaveId) {
        Leave leave = leaveRepository.getByLeaveId(leaveId);
        if (Objects.nonNull(leave)) {
            return ResponseEntity.ok(leave);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
                        MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.LEAVE.title(), leaveId)));
    }

    // Retrieve leave details by employeeId
    public ResponseEntity<Object> getLeaveByEmployeeId(String employeeId) {
        Leave leave = leaveRepository.findByEmployeeId(employeeId);
        if (Objects.nonNull(leave)) {
            return ResponseEntity.ok(leave);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
                        MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.LEAVE.title(), employeeId)));
    }

    // Update leave status to Approved
    @Transactional
    public ResponseEntity<Object> approveLeave(String leaveId) {
        return updateLeaveStatus(leaveId, Constants.LEAVE_STATUS_APPROVED);
    }

    // Update leave status to Rejected
    @Transactional
    public ResponseEntity<Object> rejectLeave(String leaveId) {
        return updateLeaveStatus(leaveId, Constants.LEAVE_STATUS_REJECTED);
    }

    // Internal method to update leave status
    private ResponseEntity<Object> updateLeaveStatus(String leaveId, String status) {
        Leave leave = leaveRepository.getByLeaveId(leaveId);
        if (Objects.isNull(leave)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
                            MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.LEAVE.title(), leaveId)));
        }

        // Update leave status
        leave.setStatus(status);
        leave.setUpdatedAt(LocalDateTime.now().toString());
        leaveRepository.save(leave);

        return ResponseEntity.ok(new ResponseDetails(ApplicationResponseCode.SUCCESS,
                MessageFormat.format(Constants.LEAVE_STATUS_UPDATED, leave.getLeaveId(), status)));
    }

    // Delete leave by leaveId
    @Transactional
    public ResponseEntity<Object> deleteLeave(String leaveId) {
        Leave leave = leaveRepository.getByLeaveId(leaveId);
        if (Objects.isNull(leave)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDetails(ApplicationResponseCode.RESOURCE_NOT_FOUND,
                            MessageFormat.format(Constants.RESOURCE_NOT_FOUND, EntityTypes.LEAVE.title(), leaveId)));
        }

        leave.setDeleted(true);
        leaveRepository.save(leave);
        return ResponseEntity.ok(new ResponseDetails(ApplicationResponseCode.SUCCESS,
                MessageFormat.format(Constants.RESOURCE_DELETED, EntityTypes.LEAVE.title(), leaveId)));
    }

    // Bulk delete leaves by leaveIds
    @Transactional
    public ResponseEntity<Object> bulkDeleteLeaves(List<String> leaveIds) {
        List<Leave> leaves = leaveRepository.getLeaveByIds(leaveIds);
        for (Leave leave : leaves) {
        	leave.setDeleted(true);
            leaveRepository.save(leave);
        }

        return ResponseEntity.ok(new ResponseDetails(ApplicationResponseCode.SUCCESS,
                MessageFormat.format(Constants.RESOURCES_DELETED, EntityTypes.LEAVE.title())));
    }

    
}
