
package com.clasifacil.service;

import com.clasifacil.entidades.Prestador;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

@Component("/usuario/inicio")
public class ExcelService extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
       
        response.setHeader("Content-Disposition","attachment;filename=\"listado-prestadores.xlsx\"");
        Sheet hoja = workbook.createSheet("Prestadores");
        
        Row filaTitulo = hoja.createRow(0);
        Cell celda = filaTitulo.createCell(0);
        celda.setCellValue("LISTADO PRESTADORES");
        
        Row filaData = hoja.createRow(2);
       String[] columnas = {"CUIT", "NOMBRE", "APELLIDO", "MAIL","TELEFONO", "ZONA", "RUBRO", "VALORACION"};
       
        for (int i = 0; i<columnas.length; i++) {
          
            celda= filaData.createCell(i);
            celda.setCellValue(columnas[i]);
        }
        
        List<Prestador> prestadores = (List<Prestador>) model.get("prestadores");
        
        int numfila =3;
        
        for (Prestador prestador : prestadores) {
           filaData = hoja.createRow(numfila);
           filaData.createCell(0).setCellValue(prestador.getCuit());
           filaData.createCell(1).setCellValue(prestador.getNombre());
           filaData.createCell(2).setCellValue(prestador.getApellido());
           filaData.createCell(3).setCellValue(prestador.getMail());
           filaData.createCell(4).setCellValue(prestador.getTelefono());
           filaData.createCell(5).setCellValue(prestador.getZona().getNombre());
           filaData.createCell(6).setCellValue(prestador.getRubro().toString());
           filaData.createCell(0).setCellValue(prestador.getValoracion());
           
           numfila ++;
        }
        
        
    }
    
    
 
    
}
 