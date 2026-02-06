package com.salesianostriana.dam.fleetmanager.service;

import com.salesianostriana.dam.fleetmanager.dto.CreateMaintenanceRequest;
import com.salesianostriana.dam.fleetmanager.exception.BadRequestException;
import com.salesianostriana.dam.fleetmanager.exception.VehicleNotFoundException;
import com.salesianostriana.dam.fleetmanager.model.Maintenance;
import com.salesianostriana.dam.fleetmanager.model.Status;
import com.salesianostriana.dam.fleetmanager.model.Vehicle;
import com.salesianostriana.dam.fleetmanager.repository.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehicleService vehicleService;

    public Maintenance createMaintenance(CreateMaintenanceRequest dto) {
        Maintenance maintenance = dto.toEntity();
        Vehicle vehicle;
        try {
            vehicle = vehicleService.getVehicleById(dto.idVehicle());
        } catch (VehicleNotFoundException e) {
            throw new BadRequestException();
        }
        if (vehicle.getStatus() != Status.AVAILABLE
                || maintenance.getKmInRevision() < vehicle.getCurrentKm()) {
            throw new IllegalArgumentException();
        }
        vehicle.changeCurrentKm(maintenance.getKmInRevision());
        vehicle.changeToUnderMaintenance();
        vehicle.addMaintenance(maintenance);
        return maintenanceRepository.save(maintenance);
    }

}
