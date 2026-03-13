package com.lashes.lashes_backend.appointment;

import com.lashes.lashes_backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByCliente(User cliente);
    List<Appointment> findByEstado(EstadoCita estado);
    List<Appointment> findBySlot_FechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    @Query("SELECT a.estado, COUNT(a) FROM Appointment a GROUP BY a.estado")
    List<Object[]> reportePorEstadoRaw();

    @Query("SELECT a.servicio.nombre, COUNT(a), SUM(a.servicio.precio) " +
           "FROM Appointment a GROUP BY a.servicio.nombre")
    List<Object[]> reportePorServicioRaw();

    @Query("SELECT CAST(a.slot.fechaHora AS date), COUNT(a) " +
           "FROM Appointment a " +
           "WHERE a.slot.fechaHora BETWEEN :inicio AND :fin " +
           "GROUP BY CAST(a.slot.fechaHora AS date) " +
           "ORDER BY CAST(a.slot.fechaHora AS date)")
    List<Object[]> reportePorDiaRaw(
        @Param("inicio") LocalDateTime inicio,
        @Param("fin") LocalDateTime fin
    );

    @Query("SELECT COUNT(a) FROM Appointment a")
    Long contarTodas();

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.estado = :estado")
    Long contarPorEstado(@Param("estado") EstadoCita estado);

    @Query("SELECT COALESCE(SUM(a.servicio.precio), 0) FROM Appointment a " +
           "WHERE a.estado <> com.lashes.lashes_backend.appointment.EstadoCita.CANCELADA")
    Double ingresoEstimadoTotal();
}
