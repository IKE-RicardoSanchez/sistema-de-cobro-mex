/* 
 * Login.java
 *
 * Created on 17 de noviembre de 2004, 11:05 AM
 */

package Seguridad;

import com.ike.model.DAOSeguridad;
import com.ike.model.to.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import java.sql.ResultSet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import Utilerias.UtileriasBDF;
import Seguridad.SeguridadC;

/**
 *
 * @author  cabrerar
 * @version
 */
public class Login extends HttpServlet {
    /** Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    /** Destroys the servlet.
     */
    public void destroy() {
    }
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession sessionH = request.getSession(true);
        ResultSet rsHost = null;
        DAOSeguridad daos = new DAOSeguridad();
        
        PrintWriter out = response.getWriter();
        String usr = request.getParameter("Usr");
        String pas = request.getParameter("Pass");
        String strIP = "";

        strIP =  request.getRemoteAddr();
        response.setContentType("text/html");
        
      //<<<<<<<<<<<<<<<<< Crear Clave de Login >>>>>>>>>>>>
        Random rdm=new Random();    
        int rl=rdm.nextInt();
        String hash1 = Integer.toHexString(rl);
        String ValidaSession=hash1.substring(0,7);
                
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servicio Seguridad</title>");
                out.println("</head>");
                out.println("<link href='../StyleClasses/Global.css' rel='stylesheet' type='text/css'>");
                out.println("<body class='cssBody'>");
                out.println("<div align='center'>");
                out.println("<img src='../Imagenes/bgEncabezado.jpg' width='1024' height='70'>");
                out.println("<BR><BR><BR><font color='#000066' face='Arial, Helvetica, sans-serif'><strong>");
                
        // Verifica la IP del Usuario
        
/*        out.println("BASIC_AUTH" + request.BASIC_AUTH);
        out.println("CLIENT_CERT_AUTH" + request.CLIENT_CERT_AUTH);
        out.println("DIGEST_AUTH" + request.DIGEST_AUTH);
        out.println("FORM_AUTH" + request.FORM_AUTH);
        out.println("getAuthType" + request.getAuthType());
        out.println("getCharacterEncoding" + request.getCharacterEncoding());
        out.println("getContentType" + request.getContentType());
        out.println("getContentLength" + request.getContentLength());
        out.println("getContextPath" + request.getContextPath());
        out.println("getPathInfo" + request.getPathInfo());
        out.println("getRemoteAddr" + request.getRemoteAddr());
        out.println("getRemoteHost" + request.getRemoteHost());
        out.println("getRemoteUser" + request.getRemoteUser());
        out.println("getRequestURI" + request.getRequestURI());
        out.println("getRequestURL" + request.getRequestURL());*/ 
        //sessionH.setMaxInactiveInterval(3000);
       usr = usr.replace("'","");
       pas = pas.replace("'","");
       
       usr = usr.replace(" ","");
       pas = pas.replace(" ","");
       
        if (usr.length() > 17 )
            usr = usr.substring(0,17);
        
        if (pas.length() > 10 )
            pas = pas.substring(0,10);
        
