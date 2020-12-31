/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilerias;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rarodriguez
 */
public class ProcesoAdicional extends HttpServlet {

    public void getProcess(HttpServletRequest request, HttpServletResponse response) throws IOException{
            System.out.println("\n\ngetProcess Java");
        HttpSession sessionH = request.getSession(false);

        if(sessionH.getAttribute("proyecto")!=null)
        {
            String producto="";
            StringBuffer query= new StringBuffer();

            if(request.getParameter("prod")!=null)
                producto= request.getParameter("prod").toString();
            query.append("sp_tmkgcA_ProcesosAdicionales '").append(sessionH.getAttribute("proyecto")).append("','").append(producto).append("'");
            try{
                System.out.println(query);
            ResultSet rs= UtileriasBDF.rsSQLNP(query.toString());
            query.delete(0, query.length());
            
                if(rs.next())
                {
                    System.out.println(rs.getString("NombreProceso").toString());
                    query.append(rs.getString("SP_Genera").toString());
                    DownLoad.DescargaAdicional(query, rs.getString("NombreArchivo").toString(), rs.getString("TipoArchivo").toString(), request, response);
                }
            }catch(SQLException e){System.out.println("Error SQL Proceso Adicional");}
        }
        else
            System.out.println("Proyecto de Session no informado");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    getProcess(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getProcess(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
}//CLASS
