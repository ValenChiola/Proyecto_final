
package com.clasifacil.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Prestador {

    public Object getCUIT() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    


    @Id
    private String cuit;
    private String nombre;
    private String apellido;
    private String mail;
    private String clave;
    private String telefono;
    private Integer serviciosprestados;
    private String descripcion;
    
    @OneToMany
    private Zona zona;
    @Enumerated
    private Enum rubro;
    @Enumerated
    private Enum valoracion;
    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    @OneToMany
    private Foto foto;

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getServiciosprestados() {
        return serviciosprestados;
    }

    public void setServiciosprestados(Integer serviciosprestados) {
        this.serviciosprestados = serviciosprestados;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public Enum getRubro() {
        return rubro;
    }

    public void setRubro(Enum rubro) {
        this.rubro = rubro;
    }

    public Enum getValoracion() {
        return valoracion;
    }

    public void setValoracion(Enum valoracion) {
        this.valoracion = valoracion;
    }

    public Date getAlta() {
        return alta;
    }

    public void setAlta(Date alta) {
        this.alta = alta;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    

}
