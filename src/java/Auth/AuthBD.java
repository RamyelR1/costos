package Auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AuthBD {

    //Connection conn = null;
    public String autUser(String user, String pass) {

        String driver = "";
        String db = "";
        String port = "";
        String sid = "";
        String resp = "0";
        Connection conn = null;

        try {
            driver = "oracle.jdbc.driver.OracleDriver";
            db = "10.75.1.107";  //PRODUCCION 107
            port = "1521";
            sid = "EPQ";    //PRODUCCION epq

            Class.forName(driver);

            String url = "jdbc:oracle:thin:@";
            url += db;
            url += ":" + port;
            url += "/" + "EPQ";

            conn = DriverManager.getConnection(url, user, pass);
            if (conn != null) {
                resp = "1";
            } else {
                resp = "0";
            }
            conn.close();
            conn = null;

        } catch (ClassNotFoundException e) {
            //return null;
            System.out.println("ERROR.: " + e.getMessage());
            resp = e.getMessage();

        } catch (SQLException e) {
            //return null;
            System.out.println("ERROR.: " + e.getMessage());
            resp = e.getMessage();
        }
        return resp;
    }

}
