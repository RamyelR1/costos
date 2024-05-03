
package Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestClient {
    
    String output = "";
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NDQyNzE1MTUsImV4cCI6MTY0NDM2MTUxNX0.BHb0wzr6fz0bQHlhgolIqaMlbvK-qWARQhTokF44eo8";
 
    public String views(String user, String sistem) {

        try {

            URL url = new URL("http://10.75.1.34:8080/ApiLoginEPQ/api/getVistas");
            //URL url = new URL("http://10.75.15.88:8080/ApiLoginEPQ/api/getVistas");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", token);
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("CustomHeader", token);
            //conn.setRequestProperty("Authorization", token);
            String input = "{\"idSistema\":\"" + sistem + "\",\"idUsr\":\"" + user + "\"}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes("UTF-8"));
            os.flush();

            //Obtener Respuesta
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            //String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            output = e.getMessage();
            e.printStackTrace();

        } catch (IOException e) {
            output = e.getMessage();
            e.printStackTrace();

        }
        return output;
    }
    
}
