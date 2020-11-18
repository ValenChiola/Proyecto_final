package com.clasifacil.service;

import com.clasifacil.entidades.Prestador;
import com.clasifacil.entidades.Usuario;
import com.clasifacil.entidades.Voto;
import com.clasifacil.repositorios.PrestadorRepositorio;
import com.clasifacil.repositorios.VotoRepositorio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoService {
    
    
    
    @Autowired
    private PrestadorRepositorio prestadorRepositorio;
    
    public void votar(String id, String CUIT, Usuario usuario, Prestador prestador){
        
        Voto voto = new Voto();
        
        Optional<Prestador> respuesta = prestadorRepositorio.findById(id);
                
        if (respuesta.isPresent()) {
            Prestador prestador1 = respuesta.get();
            
        }
        
        
        
    }
    
    
}
