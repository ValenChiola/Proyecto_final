
package com.clasifacil.controladores;

import com.clasifacil.entidades.Foto;
import com.clasifacil.repositorios.FotoRepositorio;
import com.clasifacil.service.FotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/foto")
public class FotoController {

	@Autowired
	private FotoService fotoService;
        
        @Autowired
        private FotoRepositorio fotoRepositorio;
       

	@GetMapping("/load/{id}")
	public ResponseEntity<byte[]> photo(@PathVariable String id) {
		Foto foto = fotoRepositorio.getOne(id);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<>(foto.getContenido(),headers, HttpStatus.OK);
	}
	
}	

