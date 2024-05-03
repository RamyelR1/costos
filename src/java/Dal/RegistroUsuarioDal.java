/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dal;

import Conexion.Conexion;
import Util.Cripto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.zkoss.zk.ui.util.Clients;

/**
 *
 * @author alfonsoc7905
 */
public class RegistroUsuarioDal {
    private String key="epqCitas*8965*";
    private Connection conexion = null;
    private Conexion cnn = new Conexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    Cripto cp = new Cripto();
    
    public String creaUser(String usuario, String pass, String empresa, String doc, String nomEmp, String nombre, String nit, String telefono, String email, String user){
        String resp = "0";
        String crea = "";
        String sql=" INSERT INTO SYSAUDTEMP.usuarios_sat (usuario, pswd,ultimo_acceso, correo_naviera, CORREO_TORRECONTROL, "
                + " NOMBRE_COMPLETO, TELEFONO, CORREO_ELECTRONICO, RTU, NOMBRE_COMP_EMPRESA, NUMERO_OFICIO) "
                + " VALUES(?,?,TO_CHAR(sysdate,'DD/MM/YYYY hh:mm:ss'),?,?,?,?,?,?,?,?) ";
        String newpass = "";
        newpass = cp.encrypt(pass, key);
        crea = "USU CREA.: "+user;
        try{
            conexion = cnn.Conexion();
            ps = conexion.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, newpass);
            ps.setString(3, empresa);
            ps.setString(4, crea);
            ps.setString(5, nombre);
            ps.setString(6, telefono);
            ps.setString(7, email);
            ps.setString(8, nit);
            ps.setString(9, nomEmp);
            ps.setString(10, doc);
            ps.executeUpdate();
            //ps.close();
            resp = "1";
            ps.close();
            conexion.close();
            Clients.showNotification("USUARIO CREADO<br/>  <br/> CON EXITO<br/>",
                    Clients.NOTIFICATION_TYPE_INFO, null, "middle_center", 0);
        } catch(SQLException e){
            resp = "0";
            System.out.println("ERROR.: "+e.getMessage());
            Clients.showNotification("ERROR <br/>  <br/>No se ha podido crear el USUARIO<br/><br/>Vuelve a intentarlo!<br/>"+e.getMessage().toString(),
                    Clients.NOTIFICATION_TYPE_WARNING, null, "middle_center", 0);
        }
        return resp;
    }
    
}
