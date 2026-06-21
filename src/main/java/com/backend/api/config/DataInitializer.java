package com.backend.api.config;

import com.backend.api.model.Event;
import com.backend.api.model.EventSchedule;
import com.backend.api.model.User;
import com.backend.api.repository.EventRepository;
import com.backend.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           EventRepository eventRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
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

        // 2. Seed events if database is empty
        if (eventRepository.count() == 0) {
            Event event1 = Event.builder()
                    .id("neon-beats")
                    .title("Neon Beats Arena Music Festival")
                    .date("2026-07-24")
                    .location("Central Arena Metropolis")
                    .category("concert")
                    .description("Grand central arena structural sound coordination and live multi-axis lasers.")
                    .fullDescription("Experience the absolute pinnacle of high-intensity electronic staging. VSI Creations coordinates the full physical engineering setup, combining high-load aluminum structures, state-of-the-art linear acoustic systems, and high-frequency laser diode matrices synchronised dynamically to audio timelines. Perfect for audiophiles and luxury concert attendees alike.")
                    .banner("https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?w=1000")
                    .videoThumbnail("https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=600")
                    .videoUrl("https://www.w3schools.com/html/mov_bbb.mp4")
                    .venueDetails("Central Arena Gate 4, Metropolis. Multi-level parking and VIP dropoff gates available.")
                    .participationInfo("Open to ages 18+. VIP pass includes access to stage-front lounges and acoustic control booths.")
                    .price(49.99)
                    .build();

            List<EventSchedule> schedule1 = new ArrayList<>();
            schedule1.add(EventSchedule.builder().time("14:00").title("Line-Array Calibration & Acoustic Mapping").event(event1).build());
            schedule1.add(EventSchedule.builder().time("17:00").title("Laser Alignment & Pyro Check").event(event1).build());
            schedule1.add(EventSchedule.builder().time("19:00").title("Gates Open & Opening VJ Sets").event(event1).build());
            schedule1.add(EventSchedule.builder().time("21:00").title("Main Arena Electronic Symphony").event(event1).build());
            event1.setSchedule(schedule1);

            eventRepository.save(event1);

            Event event2 = Event.builder()
                    .id("apex-gala")
                    .title("The Apex Annual Corporate Gala Awards")
                    .date("2026-09-12")
                    .location("Grand Ballroom Plaza Hotel")
                    .category("gala")
                    .description("Luxury red-carpet configurations and live multi-camera broadcast capture.")
                    .fullDescription("A premium, high-luxury corporate gathering celebrating outstanding achievements. VSI Creations manages the entire visual blueprint of the ballroom: custom step-and-repeat layouts with photography matte diffusers, live multi-camera broadcast cranes, zero-delay wireless HDMI rigs, and detailed projection mapping highlighting award announcements.")
                    .banner("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=1000")
                    .videoThumbnail("https://images.unsplash.com/photo-1489641493513-ba4ee84ccea9?w=600")
                    .videoUrl("https://www.w3schools.com/html/movie.mp4")
                    .venueDetails("Grand Ballroom, Plaza Hotel, Suite 100. Valet parking provided at the front portico.")
                    .participationInfo("Black tie dress code mandatory. Pre-registration code or corporate invitation token required.")
                    .price(149.99)
                    .build();

            List<EventSchedule> schedule2 = new ArrayList<>();
            schedule2.add(EventSchedule.builder().time("15:00").title("Broadcasting Crane Setup & Lighting Checks").event(event2).build());
            schedule2.add(EventSchedule.builder().time("18:00").title("VIP Reception & Red Carpet Live Stream").event(event2).build());
            schedule2.add(EventSchedule.builder().time("19:30").title("Keynote & Projection Mapping Intro").event(event2).build());
            schedule2.add(EventSchedule.builder().time("21:30").title("Awards Ceremony & Broadcast Archive Save").event(event2).build());
            event2.setSchedule(schedule2);

            eventRepository.save(event2);

            Event event3 = Event.builder()
                    .id("velvet-symphony")
                    .title("Velvet Symphony Milestone Celebration")
                    .date("2026-11-05")
                    .location("Symphony Gardens Chateau")
                    .category("private")
                    .description("Immersive lighting displays, projection mapping, and fine dining staging.")
                    .fullDescription("A bespoke celebration for private milestones. We turn the historic facade of the Chateau into a custom projection canvas, accompanying it with suspended floral designs loaded with warm-ambient lighting fixtures, high-end cinema capture, and detailed multi-axis lasers.")
                    .banner("https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=1000")
                    .videoThumbnail("https://images.unsplash.com/photo-1519741497674-611481863552?w=600")
                    .videoUrl("https://www.w3schools.com/html/mov_bbb.mp4")
                    .venueDetails("Symphony Gardens Chateau, West Wing. Private shuttle transfers leave from Central Station every 30 mins.")
                    .participationInfo("By invite only. Registration requires entering valid invitation IDs in the notes field.")
                    .price(89.99)
                    .build();

            List<EventSchedule> schedule3 = new ArrayList<>();
            schedule3.add(EventSchedule.builder().time("16:00").title("Symphony Gardens Power-On & Atmospheric Fog Setup").event(event3).build());
            schedule3.add(EventSchedule.builder().time("18:00").title("Chateau Exterior Projection Mapping Preview").event(event3).build());
            schedule3.add(EventSchedule.builder().time("19:00").title("Orchestral Welcome & Banquet Seating").event(event3).build());
            schedule3.add(EventSchedule.builder().time("21:30").title("Light Symphony & Firework Finale").event(event3).build());
            event3.setSchedule(schedule3);

            eventRepository.save(event3);

            System.out.println("Default events seeded successfully.");
        }
    }
}