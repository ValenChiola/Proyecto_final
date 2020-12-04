package com.clasifacil.service;


import com.clasifacil.entidades.Prestador;
import com.clasifacil.entidades.Usuario;
import com.clasifacil.entidades.Zona;
import com.clasifacil.enums.Roles;
import com.clasifacil.repositorios.PrestadorRepositorio;

import com.clasifacil.repositorios.UsuarioRepositorio;
import com.clasifacil.repositorios.ZonaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio ur;

    @Autowired
    private ZonaRepositorio zr;


    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private PrestadorRepositorio prestadorRepositorio;

    @Transactional
    public void registrar(String dni, String nombre, String apellido, String mail, String telefono, String clave1, String clave2, String idZona) throws Error {

        validar(dni, nombre, apellido, mail, telefono, clave1, clave2, idZona);

        Optional<Usuario> respuesta = ur.findById(dni);
        Usuario respuesta2 = ur.buscarPorMail(mail);

        if (!respuesta.isPresent()) {
            if (respuesta2 == null) {
                Usuario u = new Usuario();
                Zona z = zr.getOne(idZona);

                u.setDni(dni);
                u.setNombre(nombre);
                u.setApellido(apellido);
                u.setMail(mail);
                u.setTelefono(telefono);
                String encriptada = new BCryptPasswordEncoder().encode(clave1);
                u.setClave(encriptada);
                u.setHabilitado(true);
                u.setRol(Roles.REGULAR);
                u.setZona(z);

                ur.save(u);

            } else {
                throw new Error("Ya existe un Usuario con ese mail");
            }
        } else {
            throw new Error("Ya existe un Usuario con ese DNI.");
        }

    }

    @Transactional
    public void deshabiltar(String dni) throws Error {


        Optional<Usuario> respuesta = ur.findById(dni);

        if (respuesta.isPresent()) {
            Usuario u = respuesta.get();
            u.setHabilitado(false);
        } else {
            throw new Error("No existe ese Usuario.");
        }
    }

    @Transactional
    public void habiltar(String dni) throws Error {

        Optional<Usuario> respuesta = ur.findById(dni);

        if (respuesta.isPresent()) {
            Usuario u = respuesta.get();
            u.setHabilitado(true);
        } else {
            throw new Error("No existe ese Usuario.");
        }
    }


    @Transactional
    public void modificarUsuario(String dni, String nombre, String apellido, String mail, String telefono, String clave1, String clave2,
            String idZona) throws Error {

        validar(dni, nombre, apellido, mail, telefono, clave1, clave2, idZona);

        Optional<Usuario> us = ur.findById(dni);
        Usuario usuario = us.get();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setTelefono(telefono);
        String encriptada = new BCryptPasswordEncoder().encode(clave1);
        usuario.setClave(encriptada);

        Optional<Zona> zona = zr.findById(idZona);
        if (zona.isPresent()) {
            Zona z = zona.get();
            usuario.setZona(z);
        } else {
            throw new Error("No se encontro la zona");
        }
        ur.save(usuario);

    }

    private void validar(String dni, String nombre, String apellido, String mail, String telefono, String clave1, String clave2, String idZona) throws Error {

        if (dni == null || dni.trim().isEmpty()) {
            throw new Error("Debe indicar el DNI.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Error("Debe indicar el nombre.");
        }

        if (apellido == null || apellido.trim().isEmpty()) {
            throw new Error("Debe indicar el apellido.");
        }

        if (mail == null || mail.trim().isEmpty() || !mail.contains("@")) {
            throw new Error("Debe ser un mail correcto.");
        }

        if (telefono == null || telefono.trim().isEmpty()) {
            throw new Error("Debe indicar el teléfono.");
        }

        if (clave1 == null || clave1.trim().isEmpty()) {
            throw new Error("Debe indicar las dos claves.");
        }

        if (clave2 == null || clave2.trim().isEmpty()) {
            throw new Error("Debe indicar las dos claves.");
        }

        if (!clave1.equals(clave2)) {
            throw new Error("Las claves deben ser iguales.");
        }

        if (idZona == null || idZona.trim().isEmpty()) {
            throw new Error("No existe esa Zona.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        Usuario u = ur.buscarPorMail(mail);

        if (u == null) {
            Prestador p = prestadorRepositorio.buscarPrestadorPorMail(mail);
            if (p == null) {
                return null;
            } else {
                List<GrantedAuthority> permisos = new ArrayList<>();

                GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_PRESTADOR");
                permisos.add(p1);

                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

                HttpSession session = attr.getRequest().getSession(true);
                session.setAttribute("prestadorsession", p);
                session.setAttribute("role", "prestador");

                User user = new User(p.getMail(), p.getClave(), permisos);

                return user;
            }
        } else {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + u.getRol());
            permisos.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", u);
            session.setAttribute("role", "usuario");

            User user = new User(u.getMail(), u.getClave(), permisos);
            System.out.println(u.getRol());
            return user;
        }
    }

    @Transactional
    public List<Usuario> listarTodos() {
        return ur.listarTodos();
    }

    @Transactional
    public Usuario buscarPorDNI(String dni) throws Error {

        Optional<Usuario> respuesta = ur.findById(dni);

        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new Error("No se ha encontrado el usuario solicitado.");
        }
    }

    @Transactional
    public Usuario buscarPorMail(String mail) throws Error {

        Usuario respuesta = ur.buscarPorMail(mail);

        return respuesta;
    }

    @Transactional
    public void upgrade(String dni) {
        Usuario u = ur.getOne(dni);

        u.setRol(Roles.ADMIN);
        ur.save(u);
    }

    @Transactional
    public void recuperarContrasenia(String mail) {

        String claveNueva = UUID.randomUUID().toString();
        String claveNuevaEncriptada = new BCryptPasswordEncoder().encode(claveNueva);
        
        Usuario u = buscarPorMail(mail);
        u.setClave(claveNuevaEncriptada);
        ur.save(u);
        notificacionService.enviarModificarContraseña("", "Recuperación de contraseña", mail, claveNueva);
        
    }
    
     @Transactional
    public List<Usuario> buscarPorParteDeMail(String mail) throws Error {

        return ur.buscarPorParteDeMail(mail);
    }
}
