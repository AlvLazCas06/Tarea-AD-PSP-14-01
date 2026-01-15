package com.salesianos.dam.clinicflow.repository;

import com.salesianos.dam.clinicflow.model.Appointment;
import com.salesianos.dam.clinicflow.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByPatient_Id(Long patientId);

    Page<Appointment> findAllByStatus(Pageable pageable, Status status);

    List<Appointment> findAllByHourDateBetween(LocalDateTime hourDateAfter, LocalDateTime hourDateBefore);

}
