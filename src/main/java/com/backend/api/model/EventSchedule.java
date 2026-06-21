package com.backend.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "event_schedules")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String time; // e.g. '14:00'

    private String title; // e.g. 'Calibration & Acoustic Mapping'

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @ToString.Exclude
    private Event event;
}
