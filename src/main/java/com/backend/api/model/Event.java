package com.backend.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    private String id; // string IDs matching frontend keys (e.g., 'neon-beats')

    @Column(nullable = false)
    private String title;

    private String date; // YYYY-MM-DD format

    private String location;

    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String fullDescription;

    private String banner;

    private String videoThumbnail;

    private String videoUrl;

    private String venueDetails;

    private String participationInfo;

    private Double price;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<EventSchedule> schedule = new ArrayList<>();
}

//finaliily working
