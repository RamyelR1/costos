package Dal;

import Conexion.Conexion;
import Modelo.LoginMd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.zkoss.zhtml.Messagebox;


public class LoginDal {
    Conexion obtener = new Conexion();
    Connection conn;
    LoginMd us = new LoginMd();
    
    public LoginMd BuscaUsuario(String usuario) throws SQLException {
        PreparedStatement smt = null;
        ResultSet result = null;
        int resultado = 0;
        
        System.out.println("Credenciales.: " + usuario);
        String sql = "select trim(usuario)as usuario, trim(clave) as pass \n" +
"from control_visita.usuarios where trim(usuario) ='"+usuario+"'";
        
        try {
            conn = obtener.Conexion();
            smt = conn.prepareStatement(sql);
            result = smt.executeQuery();
            int rsp = 0;
            while (result.next()) {
                us.setUseario(result.getString("usuario"));
                //us.setEmpresa(result.getString("nav"));
                us.setPass(result.getString("pass"));
               // us.setPath(result.getString("sistema"));
                us.setRespuesta("1");
                System.out.println("USUARIO.: "+us.getUseario());
                
                rsp=1;
            }
            result.close();
            smt.close();

            conn.close();
            obtener.desconectar();
            if(rsp == 0){us.setRespuesta("0");}
        } catch (SQLException e) {
            System.out.println("ERROR CATCH.: "+e.getMessage().toLowerCase());
            Messagebox.show("Credenciales Incorrectas", "Informacion", Messagebox.OK, Messagebox.INFORMATION);
        }

        return us;
    }
    
}
