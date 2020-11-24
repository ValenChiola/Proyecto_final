package com.clasifacil.controladores;

import com.clasifacil.enums.Valoraciones;
import com.clasifacil.service.PrestadorService;
import com.clasifacil.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/votar")
public class VotoController {

    @Autowired
    private VotoService votoService;
    
    @Autowired
    private PrestadorService prestadorService;
    
    @GetMapping("")
    public String voto(){
        return "voto.html";
    }
    
    @PostMapping("")
    public String votar(@RequestParam String dni,@RequestParam String cuit,
            @RequestParam Valoraciones valoracion){
        
        votoService.votar(dni, cuit, valoracion);
        
        return "redirect:/usuario/inicio";
    }
}
