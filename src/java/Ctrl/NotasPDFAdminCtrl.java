/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ctrl;

import Dal.NotasPDFDal;
import Modelo.NotasPDFMd;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;

/**
 *
 * @author HP 15
 */
public class NotasPDFAdminCtrl extends GenericForwardComposer {
    
    private Datebox fechaG;
    private Combobox bimestre;
    
      public void doAfterComposer(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
    }
      
       public void onClick$btnDescargar(Event e) throws SQLException, ClassNotFoundException {
       String usuario = (String) desktop.getSession().getAttribute("USUARIO");
       int op=0;
        if (bimestre.getSelectedIndex() == -1) {
            op = 1;
        }
       
        if (!fechaG.getText().isEmpty() && op!=1 ) {
           
                  
           GeneraPDFTodas(fechaG.getText(), usuario, bimestre.getSelectedItem().getValue().toString());
             
                    
      
            
            
        } else {
            Clients.showNotification("INGRESE UNA FECHA Y SELECIONE UN BIMESTRE POR FAVOR PARA PODER GENERAR EL PDF");
        }
    
    }
       
       
        public void GeneraPDFTodas(String anio, String user,String bimestre1) throws SQLException, ClassNotFoundException {
        System.out.println("Generar PDF");
        Document document;
        Paragraph ParrafoHoja = new Paragraph();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        List<NotasPDFMd> alldata = new ArrayList<NotasPDFMd>();
        NotasPDFDal rd = new NotasPDFDal();
        
        alldata = rd.REGselectAdmin(anio,user,bimestre.getSelectedItem().getValue().toString());
      

     
        if (alldata.isEmpty()) {
            Clients.showNotification("NO TIENE DATOS..!");
        }
        try {

            document = new Document();
            document.setPageSize(PageSize.A4.rotate());
            //document.setPageSize(PageSize.LETTER);
            PdfWriter.getInstance(document, baos);
            System.out.println("Despues del PDFWriter" + anio);

            String dirImagen = desktop.getWebApp().getRealPath("bootstrap") + "/img/reportar.png";
            Image im = Image.getInstance(dirImagen);
            im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP);
            im.setAbsolutePosition(25, 700);
            im.scalePercent(10);
            ParrafoHoja.add(im);

            ParrafoHoja.add(new Paragraph("              ESCUELA"));
            ParrafoHoja.add(new Paragraph("              REPORTE DE NOTAS"));
            ParrafoHoja.add(new Paragraph("              BIMESTRE: "+bimestre1));
            ParrafoHoja.add(new Paragraph("              Fecha de Reporte.: " + anio));
            ParrafoHoja.add(new Paragraph("              PROFESOR: " + user));

            //System.out.println("CREAR TABLA");
            float anchosFilas[] = {0.2f, 0.3f, 0.7f, 0.7f,0.7f, 0.2f, 0.7f};//Anchos de las filas
            PdfPTable tabla = new PdfPTable(anchosFilas);
            String rotulosColumnas[] = {"No.","CODIGO", "NOMBRE","GRADO", "CURSO", "NOTA","PROFESOR"};//Rotulos de las columnas 

            tabla.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();

            System.out.println("CONSTRUIR TABLA");
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i]));
                tabla.addCell(cell);
            }

            int i = 0;
            for (NotasPDFMd a : alldata) {
                i++;
                cell = new PdfPCell(new Paragraph(a.getCorrelativo()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getCodigoEstudia()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getNombreEst()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getGrado()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getCurso()));
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph(a.getNota()));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(a.getProfeRepoAdmin()));
                tabla.addCell(cell);
              
            }


            ParrafoHoja.add(tabla);
            document.open();
            document.add(ParrafoHoja);
            document.close();

            AMedia amedia = new AMedia("NOTAS ADMINISTRADOR.PDF", "PDF", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
