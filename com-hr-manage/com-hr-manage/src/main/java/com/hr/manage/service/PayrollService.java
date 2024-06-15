//package com.hr.manage.service;
//
//import com.stripe.Stripe;
//import com.stripe.exception.StripeException;
//import com.stripe.model.PaymentIntent;
//import com.stripe.model.checkout.Session;
//import com.stripe.param.checkout.SessionCreateParams;
//import com.stripe.param.checkout.SessionCreateParams.PaymentMethodOptions;
//import com.hr.manage.entity.Admin;
//import com.hr.manage.entity.CalculatedSalary;
//import com.hr.manage.entity.Payroll;
//import com.hr.manage.payload.PayrollPayload;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class PayrollService {
//
//    @Value("${stripe.secret.key}")
//    private String stripeSecretKey; // Add your Stripe secret key here
//
//    public PaymentIntent createPaymentIntent(CalculatedSalary calculatedSalary) throws StripeException {
//        Stripe.apiKey = stripeSecretKey;
//
//        // Use netsalary as the payment amount
//        long salaryAmountInCents = calculatedSalary.getNetSalary().multiply(new BigDecimal("100")).longValue();
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("amount", salaryAmountInCents);
//        params.put("currency", "inr");
//
//        return PaymentIntent.create(params);
//    }
//
//    public String initiateStripeCheckout(CalculatedSalary calculatedSalary) throws StripeException {
//        Stripe.apiKey = stripeSecretKey;
//
//        Map<String, Object> item = new HashMap<>();
//        item.put("price", "price_id"); // Replace with your actual price ID
//
//        Map<String, Object> lineItem = new HashMap<>();
//        lineItem.put("quantity", 1); // Assuming one quantity for simplicity
//        lineItem.put("price_data", item);
//
//        Map<String, Object> sessionParams = new HashMap<>();
//        sessionParams.put("payment_method_types", Collections.singletonList("card"));
//        sessionParams.put("line_items", Collections.singletonList(lineItem));
//
//        Session session = Session.create(sessionParams);
//        return session.getId();
//    }
//
//
//    public void updatePayrollRecordAfterPaymentConfirmation(String paymentIntentId) {
//        // Add your logic to update the payroll record after confirming the payment
//        System.out.println("Payment confirmed. Update payroll record with payment intent ID: " + paymentIntentId);
//    }
//
//    public PaymentIntent getPaymentDetails(String paymentIntentId) throws StripeException {
//        // Set your Stripe secret key
//        Stripe.apiKey = stripeSecretKey;
//
//        // Retrieve payment intent details
//        return PaymentIntent.retrieve(paymentIntentId);
//    }
//
//    @SuppressWarnings("unused")
//	private void updatePayrollFields(PayrollPayload payrollPayload) {
//    	
//    	
//    	
//    	
//    	
//    	String id = payrollPayload.getId();
//    	Payroll payroll = new Payroll();
//    	payroll.setId(id);
//    	payroll.setBasicSalary(payrollPayload.getBasicSalary());
//    	payroll.setBonus(payrollPayload.getBonus());
//    	payroll.setDeductions(payrollPayload.getDeductions());
//    	payroll.setOtherAllowances(payrollPayload.getOtherAllowances());
//		payroll.setLeaveDeduction(payrollPayload.getLeaveDeduction());
//		payroll.setLeaveDeduction(payrollPayload.getLeaveDeduction());
//		payroll.setLeaveDeduction(payrollPayload.getLeaveDeduction());
//		payroll.setLeaveDeduction(payrollPayload.getLeaveDeduction());
//		payroll.setLeaveDeduction(payrollPayload.getLeaveDeduction());
//		payroll.setCreatedAt(LocalDateTime.now().toString());
//		payroll.setUpdatedAt(LocalDateTime.now().toString());
//		return ResponseEntity.ok(payrollRepository.save(admin));
//        calculatedSalary(payroll);
//    }
//
//    private void calculatedSalary(Payroll payroll) {
//        // Perform salary calculation logic based on predefined fields
//        BigDecimal grossSalary = payroll.getBasicSalary().add(payroll.getBonus())
//                .subtract(payroll.getDeductions()).add(payroll.getOtherAllowances());
//        BigDecimal netSalary = grossSalary.subtract(payroll.getLeaveDeduction());
//
//        // Set the calculated values
//        payroll.setGrossSalary(grossSalary);
//        payroll.setNetSalary(netSalary);
//    }
//
//    @SuppressWarnings("unused")
//	private LocalDateTime getCurrentDateTime() {
//        // This method returns the current date and time.
//        // Implement it based on your application's requirements.
//        return LocalDateTime.now();
//    }
//}