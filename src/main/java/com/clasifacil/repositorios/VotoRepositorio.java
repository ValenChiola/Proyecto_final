package com.clasifacil.repositorios;

import com.clasifacil.entidades.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepositorio extends JpaRepository<Voto, String>{
    
}
