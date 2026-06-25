package com.backend.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceEntity {

    @Id
    private String id; // e.g. "event-management"

    private String num; // e.g. "01"
    private String title;
    private String tagline;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    private String icon; // Lucide icon identifier string
    @Column(name = "banner", columnDefinition = "TEXT")
    private String banner; // Banner image URL or Base64 string
}
