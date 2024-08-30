package org.proway.student.hospitalsys.repository;

import org.proway.student.hospitalsys.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findAllByPatient_Id(UUID patient);

    List<Appointment> findAllByDoctor_Id(UUID doctor);
}
