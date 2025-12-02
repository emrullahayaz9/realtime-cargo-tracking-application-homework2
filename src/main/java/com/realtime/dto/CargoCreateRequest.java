package com.realtime.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class CargoCreateRequest {
    private String senderName;
    private String receiverName;
    private String destinationAddress;
}
