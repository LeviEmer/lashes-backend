package com.lashes.lashes_backend.appointment;

import com.lashes.lashes_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByCliente(User cliente);
    List<Appointment> findByEstado(EstadoCita estado);
    List<Appointment> findBySlot_FechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
}

