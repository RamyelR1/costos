/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dal;

import Conexion.Conexion;
import Modelo.EditarGradoMd;
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
public class EditarGradoDal {

    Conexion obtener = new Conexion();
    Connection conn;
    EditarGradoMd cl = new EditarGradoMd();

    public EditarGradoMd updateUS(EditarGradoMd data) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";
        int resp = 0;
        cl = new EditarGradoMd();

        try {
            conn = obtener.Conexion();
            conn.setAutoCommit(false);
            int vl = 0;
            st = conn.createStatement();
            vl = st.executeUpdate("UPDATE `notas1.0`.`asignaciones` SET `asig_nombre` = '" + data.getNombre() + "' WHERE (`asig_id` = '" + data.getCodigo()+ "'); ");
            if (vl > 0) {
                cl.setResp("1");
                cl.setMsg("DATOS ACTUALIZADOS CORRECTAMENTE");
                System.out.println("Actualizacion Exitosa");
            } else {
                cl.setResp("0");
                cl.setMsg("DATOS NO ACTUALIZADOS");
                System.out.println("Actualizacion Fallida");
            }
            st.close();

            conn.commit();
            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return cl;
    }

    public EditarGradoMd MostrarProducto(String producto) {
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String id = "";

        cl = new EditarGradoMd();
        String query0 = "select asig_nombre from asignaciones where asig_id='" + producto + "';";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {

                cl.setNombre(rs.getString(1));

                cl.setResp("1");
                cl.setMsg("ACTUALIZAR DATOS DE CATEDRATICO.!");
            }
            st.close();
            rs.close();

            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());

        }

        return cl;
    }

    public List<EditarGradoMd> findallCL() {
        List<EditarGradoMd> allClientes = new ArrayList<EditarGradoMd>();
        Statement st = null;

        ResultSet rs = null;

        String query0 = " SELECT * FROM `notas1.0`.asignaciones ";
        try {
            conn = obtener.Conexion();

            st = conn.createStatement();
            rs = st.executeQuery(query0);
            while (rs.next()) {
                cl = new EditarGradoMd();

                cl.setCodigo(rs.getString(1));
                cl.setNombre(rs.getString(2));

                allClientes.add(cl);
            }
            st.close();
            rs.close();

            conn.close();
            obtener.desconectar();

        } catch (Exception e) {
            System.out.println("ERROR CATCH.: " + e.getMessage());
            cl.setResp("0");
            cl.setMsg(e.getMessage());
            allClientes.add(cl);
        }

        return allClientes;
    }

}
