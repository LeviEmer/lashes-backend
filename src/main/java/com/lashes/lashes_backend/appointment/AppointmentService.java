package com.lashes.lashes_backend.appointment;

import com.lashes.lashes_backend.notification.EmailService;
import com.lashes.lashes_backend.reprogramacion.Reprogramacion;
import com.lashes.lashes_backend.reprogramacion.ReprogramacionRepository;
import com.lashes.lashes_backend.servicio.Servicio;
import com.lashes.lashes_backend.servicio.ServicioRepository;
import com.lashes.lashes_backend.timeslot.TimeSlot;
import com.lashes.lashes_backend.timeslot.TimeSlotRepository;
import com.lashes.lashes_backend.user.User;
import com.lashes.lashes_backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ServicioRepository servicioRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ReprogramacionRepository reprogramacionRepository;
    private final EmailService emailService;

    public AppointmentResponse agendar(String emailCliente, AppointmentRequest request) {
        User cliente = userRepository.findByEmail(emailCliente)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Servicio servicio = servicioRepository.findById(request.getServicioId())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        TimeSlot slot = timeSlotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new RuntimeException("Slot no encontrado"));

        if (!slot.getDisponible() || slot.getBloqueado()) {
            throw new RuntimeException("El horario no está disponible");
        }

        Appointment appointment = Appointment.builder()
                .cliente(cliente)
                .servicio(servicio)
                .slot(slot)
                .estado(EstadoCita.PENDIENTE)
                .notaCliente(request.getNotaCliente())
                .creadoEn(LocalDateTime.now())
                .build();

        slot.setDisponible(false);
        timeSlotRepository.save(slot);
        appointmentRepository.save(appointment);

       emailService.enviarCitaRecibida(cliente.getEmail(), cliente.getNombre(),
                servicio.getNombre(), slot.getFechaHora());

        return toResponse(appointment);
    }

    public AppointmentResponse confirmar(Long id) {
        Appointment appointment = getById(id);
        appointment.setEstado(EstadoCita.CONFIRMADA);
        appointment.setActualizadoEn(LocalDateTime.now());
        appointmentRepository.save(appointment);

       emailService.enviarCitaConfirmada(
                appointment.getCliente().getEmail(),
                appointment.getCliente().getNombre(),
                appointment.getServicio().getNombre(),
                appointment.getSlot().getFechaHora());

        return toResponse(appointment);
    }

    public AppointmentResponse reprogramar(Long id, Long nuevoSlotId, String motivo) {
        Appointment appointment = getById(id);
        TimeSlot slotAnterior = appointment.getSlot();

        TimeSlot nuevoSlot = timeSlotRepository.findById(nuevoSlotId)
                .orElseThrow(() -> new RuntimeException("Slot no encontrado"));

        if (!nuevoSlot.getDisponible() || nuevoSlot.getBloqueado()) {
            throw new RuntimeException("El nuevo horario no está disponible");
        }

        // Liberar slot anterior
        slotAnterior.setDisponible(true);
        timeSlotRepository.save(slotAnterior);

        // Registrar reprogramación
        Reprogramacion rep = Reprogramacion.builder()
                .appointment(appointment)
                .slotAnterior(slotAnterior)
                .slotNuevo(nuevoSlot)
                .motivo(motivo)
                .notificado(false)
                .fechaCambio(LocalDateTime.now())
                .build();
        reprogramacionRepository.save(rep);

        // Actualizar cita
        nuevoSlot.setDisponible(false);
        timeSlotRepository.save(nuevoSlot);
        appointment.setSlot(nuevoSlot);
        appointment.setEstado(EstadoCita.REPROGRAMADA);
        appointment.setActualizadoEn(LocalDateTime.now());
        appointmentRepository.save(appointment);

        emailService.enviarCitaReprogramada(
                appointment.getCliente().getEmail(),
                appointment.getCliente().getNombre(),
                appointment.getServicio().getNombre(),
                slotAnterior.getFechaHora(),
                nuevoSlot.getFechaHora(),
                motivo);

        rep.setNotificado(true);
        reprogramacionRepository.save(rep);

        return toResponse(appointment);
    }

    public AppointmentResponse cancelar(Long id) {
        Appointment appointment = getById(id);
        appointment.getSlot().setDisponible(true);
        timeSlotRepository.save(appointment.getSlot());
        appointment.setEstado(EstadoCita.CANCELADA);
        appointment.setActualizadoEn(LocalDateTime.now());
        appointmentRepository.save(appointment);

       emailService.enviarCitaCancelada(
                appointment.getCliente().getEmail(),
                appointment.getCliente().getNombre(),
                appointment.getServicio().getNombre(),
                appointment.getSlot().getFechaHora());

        return toResponse(appointment);
    }

    public List<AppointmentResponse> listarPorCliente(String emailCliente) {
        User cliente = userRepository.findByEmail(emailCliente)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return appointmentRepository.findByCliente(cliente)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<AppointmentResponse> listarTodos() {
        return appointmentRepository.findAll()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<AppointmentResponse> listarPorEstado(EstadoCita estado) {
        return appointmentRepository.findByEstado(estado)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<AppointmentResponse> listarHoy() {
        LocalDateTime inicio = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fin = inicio.plusDays(1);
        return appointmentRepository.findBySlot_FechaHoraBetween(inicio, fin)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    private Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));
    }

    private AppointmentResponse toResponse(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getCliente().getNombre(),
                a.getCliente().getEmail(),
                a.getServicio().getNombre(),
                a.getServicio().getPrecio().doubleValue(),
                a.getServicio().getDuracionMinutos(),
                a.getSlot().getFechaHora(),
                a.getEstado().name(),
                a.getNotaCliente(),
                a.getNotaAdmin(),
                a.getCreadoEn()
        );
    }
}
