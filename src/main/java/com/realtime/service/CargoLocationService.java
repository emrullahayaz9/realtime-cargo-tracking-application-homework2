package com.realtime.service;

import com.realtime.dto.LocationCreateRequest;
import com.realtime.dto.LocationResponse;
import com.realtime.entity.Cargo;
import com.realtime.entity.CargoLocation;
import com.realtime.repository.CargoLocationRepository;
import com.realtime.repository.CargoRepository;
import com.realtime.repository.LatLonSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CargoLocationService {

    private final CargoLocationRepository locationRepository;
    private final CargoRepository cargoRepository;

    public LocationResponse addLocation(Long cargoId, LocationCreateRequest request) {
        Cargo cargo = cargoRepository.findById(cargoId)
                .orElseThrow(() -> new RuntimeException("Cargo not found: " + cargoId));

        CargoLocation location = CargoLocation.builder()
                .cargo(cargo)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .timestamp(request.getTimestamp())
                .build();
        CargoLocation saved = locationRepository.save(location);

        return LocationResponse.builder()
                .latitude(saved.getLatitude())
                .longitude(saved.getLongitude())
                .timestamp(saved.getTimestamp())
                .build();
    }

    public LatLonSummary getLatestLocation(Long cargoId) {
        return locationRepository.findTopByCargoIdOrderByTimestampDesc(cargoId);
    }

    public List<LatLonSummary> getLocationHistory(Long cargoId) {
        return locationRepository.findByCargoIdOrderByTimestampAsc(cargoId);    }
}
