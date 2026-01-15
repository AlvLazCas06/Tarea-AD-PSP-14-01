package com.salesianos.dam.clinicflow.service;

import com.salesianos.dam.clinicflow.exception.AppointmentNotFoundException;
import com.salesianos.dam.clinicflow.exception.BadRequestException;
import com.salesianos.dam.clinicflow.exception.NotFoundException;
import com.salesianos.dam.clinicflow.model.Appointment;
import com.salesianos.dam.clinicflow.model.Patient;
import com.salesianos.dam.clinicflow.model.Professional;
import com.salesianos.dam.clinicflow.model.Status;
import com.salesianos.dam.clinicflow.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final ProfessionalService professionalService;

    public Appointment createAppointment(Appointment appointment, Long idPatient, Long idProfessional) {
        Patient patient;
        Professional professional;
        try {
            patient = patientService.findPatientById(idPatient);
            professional = professionalService.findProfessionalById(idProfessional);
            patient.addAppointment(appointment);
            professional.addAppointment(appointment);
        } catch (NotFoundException e) {
            throw new BadRequestException();
        }
        if (appointment.getHourDate().isBefore(LocalDateTime.now())
                || appointment.getStatus() != Status.SCHEDULED
                || appointmentRepository.findAllByPatient_Id(idPatient).getLast().getHourDate().equals(appointment.getHourDate())) {
            throw new BadRequestException();
        }
        return appointmentRepository.save(appointment);
    }

    public Appointment cancelAppointment(Appointment appointment) {
        if (appointment.getStatus() != Status.ATTENDED)
            throw new BadRequestException();
        return appointmentRepository.save(appointment);
    }

    public Appointment findAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
    }

}
