package com.clasifacil.controladores;

import com.clasifacil.enums.Rubros;
import com.clasifacil.repositorios.PrestadorRepositorio;
import com.clasifacil.service.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/prestador")
public class PrestadorController {

    @Autowired
    private PrestadorService prestadorService;
    @Autowired
    private PrestadorRepositorio prestadorRepositorio;

    @GetMapping("/registro")
    public String registrar() {

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
            @RequestParam Integer serviciosprestados,
            @RequestParam String idFoto,
            @RequestParam String descripcion,
            @RequestParam Rubros rubro) {

        try {
            prestadorService.registrar(cuit, nombre, apellido, mail, clave, clave2, telefono, idZona,
                    serviciosprestados, idFoto, descripcion, rubro);
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
            modelo.put("serviciosprestados", serviciosprestados);
            modelo.put("idFoto", idFoto);
            modelo.put("descripcion", descripcion);
            modelo.put("rubro", rubro);

            return "registrar-prestador.html";
        }
        modelo.put("titulo", "Bievenido");
        modelo.put("descripcion", "El prestador del servicio se registro con exito");
        return "index.html";

    }

    @GetMapping("/modificar-prestador")
    public String modificarPrestador(){
         
        return "registro-prestador.hml";
}
    
    
    @PostMapping("/modificar-prestador")
    public String modificarPrestador(ModelMap modelo,
            @RequestParam String cuit,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String mail,
            @RequestParam String clave,
            @RequestParam String clave2,
            @RequestParam String telefono,
            @RequestParam String idZona,        
            @RequestParam Integer serviciosprestados,     
            @RequestParam String idFoto,
            @RequestParam String descripcion,        
            @RequestParam Rubros rubro){

        try {
            prestadorService.ModificarPrestador(cuit, nombre, 
                    apellido, mail, clave, clave2, telefono,
                    idZona, serviciosprestados, idFoto, descripcion, rubro);
            
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
            modelo.put("serviciosprestados", serviciosprestados);
            modelo.put("idFoto", idFoto);
            modelo.put("descripcion", descripcion);
            modelo.put("rubro", rubro);
            
         
            return "modificar-prestador.html";
        }
        modelo.put("Exito","Se modifico");
        modelo.put("descripcion","El prestador del servicio se modifico con exito");
        return "index.html";

    }
    

}
