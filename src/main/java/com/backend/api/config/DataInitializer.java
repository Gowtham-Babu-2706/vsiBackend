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
    private final ServiceRepository serviceRepository;
    private final GalleryEventRepository galleryEventRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           ServiceRepository serviceRepository,
                           GalleryEventRepository galleryEventRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
        this.galleryEventRepository = galleryEventRepository;
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

        // 2. Seed services if empty
        if (serviceRepository.count() == 0) {
            seedServices();
            System.out.println("Default VSI services seeded successfully.");
        }

        // 3. Seed gallery events if empty
        if (galleryEventRepository.count() == 0) {
            seedGalleryEvents();
            System.out.println("Default VSI gallery events seeded successfully.");
        }
    }

    private void seedServices() {
        String[][] servicesData = {
            {"event-management", "01", "Event Management", "End-to-end planning, execution, and coordination for premium events.", "We turn your concepts into reality. From high-profile corporate galas to immersive brand launches and massive trade shows, our production team handles scheduling, design, vendor management, and execution flawlessly.", "Briefcase", "https://images.unsplash.com/photo-1511578314322-379afb476865?w=1000"},
            {"college-educational", "02", "College & Educational Events", "Energetic cultural fests, academic symposiums, and college events.", "Empowering student communities and academic institutions with high-production value. We specialize in coordinating multi-day college festivals, tech expos, seminars, and graduation ceremonies.", "GraduationCap", "https://images.unsplash.com/photo-1523050854058-8df90110c9f1?w=1000"},
            {"cultural-programs", "03", "Cultural Programs", "Celebrating art, heritage, and community through grand staging.", "Bringing local and international heritage to life. We orchestrate grand traditional dance performances, music festivals, theater productions, and community cultural celebrations with specialized acoustic and visual design.", "Music", "https://images.unsplash.com/photo-1460661419201-fd4cecdf8a8b?w=1000"},
            {"wedding-planning", "04", "Wedding Planning & Management", "Immersive, luxurious, and custom-tailored wedding celebrations.", "Your dream wedding, seamlessly orchestrated. We offer complete wedding design, custom theme building, guest logistics, vendor coordination, and live event direction to create unforgettable memories.", "Heart", "https://images.unsplash.com/photo-1519741497674-611481863552?w=1000"},
            {"birthday-celebrations", "05", "Birthday & Private Celebrations", "Custom themes, vibrant styling, and intimate event coordination.", "Crafting unforgettable milestones and intimate celebrations. Whether it is a grand milestone birthday, an anniversary, or a private dinner, we design customized experiences with curated styling.", "Cake", "https://images.unsplash.com/photo-1530103862676-de8c9debad1d?w=1000"},
            {"event-logistics", "06", "Event Coordination & Logistics", "Precise vendor alignment, RSVP tracking, and on-ground management.", "The backbone of every successful production. We manage complex crowd management, vendor operations, scheduling, permits, security grids, and physical logistics mapping for events of all scales.", "Truck", "https://images.unsplash.com/photo-1586528116311-ad8dd3c8310d?w=1000"},
            {"media-production", "07", "Media Production", "High-fidelity photography, videography, and commercial film assets.", "Staging high-quality visuals. Our camera units and production crews deliver professional event photography, drone flythroughs, corporate films, promotional videos, and full live coverage in pristine formats.", "Video", "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=1000"},
            {"creative-services", "08", "Creative Services", "Branding, graphic design, and custom motion animation assets.", "Sculpting the visual identity of your events. We craft custom event logos, stage graphics, social media promotions, physical brochures, flyers, and dynamic motion animations to drive engagement.", "Palette", "https://images.unsplash.com/photo-1550745165-9bc0b252726f?w=1000"},
            {"digital-marketing", "09", "Digital Marketing", "Strategic social campaigns, content creation, and event promotion.", "Amplifying event reach and brand presence. We specialize in target audience generation, social media management, organic search optimization, online ticket promotions, and targeted ad campaigns.", "Megaphone", "https://images.unsplash.com/photo-1460925895917-afdab827c52f?w=1000"},
            {"event-production", "10", "Event Production", "Stage design, LED walls, audio systems, and professional lighting.", "Building spectacular environments. We construct heavy-duty stages, deploy massive LED screens, design custom truss setups, align sound systems, and install professional light rigs for maximum sensory impact.", "Layers", "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?w=1000"},
            {"talent-entertainment", "11", "Talent & Entertainment", "Artist bookings, live bands, celebrity scheduling, and performers.", "Elevating events with elite entertainment. We manage direct booking and coordination for celebrities, event hosts, DJs, live acoustic bands, classical dancers, and custom performance acts.", "Users", "https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=1000"},
            {"equipment-rental", "12", "Equipment Rental", "High-end audio, video, lighting, and stage hardware.", "Rent premium event hardware on demand. We provide industry-standard sound boards, high-definition LED screens, stage lights, digital projectors, and advanced live-streaming hardware packages.", "Wrench", "https://images.unsplash.com/photo-1484755560693-a4074577af3a?w=1000"}
        };

        for (String[] service : servicesData) {
            ServiceEntity entity = ServiceEntity.builder()
                    .id(service[0])
                    .num(service[1])
                    .title(service[2])
                    .tagline(service[3])
                    .description(service[4])
                    .icon(service[5])
                    .banner(service[6])
                    .build();
            serviceRepository.save(entity);
        }
    }

    private void seedGalleryEvents() {
        GalleryEvent event1 = GalleryEvent.builder()
                .id("royal-crimson-wedding")
                .name("The Royal Crimson Wedding")
                .date("2025-11-20")
                .category("weddings")
                .categoryLabel("Weddings")
                .location("Umaid Bhawan Palace, Jodhpur")
                .description("A luxury royal wedding story featuring custom floral structures and golden candlelit pathways.")
                .fullDescription("This grand royal destination wedding blended traditional heritage with luxury staging. VSI Creations handled complete design blueprint: coordinating high-mast architectural wash lights on the palace walls, rigging gold-candlestick chandeliers, and delivering multi-camera cinema captures for the high-profile guests.")
                .banner("https://images.unsplash.com/photo-1519741497674-611481863552?w=1200")
                .videoUrl("https://www.w3schools.com/html/mov_bbb.mp4")
                .videoType("mp4")
                .highlights(List.of(
                        "3D facade projection mapping detailing dynastic lineage.",
                        "Rigged suspended floral design weighing 1.2 metric tons.",
                        "Symmetric candlelit entryway spanning 200 meters.",
                        "8-camera cinema crew tracking cinematic details."
                ))
                .photos(List.of(
                        "https://images.unsplash.com/photo-1519671482749-fd09be7ccebf?w=800",
                        "https://images.unsplash.com/photo-1511285560929-80b456fea0bc?w=800",
                        "https://images.unsplash.com/photo-1583939003579-730e3918a45a?w=800",
                        "https://images.unsplash.com/photo-1469371670807-013ccf25f16a?w=800"
                ))
                .btsPhotos(List.of(
                        "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=800",
                        "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=800"
                ))
                .build();
        galleryEventRepository.save(event1);

        GalleryEvent event2 = GalleryEvent.builder()
                .id("innovate-summit-2026")
                .name("Innovate Summit 2026")
                .date("2026-03-05")
                .category("corporate-events")
                .categoryLabel("Corporate Events")
                .location("Grand Hyatt Ballroom, Bangalore")
                .description("Symmetrical pin-spot staging and real-time live stream coverage for tech visionaries.")
                .fullDescription("Innovate Summit gathered global technology leaders. VSI Creations designed a futuristic stage featuring fine-pitch modular LED panels, custom spatial layouts, and an automated television broadcast station capturing keynote addresses with zero-latency HDMI feeds.")
                .banner("https://images.unsplash.com/photo-1511795409834-ef04bbd61622?w=1200")
                .videoUrl("https://www.youtube.com/embed/dQw4w9WgXcQ")
                .videoType("youtube")
                .highlights(List.of(
                        "Ultra-fine pixel pitch LED wall (80ft wide, 16ft high).",
                        "Network bonded dual 5G digital live webcast station.",
                        "Automated smart stage lighting targeting speaker positions.",
                        "Post-event highlights packages compiled in under 12 hours."
                ))
                .photos(List.of(
                        "https://images.unsplash.com/photo-1531058020387-3be344559be6?w=800",
                        "https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=800",
                        "https://images.unsplash.com/photo-1475721027785-f74eccf877e2?w=800",
                        "https://images.unsplash.com/photo-1505232458627-a7272640408a?w=800"
                ))
                .btsPhotos(List.of(
                        "https://images.unsplash.com/photo-1451187580459-43490279c0fa?w=800",
                        "https://images.unsplash.com/photo-1461749280684-dccba630e2f6?w=800"
                ))
                .build();
        galleryEventRepository.save(event2);

        GalleryEvent event3 = GalleryEvent.builder()
                .id("sonic-vibrations-concert")
                .name("Sonic Vibrations Arena Concert")
                .date("2025-12-15")
                .category("music-shows")
                .categoryLabel("Music Shows")
                .location("Jawaharlal Nehru Stadium, Delhi")
                .description("Truss rigging, synchronized laser alignment, and massive line-array acoustics.")
                .fullDescription("A colossal live music arena production. VSI Creations oversaw structural rigging plans and engineered a high-volume sound layout with 64 subwoofers stack, delivering flawless audio curves across the stadium.")
                .banner("https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=1200")
                .videoUrl("https://www.w3schools.com/html/movie.mp4")
                .videoType("mp4")
                .highlights(List.of(
                        "120-piece metallic truss system certified for heavy payloads.",
                        "3D audio modeling maps aligning delay stacks.",
                        "High-speed aerial camera crane capturing band motion.",
                        "CO2 jet blast units and heavy stage pyrotechnics."
                ))
                .photos(List.of(
                        "https://images.unsplash.com/photo-1506157786151-b8491531f063?w=800",
                        "https://images.unsplash.com/photo-1514525253161-7a46d19cd819?w=800",
                        "https://images.unsplash.com/photo-1465847899084-d164df4dedc6?w=800",
                        "https://images.unsplash.com/photo-1501281668745-f7f57925c3b4?w=800"
                ))
                .btsPhotos(List.of(
                        "https://images.unsplash.com/photo-1472653431158-6364773b2a56?w=800",
                        "https://images.unsplash.com/photo-1516450360452-9312f5e86fc7?w=800"
                ))
                .build();
        galleryEventRepository.save(event3);
    }
}