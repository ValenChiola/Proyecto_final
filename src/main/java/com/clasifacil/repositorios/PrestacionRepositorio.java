/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clasifacil.repositorios;


import com.clasifacil.entidades.Prestacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

/**
 *
 * @author Estela
 */

public interface PrestacionRepositorio {
    
    
  @Query("SELECT u FROM prestacion u WHERE u.id LIKE :id")
    public Prestacion buscarPrestacionPorid(@Param("id") String id);   
}
