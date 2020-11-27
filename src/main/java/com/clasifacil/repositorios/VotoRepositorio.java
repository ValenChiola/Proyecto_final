/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clasifacil.repositorios;

import com.clasifacil.entidades.Voto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Estela
 */
@Repository
public interface VotoRepositorio extends JpaRepository<Voto, String> {
 
    @Query(value="DELETE FROM Voto v WHERE v.prestador_cuit LIKE :cuit",nativeQuery=true)
    public void eliminarVotoPorPrestador(@Param("cuit") String cuit);
   
    @Query(value="SELECT * FROM Voto v WHERE v.prestador_cuit LIKE :cuit",nativeQuery=true)
    public List<Voto> listarVotosPorPrestador(@Param("cuit") String cuit);
}
