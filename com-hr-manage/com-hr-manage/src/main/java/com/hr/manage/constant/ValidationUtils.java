package com.hr.manage.constant;

import com.hr.manage.payload.ResponseDetails;

public class ValidationUtils {

	public static final boolean isInvalidName(String name) {
		return name == null || name.trim().isEmpty() || !name.matches("^[a-zA-Z\\s]*$");
	}

	public static final boolean isInvalidContactNumber(String contactNumber) {
		return contactNumber == null || !contactNumber.matches("\\d{10}");
	}

	public static final boolean isInvalidEmail(String email) {
		return email == null || !email.matches("^(?!.*([.]{2}))[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
				|| email.length() < 15;
	}

	public static final ResponseDetails isValidPassword(String password) {
		if (password == null || password.length() < 8) {
			return new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.PASSWORD_INVALID_LENGTH);
		} else if (!password.matches(".*[0-9].*")) {
			return new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.PASSWORD_MISSING_NUM);
		} else if (!password.matches(".*[!@#$%^&*()].*")) {
			return new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.PASSWORD_MISSING_SPCHAR);
		} else if (!password.matches(".*[A-Z].*")) {
			return new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION, Constants.PASSWORD_MISSING_UPCHAR);
		} else if (!password.matches(".*[a-z].*")) {
			return new ResponseDetails(ApplicationResponseCode.CONSTRAINT_VIOLATION,
					Constants.PASSWORD_MISSING_LOWCHAR);
		}
		return null;
	}

	public static final boolean isInvalidDesignation(String designation) {
		// TODO Auto-generated method stub
		return false;
	}

	public static final boolean isInvalidHighestQualification(String highestQualification) {
		// TODO Auto-generated method stub
		return false;
	}

	public static final boolean isInvalidAddress(String permanentAddress) {
		// TODO Auto-generated method stub
		return false;
	}

	@SuppressWarnings("null")
	public static final boolean isInvalidGender(String gender) {
		if (gender == null) {
			String lowerCaseGender = gender.toLowerCase();
			return ("male".equals(lowerCaseGender) || "female".equals(lowerCaseGender)
					|| "other".equals(lowerCaseGender));
		}

		return false;
	}

}
