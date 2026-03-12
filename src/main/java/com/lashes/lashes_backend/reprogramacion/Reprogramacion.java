package com.lashes.lashes_backend.reprogramacion;

import com.lashes.lashes_backend.appointment.Appointment;
import com.lashes.lashes_backend.timeslot.TimeSlot;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reprogramaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reprogramacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "slot_anterior_id")
    private TimeSlot slotAnterior;

    @ManyToOne
    @JoinColumn(name = "slot_nuevo_id")
    private TimeSlot slotNuevo;

    private String motivo;

    @Column(nullable = false)
    private Boolean notificado = false;

    @Column(name = "fecha_cambio")
    private LocalDateTime fechaCambio = LocalDateTime.now();
}

