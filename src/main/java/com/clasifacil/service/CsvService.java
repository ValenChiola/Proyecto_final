package com.clasifacil.service;

import com.clasifacil.entidades.Prestador;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CsvService {

    @Autowired
    private PrestadorService prestadorService;

    public void imrpimirListaPrestadores() throws IOException {
        String SAMPLE_CSV_FILE = "C:\\Users\\Usuario\\Desktop\\" + new Date().getDay() + "-" + new Date().getMonth() + "lista-prestadores.csv";

        BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL
                .withHeader("CUIT", "NOMBRE", "APELLIDO", "MAIL", "TELEFONO", "RUBRO", "ZONA", "VALORACION"))) {
            List<Prestador> prestadores = prestadorService.listarTodosPorValoracion();

            for (Prestador p : prestadores) {
                System.out.println("Hola");
                csvPrinter.printRecord(p.getCuit(),p.getNombre(),
                         p.getApellido(),p.getMail(),
                         p.getTelefono(),p.getRubro().toString(),
                        p.getZona().getNombre(), p.getValoracion());
            }
        }
    }
}
