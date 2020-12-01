package com.clasifacil.service;

import com.clasifacil.entidades.Prestador;
import com.clasifacil.entidades.Usuario;
import com.clasifacil.repositorios.PrestadorRepositorio;
import com.clasifacil.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PrestadorService prestadorService;

    @Async
    public void enviar(String cuerpo, String titulo, String mail) {
        SimpleMailMessage mensaje = new SimpleMailMessage();

        mensaje.setTo(mail);
        mensaje.setFrom("clasifacilarg@gmail.com");
        mensaje.setSubject(titulo);
        mensaje.setText(cuerpo);

        sender.send(mensaje);
    }

    @Async
    public void enviarPorVoto(String dni, String cuit) {
        SimpleMailMessage mensaje = new SimpleMailMessage();

        Prestador p = prestadorService.buscarPrestadorPorCuit(cuit);
        Usuario u = usuarioService.buscarPorDNI(dni);

        mensaje.setTo(p.getMail());
        mensaje.setFrom("clasifacilarg@gmail.com");
        mensaje.setSubject("Voto");
        mensaje.setText(u.getNombre() + " " + u.getApellido() + " te ha votado. Ingresá a tu perfil para ver tu nueva puntuación :)");

        sender.send(mensaje);

        p.setServiciosprestados(p.getServiciosprestados() + 1);
    }

    @Async
    public void enviarContacto(String cuerpo, String titulo, String mail) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        String mensajeAnterior = "";

        Usuario u = usuarioService.buscarPorMail(mail);
        if (u == null) {
            Prestador p = prestadorService.buscarPorMail(mail);
            if (p == null) {
                mensajeAnterior = mail + " Tiene una consulta: ";
            } else {
                mensajeAnterior = p.getNombre() + " " + p.getApellido() + " con el mail " + p.getMail() + " Tiene una consulta: ";
            }
        } else {
            mensajeAnterior = u.getNombre() + " " + u.getApellido() + " con el mail " + u.getMail() + " Tiene una consulta: ";
        }

        mensaje.setFrom(mail);
        mensaje.setTo("clasifacilarg@gmail.com");
        mensaje.setSubject(titulo);
        mensaje.setText(mensajeAnterior.concat(cuerpo));

        sender.send(mensaje);
    }

    @Async
    public void enviarModificarContraseña(String cuerpo, String titulo, String mail, String contraseña) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        String mensajeAnterior = "";

        mensajeAnterior = " Su nueva Contraseña es: " + contraseña;

        mensaje.setFrom("clasifacilarg@gmail.com");
        mensaje.setTo(mail);
        mensaje.setSubject(titulo);
        mensaje.setText(mensajeAnterior.concat(cuerpo));

        sender.send(mensaje);
    }
}
