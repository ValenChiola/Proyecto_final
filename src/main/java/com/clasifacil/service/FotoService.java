
package com.clasifacil.service;


import com.clasifacil.entidades.Foto;
import com.clasifacil.repositorios.FotoRepositorio;
import java.io.IOException;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import org.springframework.stereotype.Service;


@Service
public class FotoService {

    @Autowired
    private FotoRepositorio fotoRepositorio;

    public Foto guardar(MultipartFile archivo) throws Error {

        if (archivo != null) {
            try {

                Foto foto = new Foto();
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public Foto actulizar(String idFoto, MultipartFile archivo) throws Error {

        if (archivo != null) {
            try {

                Foto foto = new Foto();
                if (idFoto != null) {
                    Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                    if (respuesta.isPresent()) {
                        foto = respuesta.get();
                    }

                }
                foto.setMime(archivo.getContentType());
                foto.setNombre(archivo.getName());
                foto.setContenido(archivo.getBytes());

                return fotoRepositorio.save(foto);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    
    @Transactional
    public Foto buscar(String Id) throws Error{
        
        return fotoRepositorio.getOne(Id);
       // Optional<Foto> foto = fotoRepositorio.findById(Id);
//        
//        if (foto.isPresent()) {
//            
//            return foto.get();
//        } else {
//            return null;
//        }
       
    }
    }
