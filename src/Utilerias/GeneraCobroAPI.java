
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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.json.JSONObject;

@WebServlet("/GeneraCobroAPI")
public class GeneraCobroAPI  extends HttpServlet{
    private static String TokenSession= "";
    
    public GeneraCobroAPI (int lote, String dsproyecto){
        //postGeneraTokenSession("me", "password");
        postAPI(lote,dsproyecto);
    }
    private void postAPI(int lote, String dsproyecto){    
        try{
            URL url = new URL("http://localhost:8190/Prueba");
            //URL url = new URL("https://70d7507af570.ngrok.io/consulta");
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            
            //header
            conn.setRequestProperty("X-Auth-token", TokenSession);
            
            String input= "{\"lote\":"+lote+",\"dsproyecto\":\""+dsproyecto+"\"}";
            
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader (conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            
            String output;
            System.out.println("Outout from server .... \n");
            while ((output= br.readLine()) != null){
                System.out.println(output);
            }
            conn.disconnect();
            
        }catch(Exception e){
            System.out.println("Exception in NetClientGet:- " +e);
        }
        
    }
    
    private void get(){ 
        try{
            URL url = new URL("http://localhost:8190/Prueba");
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Acept", "application/json");

            if(conn.getResponseCode() != 200){
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader (conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            String output;
            while ((output= br.readLine()) != null){
                System.out.println(output);
            }
            conn.disconnect();
        }catch(Exception e){
            System.out.println("Exception in NetClientGet:- " +e);
        }
    }
    
    private void postGeneraTokenSession(String Usuario, String contrasena){    
        try{
            URL url = new URL("http://localhost:8190/Prueba");
            //URL url = new URL("https://70d7507af570.ngrok.io/api/login");
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            
            String input= "{\"username\":"+Usuario+",\"password\":\""+contrasena+"\"}";
            
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            
            InputStreamReader in = new InputStreamReader (conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            
            String responseToken="";
            String output;
            JSONObject obj=new JSONObject();
            System.out.println("Outout from server .... \n");
            
            while ((output= br.readLine()) != null){
                System.out.println(output);
                obj=new JSONObject(output);
                responseToken=obj.getString("access_token");  //Dato productivo access_token
            }
            
            System.out.println("Dato Token: "+responseToken);
            TokenSession=responseToken;
            
            conn.disconnect();
            
        }catch(Exception e){
            System.out.println("Exception in NetClientGet:- " +e);
        }
        
    }

}
