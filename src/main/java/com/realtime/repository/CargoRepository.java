package com.realtime.repository;

import com.realtime.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for Cargo entity.
 * Extends JpaRepository to provide standard CRUD operations.
 */
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    /**
     * Find a cargo by its tracking number.
     * Useful when you need to look up a specific shipment.
     *
     * @param trackingNumber the unique tracking number of the cargo
     * @return Optional containing Cargo if found, empty otherwise
     */
    Optional<Cargo> findByTrackingNumber(String trackingNumber);
}
