
package com.clasifacil.repositorios;

import com.clasifacil.entidades.Prestacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestacionRepositorio extends JpaRepository<Prestacion, String>{
    
}
