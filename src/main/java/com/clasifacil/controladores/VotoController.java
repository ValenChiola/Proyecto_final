
package com.clasifacil.controladores;

import com.clasifacil.repositorios.VotoRepositorio;
import com.clasifacil.service.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/votar")
public class VotoController {

@Autowired
private VotoRepositorio votoRepositorio;
@Autowired
private PrestadorService prestadorService;


}
