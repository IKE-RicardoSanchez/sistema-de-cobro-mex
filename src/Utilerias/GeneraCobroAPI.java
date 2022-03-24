package Utilerias;

/**
 *
 * @author rmartin
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.json.JSONObject;

@WebServlet("/GeneraCobroAPI")
public class GeneraCobroAPI extends HttpServlet {

    private static String TokenSession = "";

    public GeneraCobroAPI(int lote, String dsproyecto, String URLcon, String URLToken, String Usuario, String contrasena) {
        //System.out.println(" " + lote + " " + dsproyecto + " " + URLcon + " " + URLToken + " " + Usuario + " " + contrasena);
        if (URLcon.substring(0, 5).equals("HTTPS") || URLcon.substring(0, 5).equals("https")) {
            System.out.println("Entro HTTPS");
            postGeneraTokenSession(URLToken, Usuario, contrasena);
            postAPI(URLcon, lote, dsproyecto);
        } else {
            System.out.println("Entro HTTP: " + URLToken);
            postGeneraTokenSessionhttp(URLToken, Usuario, contrasena);
            postAPIhttp(URLcon, lote, dsproyecto);
        }
    }

    private void postAPI(String URLcon, int lote, String dsproyecto) {
        try {
            //URL url = new URL("http://c98d-187-234-105-221.ngrok.io/consulta");
            //URL url = new URL("https://appstest.ikeasistencia.com/tokenizacion-jenkins/consulta");  //QA
            //URL url = new URL("https://apihub.ikeasistencia.com/tokenizacion-jenkins/consulta");  //PROD
            //HttpURLConnection conn= (HttpURLConnection) url.openConnection();

            URL url = new URL(URLcon);  //Nuevo en base a conf en  --Ruta URL API 
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            //header
            conn.setRequestProperty("X-Auth-token", TokenSession);

            String input = "{\"lote\":" + lote + ",\"dsproyecto\":\"" + dsproyecto + "\"}";

            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, null, new java.security.SecureRandom());
            conn.setSSLSocketFactory(sc.getSocketFactory());

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes("UTF-8"));
            os.flush();

            if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);

            String output;
            System.out.println("Outout from server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }

    }

    private void postGeneraTokenSession(String URLToken, String Usuario, String contrasena) {
        try {
            //URL url = new URL("https://c98d-187-234-105-221.ngrok.io/api/login");
            //URL url = new URL("https://appstest.ikeasistencia.com/tokenizacion-jenkins/api/login"); //QA
            //URL url = new URL("https://apihub.ikeasistencia.com/tokenizacion-jenkins/api/login");  //PROD 16.84
            //HttpURLConnection conn= (HttpURLConnection) url.openConnection();

            URL url = new URL(URLToken); //Nuevo en base a conf en  --Ruta URL API 
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"username\":\"" + Usuario + "\",\"password\":\"" + contrasena + "\"}";

            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, null, new java.security.SecureRandom());
            conn.setSSLSocketFactory(sc.getSocketFactory());

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes("UTF-8"));
            os.flush();

            if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);

            String responseToken = "";
            String output;
            JSONObject obj = new JSONObject();
            System.out.println("Outout from server .... \n");

            while ((output = br.readLine()) != null) {
                System.out.println(output);
                obj = new JSONObject(output);
                responseToken = obj.getString("access_token");  //Dato productivo access_token
            }

            //System.out.println("Dato Token: " + responseToken);
            TokenSession = responseToken;

            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }

    }

    private void postAPIhttp(String URLcon, int lote, String dsproyecto) {
        try {
            TokenSession = "Bearer " + TokenSession;
            URL url = new URL(URLcon);  //Nuevo en base a conf en  --Ruta URL API 
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            //header
            conn.setRequestProperty("X-Auth-token", TokenSession);

            String input = "{\"lote\":" + lote + ",\"dsproyecto\":\"" + dsproyecto + "\"}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes("UTF-8"));
            os.flush();
            //System.out.println("Conexion response: " + conn.getResponseCode());
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);

            String output;
            System.out.println("Outout from server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }

    }

    private void postGeneraTokenSessionhttp(String URLToken, String Usuario, String contrasena) {
        try {
            URL url = new URL(URLToken); //Nuevo en base a conf en  --Ruta URL API 
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"username\":\"" + Usuario + "\",\"password\":\"" + contrasena + "\"}";
            
            OutputStream os = conn.getOutputStream();
            //os.write(input.getBytes());
            os.write(input.getBytes("UTF-8"));
            os.flush();
            //System.out.println("Coenxion respones: " + conn.getResponseCode());
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);

            String responseToken = "";
            String output;
            JSONObject obj = new JSONObject();
            System.out.println("Outout from server .... \n");

            while ((output = br.readLine()) != null) {
                System.out.println(output);
                obj = new JSONObject(output);
                responseToken = obj.getString("access_token");  //Dato productivo access_token
            }

            //System.out.println("Dato Token: " + responseToken);
            TokenSession = responseToken;
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e.getMessage());
        }

    }

    private void get() {
        try {
            URL url = new URL("http://localhost:8190/Prueba");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Acept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception in NetClientGet:- " + e);
        }
    }

}
