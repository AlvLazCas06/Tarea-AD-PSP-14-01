package com.salesianostriana.dam.fleetmanager.dto;

import com.salesianostriana.dam.fleetmanager.model.Allowance;
import com.salesianostriana.dam.fleetmanager.model.Maintenance;
import com.salesianostriana.dam.fleetmanager.model.Vehicle;

import java.util.List;

public record VehicleDetailDto(
        Long id,
        String plate,
        int currentKm,
        String status,
        List<AllowanceSummaryDto> allowances,
        List<MaintenanceSummaryDto> maintenances
) {

    public static VehicleDetailDto of(Vehicle vehicle) {
        return new VehicleDetailDto(
                vehicle.getId(),
                vehicle.getPlate(),
                vehicle.getCurrentKm(),
                vehicle.getStatus().name(),
                vehicle.getAllowances()
                        .stream()
                        .map(AllowanceSummaryDto::of)
                        .toList(),
                vehicle.getMaintenances()
                        .stream()
                        .map(MaintenanceSummaryDto::of)
                        .toList()
        );
    }

}
