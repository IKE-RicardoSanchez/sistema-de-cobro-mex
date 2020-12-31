/*
 * Login.java
 *
 * Created on 17 de noviembre de 2004, 11:05 AM
 */

package Utilerias;

import java.sql.ResultSet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import Utilerias.UtileriasBDF;
import java.sql.Connection;

/**
 *
 * @author  cabrerar
 * @version
 */
public class LlenaCombos extends HttpServlet {

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
        HttpSession sessionH = request.getSession(false);
        PrintWriter out = response.getWriter();
        Connection con = UtileriasBDF.getConnection();
        StringBuffer strSelectHTML = new StringBuffer();

        response.setContentType("text/html");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Llena Combos</title>");
                out.println("</head>");
                out.println("<body><script>");
                
         ResultSet rs = null;
         try{
            rs = UtileriasBDF.rsSQLNP( request.getParameter("strSQL"));
            while(rs.next()){
              strSelectHTML.append("<option value='").append(rs.getString(1)).append("'>").append(rs.getString(2)).append("</option>"); 
//              out.println("top.opener.fnOptionxAdd('"+request.getParameter("strName")+"','"+i+"','"+rs.getString(2)+"','"+rs.getString(1)+"');");			
            }
            out.println("top.opener.fnReplaceSelect('"+request.getParameter("strName")+"',\""+ strSelectHTML.toString() +"\");");
            strSelectHTML.delete(0,strSelectHTML.length());
            out.println("window.close();</script></body>");
            out.println("</html>");
            
        }catch(Exception e){
            e.printStackTrace();
        }
        finally
        {
          try{
            if (rs!=null)
            {
              rs.close();
              rs=null;
            }
            if (con  !=null)
            {
              con.close();
              con = null;
            }
          }
          catch(Exception ee)
          {
            ee.printStackTrace();
          }
          out.close();
          this.destroy();

        }
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
