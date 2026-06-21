package com.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String city;
    private Integer age;
    private Integer participants;
    private String notes;
    private String eventId;
    private String eventName;
    private String transactionId;
    private String paymentStatus;
    private String submittedAt;
}
