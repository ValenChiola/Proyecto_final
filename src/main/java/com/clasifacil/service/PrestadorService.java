package com.clasifacil.service;

import com.clasifacil.entidades.Foto;
import com.clasifacil.entidades.Prestador;
import com.clasifacil.entidades.Zona;
import com.clasifacil.enums.Rubros;
import com.clasifacil.repositorios.FotoRepositorio;
import com.clasifacil.repositorios.PrestadorRepositorio;
import com.clasifacil.repositorios.ZonaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Service

public class PrestadorService {
    
    @Autowired
    private ZonaRepositorio zr;
    @Autowired
    private FotoRepositorio fr;
    @Autowired
    private PrestadorRepositorio pr;
    
    @Transactional
    public void registrar(String cuit, String nombre, String apellido, String mail, String clave, String clave2, String telefono,
            String idZona, Integer serviciosprestados, String idFoto, String descripcion,Rubros rubro) throws Error {
        
        validar(cuit, nombre, apellido, mail, clave, clave2, telefono, serviciosprestados, descripcion, rubro);
        Prestador prestador = new Prestador();
        
        prestador.setCuit(cuit);
        prestador.setNombre(nombre);
        prestador.setMail(mail);
        prestador.setClave(clave);
        prestador.setTelefono(telefono);
        prestador.setServiciosprestados(serviciosprestados);
        prestador.setDescripcion(descripcion);
        prestador.setAlta(new Date());
        prestador.setRubro(rubro);
        
        Optional<Foto> f = fr.findById(idFoto);
        if (f.isPresent()) {
            Foto foto = f.get();
            prestador.setFoto(foto);
        } else {
            throw new Error("No se encontro la foto");
        }
        
        Optional<Zona> zona = zr.findById(idZona);
        if (zona.isPresent()) {
            Zona z = zona.get();
            prestador.setZona(z);
        } else {
            throw new Error("No se encontro la zona");
        }
        
        pr.save(prestador);
    }
    
    
    @Transactional
    public void ModificarPrestador(String cuit, String nombre, String apellido, String mail, String clave, String clave2, String telefono,
            String idZona, Integer serviciosprestados, String idFoto, String descripcion, Rubros rubro) throws Error {
        
        validar(cuit, nombre, apellido, mail, clave, clave2, telefono, serviciosprestados, descripcion, rubro);
        

        Optional<Prestador> pres = pr.findById(cuit);
        Prestador prestador= pres.get();
        
        prestador.setCuit(cuit);
        prestador.setNombre(nombre);
        prestador.setMail(mail);
        prestador.setClave(clave);
        prestador.setTelefono(telefono);
        prestador.setServiciosprestados(serviciosprestados);
        prestador.setDescripcion(descripcion);
        
        
        Optional<Foto> f = fr.findById(idFoto);
        if (f.isPresent()) {
            Foto foto = f.get();
            prestador.setFoto(foto);
        } else {
            throw new Error("No se encontro la foto");
        }
        
        Optional<Zona> zona = zr.findById(idZona);
        if (zona.isPresent()) {
            Zona z = zona.get();
            prestador.setZona(z);
        } else {
            throw new Error("No se encontro la zona");
        }
        pr.save(prestador);
   
    }
    @Transactional
    public void borrarPrestador(String cuit) throws Error{
        
        Optional<Prestador> pres= pr.findById(cuit);
        if(pres.isPresent()){
            Prestador prestador=pres.get();
            pr.delete(prestador);
        }else{
            throw new Error("No se encontro el Prestador.");
        }
        
        
    }
    
    

    public void validar(String cuit, String nombre, String apellido, String mail, String clave,
             String clave2, String telefono, Integer serviciosprestados, String descripcion, Rubros rubro) {
        
        if (cuit == null || cuit.isEmpty()) {
            throw new Error("Debe indicar el Cuit.");
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
        
        if (clave == null || clave.isEmpty() || clave.length() <= 6) {
            throw new Error("La clave no puede estar vacia y debe contener mas de 6 caracteres.");
        }
        
        if (clave2 == null || clave2.trim().isEmpty()) {
            throw new Error("Debe indicar las dos claves.");
        }
        
        if (!clave.equals(clave2)) {
            throw new Error("Las claves deben ser iguales.");
        }
        
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new Error("La descripcion no puede estar vacia.");
        }
        
        if (serviciosprestados == null) {
            throw new Error("Los servicios prestados no pueden ser nulos.");
        }
        
        if (rubro == null) {
            throw new Error("El rubro no puedee ser nulo.");
        }
        
    }
    
    public UserDetails loadUserByUsername(String cuit) throws UsernameNotFoundException {

        Prestador prestador = pr.buscarPrestadorPorCuit(cuit);
        if (prestador != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_PRESTADOR");
            permisos.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("prestadorsession", prestador);

//            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_MATERIA");
//            permisos.add(p2);
//            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_PAGO");
//            permisos.add(p3);
            User user = new User(prestador.getCuit(), prestador.getClave(), permisos);

            return user;

        } else {
            return null;
        }
    
    
    
    
    
}}
