package com.realtime.service;

import com.realtime.dto.LocationCreateRequest;
import com.realtime.dto.LocationResponse;
import com.realtime.entity.Cargo;
import com.realtime.entity.CargoLocation;
import com.realtime.repository.CargoLocationRepository;
import com.realtime.repository.CargoRepository;
import com.realtime.repository.LatLonSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class to handle Cargo location operations.
 * Provides methods to add a location, get latest location, and get location history.
 */
@Service
@RequiredArgsConstructor
public class CargoLocationService {

    /** Repository for accessing CargoLocation entities */
    private final CargoLocationRepository locationRepository;

    /** Repository for accessing Cargo entities */
    private final CargoRepository cargoRepository;

    /**
     * Add a new location for a specific cargo.
     *
     * @param cargoId ID of the cargo
     * @param request DTO containing latitude, longitude, timestamp
     * @return LocationResponse DTO with saved location info
     */
    public LocationResponse addLocation(Long cargoId, LocationCreateRequest request) {
        // Find cargo by ID or throw exception if not found
        Cargo cargo = cargoRepository.findById(cargoId)
                .orElseThrow(() -> new RuntimeException("Cargo not found: " + cargoId));

        // Create a new CargoLocation entity
        CargoLocation location = CargoLocation.builder()
                .cargo(cargo)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .timestamp(request.getTimestamp())
                .build();

        // Save the location to the database
        CargoLocation saved = locationRepository.save(location);

        // Return a DTO response
        return LocationResponse.builder()
                .latitude(saved.getLatitude())
                .longitude(saved.getLongitude())
                .timestamp(saved.getTimestamp())
                .build();
    }

    /**
     * Get the most recent location for a specific cargo.
     *
     * @param cargoId ID of the cargo
     * @return LatLonSummary projection containing latest latitude and longitude
     */
    public LatLonSummary getLatestLocation(Long cargoId) {
        return locationRepository.findTopByCargoIdOrderByTimestampDesc(cargoId);
    }

    /**
     * Get the full location history of a specific cargo.
     *
     * @param cargoId ID of the cargo
     * @return List of LatLonSummary projections ordered by timestamp ascending
     */
    public List<LatLonSummary> getLocationHistory(Long cargoId) {
        return locationRepository.findByCargoIdOrderByTimestampAsc(cargoId);
    }
}
