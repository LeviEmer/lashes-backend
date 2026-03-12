package com.lashes.lashes_backend.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void enviarCitaRecibida(String email, String nombre, String servicio, LocalDateTime fechaHora) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("✨ Cita recibida - BETHLASHES");
        msg.setText("Hola " + nombre + ",\n\nHemos recibido tu cita:\n\n" +
                "📌 Servicio: " + servicio + "\n" +
                "📅 Fecha y hora: " + fechaHora.format(FORMATO) + "\n\n" +
                "En breve la confirmaremos. 💕\n— BETHLASHES Studio");
        mailSender.send(msg);
    }

    public void enviarCitaConfirmada(String email, String nombre, String servicio, LocalDateTime fechaHora) {
        System.out.println(">>> ENVIANDO EMAIL A: " + email);
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("✅ Cita confirmada - BETHLASHES");
        msg.setText("Hola " + nombre + ",\n\nTu cita está confirmada:\n\n" +
                "📌 Servicio: " + servicio + "\n" +
                "📅 Fecha y hora: " + fechaHora.format(FORMATO) + "\n\n" +
                "¡Te esperamos! 💕\n— BETHLASHES Studio");
        mailSender.send(msg);
    }

    public void enviarCitaReprogramada(String email, String nombre, String servicio,
                                        LocalDateTime fechaAnterior, LocalDateTime fechaNueva, String motivo) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("🔄 Cita reprogramada - BETHLASHES");
        msg.setText("Hola " + nombre + ",\n\nTu cita ha sido reprogramada:\n\n" +
                "📌 Servicio: " + servicio + "\n" +
                "❌ Fecha anterior: " + fechaAnterior.format(FORMATO) + "\n" +
                "✅ Nueva fecha: " + fechaNueva.format(FORMATO) + "\n" +
                "📝 Motivo: " + motivo + "\n\n" +
                "¡Te esperamos! 💕\n— BETHLASHES Studio");
        mailSender.send(msg);
    }

    public void enviarCitaCancelada(String email, String nombre, String servicio, LocalDateTime fechaHora) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("❌ Cita cancelada - BETHLASHES");
        msg.setText("Hola " + nombre + ",\n\nTu cita ha sido cancelada:\n\n" +
                "📌 Servicio: " + servicio + "\n" +
                "📅 Fecha: " + fechaHora.format(FORMATO) + "\n\n" +
                "Si deseas reagendar ingresa a la app.\n— BETHLASHES Studio");
        mailSender.send(msg);
        
    }
}