       usr = usr.trim();
       pas = pas.trim();
        
        
        try {
        
            Usuario usuario = daos.getUsuario(usr, pas, strIP);
            System.out.println(usr);
            System.out.println(pas);
            System.out.println(usuario.getMess().toString()+"\n"+usuario.getMess().compareToIgnoreCase(""));
            if( usuario != null){
                if (usuario.getMess().equals("")){
                        if (usuario.getActivo().equals("0")){
                            out.println("<script>");
                            out.println("alert(\"Acceso no permitido\");history.go(-1);");
                            out.println("</script>");
                        } 
                        else if (usuario.getActivo().equals("2")){
                            out.println("<script>");
                            out.println("alert(\"Ha superado el numero de intentos de ingreso. Favor de consultar a su Administrador.\");history.go(-1);");
                            out.println("</script>");
                        } 
                        else {
                            if (usuario.getCambioPwd().equals("1")){
                                sessionH.setAttribute("clUsrCambiaPwd",usuario.getClUsrApp());
                                sessionH.setAttribute("UsrNomCambiaPwd",usr);
                                response.sendRedirect("../Seguridad/CambiaPasswd.jsp?Expiro=1");
                                //response.sendRedirect("../Seguridad/CambiaPasswd.jsp?Expiro=1&clUsrApp=" + usuario.getClUsrApp() + "&Usuario=" + usr);
                            } else {

                                if (pas.equalsIgnoreCase(usuario.getPassword())){
                                    if (SeguridadC.verificaHorarioC(Integer.parseInt( usuario.getClUsrApp()))){
                                     // System.out.println("sp_tmkgcA_sys_ValidaHost " + usuario.getClUsrApp() + " ,'" + request.getRemoteAddr() + "'");
    //                                  rsHost = UtileriasBDF.rsSQLNP( "sp_tmkgcA_sys_ValidaHost " + usuario.getClUsrApp() + " ,'" + request.getRemoteAddr() + "'");
                                        rsHost = UtileriasBDF.rsSQLNP( "sp_tmkgcA_sys_ValidaHost " + usuario.getClUsrApp() + " ,'" + request.getRemoteAddr() + "'");
                                      if(rsHost.next()){
                                        if (rsHost.getString("AccesoPermitido").compareToIgnoreCase("1")==0){
                                           sessionH.setAttribute("NombreUsuario",usuario.getNombre());
                                           sessionH.setAttribute("clUsrApp",usuario.getClUsrApp());
                                           sessionH.setAttribute("Usr",usr);
                                           sessionH.setAttribute("FechaAlta",usuario.getFechaAlta());
                                           sessionH.setAttribute("FechaInicio",usuario.getFechaInicio());
                                           sessionH.setAttribute("Correo",usuario.getCorreo());
                                           sessionH.setAttribute("AccesoId",usuario.getAccesoId());
                                           sessionH.setAttribute("ValidaSessionR",ValidaSession);
                                           sessionH.setAttribute("ValidaSessionS",ValidaSession);
                                           sessionH.setAttribute("tipoMenu", usuario.getTipoMenu());
                                           sessionH.setAttribute("PermisoConf", usuario.getPermisoConf());
                                           response.sendRedirect("../Main.jsp");
                                        }
                                        else
                                        {
                                          out.println("<script>alert('Acceso no Permitido:" + rsHost.getString("Razon") + "');history.go(-1)</script>" );
                                          out.println("<META HTTP-EQUIV='Refresh' CONTENT='1;URL=../index.html'>"); 
                                          out.println("</body>");
                                          out.println("</html>");
                                        }
                                      }
                                      else
                                      {
                                          out.println("error : Problemas con la base de datos al validar el Host, consulte a su administrador ." );
                                          out.println("<META HTTP-EQUIV='Refresh' CONTENT='1;URL=../index.html'>"); 
                                          out.println("</body>");
                                          out.println("</html>");
                                      }                                    
                                    }else{
                                      out.println("error : Fuera de horario" );
                                      out.println("<META HTTP-EQUIV='Refresh' CONTENT='1;URL=../index.html'>"); 
                                      out.println("</body>");
                                      out.println("</html>");
                                    }
                                }else{
                                    out.println("error : Acceso no válido" );
                                    out.println("<META HTTP-EQUIV='Refresh' CONTENT='1;URL=../index.html'>"); 
                                    out.println("</body>");
                                    out.println("</html>");
                                }
                            }
                        }
                }   
                else{
                    out.println(usuario.getMess().toString()+"<br>Consulte a su administrador");
                    out.println("<META HTTP-EQUIV='Refresh' CONTENT='1;URL=../index.html'>"); 
                    out.println("</body>");
                    out.println("</html>");
                }
            }else{
                out.println("<script>");
                out.println("alert(\"Usuario y password no valido\");history.go(-1);");
                out.println("</script>");
                
            }
             usuario=null;
        } catch(Exception e){
            out.println("error : Problemas con la base de datos al validar el Host, consulte a su administrador ." );
            out.println("<META HTTP-EQUIV='Refresh' CONTENT='1;URL=../index.html'>"); 
            out.println("</strong></font>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            e.printStackTrace(); 
        }
        finally 
        {
          try 
          {
            if (rsHost!=null)
            {
              rsHost.close();
              rsHost=null;

            }

          }catch (Exception e)
          {
            e.printStackTrace();
          }
        }

        out.close();
        daos=null;
        //return null;
    }
    
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    
}
