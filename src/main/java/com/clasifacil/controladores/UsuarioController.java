
package com.clasifacil.controladores;

import com.clasifacil.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/registro")
    public String registro(){
        return "registro-usuario.html";
    }
    
    @PostMapping("/registrar")
    public String registrar(ModelMap modelo, @RequestParam String dni,
            @RequestParam String nombre,@RequestParam String apellido,@RequestParam String mail,
            @RequestParam String telefono,@RequestParam String clave1,@RequestParam String clave2,
            @RequestParam String idZona) throws Error{
        
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
            
            return "registro-usuario.html";
        
        }
        
        modelo.put("exito", "Te has registrado existosamente");
        return "index.html";
    }
}
