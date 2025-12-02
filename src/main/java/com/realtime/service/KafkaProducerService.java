package com.realtime.service;

import com.realtime.event.LocationUpdateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Sends a cargo location update event to the Kafka topic.
     *
     * @param event the location update event to send
     */
    public void sendLocationUpdate(LocationUpdateEvent event) {
        // Log the event being sent
        System.out.println("Sending data to Kafka: " + event);

        // Send the event to the "cargo-location-updates" topic
        // Key: cargoId as a string, Value: the event object
        kafkaTemplate.send(
            "cargo-location-updates", 
            String.valueOf(event.getCargoId()), 
            event
        );
    }
}
