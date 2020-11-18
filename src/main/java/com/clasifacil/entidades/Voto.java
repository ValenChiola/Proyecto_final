package com.clasifacil.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Voto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    private Usuario usuario;
    
    private Prestador prestador;

    public Voto() {
    }

    public Voto(String id, Usuario usuario, Prestador prestador) {
        this.id = id;
        this.usuario = usuario;
        this.prestador = prestador;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "Voto{" + "id=" + id + ", usuario=" + usuario + ", prestador=" + prestador + '}';
    }
    
    
}
