package com.realtime.Controller;

import com.realtime.dto.CargoCreateRequest;
import com.realtime.dto.CargoResponse;
import com.realtime.entity.Cargo;
import com.realtime.service.CargoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargo")
@RequiredArgsConstructor
public class CargoController {
    private final CargoService cargoService;

    @PostMapping
    public CargoResponse createCargo(@RequestBody CargoCreateRequest cargo) {
        return cargoService.createCargo(cargo);
    }

    @GetMapping
    public List<Cargo> getAllCargo() {
        return cargoService.getAllCargo();
    }

    @GetMapping("/{id}")
    public Cargo getCargo(@PathVariable Long id) {
        return cargoService.getCargoById(id);
    }

    @PatchMapping("/{id}/status")
    public Cargo updateStatus(@PathVariable Long id, @RequestBody String status) {
        return cargoService.updateStatus(id, status.replace("\"", ""));
    }
}
