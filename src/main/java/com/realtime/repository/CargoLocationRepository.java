package com.realtime.repository;


import com.realtime.entity.CargoLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CargoLocationRepository extends JpaRepository<CargoLocation, Long> {

    List<LatLonSummary> findByCargoIdOrderByTimestampAsc(Long cargoId);
    LatLonSummary findTopByCargoIdOrderByTimestampDesc(Long cargoId);
}
