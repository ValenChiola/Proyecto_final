
package com.clasifacil.repositorios;


import com.clasifacil.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface FotoRepositorio extends JpaRepository<Foto,String>{

    
}
