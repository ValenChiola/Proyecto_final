
package com.clasifacil.repositorios;

import com.clasifacil.entidades.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD

public interface FotoRepositorio extends JpaRepository<Foto,String>{
=======
import org.springframework.stereotype.Repository;

/**
 *
 * @author Estela
 */
@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String>{
>>>>>>> 3ebea1acf965b0df4b7bffe29e89d37ac02a39dc
    
}
