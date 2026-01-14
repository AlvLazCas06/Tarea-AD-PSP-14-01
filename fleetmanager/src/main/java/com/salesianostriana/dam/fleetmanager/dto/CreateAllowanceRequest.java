package com.salesianostriana.dam.fleetmanager.dto;

import com.salesianostriana.dam.fleetmanager.model.Allowance;

import java.time.LocalDateTime;

public record CreateAllowanceRequest(
        LocalDateTime startDate,
        Long idVehicle,
        Long idDriver
) {

    public Allowance toEntity() {
        return Allowance.builder()
                .startDate(startDate)
                .build();
    }

}
