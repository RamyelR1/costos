package Ctrl;

import Dal.ParametrosDal;
import Modelo.ParametrosMd;
import Util.EstilosReporte;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Textbox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;


public class EstudianteCtrl extends GenericForwardComposer {

    private Textbox codigo;
    private Textbox nom;
    private Textbox apelli;
    private Combobox sexo;
    private Textbox dpi;
    private Combobox nacionalidad;
    
    private Datebox nacimi;
    ParametrosMd usMd = new ParametrosMd();
     
    ParametrosDal usd = new ParametrosDal();
    String User;

    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
         User = desktop.getSession().getAttribute("USUARIO").toString();

    }
      public void onClick$btnDescargar(Event e) throws SQLException, ClassNotFoundException, IOException {
      
        ReporteExcel(User);
    }
        public void ReporteExcel(String usuario) throws SQLException, IOException, ClassNotFoundException {
       // ReporteCliDal dataBase = new ReporteCliDal();
        
        List<ParametrosMd> allReporteCli = new ArrayList<ParametrosMd>();
        // List<ReporteViajesMd> lista = dataBase.GetByFecha(cambio_fecha(fechaInicial), cambio_fecha(fechaFinal));
        ParametrosDal repDal = new ParametrosDal();
        allReporteCli = repDal.REGselect();
        //PARA OBTENER LA FECHA
        usMd = new ParametrosMd();
        usMd= repDal.obtenerFecha();
        String fecha=usMd.getObtenerFecha();
        

        Workbook workbook = new HSSFWorkbook();

        EstilosReporte estilo = new EstilosReporte();
        CellStyle style = estilo.Estilo1(workbook);
        CellStyle style2 = estilo.Estilo2(workbook);
        CellStyle styleEntero = estilo.EstiloEnteros2(workbook);
        //hoja
        String dirImagen = desktop.getWebApp().getRealPath("bootstrap") + "/img/icono.png";
        
        Sheet listSheet = workbook.createSheet("Reporte Estudiantes");
        //estilo.insertImage(workbook, listSheet, dirImagen);
        //filas
        Row titulo1 = listSheet.createRow(0);
        Row titulo2 = listSheet.createRow(1);
        Row titulo3 = listSheet.createRow(2);
        Row encabezado = listSheet.createRow(3);
        int index = 4;

        Cell c1 = titulo1.createCell(1);
        c1.setCellValue("ESCUELA");
        c1.setCellStyle(style);

        Cell c2 = titulo2.createCell(1);
        c2.setCellValue("REPORTE DE ESTUDIANTES");
        c2.setCellStyle(style);

        Cell c3 = titulo3.createCell(1);
        c3.setCellValue("FECHA: " + fecha+", USUARIO: "+ usuario);
        c3.setCellStyle(style);

        Cell cE1 = encabezado.createCell(0);
        cE1.setCellValue("NOMBRE ESTUDIANTE");
        cE1.setCellStyle(style2);

        Cell cE2 = encabezado.createCell(1);
        cE2.setCellValue("GENERO");
        cE2.setCellStyle(style2);

        Cell cE3 = encabezado.createCell(2);
        cE3.setCellValue("FEHCA DE NACIMIENTO");
        cE3.setCellStyle(style2);

        Cell cE4 = encabezado.createCell(3);
        cE4.setCellValue("DPI");
        cE4.setCellStyle(style2);

        Cell cE5 = encabezado.createCell(4);
        cE5.setCellValue("NACIONALIDAD");
        cE5.setCellStyle(style2);

        

        
        
        for (ParametrosMd item : allReporteCli) {
            Row contenido = listSheet.createRow(index++);

            Cell cC1 = contenido.createCell(0);
            cC1.setCellValue(item.getNombre());
            cC1.setCellStyle(styleEntero);

            Cell cC2 = contenido.createCell(1);
            cC2.setCellValue(item.getSexo());
            cC2.setCellStyle(style2);
            
            
            Cell cC3 = contenido.createCell(2);
            cC3.setCellValue(item.getFechaNaci());
            cC3.setCellStyle(style2);

            Cell cC4 = contenido.createCell(3);
            cC4.setCellValue(item.getDpi());
            cC4.setCellStyle(style2);

            Cell cC5 = contenido.createCell(4);
            cC5.setCellValue(item.getNacionalidad());
            cC5.setCellStyle(style2);

           
           
        }

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            estilo.autoSizeColumns(workbook, 4);
            workbook.write(baos);
            AMedia amedia = new AMedia("REPORTE DE ESTUDIANTES.xls", "xls", "application/file", baos.toByteArray());
            Filedownload.save(amedia);
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onClick$btnGuardar(Event e) throws SQLException, ClassNotFoundException {
        int op = 0;

        if (nom.getText().trim().equals("")) {
            op = 1;
        }
        if (codigo.getText().trim().equals("")) {
            op = 1;
        }
        if (nacimi.getText().trim().equals("")) {
            op = 1;
        }
        if (apelli.getText().trim().equals("")) {
            op = 1;
        }
       
        if (sexo.getSelectedIndex() == -1) {
            op = 1;
        }
        if (nacionalidad.getSelectedIndex() == -1) {
            op = 1;
        }
        if (op == 0) {
            usMd = new ParametrosMd();
            usMd.setCodigo(codigo.getText().toUpperCase());
            usMd.setNombre(nom.getText().toUpperCase());
            usMd.setApellido(apelli.getText().toUpperCase());
            usMd.setDpi(dpi.getText().toUpperCase());
            usMd.setSexo(sexo.getSelectedItem().getValue().toString());
            usMd.setNacionalidad(nacionalidad.getSelectedItem().getValue().toString());
            usMd.setFechaNaci(nacimi.getText());
            usMd.setUsuario(User);

            usMd = usd.saveUS(usMd);
            if (usMd.getResp().equals("1")) {
                clear();
                Clients.showNotification(usMd.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
            } else {
                Clients.showNotification(usMd.getMsg() + "<br/>",
                        Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 0);
            }

        } else {
            Clients.showNotification("No puede dejar <br/>  <br/> Campos Vacios <br/> <br/>Intentelo de Nuevo",
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 0);
        }

    }
    
    public void onClick$btnNuevo(Event e) throws SQLException, ClassNotFoundException {
        clear();
    }
     public void clear(){
        nom.setText("");
        codigo.setText("");
        apelli.setText("");
        
        nacimi.setText("");
        sexo.setSelectedIndex(-1);
     
    }

}
