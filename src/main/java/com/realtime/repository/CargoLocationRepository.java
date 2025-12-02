package com.realtime.repository;

import com.realtime.entity.CargoLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for CargoLocation entity.
 * Extends JpaRepository to provide CRUD operations.
 */
public interface CargoLocationRepository extends JpaRepository<CargoLocation, Long> {

    /**
     * Retrieve all location entries for a specific cargo, ordered by timestamp ascending.
     * Useful for tracking the full route of a cargo from start to finish.
     *
     * @param cargoId ID of the cargo
     * @return List of LatLonSummary projections
     */
    List<LatLonSummary> findByCargoIdOrderByTimestampAsc(Long cargoId);

    /**
     * Retrieve the most recent location entry for a specific cargo.
     * Useful for getting the current/latest position.
     *
     * @param cargoId ID of the cargo
     * @return LatLonSummary projection of the latest location
     */
    LatLonSummary findTopByCargoIdOrderByTimestampDesc(Long cargoId);
}
