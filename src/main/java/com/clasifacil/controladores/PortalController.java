
package com.clasifacil.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalController {
    
    
    
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
  
    @GetMapping("/login")
    public String login(ModelMap modelo, @RequestParam(required = false) String logout, @RequestParam(required = false) String error) {

        if (error != null && !error.isEmpty()) {
            modelo.addAttribute("error", "Mail o Clave incorrectos.");
        }

        if (logout != null && !logout.isEmpty()) {
            modelo.addAttribute("logout", "Has salido de la plataforma exitosamente.");
        }

        return "login.html";
    }
}
