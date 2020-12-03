package com.clasifacil.controladores;

import com.clasifacil.entidades.Prestador;
import com.clasifacil.entidades.Zona;
import com.clasifacil.enums.Rubros;
import com.clasifacil.service.PrestadorService;
import com.clasifacil.service.ZonaService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/prestador")
public class PrestadorController {

    @Autowired
    private PrestadorService prestadorService;

    @Autowired
    private ZonaService zonaService;

    @GetMapping("/registro")
    public String registrar(ModelMap modelo) {
        List<Zona> zonas = zonaService.listarTodas();
        modelo.put("zonas", zonas);
        return "registro-prestador.html";
    }

    @PostMapping("/registrar")
    public String registrar(ModelMap modelo,
            @RequestParam String cuit,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String mail,
            @RequestParam String clave,
            @RequestParam String clave2,
            @RequestParam String telefono,
            @RequestParam String idZona,
            @RequestParam MultipartFile foto,
            @RequestParam String descripcion,
            @RequestParam Rubros rubro) {

        try {
            prestadorService.registrar(cuit, nombre, apellido, mail, clave, clave2, telefono, idZona,
                    foto, descripcion, rubro);
        } catch (Error ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("cuit", cuit);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("clave", clave);
            modelo.put("clave2", clave2);
            modelo.put("telefono", telefono);
            modelo.put("idZona", idZona);
            modelo.put("idFoto", foto);
            modelo.put("descripcion", descripcion);
            modelo.put("rubro", rubro);

            return registrar(modelo);
        } catch (Exception ex) {
            modelo.put("error", "Foto muy pesada - No debe superar 1MB");
            modelo.put("cuit", cuit);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("clave", clave);
            modelo.put("clave2", clave2);
            modelo.put("telefono", telefono);
            modelo.put("idZona", idZona);
            modelo.put("descripcion", descripcion);
            modelo.put("rubro", rubro);
            
            return registrar(modelo);
        }
        modelo.put("titulo", "Bievenido");
        modelo.put("descripcion", "El prestador del servicio se registro con exito");
        return "index.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_PRESTADOR')")
    @GetMapping("/modificar")
    public String modificar(ModelMap modelo, HttpSession session, @RequestParam String cuit) {
        List<Zona> zonas = zonaService.listarTodas();
        modelo.put("zonas", zonas);

        Prestador p = prestadorService.buscarPrestadorPorCuit(cuit);
        modelo.addAttribute("perfil", p);
        return "modificar-prestador.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PRESTADOR')")
    @PostMapping("/actualizar")
    public String modificarPrestador(ModelMap modelo,
            @RequestParam String cuit,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String mail,
            @RequestParam String clave,
            @RequestParam String clave2,
            @RequestParam String telefono,
            @RequestParam String idZona,
            @RequestParam MultipartFile foto,
            @RequestParam String descripcion,
            @RequestParam Rubros rubro,
            HttpSession session) {

        try {
            prestadorService.ModificarPrestador(cuit, nombre,
                    apellido, mail, clave, clave2, telefono,
                    idZona, foto, descripcion, rubro);

            Prestador p = prestadorService.buscarPrestadorPorCuit(cuit);
            session.setAttribute("prestadorsession", p);

        } catch (Error ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("cuit", cuit);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("clave", clave);
            modelo.put("clave2", clave2);
            modelo.put("telefono", telefono);
            modelo.put("idZona", idZona);
            modelo.put("foto", foto);
            modelo.put("descripcion", descripcion);
            modelo.put("rubro", rubro);

            return modificar(modelo, session, cuit);
        }catch (Exception ex) {
            modelo.put("error", "Foto muy pesada - No debe superar 1MB");
            modelo.put("cuit", cuit);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("mail", mail);
            modelo.put("clave", clave);
            modelo.put("clave2", clave2);
            modelo.put("telefono", telefono);
            modelo.put("idZona", idZona);
            modelo.put("descripcion", descripcion);
            modelo.put("rubro", rubro);
            
            return modificar(modelo, session, cuit);
        }
        modelo.put("Exito", "Se modifico");
        modelo.put("descripcion", "El prestador del servicio se modifico con exito");
        return "redirect:/prestador/inicio";

    }

    @PreAuthorize("hasAnyRole('ROLE_PRESTADOR')")
    @GetMapping("/inicio")
    public String inicio() {
        return "inicio-prestador.html";
    }
}
