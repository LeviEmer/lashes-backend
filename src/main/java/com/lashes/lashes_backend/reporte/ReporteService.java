package com.lashes.lashes_backend.reporte;

import com.lashes.lashes_backend.appointment.AppointmentRepository;
import com.lashes.lashes_backend.appointment.EstadoCita;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final AppointmentRepository appointmentRepository;

    public List<ReporteCitasPorEstadoDTO> citasPorEstado() {
        return appointmentRepository.reportePorEstadoRaw()
            .stream()
            .map(row -> new ReporteCitasPorEstadoDTO(
                row[0].toString(),
                (Long) row[1]
            ))
            .collect(Collectors.toList());
    }

    public List<ReporteCitasPorServicioDTO> citasPorServicio() {
        return appointmentRepository.reportePorServicioRaw()
            .stream()
            .map(row -> new ReporteCitasPorServicioDTO(
                (String) row[0],
                (Long) row[1],
                (BigDecimal) row[2]
            ))
            .collect(Collectors.toList());
    }

    public List<ReporteCitasPorDiaDTO> citasPorDia(LocalDateTime inicio, LocalDateTime fin) {
        return appointmentRepository.reportePorDiaRaw(inicio, fin)
            .stream()
            .map(row -> new ReporteCitasPorDiaDTO(
                ((java.sql.Date) row[0]).toLocalDate(),
                (Long) row[1]
            ))
            .collect(Collectors.toList());
    }

    public ReporteResumenDTO resumenGeneral() {
        return new ReporteResumenDTO(
            appointmentRepository.contarTodas(),
            appointmentRepository.contarPorEstado(EstadoCita.PENDIENTE),
            appointmentRepository.contarPorEstado(EstadoCita.CONFIRMADA),
            appointmentRepository.contarPorEstado(EstadoCita.CANCELADA),
            appointmentRepository.contarPorEstado(EstadoCita.REPROGRAMADA),
            appointmentRepository.ingresoEstimadoTotal()
        );
    }
}
