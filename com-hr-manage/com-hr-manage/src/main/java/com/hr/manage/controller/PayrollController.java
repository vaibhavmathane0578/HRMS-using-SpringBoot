//package com.hr.manage.controller;
//
//import com.hr.manage.entity.Payroll;
//import com.hr.manage.service.PayrollService;
//import com.stripe.exception.StripeException;
//import com.stripe.model.PaymentIntent;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.Collections;
//
//
//@RestController
//@RequestMapping("/api/payroll")
//public class PayrollController {
//
//    @Autowired
//    private PayrollService payrollService;
//
//    @PostMapping("/create-payment")
//    public ResponseEntity<Object> createPaymentIntent(@RequestBody Payroll payroll) {
//        try {
//            PaymentIntent paymentIntent = payrollService.createPaymentIntent(payroll);
//            return ResponseEntity.ok(paymentIntent);
//        } catch (StripeException e) {
//            return ResponseEntity.status(500).body("Error creating payment intent: " + e.getMessage());
//        }
//    }
//    
//    // Endpoint to initiate Stripe Checkout
//    @PostMapping("/checkout")
//    public ResponseEntity<Object> initiateStripeCheckout(@RequestParam Payroll payroll) {
//        try {
//            String sessionId = payrollService.initiateStripeCheckout(payroll);
//            return ResponseEntity.ok(Collections.singletonMap("sessionId", sessionId));
//        } catch (StripeException e) {
//            return ResponseEntity.status(500).body("Error initiating Stripe Checkout: " + e.getMessage());
//        }
//    }
//
//    // Endpoint to handle payment confirmation
//    @PostMapping("/confirm")
//    public ResponseEntity<Object> confirmPayment(@RequestParam String paymentIntentId) {
//        // Add logic to confirm payment and update payroll record
//        payrollService.updatePayrollRecordAfterPaymentConfirmation(paymentIntentId);
//
//        return ResponseEntity.ok("Payment confirmed successfully.");
//    }
//
//    @GetMapping("/get-payment-details")
//    public ResponseEntity<Object> getPaymentDetails(@RequestParam String paymentIntentId) {
//        try {
//            PaymentIntent paymentIntent = payrollService.getPaymentDetails(paymentIntentId);
//            return ResponseEntity.ok(paymentIntent);
//        } catch (StripeException e) {
//            return ResponseEntity.status(500).body("Error getting payment details: " + e.getMessage());
//        }
//    }
//}
