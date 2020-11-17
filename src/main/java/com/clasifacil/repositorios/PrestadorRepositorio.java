package com.clasifacil.repositorios;

import com.clasifacil.entidades.Prestador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestadorRepositorio extends JpaRepository<Prestador, String>{
    
}
