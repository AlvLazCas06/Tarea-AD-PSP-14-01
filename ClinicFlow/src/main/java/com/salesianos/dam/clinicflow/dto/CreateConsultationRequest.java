package com.salesianos.dam.clinicflow.dto;

import com.salesianos.dam.clinicflow.model.Consultation;

import java.time.LocalDateTime;

public record CreateConsultationRequest(
        String observations,
        String diagnostic,
        LocalDateTime date,
        Long idAppointment
) {

    public Consultation toEntity() {
        return Consultation.builder()
                .observations(observations)
                .diagnostic(diagnostic)
                .date(date)
                .build();
    }

}
