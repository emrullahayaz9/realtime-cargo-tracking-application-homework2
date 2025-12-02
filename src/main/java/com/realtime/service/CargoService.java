package com.realtime.service;

import com.realtime.dto.CargoCreateRequest;
import com.realtime.dto.CargoResponse;
import com.realtime.entity.Cargo;
import com.realtime.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CargoService {

    private final CargoRepository cargoRepository;

    public CargoResponse createCargo(CargoCreateRequest request) {
        // build and return
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        String trackingNo = generateTrackingNumber();
        Cargo cargo = Cargo.builder().
                trackingNumber(trackingNo) // tracking number should generated
                .senderName(request.getSenderName())
                .receiverName(request.getReceiverName())
                .destinationAddress(request.getDestinationAddress())
                .status("CREATED")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(currentUsername)
                .build();
        Cargo saved = cargoRepository.save(cargo);
        return CargoResponse.builder().id(saved.getId())
               .id(saved.getId())
               .trackingNumber(saved.getTrackingNumber())
               .createdAt(saved.getCreatedAt())
               .updatedAt(saved.getUpdatedAt())
               .build();
    }
    private String generateTrackingNumber() {
        return "TR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    public List<Cargo> getAllCargo() {
        return cargoRepository.findAll();
    }

    public Cargo getCargoById(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cargo not found: " + id));
    }

    public Cargo updateStatus(Long id, String status) {
        Cargo cargo = getCargoById(id);
        cargo.setStatus(status);
        return cargoRepository.save(cargo);
    }
}
