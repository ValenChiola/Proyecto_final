
package com.clasifacil.entidades;

import com.clasifacil.enums.Rubros;
import com.clasifacil.enums.Valoraciones;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity

public class Prestador {  


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
    private Rubros rubro;
    @Enumerated
    private Valoraciones valoracion;
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

    public Rubros getRubro() {
        return rubro;
    }

    public void setRubro(Rubros rubro) {
        this.rubro = rubro;
    }

    public Valoraciones getValoracion() {
        return valoracion;
    }

    public void setValoracion(Valoraciones valoracion) {
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
