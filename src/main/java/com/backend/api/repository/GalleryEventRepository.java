package com.backend.api.repository;

import com.backend.api.model.GalleryEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryEventRepository extends JpaRepository<GalleryEvent, String> {
}
