package com.realtime.event;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Event class representing a cargo location update.
 * Sent via Kafka to notify consumers about cargo movement.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LocationUpdateEvent {

    /** Latitude of the cargo */
    private Double latitude;

    /** Longitude of the cargo */
    private Double longitude;

    /** Timestamp of the location update */
    private LocalDateTime timestamp;

    /** ID of the cargo */
    private Long cargoId;

    /** Tracking number of the cargo */
    private String trackingNumber;
}
