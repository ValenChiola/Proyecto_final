package com.clasifacil.repositorios;

import com.clasifacil.entidades.Prestador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestadorRepositorio extends JpaRepository<Prestador, String>{
 
    
     @Query("SELECT u FROM prestador u WHERE u.cuit LIKE :cuit")
    public Prestador buscarPrestadorPorCuit(@Param("cuit") String cuit);
    
    
}
