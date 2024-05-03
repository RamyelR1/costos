/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dal;

import Conexion.Conexion;
import Modelo.NotasPDFMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author HP 15
 */
public class NotasPDFDal {

    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    Statement st = null;
    ResultSet rs = null;

    public List<NotasPDFMd> REGselectAdmin(String anio, String usuario, String bimestre) throws SQLException, ClassNotFoundException {
        List<NotasPDFMd> alldata = new ArrayList<NotasPDFMd>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = "";

        try {
            Date date = formatter.parse(anio);
            formatter.applyPattern("yyyy/MM/dd");
            dateInString = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString2 = "";
        String id = "";

        try {

            String query = "select a.est_codigo,CONCAT(a.est_nombre, ' ', a.est_apellido),b.asig_nombre,c.cur_nombre,d.usu_nombre,\n"
                    + "e.not_calificacion,d.USU_NOMBRE\n"
                    + "from estudiantes a,\n"
                    + "     asignaciones b,  \n"
                    + "     cursos c,\n"
                    + "     USUARIOS d,\n"
                    + "     notas e\n"
                    + "     where e.est_id=a.est_id and e.asig_id=b.asig_id and e.cur_id=c.cur_id and e.pro_id=d.usu_codigo\n"
                    + "     and e.not_bimes='" + bimestre + "';";
//                    + "select a.est_codigo,CONCAT(a.est_nombre, ' ', a.est_apellido),b.asig_nombre,c.cur_nombre,d.usu_nombre,\n"
//                    + "e.not_calificacion\n"
//                    + "from estudiantes a,\n"
//                    + "     asignaciones b,  \n"
//                    + "     cursos c,\n"
//                    + "     USUARIOS d,\n"
//                    + "     notas e\n"
//                    + "     where e.est_id=a.est_id and e.asig_id=b.asig_id and e.cur_id=c.cur_id and e.pro_id=d.usu_codigo\n"
//                    + "     and e.not_bimes='" + bimestre + "' and e.pro_id=d.USU_CODIGO and d.USU_USER='" + usuario + "';";

            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            NotasPDFMd rg;
            int x = 0;

            while (rs.next()) {
                rg = new NotasPDFMd();
                x++;
                rg.setCorrelativo(String.valueOf(x));
                rg.setCodigoEstudia(rs.getString(1));
                rg.setNombreEst(rs.getString(2));
                rg.setGrado(rs.getString(3));

                rg.setCurso(rs.getString(4));
                rg.setUsuario(rs.getString(5));
                rg.setNota(rs.getString(6));
                rg.setProfeRepoAdmin(rs.getString(7));

                alldata.add(rg);
            }

            st.close();
            rs.close();

            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            st.close();
            rs.close();
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return alldata;
    }

    public List<NotasPDFMd> REGselectTodas(String anio, String usuario, String bimestre) throws SQLException, ClassNotFoundException {
        List<NotasPDFMd> alldata = new ArrayList<NotasPDFMd>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = "";

        try {
            Date date = formatter.parse(anio);
            formatter.applyPattern("yyyy/MM/dd");
            dateInString = formatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString2 = "";
        String id = "";

        try {

            String query = "select a.est_codigo,CONCAT(a.est_nombre, ' ', a.est_apellido),b.asig_nombre,c.cur_nombre,d.usu_nombre,\n"
                    + "e.not_calificacion\n"
                    + "from estudiantes a,\n"
                    + "     asignaciones b,  \n"
                    + "     cursos c,\n"
                    + "     USUARIOS d,\n"
                    + "     notas e\n"
                    + "     where e.est_id=a.est_id and e.asig_id=b.asig_id and e.cur_id=c.cur_id and e.pro_id=d.usu_codigo\n"
                    + "     and e.not_bimes='" + bimestre + "' and e.pro_id=d.USU_CODIGO and d.USU_USER='" + usuario + "';";

            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            NotasPDFMd rg;
            int x = 0;

            while (rs.next()) {
                rg = new NotasPDFMd();
                x++;
                rg.setCorrelativo(String.valueOf(x));
                rg.setCodigoEstudia(rs.getString(1));
                rg.setNombreEst(rs.getString(2));
                rg.setGrado(rs.getString(3));

                rg.setCurso(rs.getString(4));
                rg.setUsuario(rs.getString(5));
                rg.setNota(rs.getString(6));

                alldata.add(rg);
            }

            st.close();
            rs.close();

            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            st.close();
            rs.close();
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return alldata;
    }

}
