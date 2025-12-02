package com.woofwoof.bookingservice.model;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long amount;        
    private String currency;    
    private String description; 
}
