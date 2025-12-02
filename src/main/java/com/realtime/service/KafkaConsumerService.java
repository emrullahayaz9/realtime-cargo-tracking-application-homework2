package com.realtime.service;

import com.realtime.event.LocationUpdateEvent;
import com.realtime.entity.CargoLocation;
import com.realtime.entity.Cargo;
import com.realtime.repository.CargoLocationRepository;
import com.realtime.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final CargoLocationRepository locationRepository;
    private final CargoRepository cargoRepository;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Listens to the "cargo-location-updates" Kafka topic and processes incoming location updates.
     */
    @KafkaListener(topics = "cargo-location-updates", groupId = "cargo-tracker-group")
    public void consume(LocationUpdateEvent event) {
        System.out.println("Information retrieved from Kafka: " + event);

        // Retrieve the Cargo entity by ID
        Cargo cargo = cargoRepository.findById(event.getCargoId()).orElse(null);
        if (cargo == null) return; // Exit if the cargo does not exist

        // Build a new CargoLocation entity from the event
        CargoLocation location = CargoLocation.builder()
                .cargo(cargo)
                .latitude(event.getLatitude())
                .longitude(event.getLongitude())
                .timestamp(LocalDateTime.now())
                .build();

        // Send the update to subscribers following this specific cargo
        if (event.getTrackingNumber() != null) {
            messagingTemplate.convertAndSend("/topic/tracking/" + event.getTrackingNumber(), event);
        }

        // Send the update to all subscribers (e.g., for admin panel monitoring)
        messagingTemplate.convertAndSend("/topic/all-cargos", event);

        // Save the new location to the database
        locationRepository.save(location);
    }
}
