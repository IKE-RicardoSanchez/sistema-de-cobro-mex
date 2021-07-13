/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilerias;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author rmartin
 */
@WebServlet("/PorcentageAPI")
public class PorcentageAPI extends HttpServlet{
    
    private static String Columns= "";
    private static String MaxRegistros= "";
    private static String dsProyecto= "";
    private static String StoreUpload= "";
    private static String detCobro= "";
    private static String LoteTabla= "";
    private static String Sufijo= "";
    private static String divisor= "";
    private static String extension_arch= "";
    private static String ErrSQL= "";
    private static String tipoR= "";
    private static String ValResp= "";
    private static String TipoResp= "";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("UploadAPI Java");
        HttpSession sessionH= request.getSession(false);
        
        String clUsrApp= "", strUrlBack= "";
        int Error= 0;
        boolean Proceso= true;
        String CargaAPI="";
        Respuesta Procesa= new Respuesta();
        
        Error= getNSaveFile(request);
        

        strUrlBack= "../CargaBD/UploadAPI.jsp";
        response.setContentType("text/html");
        PrintWriter out= response.getWriter();
      
        sessionH.setAttribute("proyecto", dsProyecto);
        System.out.println("proyecto = "+dsProyecto);
        
        System.out.println("sp_tmkgcA_getInfoResp '" + dsProyecto +"',"+tipoR);
            ResultSet rs= UtileriasBDF.rsSQLNP("sp_tmkgcA_getInfoResp '" + dsProyecto +"',"+tipoR);

                try
                {
                        if (rs.next())
                        {
                            LoteTabla= rs.getString("Lote");
                            Sufijo= rs.getString("Sufijo");
                            extension_arch= rs.getString("TipoArchivo");
                            StoreUpload= rs.getString("sp_Importa");
                            Columns= rs.getString("ncampos");
                            MaxRegistros= rs.getString("MaxRegistros");
                            detCobro= rs.getString("detalle");
                            divisor= rs.getString("Divisor");
                            ValResp= rs.getString("ValResp");
                            TipoResp= rs.getString("TipoResp");
                        }
                        extension_arch= extension_arch.trim();

                }catch (SQLException e)
                {
                    System.err.println("Upload.java - Error al validar Nombre del Archivo: "+ e.toString());
                }
                  System.out.println("Termina recuperacion de info GETInfo"); 
                        CargaAPI= Procesa.ProcesaTabla(dsProyecto, Integer.parseInt(LoteTabla), extension_arch);
                            System.out.println("\tRespuesta Procesa: "+ CargaAPI);

                            if (CargaAPI.contains("OK")||CargaAPI.contains("Ok"))
                                {   ErrSQL= "No Error";
                                    Proceso= true;
                                }
                            else{   ErrSQL= CargaAPI;
                                    System.out.println("Upload Error: "+ ErrSQL);
                                    Proceso= false;
                                }

                           if(Proceso)
                               {    //System.out.println("Proceso bit: "+ Proceso);
                                   sessionH.setAttribute("proyecto", dsProyecto);
                                   strUrlBack= "../CargaBD/DetalleResp.jsp";

                                   //SessionCobros.ValidaPath(sessionH);
                               }
                
        if(!Proceso)
                   {    //System.out.println("Proceso bit: "+ Proceso +"<script> alert('"+ ErrSQL.replace("'", "") +"');</script>");
                        out.println("<script> alert('"+ ErrSQL.replace("'", "") +"');</script>");
                        strUrlBack= "../CargaBD/UploadAPI.jsp";
                   }
        //strUrlBack= "../CargaBD/DetalleResp.jsp";
        
        out.println("<script>window.opener.fnValidaResponse(1,'" + strUrlBack + "')</script>");
        
                out.println("<script>window.opener.unblock()</script>");
                out.println("<script>window.close()</script>");

                System.out.println("Fin UploadAPI Java\n");
        out.println("");
        out.close();
   }//CLASS processRequest
         public int getNSaveFile(HttpServletRequest request)
    {   System.out.println("Entra: getNSaveFile");
        try
        {   
            DiskFileItemFactory factory= new DiskFileItemFactory();

            //factory.setSizeThreshold(4096);

            ServletFileUpload upload= new ServletFileUpload(factory);
            List items= upload.parseRequest(request);

            Iterator iter= items.iterator();

                    while (iter.hasNext())
                    {   FileItem item= (FileItem) iter.next();

                            String name= item.getFieldName();
                            String value= item.getString();

                        if(item.isFormField())
                                {
                            if (name.equalsIgnoreCase("dsProyecto")){ dsProyecto= value.toString(); }
                            if (name.equalsIgnoreCase("tipoResp")){ tipoR= value.toString(); }
                        }
                    }//WHILE
        }catch (Exception e)
        {
            System.out.println("Escritura de temporal: " +e.toString());
            return 1;
        }
        return 0;
    }    

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public String getServletInfo() {
        return "Short description";
    }
    
}
