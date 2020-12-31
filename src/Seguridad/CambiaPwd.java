/*
 * CambiaPwd.java
 *
 * Created on 18 de enero de 2005, 03:57 PM
 */

package Seguridad;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import Utilerias.UtileriasBDF;
import javax.servlet.ServletConfig;

/**
 *
 * @author lopgui
 * @version
 */
public class CambiaPwd extends HttpServlet {
    
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


        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        /* Ejecuta el Procedure de cambio de password */
    try{
    
        String StrCodeResponse = "";
        String StrMsg = "";
        StringBuffer StrSql = new StringBuffer();
        
        StrSql.append("sp_tmkgcA_sys_CambiaPassword '").append(request.getParameter("clUsrApp").toUpperCase()).append("' ,'', '" ).append(request.getParameter("Password")).append("','").append(request.getParameter("Confirma").toUpperCase()).append("'");
        
        ResultSet rs =  UtileriasBDF.rsSQLNP(StrSql.toString());
        StrSql.delete(0,StrSql.length());
        if(rs.next()) {
        StrCodeResponse = rs.getString("CodeResponse");
        StrMsg = rs.getString("msg");
        }
        rs.close();
        rs=null;
        
        out.println("<script> window.opener.fnValidaResponse( "+ StrCodeResponse +",'" + request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf(":") + 1) + "/SIAIKE/Seguridad/CambiaPwd.jsp','"+ StrMsg + "')</script>");                          
//        out.println("<script> window.opener.fnValidaResponse(1,'http://localhost:8080/SIAIKE/Seguridad/CambiaPwd.jsp')</script>");                    
        
        sessionH.removeAttribute("Pass");
        sessionH.removeAttribute("Confirma");
        out.close();
        
    } catch(Exception e){        
                out.close();
            e.printStackTrace();
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
