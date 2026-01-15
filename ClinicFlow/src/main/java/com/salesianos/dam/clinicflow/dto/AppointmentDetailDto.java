package com.salesianos.dam.clinicflow.dto;

import com.salesianos.dam.clinicflow.model.Appointment;

import java.time.LocalDateTime;

public record AppointmentDetailDto(
        Long id,
        LocalDateTime hourDate,
        String status,
        String patientName,
        String professionalName
) {

    public static AppointmentDetailDto of(Appointment appointment) {
        return new AppointmentDetailDto(
                appointment.getId(),
                appointment.getHourDate(),
                appointment.getStatus().name().toLowerCase(),
                appointment.getPatient() != null ? appointment.getPatient().getName() : "No tiene paciente",
                appointment.getProfessional() != null ? appointment.getProfessional().getName() : "No tiene profesional"
        );
    }

}
