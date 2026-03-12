package com.lashes.lashes_backend.appointment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // Cliente agenda su cita
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AppointmentResponse> agendar(
            @RequestBody AppointmentRequest request,
            Authentication auth) {
        return ResponseEntity.ok(appointmentService.agendar(auth.getName(), request));
    }

    // Cliente ve sus propias citas
    @GetMapping("/mis-citas")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<AppointmentResponse>> misCitas(Authentication auth) {
        return ResponseEntity.ok(appointmentService.listarPorCliente(auth.getName()));
    }

    // Admin ve todas las citas
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN_OPERATOR', 'ADMIN_DEV')")
    public ResponseEntity<List<AppointmentResponse>> todas() {
        return ResponseEntity.ok(appointmentService.listarTodos());
    }

    // Admin ve citas de hoy
    @GetMapping("/hoy")
    @PreAuthorize("hasAnyRole('ADMIN_OPERATOR', 'ADMIN_DEV')")
    public ResponseEntity<List<AppointmentResponse>> hoy() {
        return ResponseEntity.ok(appointmentService.listarHoy());
    }

    // Admin ve citas por estado
    @GetMapping("/estado/{estado}")
    @PreAuthorize("hasAnyRole('ADMIN_OPERATOR', 'ADMIN_DEV')")
    public ResponseEntity<List<AppointmentResponse>> porEstado(
            @PathVariable EstadoCita estado) {
        return ResponseEntity.ok(appointmentService.listarPorEstado(estado));
    }

    // Admin confirma cita
    @PutMapping("/{id}/confirmar")
    @PreAuthorize("hasAnyRole('ADMIN_OPERATOR', 'ADMIN_DEV')")
    public ResponseEntity<AppointmentResponse> confirmar(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.confirmar(id));
    }

    // Admin reprograma cita
    @PutMapping("/{id}/reprogramar")
    @PreAuthorize("hasAnyRole('ADMIN_OPERATOR', 'ADMIN_DEV')")
    public ResponseEntity<AppointmentResponse> reprogramar(
            @PathVariable Long id,
            @RequestParam Long nuevoSlotId,
            @RequestParam(required = false) String motivo) {
        return ResponseEntity.ok(appointmentService.reprogramar(id, nuevoSlotId, motivo));
    }

    // Admin o cliente cancela
    @PutMapping("/{id}/cancelar")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AppointmentResponse> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.cancelar(id));
    }
}
