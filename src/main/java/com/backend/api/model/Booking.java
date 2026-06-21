package com.backend.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    private String city;

    private Integer age;

    private Integer participants;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "event_id", nullable = false)
    private String eventId;

    @Column(name = "event_name", nullable = false)
    private String eventName;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "submitted_at")
    private String submittedAt; // ISO String timestamp
}
