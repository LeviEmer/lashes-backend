package com.lashes.lashes_backend.timeslot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    // Vista pública — slots disponibles desde ahora
    public List<TimeSlot> listarDisponibles() {
        return timeSlotRepository
                .findByDisponibleTrueAndBloqueadoFalseAndFechaHoraAfter(LocalDateTime.now());
    }

    // Admin — ver todos los slots de un rango de fechas
    public List<TimeSlot> listarPorRango(LocalDateTime inicio, LocalDateTime fin) {
        return timeSlotRepository.findByFechaHoraBetween(inicio, fin);
    }

    // Admin Dev — crear slot individual
    public TimeSlot crear(TimeSlot slot) {
        slot.setDisponible(true);
        slot.setBloqueado(false);
        return timeSlotRepository.save(slot);
    }

    // Admin Dev — generar slots automáticamente por semana
    public void generarSlotsSemana(LocalDateTime inicioSemana, int horaInicio,
                                    int horaFin, int duracionMinutos) {
        for (int dia = 0; dia < 6; dia++) { // Lunes a Sábado
            LocalDateTime dia_actual = inicioSemana.plusDays(dia);
            LocalDateTime hora = dia_actual.withHour(horaInicio).withMinute(0).withSecond(0);
            while (hora.getHour() < horaFin) {
                TimeSlot slot = TimeSlot.builder()
                        .fechaHora(hora)
                        .disponible(true)
                        .bloqueado(false)
                        .build();
                timeSlotRepository.save(slot);
                hora = hora.plusMinutes(duracionMinutos);
            }
        }
    }

    // Admin Operator — bloquear un slot
    public TimeSlot bloquear(Long id, String motivo) {
        TimeSlot slot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot no encontrado"));
        slot.setBloqueado(true);
        slot.setDisponible(false);
        slot.setMotivoBLoqueo(motivo);
        return timeSlotRepository.save(slot);
    }

    // Admin Operator — desbloquear un slot
    public TimeSlot desbloquear(Long id) {
        TimeSlot slot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot no encontrado"));
        slot.setBloqueado(false);
        slot.setDisponible(true);
        slot.setMotivoBLoqueo(null);
        return timeSlotRepository.save(slot);
    }
}
