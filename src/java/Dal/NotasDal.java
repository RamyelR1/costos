/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dal;

import Conexion.Conexion;
import Modelo.MateriaMd;
import Modelo.NotasMd;
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
public class NotasDal {

    Conexion obtener = new Conexion();
    Connection conn;
    NotasMd us = new NotasMd();

    public NotasMd updateUS(NotasMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        us = new NotasMd();

        //String sql = " UPDATE USUARIOS SET usu_nombre='" + data.getNombre() + "', usu_telefono='" + data.getTelefono() + "', usu_rol = '" + data.getRol() + "', usu_observaciones = '" + data.getObserva() + "', usu_password = '" + data.getPass() + "' WHERE usu_user ='" + data.getUser() + "' ";
        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);
            int vl = 0;
            st = conn.createStatement();
            vl = st.executeUpdate("UPDATE notas SET `not_calificacion` = '" + data.getNota() + "' "
                    + "WHERE asig_id='" + data.getGraCodigo() + "'"
                    + " and est_id='" + data.getEstCodigo() + "' and cur_id='" + data.getMateCodigo() + "' and not_bimes='" + data.getBimestre() + "'; ");
//                    
            if (vl > 0) {
                us.setResp("1");
                us.setMsg("DATOS ACTUALIZADOS CORRECTAMENTE");
                System.out.println("Actualizacion Exitosa");
            } else {
                us.setResp("0");
                us.setMsg("DATOS NO ACTUALIZADOS");
                System.out.println("Actualizacion Fallida");
            }
            st.close();

            conn.commit();
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            us.setResp("0");
            us.setMsg(e.getMessage());

        }

        return us;
    }

    public NotasMd savegrado(NotasMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        us = new NotasMd();

        String query1 = " SELECT max(not_id)+1 as id FROM notas;";
        String sql = "INSERT INTO `notas1.0`.`notas`"
                + " (`not_id`, `est_id`, `asig_id`, `cur_id`, `pro_id`, "
                + "`not_calificacion`, `not_fecha`, `not_bimes`, `not_alta`, "
                + "`not_usuario`) "
                + "VALUES (?, ?, ?, ?, ?, ?, now(), ?, now(),?);";

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
                ps.setString(2, data.getEstCodigo());
                ps.setString(3, data.getGraCodigo());
                ps.setString(4, data.getMateCodigo());
                ps.setString(5, data.getProCodigo());
                ps.setString(6, data.getNota());
                ps.setString(7, data.getBimestre());
                ps.setString(8, data.getUsuario());

                ps.executeUpdate();
                ps.close();
                conn.commit();
                us.setResp("1");
                us.setMsg("NOTA GUARDADA CORRECTAMENTE");
            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            us.setResp("0");
            us.setMsg(e.getMessage());

        }

        return us;
    }

    public List<NotasMd> allGrado() {
        List<NotasMd> data = new ArrayList<NotasMd>();
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;

        String query0 = " select asig_id, asig_nombre from asignaciones;";

        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                us = new NotasMd();
                resp = 1;
                us.setGraCodigo(rs.getString(1));
                us.setGraNombre(rs.getString(2));
                us.setMsg("TODOS.!");
                data.add(us);
            }
            st.close();
            rs.close();

            if (resp == 0) {

                us.setResp("0");
                us.setMsg("MATERIA NO EXISTEN");
            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            us.setResp("0");
            us.setMsg(e.getMessage());

        }

        return data;
    }

    public List<NotasMd> allMateriaOP(String usuario) {
        List<NotasMd> data = new ArrayList<NotasMd>();
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;

        String query0 = "select a.cur_id, CONCAT(a.cur_nombre, ' SECCION  ', a.cur_seccion) \n"
                + "from cursos a,\n"
                + "     USUARIOS d\n"
                + "where a.pro_id=d.USU_CODIGO and d.USU_USER='" + usuario + "';";
//                + " select cur_id, CONCAT(cur_nombre, ' SECCION  ', cur_seccion) from cursos;";
////                + "select est_id, CONCAT(est_nombre, ' ', est_apellido) from estudiantes;";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                us = new NotasMd();
                resp = 1;
                us.setMateCodigo(rs.getString(1));
                us.setMateNombre(rs.getString(2));
                us.setMsg("TODOS.!");
                data.add(us);
            }
            st.close();
            rs.close();

            if (resp == 0) {

                us.setResp("0");
                us.setMsg("MATERIA NO EXISTEN");
            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            us.setResp("0");
            us.setMsg(e.getMessage());

        }

        return data;
    }

    public List<NotasMd> allMateria() {
        List<NotasMd> data = new ArrayList<NotasMd>();
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;

        String query0 = " select cur_id, CONCAT(cur_nombre, ' SECCION  ', cur_seccion) from cursos;";
//                + "select est_id, CONCAT(est_nombre, ' ', est_apellido) from estudiantes;";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                us = new NotasMd();
                resp = 1;
                us.setMateCodigo(rs.getString(1));
                us.setMateNombre(rs.getString(2));
                us.setMsg("TODOS.!");
                data.add(us);
            }
            st.close();
            rs.close();

            if (resp == 0) {

                us.setResp("0");
                us.setMsg("MATERIA NO EXISTEN");
            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            us.setResp("0");
            us.setMsg(e.getMessage());

        }

        return data;
    }

    public List<NotasMd> allproyecto() {
        List<NotasMd> data = new ArrayList<NotasMd>();
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;

        String query0 = " select est_id, CONCAT(est_nombre, ' ', est_apellido) from estudiantes;";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                us = new NotasMd();
                resp = 1;
                us.setEstCodigo(rs.getString(1));
                us.setEstNombre(rs.getString(2));
                us.setMsg("TODOS LOS PROYECTOS.!");
                data.add(us);
            }
            st.close();
            rs.close();

            if (resp == 0) {

                us.setResp("0");
                us.setMsg("CURSO NO EXISTEN");
            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            us.setResp("0");
            us.setMsg(e.getMessage());

        }

        return data;
    }

    public NotasMd verNota(String grado, String estudiante, String materia, String bimestre) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        us = new NotasMd();
        String query0 = "SELECT not_calificacion FROM notas where asig_id='" + grado + "' and est_id='" + estudiante + "'"
                + " and cur_id='" + materia + "' and not_bimes='" + bimestre + "';";
