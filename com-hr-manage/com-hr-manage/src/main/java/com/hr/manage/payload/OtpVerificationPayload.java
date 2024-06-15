package com.hr.manage.payload;

public class OtpVerificationPayload {
    private String officialEmail;
    private String enteredOtp;

    public OtpVerificationPayload(String officialEmail, String enteredOtp) {
        this.officialEmail = officialEmail;
        this.enteredOtp = enteredOtp;
    }

    public String getOfficialEmail() {
        return officialEmail;
    }

    public void setOfficialEmail(String officialEmail) {
        this.officialEmail = officialEmail;
    }

    public String getEnteredOtp() {
        return enteredOtp;
    }

    public void setEnteredOtp(String enteredOtp) {
        this.enteredOtp = enteredOtp;
    }
}
