package com.realtime.service;

import com.realtime.event.LocationUpdateEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class CargoSimulationJob {

    private final KafkaProducerService kafkaProducerService;
    private final Random random = new Random();

    private final Map<Long, double[]> currentLocations = new HashMap<>();

    public CargoSimulationJob(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
        currentLocations.put(1L, new double[]{40.9901, 29.0292});
        currentLocations.put(2L, new double[]{41.0422, 29.0067});
        currentLocations.put(3L, new double[]{41.0264, 29.0152});
    }

    @Scheduled(fixedRate = 2000)
    public void simulateMovement() {
        System.out.println("--- Simulation Triggered ---");

        for (Long cargoId = 1L; cargoId <= 3L; cargoId++) {

            double[] coords = currentLocations.get(cargoId);

            double latChange = (random.nextDouble() - 0.5) * 0.002;
            double lonChange = (random.nextDouble() - 0.5) * 0.002;

            double newLat = coords[0] + latChange;
            double newLon = coords[1] + lonChange;

            coords[0] = newLat;
            coords[1] = newLon;
            LocationUpdateEvent event;
            if(cargoId == 1L) {
            event = LocationUpdateEvent.builder()
                    .cargoId(cargoId)
                    .latitude(newLat)
                    .longitude(newLon)
                    .timestamp(LocalDateTime.now())
                    .trackingNumber("TR-CC107DD8")
                    .build();
        }else if(cargoId == 2L) {
                event = LocationUpdateEvent.builder()
                        .cargoId(cargoId)
                        .latitude(newLat)
                        .longitude(newLon)
                        .timestamp(LocalDateTime.now())
                        .trackingNumber("TR-C90B1A00")
                        .build();
        }
            else {
                // for third
                event = LocationUpdateEvent.builder()
                        .cargoId(cargoId)
                        .latitude(newLat)
                        .longitude(newLon)
                        .timestamp(LocalDateTime.now())
                        .trackingNumber("TR-3C9115EA")
                        .build();
            }

            kafkaProducerService.sendLocationUpdate(event);

            System.out.printf("Kargo %d moved -> Lat: %.4f, Lon: %.4f%n", cargoId, newLat, newLon);
        }
    }
}
