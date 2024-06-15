package com.hr.manage.constant;

import java.util.Random;

public class OTPGenerator {
	 public static void main(String[] args) {
	        String otp = generateOtp();
	        System.out.println("Generated OTP: " + otp);
	    }

	    public static String generateOtp() {
	        int otpLength = 6; // Change this value if you want a different length for your OTP
	        StringBuilder otp = new StringBuilder();

	        // Use SecureRandom for better randomness, but Random is used here for simplicity
	        Random random = new Random();

	        for (int i = 0; i < otpLength; i++) {
	            otp.append(random.nextInt(10)); // Append a random digit (0-9)
	        }

	        return otp.toString();
	    }
}
