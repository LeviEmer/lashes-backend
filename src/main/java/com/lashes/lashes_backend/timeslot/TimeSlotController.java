package com.lashes.lashes_backend.timeslot;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    // Público
    @GetMapping("/disponibles")
    public ResponseEntity<List<TimeSlot>> disponibles() {
        return ResponseEntity.ok(timeSlotService.listarDisponibles());
    }

    // Admin
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN_OPERATOR', 'ADMIN_DEV')")
    public ResponseEntity<List<TimeSlot>> porRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(timeSlotService.listarPorRango(inicio, fin));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN_DEV')")
    public ResponseEntity<TimeSlot> crear(@RequestBody TimeSlot slot) {
        return ResponseEntity.ok(timeSlotService.crear(slot));
    }

    @PostMapping("/generar")
    @PreAuthorize("hasRole('ADMIN_DEV')")
    public ResponseEntity<Void> generarSemana(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam int horaInicio,
            @RequestParam int horaFin,
            @RequestParam int duracion) {
        timeSlotService.generarSlotsSemana(inicio, horaInicio, horaFin, duracion);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/bloquear")
    @PreAuthorize("hasAnyRole('ADMIN_OPERATOR', 'ADMIN_DEV')")
    public ResponseEntity<TimeSlot> bloquear(@PathVariable Long id,
                                              @RequestParam String motivo) {
        return ResponseEntity.ok(timeSlotService.bloquear(id, motivo));
    }

    @PutMapping("/{id}/desbloquear")
    @PreAuthorize("hasAnyRole('ADMIN_OPERATOR', 'ADMIN_DEV')")
    public ResponseEntity<TimeSlot> desbloquear(@PathVariable Long id) {
        return ResponseEntity.ok(timeSlotService.desbloquear(id));
    }
}
