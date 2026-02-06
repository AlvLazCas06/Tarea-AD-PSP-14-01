package com.salesianostriana.dam.fleetmanager.dto;

import com.salesianostriana.dam.fleetmanager.model.Maintenance;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CreateMaintenanceRequest(
        @NotBlank(message = "{createMaintenanceRequest.type.notBlank}")
        String type,
        @Min(0)
        int kmInRevision,
        @Min(value = 1, message = "No se pueden introducir un id inferior a 1")
        Long idVehicle,
        @Min(value = 1)
        Long idWorkshop
) {

    public Maintenance toEntity() {
        return Maintenance.builder()
                .type(type)
                .date(LocalDateTime.now())
                .kmInRevision(kmInRevision)
                .build();
    }

}