//                + "select est_codigo from estudiantes where est_id='" + valorBuscar + "';";

        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;

                us.setNota(rs.getString(1));

                us.setResp("1");
                us.setMsg("ACTUALIZAR.!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                us.setResp("0");
                us.setMsg("ESTUDIANTE NO TIENE <br/>  <br/>  NOTA EN EL BIMESTRE " + bimestre);

            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            us.setResp("0");
            us.setMsg(e.getMessage());

        }

        return us;
    }

    public NotasMd verSubRenglon(String valorBuscar) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        us = new NotasMd();
        String query0 = "select est_codigo from estudiantes where est_id='" + valorBuscar + "';";

        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;

                us.setEstCodigoMostrar(rs.getString(1));

                us.setResp("1");
                us.setMsg("ACTUALIZAR DATOS DE CATEDRATICO.!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                us.setResp("0");
                us.setMsg("ESTUDIANTE NO EXISTE <br/>  <br/>  CONSULTAR REPORTE DE CURSOS");

            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            us.setResp("0");
            us.setMsg(e.getMessage());

        }

        return us;
    }

    public NotasMd MostrarProducto(String producto) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        us = new NotasMd();
        String query0 = "select  est_id from estudiantes where est_codigo='" + producto + "';";
//                + "SELECT pro_descripcion,pro_tipo,pro_tipo_servicio,pro_marca,pro_presentacion,pro_precio_compra,pro_precio_venta,\n"
//                + "pro_descuento,pro_stock,pro_conversion,pro_medida,pro_minimo,pro_maximo,pro_ubicacion\n"
//                + "from  almacen1.productos\n"
//                + " where pro_id='" + producto + "';";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                us.setEstCodigo(rs.getString(1));

                us.setResp("1");
                us.setMsg("ACTUALIZAR DATOS DE CATEDRATICO.!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                us.setResp("0");
                us.setMsg("CODIGO NO EXISTE <br/>  <br/>  VALIDAR CODIGO");

            }
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            us.setResp("0");
            us.setMsg(e.getMessage());

        }

        return us;
    }

}
