package com.lashes.lashes_backend.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    // Público — clientes sin login pueden ver servicios
    @GetMapping("/publico")
    public ResponseEntity<List<Servicio>> listarPublico() {
        return ResponseEntity.ok(servicioService.listarActivos());
    }

    // Solo Admin
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN_OPERATOR', 'ADMIN_DEV')")
    public ResponseEntity<List<Servicio>> listarTodos() {
        return ResponseEntity.ok(servicioService.listarTodos());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN_DEV')")
    public ResponseEntity<Servicio> crear(@RequestBody Servicio servicio) {
        return ResponseEntity.ok(servicioService.crear(servicio));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_DEV')")
    public ResponseEntity<Servicio> actualizar(@PathVariable Long id, @RequestBody Servicio servicio) {
        return ResponseEntity.ok(servicioService.actualizar(id, servicio));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_DEV')")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        servicioService.desactivar(id);
        return ResponseEntity.ok().build();
    }
}

