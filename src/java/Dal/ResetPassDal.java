/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dal;

import Conexion.Conexion;
import Modelo.LoginMd;
import Modelo.ResetPassMd;
import Util.Bitacora;
import Util.Cripto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author alfonsoc7905
 */
public class ResetPassDal {

    private static String key = "epqCitas*8965*";
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement st = null;
    ResultSet rs = null;

    public int resetPass(String usuario) throws ClassNotFoundException {
        Statement st = null;
        int resp = 0;
        String sql = "";
        Cripto cp = new Cripto();
        String newpass = cp.reset();
        System.out.println("RESET PASS.: "+newpass);
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            resp = st.executeUpdate("UPDATE USUARIOS SET usu_password = '"+newpass+"' WHERE trim(usu_user) ='"+usuario+"' ");
            if (resp > 0) {
                System.out.println("Actualizacion Exitosa.: " + resp);
                
//                Clients.showNotification("PROCESO <br/>  <br/> EXITOSO <br/>",
//                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
            } else {
                System.out.println("Actualizacion Fallida.: " + resp);
            }
            st.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println("ERROR.: " + e.getMessage());
            Clients.showNotification("ERROR <br/>  <br/> No se puede Cambiar la Clave <br/>"+e.getMessage().toString(),
                    Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 0);
        }
        return resp;
    }
    
    public int cambioPass(String usuario, String pass) throws ClassNotFoundException {
        Statement st = null;
        int resp = 0;
        String sql = "";
        Cripto cp = new Cripto();
        String newpass = cp.encrypt(pass, key);
        System.out.println("NEW PASS.: "+newpass);
        try {
            conexion = cnn.Conexion();
            st = conexion.createStatement();
            resp = st.executeUpdate("UPDATE USUARIOS SET usu_password = '"+newpass+"' WHERE trim(usu_user) ='"+usuario+"' ");
            if (resp > 0) {
                System.out.println("Actualizacion Exitosa.: " + resp);
                Clients.showNotification("PROCESO <br/>  <br/> EXITOSO <br/>",
                        Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
            } else {
                System.out.println("Actualizacion Fallida.: " + resp);
            }
            st.close();
            conexion.close();
        } catch (SQLException e) {
            System.out.println("ERROR.: " + e.getMessage());
            Clients.showNotification("ERROR <br/>  <br/> No se puede Cambiar la Clave <br/>"+e.getMessage().toString(),
                    Clients.NOTIFICATION_TYPE_ERROR, null, "top_center", 0);
        }
        return resp;
    }

}
