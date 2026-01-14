package com.salesianostriana.dam.fleetmanager.dto;

import com.salesianostriana.dam.fleetmanager.model.Allowance;

import java.time.LocalDateTime;

public record AllowanceSummaryDto(
        LocalDateTime startDate,
        LocalDateTime finishDate
) {

    public static AllowanceSummaryDto of(Allowance allowance) {
        return new AllowanceSummaryDto(
                allowance.getStartDate(),
                allowance.getFinishDate()
        );
    }

}
