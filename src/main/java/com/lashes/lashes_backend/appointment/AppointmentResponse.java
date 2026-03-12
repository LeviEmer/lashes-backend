package com.lashes.lashes_backend.appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AppointmentResponse {
    private Long id;
    private String clienteNombre;
    private String clienteEmail;
    private String servicioNombre;
    private Double servicioPrice;
    private Integer duracionMinutos;
    private LocalDateTime fechaHora;
    private String estado;
    private String notaCliente;
    private String notaAdmin;
    private LocalDateTime creadoEn;
}
