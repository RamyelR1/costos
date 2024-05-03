package Dal;

import Conexion.Conexion;
import Modelo.UsuarioMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class UsuarioDal {

    Conexion obtener = new Conexion();
    Connection conn;
    UsuarioMd us = new UsuarioMd();

    public UsuarioMd saveUS(UsuarioMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        us = new UsuarioMd();
        String query0 = " SELECT * FROM control_visita.usuarios where id_usuario = '" + data.getUser() + "' ";
        String query1 = " SELECT max(id_usuario)+1 as id FROM control_visita.usuarios";
        String sql = " INSERT INTO control_visita.usuarios (id_usuario, usuario, clave) "
                + "VALUES (?,?,?)";
        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                us.setResp("0");
                us.setMsg("NOMBRE DE USUARIO YA EXISTE.!");
            }
            st.close();
            rs.close();

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
                ps.setString(2, data.getUser());
                ps.setString(3, data.getPass());

                ps.executeUpdate();
                ps.close();
                conn.commit();
                us.setResp("1");
                us.setMsg("USUARIO GUARDADO CORRECTAMENTE");
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

    public UsuarioMd findUS(String user) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        us = new UsuarioMd();
        String query0 = " SELECT * FROM control_visita.usuarios where usuario = '" + user + "' ";
        String query1 = " SELECT max(id_usuario)+1 as id FROM control_visita.usuarios";
        String sql = " INSERT INTO control_visita.usuarios (id_usuario, usuario, clave) "
                + "VALUES (?,?,?)";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                resp = 1;
                us.setUser(rs.getString(2));
                us.setPass(rs.getString(3));
                us.setResp("1");
                us.setMsg("ACTUALIZAR DATOS DE USUARIO.!");
            }
            st.close();
            rs.close();

            if (resp == 0) {

                us.setResp("0");
                us.setMsg("USUARIO NO EXISTE");
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

    public UsuarioMd updateUS(UsuarioMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        us = new UsuarioMd();

        //String sql = " UPDATE USUARIOS SET usu_nombre='" + data.getNombre() + "', usu_telefono='" + data.getTelefono() + "', usu_rol = '" + data.getRol() + "', usu_observaciones = '" + data.getObserva() + "', usu_password = '" + data.getPass() + "' WHERE usu_user ='" + data.getUser() + "' ";
        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);
            int vl = 0;
            st = conn.createStatement();
            vl = st.executeUpdate(" UPDATE control_visita.usuarios SET clave='" + data.getPass() + "'"
                    + " WHERE usuario ='" + data.getUser() + "'  ");
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

}
