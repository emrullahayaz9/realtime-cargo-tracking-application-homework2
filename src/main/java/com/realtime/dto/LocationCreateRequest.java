package com.realtime.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// dto for location create request
@Data
@Builder
@Getter
@Setter
public class LocationCreateRequest {
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;
}
