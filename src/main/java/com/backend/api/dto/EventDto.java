package com.backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private String id;
    private String title;
    private String date;
    private String location;
    private String category;
    private String description;
    private String fullDescription;
    private String banner;
    private String videoThumbnail;
    private String videoUrl;
    private String venueDetails;
    private String participationInfo;
    private Double price;
    private List<EventScheduleDto> schedule;
}
