package com.realtime.service;

import com.realtime.event.LocationUpdateEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Service class that simulates cargo movement for testing purposes.
 * Generates random location updates and sends them to Kafka at fixed intervals.
 */
@Service
public class CargoSimulationJob {

    /** Service to send location updates to Kafka */
    private final KafkaProducerService kafkaProducerService;

    /** Random generator for simulating movement */
    private final Random random = new Random();

    /** Holds current simulated coordinates of each cargo */
    private final Map<Long, double[]> currentLocations = new HashMap<>();

    /**
     * Constructor initializes Kafka service and starting positions of cargo.
     *
     * @param kafkaProducerService Kafka producer service
     */
    public CargoSimulationJob(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;

        // Initial coordinates for three cargos
        currentLocations.put(1L, new double[]{40.9901, 29.0292});
        currentLocations.put(2L, new double[]{41.0422, 29.0067});
        currentLocations.put(3L, new double[]{41.0264, 29.0152});
    }

    /**
     * Simulate cargo movement every 2 seconds.
     * Adjusts latitude and longitude slightly to mimic real movement.
     * Sends updated location as a Kafka event.
     */
    @Scheduled(fixedRate = 2000)
    public void simulateMovement() {
        System.out.println("--- Simulation Triggered ---");

        for (Long cargoId = 1L; cargoId <= 3L; cargoId++) {

            double[] coords = currentLocations.get(cargoId);

            // Generate small random changes to simulate movement
            double latChange = (random.nextDouble() - 0.5) * 0.002;
            double lonChange = (random.nextDouble() - 0.5) * 0.002;

            double newLat = coords[0] + latChange;
            double newLon = coords[1] + lonChange;

            // Update current coordinates
            coords[0] = newLat;
            coords[1] = newLon;

            // Create a location update event with tracking number
            LocationUpdateEvent event;
            if(cargoId == 1L) {
                event = LocationUpdateEvent.builder()
                        .cargoId(cargoId)
                        .latitude(newLat)
                        .longitude(newLon)
                        .timestamp(LocalDateTime.now())
                        .trackingNumber("TR-CC107DD8")
                        .build();
            } else if(cargoId == 2L) {
                event = LocationUpdateEvent.builder()
                        .cargoId(cargoId)
                        .latitude(newLat)
                        .longitude(newLon)
                        .timestamp(LocalDateTime.now())
                        .trackingNumber("TR-C90B1A00")
                        .build();
            } else {
                event = LocationUpdateEvent.builder()
                        .cargoId(cargoId)
                        .latitude(newLat)
                        .longitude(newLon)
                        .timestamp(LocalDateTime.now())
                        .trackingNumber("TR-3C9115EA")
                        .build();
            }

            // Send the simulated location update to Kafka
            kafkaProducerService.sendLocationUpdate(event);

            System.out.printf("Cargo %d moved -> Lat: %.4f, Lon: %.4f%n", cargoId, newLat, newLon);
        }
    }
}
