package com.salesianostriana.dam.fleetmanager.service;

import com.salesianostriana.dam.fleetmanager.model.Maintenance;
import com.salesianostriana.dam.fleetmanager.model.Status;
import com.salesianostriana.dam.fleetmanager.model.Vehicle;
import com.salesianostriana.dam.fleetmanager.repository.MaintenanceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehicleService vehicleService;

    public Maintenance createMaintenance(Maintenance maintenance, Long idVehicle) throws EntityNotFoundException {
        Vehicle vehicle = vehicleService.getVehicleById(idVehicle);
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
