/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dal;

import Conexion.Conexion;
import Modelo.PromedioMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author HP 15
 */
public class PromedioDal {

    Connection conexion = null;
    Conexion cnn = new Conexion();
    PreparedStatement ps = null;

    public List<PromedioMd> BuscarProAdmin(String grado) throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        List<PromedioMd> allBusca = new ArrayList<PromedioMd>();
        String query = " select b.est_codigo,CONCAT(b.est_nombre, ' ', b.est_apellido),c.cur_nombre,round(avg(a.not_calificacion),0),d.USU_NOMBRE\n"
                + "    from notas a,\n"
                + "         estudiantes b,\n"
                + "         cursos c,\n"
                + "         USUARIOS d\n"
                + "    where a.pro_id=d.USU_CODIGO and a.asig_id='" + grado + "' and a.est_id=b.est_id and a.cur_id=c.cur_id \n"
                + "    group by a.cur_id,a.est_id,b.est_nombre,b.est_apellido,b.est_codigo,d.USU_NOMBRE;";
            

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            PromedioMd rg;
            while (rs.next()) {
                rg = new PromedioMd();
                rg.setCodigo(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setCurso(rs.getString(3));
                rg.setNota(rs.getString(4));
                rg.setProfesor(rs.getString(5));

                allBusca.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allBusca;
    }

    public List<PromedioMd> BuscarProd(String grado, String usuario) throws SQLException, ClassNotFoundException {
        Statement st = null;
        ResultSet rs = null;
        List<PromedioMd> allBusca = new ArrayList<PromedioMd>();
        String query = "  select b.est_codigo,CONCAT(b.est_nombre, ' ', b.est_apellido),c.cur_nombre,round(avg(a.not_calificacion),0)\n"
                + "    from notas a,\n"
                + "         estudiantes b,\n"
                + "         cursos c,\n"
                + "         USUARIOS d\n"
                + "         \n"
                + "    where a.pro_id=d.USU_CODIGO and a.asig_id='" + grado + "' and a.est_id=b.est_id and a.cur_id=c.cur_id and d.USU_USER='" + usuario + "'\n"
                + "    group by a.cur_id,a.est_id,b.est_nombre,b.est_apellido,b.est_codigo;";
//             

        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            PromedioMd rg;
            while (rs.next()) {
                rg = new PromedioMd();
                rg.setCodigo(rs.getString(1));
                rg.setNombre(rs.getString(2));
                rg.setCurso(rs.getString(3));
                rg.setNota(rs.getString(4));

                allBusca.add(rg);
            }

            st.close();
            rs.close();
            conexion.close();
            conexion = null;
        } catch (SQLException e) {
            conexion.close();
            conexion = null;
            Clients.showNotification("ERROR AL CONSULTAR <br/> <br/> REGISTROS! <br/> " + e.getMessage().toString(),
                    "warning", null, "middle_center", 0);
        }
        return allBusca;
    }

}
