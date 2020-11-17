/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clasifacil.repositorios;

import com.clasifacil.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Estela
 */
@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String>{
    
}
