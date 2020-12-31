/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilerias;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 *
 * @author rarodriguez
 */
public class SessionCobros extends HttpServlet{

    public void getSessionCobros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            System.out.println("\ngetSession Java");
        HttpSession sessionH = request.getSession(false);

        String clUsrApp = "", dsProyecto= "";
               //strUrlBack = "../cobros/DescargaCobro.jsp";
        boolean confirma=false;

        if (sessionH.getAttribute("clUsrApp") != null) {
            clUsrApp = sessionH.getAttribute("clUsrApp").toString();
        }

        if(request.getParameter("P")!=null)
        {   dsProyecto= request.getParameter("P").toString();
            sessionH.setAttribute("pre", "pre");
        }

        if(request.getParameter("C")!=null)
        {   dsProyecto= request.getParameter("C").toString();
            sessionH.setAttribute("pre", "");
        }
        
        //System.out.println("Attribute: "+dsProyecto);
            confirma= getAttributesCobros(dsProyecto, sessionH);
            
            System.out.println("Confirmación: "+confirma);

            DownLoad.Descarga(sessionH, request, response);
            //response.sendRedirect(strUrlBack);

        System.out.println("Fin GetSession\n");
    }//GETSESSION
    
    public boolean getAttributesCobros(String Proyecto, HttpSession session){
        System.out.println("getAttributes Java");

        session.setAttribute("proyecto", Proyecto);
        System.out.println("\t"+ session.getAttribute("proyecto"));
        
        ResultSet rs=null;
        boolean Proceso=false;

        try{
            rs= UtileriasBDF.rsSQLNP("sp_tmkgcA_ValidaCobroMx '"+Proyecto+"',6");

            if(rs.next())
            {
                session.setAttribute("archivo", rs.getString("archivo").toString().replace("|", "-"));
                session.setAttribute("lote", rs.getString("id").toString());
                session.setAttribute("concat", rs.getString("tipoC").toString());
                session.setAttribute("nombre", rs.getString("nombre").toString());
            }

            System.out.println("\tarchivo: "+session.getAttribute("archivo").toString() +"\n\tlote: "+session.getAttribute("lote").toString()
                    +"\n\tconcat: "+session.getAttribute("concat"));

            if(rs!=null)
            {   
                rs.close();
            }
            
            Proceso= true;
        }catch(SQLException e){ System.out.println("Error al Buscar atributos ::"+ e);}
        System.out.println("Fin Attributes");
    return Proceso;
    }//GETATTRIBUTES

    public static void ValidaPath(HttpSession sesion){
        if(sesion.getAttribute("path")!=null){
                File descargable = new File(sesion.getAttribute("path").toString());

                    if(descargable.exists())
                    {   if(descargable.delete())
                        {   System.out.println("Archivo limpiado");
                                sesion.setAttribute("path", null);
                        }
                        else
                            System.out.println("Archivo No se pudo limpiar");
                        }
                    else
                           System.out.println("Archivo No encontrado");
        }
        System.out.println("Path limpio");
    }//VALIDAPATH


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    getSessionCobros(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getSessionCobros(request, response);
    }

    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
}//CLASS
