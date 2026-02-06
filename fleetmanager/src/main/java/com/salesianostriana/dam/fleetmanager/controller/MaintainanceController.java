package com.salesianostriana.dam.fleetmanager.controller;

import com.salesianostriana.dam.fleetmanager.dto.CreateMaintenanceRequest;
import com.salesianostriana.dam.fleetmanager.dto.MaintenanceSummaryDto;
import com.salesianostriana.dam.fleetmanager.service.MaintenanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MaintainanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<MaintenanceSummaryDto> save(@RequestBody @Valid CreateMaintenanceRequest cmd) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MaintenanceSummaryDto.of(maintenanceService.createMaintenance(cmd)));
    }

}
