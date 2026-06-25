package com.backend.api.controller;

import com.backend.api.model.ServiceEntity;
import com.backend.api.repository.ServiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceRepository serviceRepository;

    public ServiceController(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping
    public ResponseEntity<List<ServiceEntity>> getAllServices() {
        return ResponseEntity.ok(serviceRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceEntity> getServiceById(@PathVariable String id) {
        return serviceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ServiceEntity> createService(@RequestBody ServiceEntity service) {
        if (service.getId() == null || service.getId().trim().isEmpty()) {
            // Generate id from title or random UUID
            String id = service.getTitle().toLowerCase().replaceAll("[^a-z0-9]+", "-");
            if (id.endsWith("-")) {
                id = id.substring(0, id.length() - 1);
            }
            if (id.isEmpty()) {
                id = UUID.randomUUID().toString();
            }
            service.setId(id);
        }
        ServiceEntity saved = serviceRepository.save(service);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceEntity> updateService(@PathVariable String id, @RequestBody ServiceEntity serviceDetails) {
        return serviceRepository.findById(id)
                .map(service -> {
                    service.setNum(serviceDetails.getNum());
                    service.setTitle(serviceDetails.getTitle());
                    service.setTagline(serviceDetails.getTagline());
                    service.setDescription(serviceDetails.getDescription());
                    service.setIcon(serviceDetails.getIcon());
                    service.setBanner(serviceDetails.getBanner());
                    ServiceEntity updated = serviceRepository.save(service);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable String id) {
        return serviceRepository.findById(id)
                .map(service -> {
                    serviceRepository.delete(service);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
