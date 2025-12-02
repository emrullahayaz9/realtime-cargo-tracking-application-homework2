package com.realtime.service;

import com.realtime.dto.CargoCreateRequest;
import com.realtime.dto.CargoResponse;
import com.realtime.entity.Cargo;
import com.realtime.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service class for managing Cargo entities.
 * Provides methods for creating, fetching, and updating cargo.
 */
@Service
@RequiredArgsConstructor
public class CargoService {

    /** Repository for accessing Cargo entities */
    private final CargoRepository cargoRepository;

    /**
     * Create a new cargo record.
     * Generates a unique tracking number and sets the current user as creator.
     *
     * @param request DTO containing cargo details
     * @return CargoResponse DTO with saved cargo info
     */
    public CargoResponse createCargo(CargoCreateRequest request) {
        // Get currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Generate a unique tracking number
        String trackingNo = generateTrackingNumber();

        // Build Cargo entity
        Cargo cargo = Cargo.builder()
                .trackingNumber(trackingNo)
                .senderName(request.getSenderName())
                .receiverName(request.getReceiverName())
                .destinationAddress(request.getDestinationAddress())
                .status("CREATED")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(currentUsername)
                .build();

        // Save to database
        Cargo saved = cargoRepository.save(cargo);

        // Return response DTO
        return CargoResponse.builder()
                .id(saved.getId())
                .trackingNumber(saved.getTrackingNumber())
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }

    /**
     * Generate a unique tracking number for a cargo.
     *
     * @return tracking number in format "TR-XXXXXXXX"
     */
    private String generateTrackingNumber() {
        return "TR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Retrieve all cargo records.
     *
     * @return List of Cargo entities
     */
    public List<Cargo> getAllCargo() {
        return cargoRepository.findAll();
    }

    /**
     * Retrieve a cargo by its ID.
     *
     * @param id Cargo ID
     * @return Cargo entity
     * @throws RuntimeException if not found
     */
    public Cargo getCargoById(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cargo not found: " + id));
    }

    /**
     * Update the status of a cargo.
     *
     * @param id Cargo ID
     * @param status New status
     * @return Updated Cargo entity
     */
    public Cargo updateStatus(Long id, String status) {
        Cargo cargo = getCargoById(id);
        cargo.setStatus(status);
        return cargoRepository.save(cargo);
    }
}
