package com.hr.manage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payroll")
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "basic_salary")
    private BigDecimal basicSalary;

    @Column(name = "bonus")
    private BigDecimal bonus;

    @Column(name = "deductions")
    private BigDecimal deductions;

    @Column(name = "other_allowances")
    private BigDecimal otherAllowances;

    @Column(name = "leave_deduction")
    private BigDecimal leaveDeduction;

    @Column(name = "gross_salary")
    private BigDecimal grossSalary;

    @Column(name = "net_salary")
    private BigDecimal netSalary;
    
    @Column(name = "month")
    private String month;

    @Column(name = "year")
    private int year;
    

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    

    public Payroll() {
		super();
		this.basicSalary = new BigDecimal("420000.00");
		this.bonus = BigDecimal.ZERO;
        this.deductions = BigDecimal.ZERO;
        this.otherAllowances = new BigDecimal("10000.00"); // Fixed allowance of 10,000
        this.leaveDeduction = BigDecimal.ZERO;
	}
    


		


	public Payroll(String id, Employee employee, BigDecimal basicSalary, BigDecimal bonus, BigDecimal deductions,
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






	// Constructors, getters, setters, and other annotations...

    public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee employee) {
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

	
	
	@PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        calculateSalary();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        calculateSalary();
    }

    private void calculateSalary() {
        // Perform salary calculation logic based on predefined fields
        this.grossSalary = basicSalary.add(bonus).subtract(deductions).add(otherAllowances);
        this.netSalary = grossSalary.subtract(leaveDeduction);
    }
}
