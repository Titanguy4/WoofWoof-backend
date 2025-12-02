package com.woofwoof.bookingservice.controller;

import com.stripe.exception.StripeException;
import com.woofwoof.bookingservice.entity.PaymentRequest;
import com.woofwoof.bookingservice.entity.PaymentResponse;
import com.woofwoof.bookingservice.service.PaymentService;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public PaymentResponse createPayment(@RequestBody PaymentRequest request) throws StripeException {
        String clientSecret = paymentService.createPaymentIntent(
                request.getAmount(),
                request.getCurrency(),
                request.getDescription()
        );

        return new PaymentResponse(clientSecret);
    }

    @PostMapping("/checkout/session")
    public ResponseEntity<String> createCheckoutSession(@RequestBody PaymentRequest request) throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("woofwoof://paymentsuccess?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("woofwoof://paymentcancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency(request.getCurrency())
                                .setUnitAmount(request.getAmount())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(request.getDescription())
                                        .build())
                                .build())
                        .build())
                .build();
        Session session = Session.create(params);
        return ResponseEntity.ok(session.getUrl());
    }

    @GetMapping("/verify-session")
    public ResponseEntity<Map<String, Object>> verifySession(
            @RequestParam("session_id") String sessionId
    ) throws StripeException {

        Session session = Session.retrieve(sessionId);

        boolean paid = "paid".equals(session.getPaymentStatus());

        Map<String, Object> response = new HashMap<>();
        response.put("paid", paid);

        return ResponseEntity.ok(response);
    }

}
