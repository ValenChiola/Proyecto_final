package com.clasifacil.controladores;

import com.clasifacil.enums.Valoraciones;
import com.clasifacil.service.PrestadorService;
import com.clasifacil.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/votar")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @GetMapping("/{cuit}/{valoracion}")
    public String votar(@RequestParam String dni,@PathVariable("cuit") String cuit,
            @PathVariable("valoracion") Valoraciones valoracion){
        
        votoService.votar(dni, cuit, valoracion);
        
        return "redirect:/usuario/inicio";
    }
}
