/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clasifacil.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Estela
 */
@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    private String CUIT;
    
    private Usuario usuario;
    
    private Prestador prestador;

    public Voto() {
    }

    public Voto(String id, String CUIT, Usuario usuario, Prestador prestador) {
        this.id = id;
        this.CUIT = CUIT;
        this.usuario = usuario;
        this.prestador = prestador;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCUIT() {
        return CUIT;
    }

    public void setCUIT(String CUIT) {
        this.CUIT = CUIT;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    @Override
    public String toString() {
        return "Voto{" + "id=" + id + ", CUIT=" + CUIT + ", usuario=" + usuario + ", prestador=" + prestador + '}';
    }
    
    
}
