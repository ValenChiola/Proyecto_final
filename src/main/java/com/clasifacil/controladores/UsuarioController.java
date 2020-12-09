package com.clasifacil.controladores;

import com.clasifacil.entidades.Prestador;
import com.clasifacil.entidades.Usuario;
import com.clasifacil.entidades.Zona;
import com.clasifacil.service.CsvService;
import com.clasifacil.service.PrestadorService;
import com.clasifacil.service.UsuarioService;
import com.clasifacil.service.VotoService;
import com.clasifacil.service.ZonaService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CsvService csvService;

    @Autowired
    private ZonaService zonaService;

    @Autowired
    private PrestadorService prestadorService;

    @Autowired
    private VotoService votoService;

    @GetMapping("/registro")
    public String registro(ModelMap modelo) {
        List<Zona> zonas = zonaService.listarTodas();
        modelo.put("zonas", zonas);
        return "registro-usuario.html";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String dni,
            @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail,
            @RequestParam String telefono, @RequestParam String clave1, @RequestParam String clave2,
            @RequestParam String idZona) throws Error {

        try {
            usuarioService.registrar(dni, nombre, apellido, mail, telefono, clave1, clave2, idZona);
        } catch (Error e) {
            modelo.put("error", e.getMessage());
            modelo.put("dni", dni);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("telefono", telefono);
            modelo.put("clave1", clave1);
            modelo.put("clave2", clave2);
            modelo.put("idZona", idZona);

            return registro(modelo);

        }

        modelo.put("exito", "Te has registrado existosamente");
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/deshabilitar/{dni}")
    public String deshabiltar(ModelMap modelo, @PathVariable("dni") String dni) {

        usuarioService.deshabiltar(dni);

        return "redirect:/usuario/listar-usuarios";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/habilitar/{dni}")
    public String habiltar(ModelMap modelo, @PathVariable("dni") String dni) {

        usuarioService.habiltar(dni);

        return "redirect:/usuario/listar-usuarios";
    }

    @GetMapping("/modificar")
    public String modificar(ModelMap modelo, HttpSession session, @RequestParam String dni) {
        List<Zona> zonas = zonaService.listarTodas();
        modelo.put("zonas", zonas);

        Usuario u = usuarioService.buscarPorDNI(dni);
        modelo.addAttribute("perfil", u);

        return "modificar-usuario.html";
    }

    @PostMapping("/actualizar")
    public String modificarUsuario(ModelMap modelo, @RequestParam String dni,
            @RequestParam String nombre, @RequestParam String apellido, @RequestParam String mail,
            @RequestParam String telefono, @RequestParam String clave1, @RequestParam String clave2,
            @RequestParam String idZona, HttpSession session) throws Error {

        try {

            usuarioService.modificarUsuario(dni, nombre, apellido, mail, telefono, clave1, clave2, idZona);

            Usuario u = usuarioService.buscarPorDNI(dni);
            session.setAttribute("usuariosession", u);
        } catch (Error e) {
            modelo.put("error", e.getMessage());
            modelo.put("dni", dni);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("telefono", telefono);
            modelo.put("clave1", clave1);
            modelo.put("clave2", clave2);
            modelo.put("idZona", idZona);

            return modificar(modelo, session, dni);

        }

        modelo.put("exito", "Te has registrado existosamente");
        return "redirect:/usuario/inicio";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_REGULAR')")
    @GetMapping("/inicio")
    public String inicio(Model modelo, HttpSession session) {
            List<Prestador> prestadores = prestadorService.listarTodosPorValoracion();
            modelo.addAttribute("prestadores", prestadores);
            
            return "inicio-usuario.html";
    }

    @GetMapping("inicio/descargar")
    public String descargar(@RequestParam String opc, @RequestParam(required = false) String rubro) {
        try {
            csvService.imrpimirListaPrestadores(opc,rubro);
        } catch (IOException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "redirect:/usuario/inicio";
    }

    @PostMapping("/buscar")
    public String buscarPorRubro(ModelMap modelo, @RequestParam String q) {

        try {
            List<Prestador> prestadores = prestadorService.listarPorRubro("%" + q.substring(0, 3).toUpperCase() + "%");
            modelo.put("prestadores", prestadores);
            Prestador prestador = prestadores.get(0);
            modelo.put("prestador", prestador);
        } catch (Exception e) {
        }

        return "inicio-usuario.html";
    }

    @PostMapping("/buscar-usuarios")
    public String buscarUsuario(ModelMap modelo, @RequestParam String q) {

        List<Usuario> usuarios = new ArrayList<>();
        try {

            usuarios = usuarioService.buscarPorParteDeMail("%" + q + "%");

        } catch (Error e) {
            modelo.put("error", e.getMessage());
        }
        System.out.println(usuarios.size());
        modelo.put("usuarios", usuarios);
        return "listar-usuarios.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_REGULAR')")
    @GetMapping("/{cuit}/details")
    public String leerMas(ModelMap modelo, @PathVariable("cuit") String cuit) {

        Prestador prestador = prestadorService.buscarPrestadorPorCuit(cuit);
        modelo.put("prestador", prestador);

        return "leer-mas.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("{cuit}")
    public String eliminar(HttpSession session, Model modelo, @PathVariable("cuit") String cuit) {

        try {
            votoService.eliminarVotoPorPrestador(cuit);
            prestadorService.eliminar(cuit);
        } catch (Exception e) {
            modelo.addAttribute("error", e.getMessage());
        }
        return inicio(modelo, session);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/listar-usuarios")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioService.listarTodos();
        modelo.put("usuarios", usuarios);

        return "listar-usuarios.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/upgrade")
    public String upragade(ModelMap modelo, @RequestParam String dni) {
        usuarioService.upgrade(dni);
        return listar(modelo);
    }
}
