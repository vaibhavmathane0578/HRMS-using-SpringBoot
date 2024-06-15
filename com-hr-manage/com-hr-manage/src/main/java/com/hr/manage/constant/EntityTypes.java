package com.hr.manage.constant;

public enum EntityTypes implements BaseEnum {

	ADMIN("Admin", "admn-"),EMPLOYEE("Employee", "empl-"), DEPARTMENT("Department","dept-"), LEAVE("Leave","leav");

	private final String title;
	private final String idPrefix;

	private EntityTypes(String title, String idPrefix) {
		this.title = title;
		this.idPrefix = idPrefix;
	}

	@Override
	public String title() {
		return this.title;
	}

	public String idPrefix() {
		return this.idPrefix;
	}

}
