package com.clasifacil.service;

import com.clasifacil.entidades.Prestador;
import com.clasifacil.entidades.Usuario;
import com.clasifacil.entidades.Voto;
import com.clasifacil.enums.Valoraciones;
import com.clasifacil.repositorios.PrestadorRepositorio;
import com.clasifacil.repositorios.UsuarioRepositorio;
import com.clasifacil.repositorios.VotoRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PrestadorRepositorio prestadorRepositorio;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private VotoRepositorio votoRepositorio;

    public void votar(String id, String CUIT, Valoraciones val) {

        Voto voto = new Voto();
        int puntaje;

        switch (val.toString()) {
            case "MALA":
                puntaje = 1;
                break;
            case "REGULAR":
                puntaje = 2;
                break;
            case "BUENA":
                puntaje = 3;
                break;
            case "MUY_BUENA":
                puntaje = 4;
                break;
            case "EXCELENTE":
                puntaje = 5;
                break;
                
            default:
                puntaje = 0;
        }

        Optional<Usuario> respuesta2 = usuarioRepositorio.findById(id);
        Optional<Prestador> respuesta = prestadorRepositorio.findById(CUIT);

        if (respuesta.isPresent()) {
            Prestador prestador1 = respuesta.get();

            if (prestador1.getCuit().equals(CUIT)) {
                voto.setPrestador(prestador1);
            } else {
                throw new Error("No tiene permisos para realizar la operación solicitada");
            }

            if (prestador1.getValoracion() == null) {
                prestador1.setValoracion(puntaje);
            } else {
                
                float suma = (prestador1.getValoracion() + puntaje);
                float promedio = suma/2;
                
                if (promedio == 4.5) {
                    prestador1.setValoracion(5);
                } else {
                    prestador1.setValoracion((int)promedio);
                }
            }

            prestadorRepositorio.save(prestador1);
        } else {
            throw new Error("No existe un prestador vinculado a ese identificador");
        }

        if (respuesta2.isPresent()) {
            Usuario usuario1 = respuesta2.get();

            if (usuario1.getDni().equals(id)) {
                voto.setUsuario(usuario1);
            } else {
                throw new Error("No tiene permisos para realizar la operación solicitada");
            }
        } else {
            throw new Error("No existe un usuario vinculado a ese identificador");
        }

        votoRepositorio.save(voto);
        notificacionService.enviarPorVoto(id, CUIT);
    }

    @Transactional
    public void eliminarVotoPorPrestador(String cuit) {

        List<Voto> votos = votoRepositorio.listarVotosPorPrestador(cuit);

        if (!votos.isEmpty()) {
            votoRepositorio.eliminarVotoPorPrestador(cuit);
        }
    }

}
