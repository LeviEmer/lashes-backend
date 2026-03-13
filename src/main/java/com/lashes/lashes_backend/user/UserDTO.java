package com.lashes.lashes_backend.user;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private Rol rol;
    private Boolean activo;
    private LocalDateTime creadoEn;

    public static UserDTO from(User u) {
        UserDTO dto = new UserDTO();
        dto.setId(u.getId());
        dto.setNombre(u.getNombre());
        dto.setEmail(u.getEmail());
        dto.setTelefono(u.getTelefono());
        dto.setRol(u.getRol());
        dto.setActivo(u.getActivo());
        dto.setCreadoEn(u.getCreadoEn());
        return dto;
    }
}
