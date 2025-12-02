package com.realtime.Controller;

import com.realtime.dto.CargoCreateRequest;
import com.realtime.dto.CargoResponse;
import com.realtime.entity.Cargo;
import com.realtime.service.CargoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Cargo operations.
 * Provides endpoints to create, read, and update cargo entities.
 */
@RestController
@RequestMapping("/api/cargo")
@RequiredArgsConstructor
public class CargoController {

    /** Service layer to handle cargo business logic */
    private final CargoService cargoService;

    /**
     * Create a new cargo.
     *
     * POST /api/cargo
     * @param cargo DTO with cargo details
     * @return CargoResponse DTO with saved cargo info
     */
    @PostMapping
    public CargoResponse createCargo(@RequestBody CargoCreateRequest cargo) {
        return cargoService.createCargo(cargo);
    }

    /**
     * Get all cargos.
     *
     * GET /api/cargo
     * @return List of Cargo entities
     */
    @GetMapping
    public List<Cargo> getAllCargo() {
        return cargoService.getAllCargo();
    }

    /**
     * Get a cargo by ID.
     *
     * GET /api/cargo/{id}
     * @param id Cargo ID
     * @return Cargo entity
     */
    @GetMapping("/{id}")
    public Cargo getCargo(@PathVariable Long id) {
        return cargoService.getCargoById(id);
    }

    /**
     * Update the status of a cargo.
     *
     * PATCH /api/cargo/{id}/status
     * @param id Cargo ID
     * @param status New status in request body (JSON string)
     * @return Updated Cargo entity
     */
    @PatchMapping("/{id}/status")
    public Cargo updateStatus(@PathVariable Long id, @RequestBody String status) {
        // Remove quotes from JSON string
        return cargoService.updateStatus(id, status.replace("\"", ""));
    }
}
