package com.lashes.lashes_backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Listar todos
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN_DEV', 'ADMIN_OPERATOR')")
    public ResponseEntity<List<UserDTO>> listar(
            @RequestParam(required = false) Rol rol) {
        if (rol != null) {
            return ResponseEntity.ok(userService.listarPorRol(rol));
        }
        return ResponseEntity.ok(userService.listarTodos());
    }

    // Cambiar rol
    @PutMapping("/{id}/rol")
    @PreAuthorize("hasRole('ADMIN_DEV')")
    public ResponseEntity<UserDTO> cambiarRol(
            @PathVariable Long id,
            @RequestParam Rol nuevoRol) {
        return ResponseEntity.ok(userService.cambiarRol(id, nuevoRol));
    }

    // Activar / desactivar
    @PutMapping("/{id}/toggle-activo")
    @PreAuthorize("hasAnyRole('ADMIN_DEV', 'ADMIN_OPERATOR')")
    public ResponseEntity<UserDTO> toggleActivo(@PathVariable Long id) {
        return ResponseEntity.ok(userService.toggleActivo(id));
    }
}
