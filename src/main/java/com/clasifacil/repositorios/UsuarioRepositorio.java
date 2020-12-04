/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clasifacil.repositorios;

import com.clasifacil.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Estela
 */
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {

    @Query("SELECT u FROM Usuario u WHERE u.mail LIKE :mail")
    public Usuario buscarPorMail(@Param("mail") String mail);

    @Query("SELECT u FROM Usuario u WHERE u.rol LIKE 'REGULAR'")
    public List<Usuario> listarTodos();

    @Query("SELECT u FROM Usuario u WHERE u.mail LIKE :mail AND u.rol LIKE 'REGULAR'")
    public List<Usuario> buscarPorParteDeMail(@Param("mail") String mail);

    @Query("SELECT u FROM Usuario u WHERE u.dni LIKE :dni")
    public Usuario buscarPorDNI(@Param("dni") String dni);

}
