/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clasifacil.repositorios;

import com.clasifacil.entidades.Prestador;
import com.clasifacil.entidades.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Estela
 */

public interface PrestadorRepositorio extends JpaRepository<Prestador, String>{

    
     @Query("SELECT u FROM Prestador u WHERE u.cuit LIKE :cuit")
    public Prestador buscarPrestadorPorCuit(@Param("cuit") String cuit);

    
    
}
