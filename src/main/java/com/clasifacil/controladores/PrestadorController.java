package com.clasifacil.controladores;

import com.clasifacil.entidades.Zona;
import com.clasifacil.enums.Rubros;
import com.clasifacil.repositorios.PrestadorRepositorio;
import com.clasifacil.repositorios.ZonaRepositorio;
import com.clasifacil.service.PrestadorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PrestadorRepositorio prestadorRepositorio;

    @Autowired
    private ZonaRepositorio zonaRepositorio;

    @GetMapping("/registro")
    public String registrar(ModelMap modelo) {
        List<Zona> zonas = zonaRepositorio.findAll();
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
            @RequestParam Integer serviciosPrestados,
            @RequestParam MultipartFile foto,
            @RequestParam String descripcion,
            @RequestParam Rubros rubro) {

        try {
            prestadorService.registrar(cuit, nombre, apellido, mail, clave, clave2, telefono, idZona,
                    serviciosPrestados, foto, descripcion, rubro);
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
            modelo.put("serviciosprestados", serviciosPrestados);
            modelo.put("idFoto", foto);
            modelo.put("descripcion", descripcion);
            modelo.put("rubro", rubro);

            return "registro-prestador.html";
        }
        modelo.put("titulo", "Bievenido");
        modelo.put("descripcion", "El prestador del servicio se registro con exito");
        return "index.html";

    }

    @GetMapping("/modificar")
    public String modificarPrestador() {

        return "modificar-prestador.html";
    }

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
            @RequestParam Integer serviciosPrestados,
            @RequestParam String idFoto,
            @RequestParam String descripcion,
            @RequestParam Rubros rubro) {

        try {
            prestadorService.ModificarPrestador(cuit, nombre,
                    apellido, mail, clave, clave2, telefono,
                    idZona, serviciosPrestados, idFoto, descripcion, rubro);

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
            modelo.put("serviciosPrestados", serviciosPrestados);
            modelo.put("idFoto", idFoto);
            modelo.put("descripcion", descripcion);
            modelo.put("rubro", rubro);

            return "modificar-prestador.html";
        }
        modelo.put("Exito", "Se modifico");
        modelo.put("descripcion", "El prestador del servicio se modifico con exito");
        return "redirect:/prestador/inicio";

    }

    @GetMapping("/inicio-usuario")
    public String inicio() {
        return "inicio-usuario.html";
    }
}
