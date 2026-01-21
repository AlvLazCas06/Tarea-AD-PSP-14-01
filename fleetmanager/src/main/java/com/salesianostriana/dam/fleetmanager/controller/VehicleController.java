package com.salesianostriana.dam.fleetmanager.controller;

import com.salesianostriana.dam.fleetmanager.model.Status;
import com.salesianostriana.dam.fleetmanager.model.Vehicle;
import com.salesianostriana.dam.fleetmanager.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    /**
     * Ejemplo de endpoint que usa Specifications para búsqueda dinámica
     *
     * Ejemplos de uso:
     * - GET /api/vehicle/search?status=AVAILABLE&maxKm=50000
     * - GET /api/vehicle/search?plate=ABC
     * - GET /api/vehicle/search?minKm=10000&maxKm=80000
     * - GET /api/vehicle/search?plate=123&status=AVAILABLE&maxKm=100000
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Vehicle>> searchVehicles(
            @RequestParam(required = false) String plate,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Integer minKm,
            @RequestParam(required = false) Integer maxKm,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        Page<Vehicle> vehicles = vehicleService.searchVehicles(plate, status, minKm, maxKm, pageable);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Ejemplo: Buscar vehículos disponibles con bajo kilometraje
     * GET /api/vehicle/available-low-km?maxKm=50000
     */
    @GetMapping("/available-low-km")
    public ResponseEntity<Page<Vehicle>> getAvailableVehiclesWithLowKm(
            @RequestParam(defaultValue = "50000") int maxKm,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        Page<Vehicle> vehicles = vehicleService.findAvailableVehiclesWithLowKm(maxKm, pageable);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Ejemplo: Buscar vehículos en mantenimiento con alto kilometraje
     * GET /api/vehicle/maintenance-high-km?minKm=100000
     */
    @GetMapping("/maintenance-high-km")
    public ResponseEntity<Page<Vehicle>> getVehiclesUnderMaintenanceWithHighKm(
            @RequestParam(defaultValue = "100000") int minKm,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        Page<Vehicle> vehicles = vehicleService.findVehiclesUnderMaintenanceWithHighKm(minKm, pageable);
        return ResponseEntity.ok(vehicles);
    }

}
