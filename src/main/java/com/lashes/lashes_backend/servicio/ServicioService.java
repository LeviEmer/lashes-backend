package com.lashes.lashes_backend.servicio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public List<Servicio> listarActivos() {
        return servicioRepository.findByActivoTrue();
    }

    public List<Servicio> listarTodos() {
        return servicioRepository.findAll();
    }

    public Servicio crear(Servicio servicio) {
        servicio.setActivo(true);
        return servicioRepository.save(servicio);
    }

    public Servicio actualizar(Long id, Servicio datos) {
        Servicio existing = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        existing.setNombre(datos.getNombre());
        existing.setDescripcion(datos.getDescripcion());
        existing.setDuracionMinutos(datos.getDuracionMinutos());
        existing.setPrecio(datos.getPrecio());
        return servicioRepository.save(existing);
    }

    public void desactivar(Long id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        servicio.setActivo(false);
        servicioRepository.save(servicio);
    }
}
