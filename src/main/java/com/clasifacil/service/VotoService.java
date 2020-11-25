package com.clasifacil.service;

import com.clasifacil.entidades.Prestador;
import com.clasifacil.entidades.Usuario;
import com.clasifacil.entidades.Voto;
import com.clasifacil.enums.Valoraciones;
import com.clasifacil.repositorios.PrestadorRepositorio;
import com.clasifacil.repositorios.UsuarioRepositorio;
import com.clasifacil.repositorios.VotoRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoService {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private PrestadorRepositorio prestadorRepositorio;
    
    @Autowired
    private PrestadorService prestadorService;
    
    @Autowired
    private VotoRepositorio votoRepositorio;
    
    public void votar(String id, String CUIT,Valoraciones val){
        
        Voto voto = new Voto();
        int puntaje;
        
        switch (val.toString()) {
            case "MALA":
                puntaje = 1;
                break;
            case "REGULAR":
                puntaje = 2;
                break;
            case "BUENA":
                puntaje = 3;
                break;
            case "MUY_BUENA":
                puntaje = 4;
                break;
            default:
                puntaje = 5;
                break;
        }
        
        Optional<Usuario> respuesta2 = usuarioRepositorio.findById(id);
        Optional<Prestador> respuesta = prestadorRepositorio.findById(CUIT);
                
        if (respuesta.isPresent()) {
            Prestador prestador1 = respuesta.get();
            
            if (prestador1.getCuit().equals(CUIT)) {
                voto.setPrestador(prestador1);
            }else {
                throw new Error("No tiene permisos para realizar la operación solicitada");
            }
//            prestador1.getValoraciones().add(puntaje);
//            int promedio = prestadorService.promediar(prestador1.getValoraciones());
//            prestador1.setValoracion(promedio);

            if(prestador1.getValoracion() == null){
                prestador1.setValoracion(puntaje);
            }else{
                prestador1.setValoracion(Math.toIntExact((prestador1.getValoracion()+puntaje)/2));
            }
            
            prestadorRepositorio.save(prestador1);
        }else {
            throw new Error("No existe un prestador vinculado a ese identificador");
        }
        
        if (respuesta2.isPresent()) {
            Usuario usuario1 = respuesta2.get();
            
            if (usuario1.getDni().equals(id)) {
                voto.setUsuario(usuario1);
            }else {
                throw new Error("No tiene permisos para realizar la operación solicitada");
            }
        }else {
            throw new Error("No existe un usuario vinculado a ese identificador");
        }
        
        votoRepositorio.save(voto);
        
    }
    
    
}
