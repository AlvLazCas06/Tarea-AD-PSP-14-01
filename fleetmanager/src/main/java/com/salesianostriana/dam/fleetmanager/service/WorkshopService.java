package com.salesianostriana.dam.fleetmanager.service;

import com.salesianostriana.dam.fleetmanager.repository.WorkshopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkshopService {

    private final WorkshopRepository workshopRepository;

}
