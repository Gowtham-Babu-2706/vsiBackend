package com.backend.api.controller;

import com.backend.api.model.GalleryEvent;
import com.backend.api.repository.GalleryEventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gallery")
public class GalleryController {

    private final GalleryEventRepository galleryEventRepository;

    public GalleryController(GalleryEventRepository galleryEventRepository) {
        this.galleryEventRepository = galleryEventRepository;
    }

    @GetMapping
    public ResponseEntity<List<GalleryEvent>> getAllGalleryEvents() {
        return ResponseEntity.ok(galleryEventRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalleryEvent> getGalleryEventById(@PathVariable String id) {
        return galleryEventRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GalleryEvent> createGalleryEvent(@RequestBody GalleryEvent galleryEvent) {
        if (galleryEvent.getId() == null || galleryEvent.getId().trim().isEmpty()) {
            String id = galleryEvent.getName().toLowerCase().replaceAll("[^a-z0-9]+", "-");
            if (id.endsWith("-")) {
                id = id.substring(0, id.length() - 1);
            }
            if (id.isEmpty()) {
                id = UUID.randomUUID().toString();
            }
            galleryEvent.setId(id);
        }
        
        // Ensure default values for labels matching categories
        if (galleryEvent.getCategoryLabel() == null || galleryEvent.getCategoryLabel().trim().isEmpty()) {
            String label = galleryEvent.getCategory().substring(0, 1).toUpperCase() + galleryEvent.getCategory().substring(1);
            galleryEvent.setCategoryLabel(label.replace("-", " "));
        }

        GalleryEvent saved = galleryEventRepository.save(galleryEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GalleryEvent> updateGalleryEvent(@PathVariable String id, @RequestBody GalleryEvent eventDetails) {
        return galleryEventRepository.findById(id)
                .map(event -> {
                    event.setName(eventDetails.getName());
                    event.setDate(eventDetails.getDate());
                    event.setCategory(eventDetails.getCategory());
                    event.setCategoryLabel(eventDetails.getCategoryLabel());
                    event.setLocation(eventDetails.getLocation());
                    event.setDescription(eventDetails.getDescription());
                    event.setFullDescription(eventDetails.getFullDescription());
                    event.setBanner(eventDetails.getBanner());
                    event.setVideoUrl(eventDetails.getVideoUrl());
                    event.setVideoType(eventDetails.getVideoType());
                    
                    // Clear and set element collections to allow full updates
                    event.getHighlights().clear();
                    event.getHighlights().addAll(eventDetails.getHighlights());
                    
                    event.getPhotos().clear();
                    event.getPhotos().addAll(eventDetails.getPhotos());
                    
                    event.getBtsPhotos().clear();
                    event.getBtsPhotos().addAll(eventDetails.getBtsPhotos());

                    GalleryEvent updated = galleryEventRepository.save(event);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGalleryEvent(@PathVariable String id) {
        return galleryEventRepository.findById(id)
                .map(event -> {
                    galleryEventRepository.delete(event);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
