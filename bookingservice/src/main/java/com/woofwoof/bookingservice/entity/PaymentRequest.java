package com.woofwoof.bookingservice.entity;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long amount;        
    private String currency;    
    private String description; 
}
