package com.salesianostriana.dam.fleetmanager.dto;

import com.salesianostriana.dam.fleetmanager.model.Maintenance;

import java.time.LocalDateTime;

public record CreateMaintenanceRequest(
        String type,
        LocalDateTime date,
        int kmInRevision,
        Long idVehicle,
        Long idWorkshop
) {

    public Maintenance toEntity() {
        return Maintenance.builder()
                .type(type)
                .date(date)
                .kmInRevision(kmInRevision)
                .build();
    }

}
