package com.hr.manage.payload;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PayrollPayload {
	private String id;
	private String employee;
	private BigDecimal basicSalary;
    private BigDecimal bonus;
    private BigDecimal deductions;
    private BigDecimal otherAllowances;
    private BigDecimal leaveDeduction;
    private BigDecimal grossSalary;
    private BigDecimal netSalary;
    private String month;
    private int year;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
	public PayrollPayload() {
		super();
	}
	public PayrollPayload(String id, String employee, BigDecimal basicSalary, BigDecimal bonus, BigDecimal deductions,
			BigDecimal otherAllowances, BigDecimal leaveDeduction, BigDecimal grossSalary, BigDecimal netSalary,
			String month, int year, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.employee = employee;
		this.basicSalary = basicSalary;
		this.bonus = bonus;
		this.deductions = deductions;
		this.otherAllowances = otherAllowances;
		this.leaveDeduction = leaveDeduction;
		this.grossSalary = grossSalary;
		this.netSalary = netSalary;
		this.month = month;
		this.year = year;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmployee() {
		return employee;
	}
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	public BigDecimal getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}
	public BigDecimal getBonus() {
		return bonus;
	}
	public void setBonus(BigDecimal bonus) {
		this.bonus = bonus;
	}
	public BigDecimal getDeductions() {
		return deductions;
	}
	public void setDeductions(BigDecimal deductions) {
		this.deductions = deductions;
	}
	public BigDecimal getOtherAllowances() {
		return otherAllowances;
	}
	public void setOtherAllowances(BigDecimal otherAllowances) {
		this.otherAllowances = otherAllowances;
	}
	public BigDecimal getLeaveDeduction() {
		return leaveDeduction;
	}
	public void setLeaveDeduction(BigDecimal leaveDeduction) {
		this.leaveDeduction = leaveDeduction;
	}
	public BigDecimal getGrossSalary() {
		return grossSalary;
	}
	public void setGrossSalary(BigDecimal grossSalary) {
		this.grossSalary = grossSalary;
	}
	public BigDecimal getNetSalary() {
		return netSalary;
	}
	public void setNetSalary(BigDecimal netSalary) {
		this.netSalary = netSalary;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
 
    
    
    
}