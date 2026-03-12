package com.lashes.lashes_backend.timeslot;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "time_slots")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(nullable = false)
    private Boolean disponible = true;

    @Column(nullable = false)
    private Boolean bloqueado = false;

    @Column(name = "motivo_bloqueo")
    private String motivoBloqueo;
    public void setMotivoBLoqueo(String motivo){
    this.motivoBloqueo = motivo;
}
}


