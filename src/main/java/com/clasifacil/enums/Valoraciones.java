/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clasifacil.enums;

/**
 *
 * @author Usuario
 */
public enum Valoraciones {
    MALA(1),
    REGULAR(2),
    BUENA(3),
    MUY_BUENA(4),
    EXCELENTE(5);
    
    private final Integer valoracion;
    
    Valoraciones(Integer valoracion){
        this.valoracion = valoracion;
    }

    public Integer getValoracion() {
        return valoracion;
    }
}
