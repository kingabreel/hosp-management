package org.proway.student.hospitalsys.service;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.proway.student.hospitalsys.domain.Appointment;
import org.proway.student.hospitalsys.domain.dto.AppointmentDto;
import org.proway.student.hospitalsys.repository.AppointmentRepository;
import org.proway.student.hospitalsys.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;

    public Appointment create(AppointmentDto data){
        var doc = userRepository.findById(UUID.fromString(data.doctorId()));
        var pat = userRepository.findById(UUID.fromString(data.patientId()));

        if (doc.isEmpty() || pat.isEmpty()) throw new UsernameNotFoundException("Id not found");

        Appointment appointment = new Appointment();

        BeanUtils.copyProperties(data, appointment);
        appointment.setDoctor(doc.get());
        appointment.setPatient(pat.get());

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getPatientsAppointments(String id) {
        var user = userRepository.findById(UUID.fromString(id));

        if (user.isEmpty()) throw new UsernameNotFoundException("Id not found");

        return appointmentRepository.findAllByPatient_Id(user.get().getId());
    }

    public List<Appointment> getDoctorAppointments(String id) {
        var user = userRepository.findById(UUID.fromString(id));

        if (user.isEmpty()) throw new UsernameNotFoundException("Id not found");

        return appointmentRepository.findAllByDoctor_Id(user.get().getId());

    }
}
