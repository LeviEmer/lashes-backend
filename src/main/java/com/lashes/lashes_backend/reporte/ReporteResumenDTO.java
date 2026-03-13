package com.lashes.lashes_backend.reporte;

public record ReporteResumenDTO(
    Long totalCitas,
    Long citasPendientes,
    Long citasConfirmadas,
    Long citasCanceladas,
    Long citasReprogramadas,
    Double ingresoEstimadoTotal
) {}
