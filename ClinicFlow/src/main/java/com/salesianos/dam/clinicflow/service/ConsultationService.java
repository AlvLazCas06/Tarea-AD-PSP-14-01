package com.salesianos.dam.clinicflow.service;

import com.salesianos.dam.clinicflow.exception.AppointmentNotFoundException;
import com.salesianos.dam.clinicflow.exception.BadRequestException;
import com.salesianos.dam.clinicflow.model.Appointment;
import com.salesianos.dam.clinicflow.model.Consultation;
import com.salesianos.dam.clinicflow.repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final AppointmentService appointmentService;

    public Consultation createConsultation(Consultation consultation, Long idAppointment) {
        Appointment appointment;
        try {
            appointment = appointmentService.findAppointmentById(idAppointment);
            appointment.change();
            consultation.addAppointment(appointment);
        } catch (AppointmentNotFoundException e) {
            throw new BadRequestException();
        }
        return consultationRepository.save(consultation);
    }

}
