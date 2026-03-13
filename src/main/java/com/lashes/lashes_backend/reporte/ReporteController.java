package com.lashes.lashes_backend.reporte;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN_OPERATOR', 'ADMIN_DEV')")
public class ReporteController {

    private final ReporteService reporteService;

    // GET /api/reportes/resumen
    @GetMapping("/resumen")
    public ResponseEntity<ReporteResumenDTO> resumen() {
        return ResponseEntity.ok(reporteService.resumenGeneral());
    }

    // GET /api/reportes/por-estado
    @GetMapping("/por-estado")
    public ResponseEntity<List<ReporteCitasPorEstadoDTO>> porEstado() {
        return ResponseEntity.ok(reporteService.citasPorEstado());
    }

    // GET /api/reportes/por-servicio
    @GetMapping("/por-servicio")
    public ResponseEntity<List<ReporteCitasPorServicioDTO>> porServicio() {
        return ResponseEntity.ok(reporteService.citasPorServicio());
    }

    // GET /api/reportes/por-dia?inicio=2026-03-01T00:00:00&fin=2026-03-31T23:59:59
    @GetMapping("/por-dia")
    public ResponseEntity<List<ReporteCitasPorDiaDTO>> porDia(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(reporteService.citasPorDia(inicio, fin));
    }
}
