package org.proway.student.hospitalsys.controller;

import jakarta.validation.Valid;
import org.proway.student.hospitalsys.domain.Appointment;
import org.proway.student.hospitalsys.domain.dto.AppointmentDto;
import org.proway.student.hospitalsys.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid AppointmentDto data){
        appointmentService.create(data);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAppointments(){
        return ResponseEntity.ok(appointmentService.getAppointments());
    }

    @GetMapping("/patient/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@RequestParam String id){
        return ResponseEntity.ok(appointmentService.getPatientsAppointments(id));
    }

    @GetMapping("/doctor/{id}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@RequestParam String id){
        return ResponseEntity.ok(appointmentService.getDoctorAppointments(id));
    }
}
