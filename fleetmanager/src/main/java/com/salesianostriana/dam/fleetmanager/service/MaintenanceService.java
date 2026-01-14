package com.salesianostriana.dam.fleetmanager.service;

import com.salesianostriana.dam.fleetmanager.repository.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

}
