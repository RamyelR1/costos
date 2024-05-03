package Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author alfonsoc7905
 */
public class Bitacora {

    String output = "";
    String ccopia = "";
    
    public String login(String user, String sistem, String modulo, String conte, int an_arr, int nm_arr, String obs) {

        try {

            URL url = new URL("http://www.puerto-quetzal.com:3000/Audit/ext");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("CustomHeader", token);
            //conn.setRequestProperty("Authorization", conte);
            String input = "{\"USUARIO\":\"" + user + "\",\"SISTEMA\":\"" + sistem + "\","
                    + "\"MODULO\":\"" + modulo + "\",\"CONTENEDOR\":\"" + conte + "\","
                    + "\"ANO_ARRIBO\":" + an_arr + ",\"NUM_ARRIBO\":" + nm_arr + ","
                    + "\"OBSERVACION\":\"" + obs + "\"}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            /*
		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
		}
             */
            //Utilizado en Metodo GET
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

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

    // CONSUMO DE WEB SERVICE COntrol de COPIAS
    public String CCopias(String Nosistema, String Nomreporte, String Obs, String user) throws JSONException {

        try {

            //URL url = new URL("http://www.puerto-quetzal.com:4000/ccopias/str");
            URL url = new URL("http://www.puerto-quetzal.com:4000/ccopias");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            //conn.setRequestProperty("CustomHeader", token);
            //conn.setRequestProperty("Authorization", conte);
            String input = "{\"CON_SISTEMA\":\"" + Nosistema + "\",\"CON_NOMBRE_REPORTE\":\"" + Nomreporte + "\","
                    + "\"CON_OBSERVACIONES\":\"" + Obs + "\",\"CON_USUARIO\":\"" + user + "\"}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            //Utilizado en Metodo GET
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            //String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                JSONObject json = new JSONObject(output);
                // print object
                System.out.println(json.toString());
                // get value for a key
                Object brand = json.get("NoCorr");
                ccopia = brand.toString();
                System.out.println(ccopia);
                // convert to json object
                //JSONObject json = new JSONObject(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            output = e.getMessage();
            e.printStackTrace();

        } catch (IOException e) {
            output = e.getMessage();
            e.printStackTrace();

        }
        return ccopia;
    }

}
