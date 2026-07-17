package com.backend.api.config;

import com.backend.api.model.GalleryEvent;
import com.backend.api.model.ServiceEntity;
import com.backend.api.model.User;
import com.backend.api.repository.GalleryEventRepository;
import com.backend.api.repository.ServiceRepository;
import com.backend.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Seed admin user
        String adminUsername = "admin@vsicreations.com";
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            User admin = User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode("AdminPassword123"))
                    .role("ROLE_ADMIN")
                    .build();
            userRepository.save(admin);
            System.out.println("Default admin user seeded: admin@vsicreations.com / AdminPassword123");
        }

    }

}