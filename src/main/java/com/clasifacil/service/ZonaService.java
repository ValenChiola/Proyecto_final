/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clasifacil.service;

import com.clasifacil.entidades.Zona;
import com.clasifacil.repositorios.ZonaRepositorio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Estela
 */
@Service
public class ZonaService {
    
    @Autowired
    private ZonaRepositorio zonaRepostorio;
    
    @Transactional
    public List<Zona> listarTodas(){
        return zonaRepostorio.findAll();
    }
}
