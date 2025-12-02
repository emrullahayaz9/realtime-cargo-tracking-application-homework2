package com.realtime.event;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LocationUpdateEvent {
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;
    private Long cargoId;
    private String trackingNumber;
}
