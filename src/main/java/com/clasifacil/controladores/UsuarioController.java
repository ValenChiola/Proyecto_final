package com.clasifacil.controladores;

import com.clasifacil.entidades.Prestador;
import com.clasifacil.entidades.Usuario;
import com.clasifacil.entidades.Zona;
import com.clasifacil.repositorios.PrestadorRepositorio;
import com.clasifacil.repositorios.ZonaRepositorio;
import com.clasifacil.service.UsuarioService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
    private ZonaRepositorio zonaRepositorio;
    
    @Autowired
    private PrestadorRepositorio prestadorRepositorio;
    
    @GetMapping("/registro")
    public String registro(ModelMap modelo) {
        List<Zona> zonas = zonaRepositorio.findAll();
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
        
        try {
            
            usuarioService.deshabiltar(dni);
            
        } catch (Error e) {
            modelo.put("error", e.getMessage());
            modelo.put("dni", dni);
            
            return "deshabiltar.html";
        }
        
        return "redirect:/usuario/inicio";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/habilitar/{dni}")
    public String habiltar(ModelMap modelo, @PathVariable("dni") String dni) {
        
        try {
            
            usuarioService.habiltar(dni);
            
        } catch (Error e) {
            modelo.put("error", e.getMessage());
            modelo.put("dni", dni);
            
            return "habiltar.html";
        }
        
        return "redirect:/usuario/inicio";
    }
    
    @GetMapping("/modificar")
    public String modificar(ModelMap modelo, HttpSession session, @RequestParam String dni) {
        List<Zona> zonas = zonaRepositorio.findAll();
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
            
            return modificar(modelo,session,dni);
            
        }
        
        modelo.put("exito", "Te has registrado existosamente");
        return "redirect:/usuario/inicio";
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_REGULAR')")
    @GetMapping("/inicio")
    public String inicio(ModelMap modelo) {
        List<Prestador> prestadores = prestadorRepositorio.findAll();
        modelo.put("prestadores", prestadores);
        return "inicio-usuario.html";
    }
    
    @GetMapping("/buscar/{rubro}")
    public String buscarPorRubro(ModelMap modelo, @PathVariable("rubro") String rubro) {
        List<Prestador> prestadores = prestadorRepositorio.listarPorRubro(rubro);
        modelo.put("prestadoresBuscados", prestadores);
        
        return "inicio-usuario.html";
    }
    
    @GetMapping("/{cuit}/details")
    public String leerMas(ModelMap modelo,@PathVariable("cuit") String cuit){
        
        Prestador prestador = prestadorRepositorio.buscarPrestadorPorCuit(cuit);
        modelo.put("cuit",cuit);
        modelo.put("nombre",prestador.getNombre());
        modelo.put("apellido",prestador.getApellido());
        modelo.put("telefono",prestador.getTelefono());
        modelo.put("serviciosPrestados",prestador.getServiciosprestados());
        modelo.put("descripcion",prestador.getDescripcion());
        modelo.put("foto",prestador.getFoto().getId());
        
        return "leer-mas.html";
    }
    
    @GetMapping("{cuit}")
    public String eliminar(ModelMap modelo,@PathVariable("cuit") String cuit){
        prestadorRepositorio.delete(prestadorRepositorio.buscarPrestadorPorCuit(cuit));
        
        return inicio(modelo);
    }
}
