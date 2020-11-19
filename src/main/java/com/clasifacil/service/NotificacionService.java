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
    
//    @Autowired
//    private UsuarioRepositorio usuarioRepositorio;
//    
//    @Autowired
//    private PrestadorRepositorio prestadorRepositorio;
    
    @Async
    public void enviar(String cuerpo, String titulo, String mail) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        
        mensaje.setTo(mail);
        mensaje.setFrom("tinochiola06@gmail.com");
        mensaje.setSubject(titulo);
        mensaje.setText(cuerpo);
        
        sender.send(mensaje);
    }
    
//    @Async
//    public void enviarPorVoto(String dni, String cuit) {
//        SimpleMailMessage mensaje = new SimpleMailMessage();
//        
//        Prestador p = prestadorRepositorio.getOne(cuit);
//        Usuario u = usuarioRepositorio.getOne(dni);
//        
//        mensaje.setTo(p.getMail());
//        mensaje.setFrom("clasifacil.com");
//        mensaje.setSubject("Voto");
//        mensaje.setText(u.getNombre() + " " + u.getApellido() + " te ha votado");
//        
//        sender.send(mensaje);
//        
//        p.setServiciosprestados(p.getServiciosprestados() + 1);
//    }
}
