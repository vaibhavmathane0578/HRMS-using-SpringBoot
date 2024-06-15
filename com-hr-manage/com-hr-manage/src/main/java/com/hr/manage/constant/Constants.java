package com.hr.manage.constant;

public class Constants {
	
	public static final String RESOURCE_ALREADY_EXISTS = "{0} with {1} ''{2}'' already exists. ";
	public static final String EMAIL ="email";
	
	public static final String FIRSTNAME_INVALID = "First Name is invalid. ";
	public static final String LASTNAME_INVALID = "Last Name is invalid. ";
	public static final String CONTACT_NUMBER_INVALID = "Contact Number must be of 10 digits. ";
	public static final String EMAIL_INVALID = "Email is invalid. ";
	public static final String PASSWORD_INVALID = "Password is invalid. Password must be of more than 8 digit and contain a minimum of 1 lower case letter ,1 upper case letter ,1 numeric character,1 special character. ";
	public static final String RESOURCE_NOT_FOUND= "{0} with id ''{1}'' not found in the system";
	public static final String ADMIN_ID_REGEX= "^admn-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
	public static final String ADMIN_ID_PREFIX="admn-";
	public static final String RESOURCE_DELETED="{0} with id ''{1}'' deleted.";
	public static final String RESOURCES_DELETED = "{0}(s) deleted successfully.";
	public static final String EMPLOYEE_ID_PREFIX = "^empl-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
	public static final String GENDER_INVALID = "Gender input invalid.";
	public static final String ADDRESS_INVALID = "Invalid Address.";
	public static final String HIGHEST_QUALIFICATION_INVALID = "Invalid Qualification";
	public static final String DESIGNATION_INVALID = "Invalid Designation";
	public static final String PASSWORD_INVALID_LENGTH = "Password must be at least 8 characters long.";
	public static final String PASSWORD_MISSING_NUM = "Password must contain at least one digit.";
	public static final String PASSWORD_MISSING_SPCHAR = "Password must contain at least one special character.";
	public static final String PASSWORD_MISSING_UPCHAR = "Password must contain at least one uppercase letter.";
	public static final String PASSWORD_MISSING_LOWCHAR = "Password must contain at least one lowercase letter.";
	public static final String VALID_CREDENTIALS = "Credentials are valid.";
	public static final String INVALID_CREDENTIALS = "Credentials are invalid.";
	public static final Object DEPTARTMENT = "department";
	public static final Object LEAVE = "leave";
	public static final String OTP_VERIFIED = "OTP verified.";
	public static final String INVALID_OTP = "Invalid OTP.";
	public static final String LEAVE_STATUS_PENDING = "Leave status Pending...";
	public static final String LEAVE_APPLIED_SUCCESSFULLY = "Leave Applied Successfully.";
	public static final String INTERNAL_SERVER_ERROR = "Internal server error. Retry again.";
	public static final String LEAVE_STATUS_APPROVED = "Leave Approved.";
	public static final String LEAVE_STATUS_REJECTED = "Leave is not approved.";
	public static final String LEAVE_STATUS_UPDATED = "Leave status updated successfully.";
	
	
}
