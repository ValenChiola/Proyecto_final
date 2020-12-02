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

    public void imrpimirListaPrestadores(String opc, String rubro) throws IOException {

        List<Prestador> prestadores = null;

        switch (opc) {
            case "todos":
                prestadores = prestadorService.listarTodosPorValoracion();
                break;

            case "buscados":
                prestadores = prestadorService.listarPorRubro(rubro);
                break;
        }

        if (prestadores == null) {
            return;
        }

        String SAMPLE_CSV_FILE = "";

        if (rubro != null) {
            SAMPLE_CSV_FILE = "C:\\Users\\Usuario\\Downloads\\" + "lista-prestadores-" + rubro + ".csv";
        } else {
            SAMPLE_CSV_FILE = "C:\\Users\\Usuario\\Downloads\\" + "lista-prestadores.csv";
        }

        BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));

        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL
                .withHeader("CUIT", "NOMBRE", "APELLIDO", "MAIL", "TELEFONO", "RUBRO", "ZONA", "VALORACION"))) {

            for (Prestador p : prestadores) {
                csvPrinter.printRecord(p.getCuit(), p.getNombre(),
                        p.getApellido(), p.getMail(),
                        p.getTelefono(), p.getRubro().toString(),
                        p.getZona().getNombre(), p.getValoracion());
            }
        }
    }
}
