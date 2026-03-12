package com.lashes.lashes_backend.appointment;

import lombok.Data;

@Data
public class AppointmentRequest {
    private Long servicioId;
    private Long slotId;
    private String notaCliente;
}
