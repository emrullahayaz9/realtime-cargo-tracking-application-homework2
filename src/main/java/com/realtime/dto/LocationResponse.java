package com.realtime.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
@Setter
public class LocationResponse {
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;
}
