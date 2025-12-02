package com.realtime.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity representing a Cargo shipment.
 * Stores general information about a shipment and its tracking data.
 */
@Entity
@Table(name = "cargo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cargo {

    /** Primary key for Cargo entity */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Unique tracking number for the cargo */
    @Column(name = "tracking_number", nullable = false, unique = true, length = 50)
    private String trackingNumber;

    /** Name of the sender */
    @Column(name = "sender_name", length = 100)
    private String senderName;

    /** Name of the receiver */
    @Column(name = "receiver_name", length = 100)
    private String receiverName;

    /** Full destination address */
    @Column(name = "destination_address", columnDefinition = "TEXT")
    private String destinationAddress;

    /** Current status of the cargo (default is "CREATED") */
    @Column(name = "status", length = 30)
    private String status = "CREATED";

    /** Timestamp of when the cargo record was created */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /** Timestamp of last update to the cargo record */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** User who created the cargo entry */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * One cargo can have many locations.
     * CascadeType.ALL → changes to Cargo propagate to locations
     * orphanRemoval = true → removing a location from the list deletes it from DB
     * FetchType.LAZY → locations are loaded only when accessed
     */
    @OneToMany(mappedBy = "cargo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CargoLocation> locations;
}
