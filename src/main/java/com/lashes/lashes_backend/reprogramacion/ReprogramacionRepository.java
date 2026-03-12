package com.lashes.lashes_backend.reprogramacion;

import com.lashes.lashes_backend.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReprogramacionRepository extends JpaRepository<Reprogramacion, Long> {
    List<Reprogramacion> findByAppointment(Appointment appointment);
}

