package com.lashes.lashes_backend.reporte;

import java.math.BigDecimal;

public record ReporteCitasPorServicioDTO(String servicio, Long total, BigDecimal ingresoTotal) {}
