package com.clasifacil.controladores;

import javax.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/resolver")
@PreAuthorize("isAuthenticated()")
public class ResolverController {
 
    @GetMapping
    public RedirectView resolver(HttpSession session){
        String rol = (String) session.getAttribute("role");
        
        if(rol.equals("prestador")){
            return new RedirectView("/prestador/inicio-prestador");
        }else{
            return new RedirectView("/usuario/inicio-usuario");
        }
    }
}
