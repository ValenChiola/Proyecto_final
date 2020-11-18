package com.clasifacil.service;

import com.clasifacil.entidades.Usuario;
import com.clasifacil.entidades.Zona;
import com.clasifacil.enums.Roles;
import com.clasifacil.repositorios.UsuarioRepositorio;
import com.clasifacil.repositorios.ZonaRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio ur;

    @Autowired
    private ZonaRepositorio zr;

    @Transactional
    public void registrar(String dni, String nombre, String apellido, String mail, String telefono, String clave1, String clave2, String idZona) throws Error {

        validar(dni, nombre, apellido, mail, telefono, clave1, clave2, idZona);

        Optional<Usuario> respuesta = ur.findById(dni);
        Usuario respuesta2 = ur.buscarPorMail(mail);
        Optional<Zona> respuestaZ = zr.findById(idZona);

        if (!respuesta.isPresent()) {
            if (respuesta2 == null) {
                if (respuestaZ.isPresent()) {
                    Usuario u = respuesta.get();
                    Zona z = respuestaZ.get();

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
                }
            } else {
                throw new Error("Ya existe un Usuario con ese mail");
            }
        } else {
            throw new Error("Ya existe un Usuario con ese DNI.");
        }
    }

    @Transactional
    public void deshabiltar(String dni) {

        Optional<Usuario> respuesta = ur.findById(dni);

        if (respuesta.isPresent()) {
            Usuario u = respuesta.get();
            u.setHabilitado(false);
        } else {
            throw new Error("No existe ese Usuario.");
        }
    }
   
    @Transactional
    public void habiltar(String dni) {

        Optional<Usuario> respuesta = ur.findById(dni);

        if (respuesta.isPresent()) {
            Usuario u = respuesta.get();
            u.setHabilitado(true);
        } else {
            throw new Error("No existe ese Usuario.");
        }
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
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
