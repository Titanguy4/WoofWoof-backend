package com.woofwoof.bookingservice.controller;

import com.stripe.exception.StripeException;
import com.woofwoof.bookingservice.model.PaymentRequest;
import com.woofwoof.bookingservice.model.PaymentResponse;
import com.woofwoof.bookingservice.service.PaymentService;
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
}
