/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dal;

import Conexion.Conexion;
import Modelo.MateriaMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class MateriaDal {

    Conexion obtener = new Conexion();
    Connection conn;
    MateriaMd cl = new MateriaMd();
    
    
    
      public MateriaMd Mostrarpro(String usuario) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new MateriaMd();
        String query0 = "select usu_codigo, usu_nombre from USUARIOS where usu_estatus=1 and usu_user='" + usuario + "';";
//                + "SELECT pro_descripcion,pro_tipo,pro_tipo_servicio,pro_marca,pro_presentacion,pro_precio_compra,pro_precio_venta,\n"
//                + "pro_descuento,pro_stock_barrita,pro_stock_Carrizal,pro_stock_angeles,pro_conversion,pro_medida,pro_minimo,pro_maximo,pro_ubicacion,pro_ferreteria\n"
//                + "from  almacen.productos\n"
//                + " where pro_id='" + producto + "';";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
               cl.setProCodigo(rs.getString(1));
                cl.setProNombre(rs.getString(2));
                cl.setResp("1");
                cl.setMsg("ACTUALIZAR DATOS DE CATEDRATICO.!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg("PROFESOR ESTA  <br/>  <br/>  INACTIVO");

            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return cl;
    }

    public List<MateriaMd> allproyecto() {
        List<MateriaMd> data = new ArrayList<MateriaMd>();
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;

        String query0 = "select usu_codigo, usu_nombre from USUARIOS where usu_estatus=1;";
//                + "select pro_id, CONCAT(pro_nombre, ' ', pro_apellido) from profesores;";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                cl = new MateriaMd();
                resp = 1;
                cl.setProCodigo(rs.getString(1));
                cl.setProNombre(rs.getString(2));
                cl.setMsg("TODOS LOS PROYECTOS.!");
                data.add(cl);
            }
            st.close();
            rs.close();

            if (resp == 0) {

                cl.setResp("0");
                cl.setMsg("CURSO NO EXISTEN");
            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return data;
    }

    public MateriaMd savegrado(MateriaMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new MateriaMd();

        String query1 = " SELECT max(cur_id)+1 as id FROM cursos; ";
        String sql = "INSERT INTO `notas1.0`.`cursos`"
                + " (`cur_id`, `cur_nombre`, `pro_id`, `cur_seccion`, `cur_usuario`, `cur_alta`)"
                + " VALUES (?, ?, ?, ?, ?, NOW());";
        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);

            if (resp == 0) {
                st = conn.createStatement();
                rs = st.executeQuery(query1);
                while (rs.next()) {
                    id = rs.getString("id");
                }
                st.close();
                rs.close();

                ps = conn.prepareStatement(sql);

                ps.setString(1, id);
                ps.setString(2, data.getMateria());
                ps.setString(3, data.getProCodigo());
                ps.setString(4, data.getSeccion());
                ps.setString(5, data.getUsuario());

                ps.executeUpdate();
                ps.close();
                conn.commit();
                cl.setResp("1");
                cl.setMsg("MATERIA GUARDADA CORRECTAMENTE");
            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return cl;
    }

}
