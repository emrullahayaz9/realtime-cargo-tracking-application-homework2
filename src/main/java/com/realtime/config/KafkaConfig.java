package com.realtime.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    /**
     * Defines the Kafka topic for cargo location updates.
     * 
     * @return a new Kafka topic named "cargo-location-updates"
     */
    @Bean
    public NewTopic locationTopic() {
        return TopicBuilder
                .name("cargo-location-updates") // The name of the topic
                .partitions(3)                  // Number of partitions (allows parallel processing)
                .replicas(1)                     // Number of replicas (for fault tolerance)
                .build();
    }
}
