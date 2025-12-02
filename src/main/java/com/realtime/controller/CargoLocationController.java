package com.realtime.Controller;

import com.realtime.dto.LocationCreateRequest;
import com.realtime.dto.LocationResponse;
import com.realtime.event.LocationUpdateEvent;
import com.realtime.repository.LatLonSummary;
import com.realtime.service.CargoLocationService;
import com.realtime.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/cargo/{cargoId}/location")
@RequiredArgsConstructor
public class CargoLocationController {

    private final CargoLocationService locationService;
    private final KafkaProducerService kafkaProducerService;

    /**
     * Adds a new location for a cargo.
     * Location data is received in the request body.
     */
    @PostMapping
    public LocationResponse addLocation(@PathVariable Long cargoId, @RequestBody LocationCreateRequest location) {
        return locationService.addLocation(cargoId, location);
    }

    /**
     * Retrieves the latest location for a specific cargo.
     */
    @GetMapping("/latest")
    public LatLonSummary getLatestLocation(@PathVariable Long cargoId) {
        return locationService.getLatestLocation(cargoId);
    }

    /**
     * Retrieves the location history for a specific cargo in chronological order.
     */
    @GetMapping("/history")
    public List<LatLonSummary> getLocationHistory(@PathVariable Long cargoId) {
        return locationService.getLocationHistory(cargoId);
    }

    /**
     * Updates the cargo location via Kafka.
     * If the timestamp is missing, the current time is used.
     */
    @PostMapping("/update-location")
    public ResponseEntity<String> updateLocation(@PathVariable Long cargoId, @RequestBody LocationUpdateEvent request) {

        request.setCargoId(cargoId);

        if (request.getTimestamp() == null) {
            request.setTimestamp(LocalDateTime.now());
        }

        kafkaProducerService.sendLocationUpdate(request);

        return ResponseEntity.ok("Location Queued.");
    }
}
