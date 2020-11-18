
package com.clasifacil.repositorios;

import com.clasifacil.entidades.Prestacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestacionRepositorio extends JpaRepository<Prestacion, String>{
    
    
  @Query("SELECT u FROM prestacion u WHERE u.id LIKE :id")
    public Prestacion buscarPrestacionPorid(@Param("id") String id);   
}
