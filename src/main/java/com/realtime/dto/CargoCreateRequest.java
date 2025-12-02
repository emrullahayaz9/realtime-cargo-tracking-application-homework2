package com.realtime.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


// dto for cargo create request
@Data
@Builder
@Getter
@Setter
public class CargoCreateRequest {
    private String senderName;
    private String receiverName;
    private String destinationAddress;
}
