package com.backend.api.repository;

import com.backend.api.model.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TestimonialRepository extends JpaRepository<Testimonial, String> {
    List<Testimonial> findByStatusOrderBySubmittedAtDesc(String status);
    List<Testimonial> findAllByOrderBySubmittedAtDesc();
}
