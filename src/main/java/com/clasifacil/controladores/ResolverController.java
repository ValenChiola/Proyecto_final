package com.clasifacil.controladores;

import com.clasifacil.entidades.Usuario;
import java.util.Properties;
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
    public RedirectView resolver(HttpSession session) {
        String rol = (String) session.getAttribute("role");

        if (rol.equals("prestador")) {
            return new RedirectView("/prestador/inicio");
        } else {
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            if (u.getHabilitado()) {
                return new RedirectView("/usuario/inicio");
            }

            RedirectView r = new RedirectView("/login");
            Properties p = new Properties();
            p.setProperty("error", "Has sido deshabilitado");
            
            r.setAttributes(p);
            return r;
        }
    }
}
