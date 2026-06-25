package com.backend.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "gallery_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryEvent {

    @Id
    private String id; // e.g. "royal-crimson-wedding"

    private String name;
    private String date;
    private String category;
    private String categoryLabel;
    private String location;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "full_description", columnDefinition = "TEXT")
    private String fullDescription;

    @Column(name = "banner", columnDefinition = "TEXT")
    private String banner;
    private String videoUrl;
    private String videoType; // "mp4" or "youtube"

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "gallery_event_highlights", joinColumns = @JoinColumn(name = "gallery_event_id"))
    @Column(name = "highlight", columnDefinition = "TEXT")
    private List<String> highlights;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "gallery_event_photos", joinColumns = @JoinColumn(name = "gallery_event_id"))
    @Column(name = "photo_url", columnDefinition = "TEXT")
    private List<String> photos;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "gallery_event_bts_photos", joinColumns = @JoinColumn(name = "gallery_event_id"))
    @Column(name = "bts_photo_url", columnDefinition = "TEXT")
    private List<String> btsPhotos;
}
