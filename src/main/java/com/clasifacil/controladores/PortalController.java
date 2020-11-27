package com.clasifacil.controladores;

import com.clasifacil.entidades.Prestador;
import com.clasifacil.entidades.Zona;
import com.clasifacil.service.PrestadorService;
import com.clasifacil.service.ZonaService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalController {

    @Autowired
    private PrestadorService prestadorService;

    @Autowired
    private UsuarioController usuarioController;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login(HttpSession session, ModelMap modelo, @RequestParam(required = false) String logout, @RequestParam(required = false) String error) {

        checkLogueado(modelo,session);

        if (error != null && !error.isEmpty()) {
            modelo.addAttribute("error", "Mail o Clave incorrectos.");
        }

        if (logout != null && !logout.isEmpty()) {
            modelo.addAttribute("logout", "Has salido de la plataforma exitosamente.");
        }

        return "login.html";
    }

    @GetMapping("/{rubro}")
    public String buscar(ModelMap modelo, @PathVariable("rubro") String rubro) {
        System.out.println(rubro);
        List<Prestador> prestadores = prestadorService.listarPorRubro(rubro);
        modelo.put("prestadores", prestadores);

        return "index.html";
    }

    private String checkLogueado(ModelMap modelo, HttpSession session) {
        if (session.getAttribute("usuariosession") != null || session.getAttribute("prestadorsession") != null) {
            if (session.getAttribute("role").equals("prestador")) {
                return "inicio-prestador.html";
            }

            return usuarioController.inicio(modelo, session);
        }
        
        return null;
    }
}
