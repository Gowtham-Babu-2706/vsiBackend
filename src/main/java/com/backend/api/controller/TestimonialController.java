package com.backend.api.controller;

import com.backend.api.model.Testimonial;
import com.backend.api.repository.TestimonialRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/testimonials")
public class TestimonialController {

    private final TestimonialRepository testimonialRepository;

    public TestimonialController(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    // PUBLIC: Get only approved testimonials for the public website
    @GetMapping
    public ResponseEntity<List<Testimonial>> getApprovedTestimonials() {
        return ResponseEntity.ok(testimonialRepository.findByStatusOrderBySubmittedAtDesc("APPROVED"));
    }

    // ADMIN: Get all testimonials (pending, approved, rejected)
    @GetMapping("/all")
    public ResponseEntity<List<Testimonial>> getAllTestimonials() {
        return ResponseEntity.ok(testimonialRepository.findAllByOrderBySubmittedAtDesc());
    }

    // PUBLIC: Submit a new testimonial (status defaults to PENDING)
    @PostMapping
    public ResponseEntity<Testimonial> submitTestimonial(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        Integer rating = body.get("rating") != null ? Integer.parseInt(body.get("rating").toString()) : 5;
        String message = (String) body.get("message");

        if (name == null || name.isBlank() || message == null || message.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Testimonial testimonial = Testimonial.builder()
                .name(name.trim())
                .rating(Math.max(1, Math.min(5, rating)))
                .message(message.trim())
                .status("PENDING")
                .submittedAt(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(testimonialRepository.save(testimonial));
    }

    // ADMIN: Approve a testimonial
    @PutMapping("/{id}/approve")
    public ResponseEntity<Testimonial> approveTestimonial(@PathVariable String id) {
        return testimonialRepository.findById(id)
                .map(t -> { t.setStatus("APPROVED"); return ResponseEntity.ok(testimonialRepository.save(t)); })
                .orElse(ResponseEntity.notFound().build());
    }

    // ADMIN: Reject a testimonial
    @PutMapping("/{id}/reject")
    public ResponseEntity<Testimonial> rejectTestimonial(@PathVariable String id) {
        return testimonialRepository.findById(id)
                .map(t -> { t.setStatus("REJECTED"); return ResponseEntity.ok(testimonialRepository.save(t)); })
                .orElse(ResponseEntity.notFound().build());
    }

    // ADMIN: Delete a testimonial
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable String id) {
        if (testimonialRepository.existsById(id)) {
            testimonialRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
