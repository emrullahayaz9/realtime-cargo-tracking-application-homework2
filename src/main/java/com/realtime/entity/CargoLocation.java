package com.realtime.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity representing a single location update of a cargo.
 * Stores latitude, longitude, and timestamp for tracking purposes.
 */
@Entity
@Table(name = "cargo_location")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoLocation {

    /** Primary key for CargoLocation */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ManyToOne relationship with Cargo.
     * Many locations can belong to one cargo.
     * FetchType.LAZY → Cargo object is loaded only when accessed
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargo_id", nullable = false)
    private Cargo cargo;

    /** Latitude coordinate of cargo location */
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    /** Longitude coordinate of cargo location */
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    /** Timestamp when this location was recorded */
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;
}
