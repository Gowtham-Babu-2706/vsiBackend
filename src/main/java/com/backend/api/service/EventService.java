package com.backend.api.service;

import com.backend.api.dto.EventDto;
import com.backend.api.dto.EventScheduleDto;
import com.backend.api.model.Event;
import com.backend.api.model.EventSchedule;
import com.backend.api.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<EventDto> getEventById(String id) {
        return eventRepository.findById(id)
                .map(this::convertToDto);
    }

    public EventDto createEvent(EventDto eventDto) {
        Event event = convertToEntity(eventDto);
        if (event.getId() == null || event.getId().trim().isEmpty()) {
            event.setId(String.valueOf(System.currentTimeMillis()));
        }
        Event savedEvent = eventRepository.save(event);
        return convertToDto(savedEvent);
    }

    public Optional<EventDto> updateEvent(String id, EventDto eventDto) {
        if (!eventRepository.existsById(id)) {
            return Optional.empty();
        }
        Event event = convertToEntity(eventDto);
        event.setId(id); // Ensure ID is persistent
        Event savedEvent = eventRepository.save(event);
        return Optional.of(convertToDto(savedEvent));
    }

    public boolean deleteEvent(String id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private EventDto convertToDto(Event event) {
        List<EventScheduleDto> scheduleDtos = new ArrayList<>();
        if (event.getSchedule() != null) {
            scheduleDtos = event.getSchedule().stream()
                    .map(s -> new EventScheduleDto(s.getTime(), s.getTitle()))
                    .collect(Collectors.toList());
        }
        return new EventDto(
                event.getId(),
                event.getTitle(),
                event.getDate(),
                event.getLocation(),
                event.getCategory(),
                event.getDescription(),
                event.getFullDescription(),
                event.getBanner(),
                event.getVideoThumbnail(),
                event.getVideoUrl(),
                event.getVenueDetails(),
                event.getParticipationInfo(),
                event.getPrice(),
                scheduleDtos
        );
    }

    private Event convertToEntity(EventDto dto) {
        Event event = Event.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .date(dto.getDate())
                .location(dto.getLocation())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .fullDescription(dto.getFullDescription())
                .banner(dto.getBanner())
                .videoThumbnail(dto.getVideoThumbnail())
                .videoUrl(dto.getVideoUrl())
                .venueDetails(dto.getVenueDetails())
                .participationInfo(dto.getParticipationInfo())
                .price(dto.getPrice())
                .build();

        if (dto.getSchedule() != null) {
            List<EventSchedule> scheduleList = dto.getSchedule().stream()
                    .map(s -> EventSchedule.builder()
                            .time(s.getTime())
                            .title(s.getTitle())
                            .event(event)
                            .build())
                    .collect(Collectors.toList());
            event.setSchedule(scheduleList);
        }
        return event;
    }
}
