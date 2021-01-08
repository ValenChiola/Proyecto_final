package com.clasifacil.controladores;

import com.clasifacil.entidades.Prestador;
import com.clasifacil.entidades.Usuario;
import com.clasifacil.service.NotificacionService;
import com.clasifacil.service.PrestadorService;
import com.clasifacil.service.UsuarioService;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PortalController {

    @Autowired
    private PrestadorService prestadorService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login(HttpSession session, ModelMap modelo, @RequestParam(required = false) String logout, @RequestParam(required = false) String error) {

        if (error != null && !error.isEmpty()) {
            if (error.equals("error")) {
                modelo.addAttribute("error", "Mail o Clave incorrecta");
            } else {
                modelo.addAttribute("error", error);
            }
        }

        if (logout != null && !logout.isEmpty()) {
            modelo.addAttribute("logout", "Has salido de la plataforma exitosamente.");
        }

        return checkLogueado(session);
    }

    private String checkLogueado(HttpSession session) {
        if (session.getAttribute("usuariosession") != null || session.getAttribute("prestadorsession") != null) {
            if (session.getAttribute("role").equals("prestador")) {
                return "redirect:/prestador/inicio";
            }
            Usuario u = (Usuario) session.getAttribute("usuariosession");
            if (u.getHabilitado()) {
                return "redirect:/usuario/inicio";
            }
        }

        return "login.html";
    }

    @GetMapping("/buscar/{rubro}")
    public String buscar(ModelMap modelo, @PathVariable("rubro") String rubro) {
        System.out.println(rubro);
        List<Prestador> prestadores = prestadorService.listarPorRubro(rubro);
        modelo.put("prestadores", prestadores);

        return "index.html";
    }

    @GetMapping("/contacto")
    public String contacto() {

        return "contacto.html";
    }

    @PostMapping("/contactar")
    public String contactar(ModelMap modelo, @RequestParam String mail, @RequestParam String mensaje) {

        try {
            checkMensajeYMail(mensaje, mail);

            notificacionService.enviarContacto(mensaje, "Consulta", mail);
        } catch (Error e) {
            modelo.put("error", e.getMessage());

            return "contacto.html";
        }

        return "redirect:/";

    }

    private void checkMensajeYMail(String mensaje, String mail) throws Error {

        if (mensaje.trim().isEmpty() || mensaje == null) {
            throw new Error("El mensaje no puede estar vacío.");
        }

        if (!mail.contains("@")) {
            throw new Error("Ingrese un mail válido.");
        }
    }

    @GetMapping("/recuperacion")
    public String recuperaracion() {
        return "recuperar.html";
    }

    @PostMapping("/recuperar")
    public String recuperar(ModelMap modelo, @RequestParam String mail) {
        try {
            checkMensajeYMail(mail, mail);

            String retorno = checkIfSomebodyIsInTheDataBasePorMail(mail);

            if (retorno.equals("usuario")) {
                usuarioService.recuperarContrasenia(mail);
            } else {
                prestadorService.recuperarContrasenia(mail);
            }

        } catch (Error e) {
            modelo.put("error", e.getMessage());
            modelo.put("mail", mail);

            return "recuperar.html";
        }

        modelo.put("exito", "Se ha enviado tu nueva contraseña a tu mail. Luego podrás cambiarla en tu perfil.");
        return "login.html";
    }

    private String checkIfSomebodyIsInTheDataBasePorMail(String mail) throws Error {
        Usuario u = usuarioService.buscarPorMail(mail);
        if (u == null) {
            Prestador p = prestadorService.buscarPorMail(mail);

            if (p == null) {
                throw new Error("No hay nadie con ese mail. No seas chanta");
            }

            return "prestador";
        }

        return "usuario";
    }
}
