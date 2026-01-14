package com.salesianostriana.dam.fleetmanager.dto;

import com.salesianostriana.dam.fleetmanager.model.Maintenance;

import java.time.LocalDateTime;

public record MaintenanceSummaryDto(
        String type,
        LocalDateTime date,
        int kmInRevision
) {

    public static MaintenanceSummaryDto of(Maintenance maintenance) {
        return new MaintenanceSummaryDto(
                maintenance.getType(),
                maintenance.getDate(),
                maintenance.getKmInRevision()
        );
    }

}
