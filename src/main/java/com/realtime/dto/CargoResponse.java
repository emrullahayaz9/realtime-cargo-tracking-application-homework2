package com.realtime.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// dto for cargo create response
@Data
@Builder
@Getter
@Setter
public class CargoResponse {
    private Long id;
    private String trackingNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
