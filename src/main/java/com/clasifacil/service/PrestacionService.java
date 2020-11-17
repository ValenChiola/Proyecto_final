package com.clasifacil.service;

import com.clasifacil.entidades.Prestacion;
import com.clasifacil.entidades.Prestador;
import com.clasifacil.entidades.Usuario;
import com.clasifacil.repositorios.PrestacionRepositorio;
import com.clasifacil.repositorios.PrestadorRepositorio;
import com.clasifacil.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestacionService {

    @Autowired
    private PrestacionRepositorio prestacionRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PrestadorRepositorio prestadorRepositorio;

    @Transactional
    public void guardar(String idUsuario, String idPrestador) throws Error{

        Optional<Usuario> respuestaU = usuarioRepositorio.findById(idUsuario);
        Optional<Prestador> respuestaP = prestadorRepositorio.findById(idPrestador);
        
        if(respuestaU.isPresent() && respuestaP.isPresent()){
            Prestacion prestacion = new Prestacion();
            Usuario usuario = respuestaU.get();
            Prestador prestador = respuestaP.get();
            
            prestacion.setFecha(new Date());
            prestacion.setUsuario(usuario);
            prestacion.setPrestador(prestador);
            
            prestacionRepositorio.save(prestacion);
        }else{
            throw new Error("Usuario o Prestador inexistente.");
        }
    }
}
