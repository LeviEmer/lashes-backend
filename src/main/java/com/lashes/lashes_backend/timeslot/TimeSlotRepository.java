package com.lashes.lashes_backend.timeslot;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByDisponibleTrueAndBloqueadoFalseAndFechaHoraAfter(LocalDateTime fecha);
    List<TimeSlot> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
}
