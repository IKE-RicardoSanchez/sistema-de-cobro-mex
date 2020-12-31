/*
 * Upload.java
 *
 * Created on 10 de diciembre de 2010, 09:14 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package Utilerias;

import java.io.*;
import java.sql.ResultSet;
//import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.util.*;
//import Utilerias.Respuesta;
import java.sql.SQLException;

/**
 *
 * @author bsanchez
 */
public class UploadReverso extends HttpServlet {

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    private static String path = "";
    private static String CodigoAccesoUsr = "";
    private static String CodigoCaptcha = "";
    private static String Columns = "";
    private static String MaxRegistros = "";
    private static String NombreArchivo = "";  //con ruta
    private static String fileName = ""; // sin ruta
    private static String dsProyecto = "";
    private static String StoreUpload = "";
    private static String detCobro = "";
    private static String Lote = "";
    private static String LoteTabla = "";
    private static String Sufijo = "";
    private static String nCamposResp = "";
    private static String extension_arch = "";
    private static String ErrSQL = "";
    private static String tipoR ="";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Upload Java");
        HttpSession sessionH = request.getSession(false);

        //<<<<<<<<<<<<  Obtener el usuario que Restra >>>>>>>>>>>
        String clUsrApp = "", clTipoArchivoUpload = "";

        if (sessionH.getAttribute("clUsrApp") != null) {
            clUsrApp = sessionH.getAttribute("clUsrApp").toString();
        }

        if (sessionH.getAttribute("CodigoLlave") != null) {
            CodigoCaptcha = sessionH.getAttribute("CodigoLlave").toString();
        }

        int Error = 0;
        String strUrlBack = "";
        strUrlBack = "../CargaReverso/Upload.jsp";
//        String registros = "0";
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>GUARDA Y ACTUALIZA (GENERICO)</title>");
        out.println("</head>");

        Error = getNSaveFile(request);

        if (Error == 0){
                boolean Proceso = true;
                String CargaTxt="";
                int IntColumns = 0, IntMaxRegistros = 0;

                System.out.println("Transferencia del Archivo...");
                //UtileriasBDF.ejecutaSQLNP("insert into tmkgcA_BitacoraUpload (clUsrApp,clTipoArchivoUpload) values (" + clUsrApp + ", 1)");

                System.out.println("Proceso antes de crear objetos.." + Proceso +" "+ path);

                sessionH.setAttribute("path", path);

                RespuestaReverso ProcesaTxt = new RespuestaReverso();
                
                
                
                if (extension_arch.equals(".txt") || extension_arch.equals(".dom")) {
                    System.out.println("entra a texto plano ... "+path);
                    System.out.println(LoteTabla);
                    System.out.println(StoreUpload);
                    
          
                    //Proceso = true;

                    //IntMaxRegistros = Integer.parseInt(MaxRegistros);//Se las paso del Upload.JSP

                          CargaTxt  = ProcesaTxt.CargaTabla(path, dsProyecto, Integer.parseInt(LoteTabla), StoreUpload, 0);
                           System.out.println("CargaTxt");
                          // CargaTxt="uij";

                               if (CargaTxt.equals("Ok"))
                               {    //System.out.println(CargaTxt);
                                    Proceso=true;
                               }
                               else{    ErrSQL= CargaTxt;
                                        Proceso= false;
                               }
                     //System.out.println(Proceso+" Error: "+ErrSQL);
//                    registros= String.valueOf(ProcesaTxt.filas);
                }

           ResultSet rs=null;

                if (Proceso) {  System.out.println("Proceso ok.." + Proceso);
                        String acep="", rech="";

                     
                        CargaTxt= ProcesaTxt.ProcesaTabla(dsProyecto, Integer.parseInt(LoteTabla), extension_arch);
                           //System.out.println(CargaTxt);
                                if (CargaTxt.equals("Archivo Procesado Correctamente"))
                                {   ErrSQL= "No Error";
                                    Proceso= true;
                                }
                                else{   ErrSQL= CargaTxt;
                                        System.out.println("Upload Error: "+ErrSQL);
                                        Proceso= false;
                                }

                           if(Proceso){

                               sessionH.setAttribute("proyecto", dsProyecto);
                               strUrlBack= "../CargaReverso/DetalleResp.jsp";

                               SessionCobros.ValidaPath(sessionH);

                               /*rs = UtileriasBDF.rsSQLNP("sp_tmkgcA_getInfoResp '" + dsProyecto +"',2");
                                 try {
                                    if (rs.next()) {
                                        detCobro = rs.getString("total");
                                        acep = rs.getString("aceptados");
                                        rech = rs.getString("rechazos");
                                    }
                                 }catch(SQLException e){ e.printStackTrace(); }
                             //System.out.println("<script>alert('Total de Registros Procesados: "+ detCobro +"  Cobro Exitoso: "+acep+"  Rechazos: "+rech+" - Archivo Procesado Correctamente.')</script>");
                            System.out.println("Total de Registros Procesados: "+ detCobro +"\nCobro Exitoso: "+acep+"\nRechazos: "+rech+"\nArchivo Procesado Correctamente.");
                            out.println("<script> alert('Total de Registros Procesados: "+ detCobro +" \\nCobro Exitoso: "+acep+" \\nRechazos: "+rech+" \\n- Archivo Procesado Correctamente.');</script>");*/
                           }
                           else
                           {out.println("<script> alert('"+ErrSQL+"');</script>");}
                     
                        if(rs!=null) {
                           try {
                            rs.close();
                            rs=null;
                           }catch (SQLException ex) { ex.printStackTrace(); }
                        }

                     out.println("<script>window.opener.fnValidaResponse(1,'" + strUrlBack + "')</script>");

                } else {
                    //System.out.println("proceso nop ok.." + Proceso);
                    out.println("<script> alert('No se Proceso el Archivo (Consulte a su administrador) Error: "+ ErrSQL +" ');</script>");
                }
            ProcesaTxt =null;
        } else {
                // System.out.println("Error!!! "+ Error);
                if (Error == 1) {
                    out.println("<script>alert('Fallo la transferencia de archivo ..');</script>");
                }
                else if (Error == 2) {
                    out.println("<script>alert('Codigo de Acceso Incorrecto ..');</script>");
                }
                else if (Error == 3) {
                    out.println("<script>alert('Nombre de Archivo Incorrecto ..');</script>");
                }
                else if (Error == 4) {
                    out.println("<script>alert('Lote del Archivo Incorrecto... \\n"+ErrSQL+"');</script>");
                }
                else if (Error == 5) {
                    out.println("<script>alert('Proyecto Incorrecto ..');</script>");
                }
                else {
                    out.println("<script>alert('"+ErrSQL+"');</script>");
                }
        }
                out.println("<script>window.opener.fnProcesoUpload(2)</script>");
                out.println("<script>window.opener.unblock()</script>");
                out.println("<script>window.close()</script>");

                System.out.println("Fin Upload Java\n");

        //<<<<<<<<<<<< Limpiar Variables >>>>>>>>>>>>
        path = null;
        fileName = null;
        strUrlBack = null;
//        registros = null;
        out.println("");
        out.println("");
        out.close();
    }

    public int getNSaveFile(HttpServletRequest request) {
        try {
            //<<<<<<<<<<<<<<<<<<<<<<< Path  >>>>>>>>>>>>>>>>>>>
            System.out.println("Entra: getNSaveFile");
            //path = "C:/Users/rarodriguez/Documents/NetBeansProjects/COBROS/public_html/CargaBD/Files";
            //String clUpload = "";
            ResultSet rsEx = UtileriasBDF.rsSQLNP("sp_tmkgcA_UploadReverso ");

                            try {
                                if (rsEx.next()) {
                                   // clUpload = rsEx.getString("clUpload");  //trae un identity de la tabla tmkgcA_BitacoraUpload
                                    path = rsEx.getString("Path");
                                }
                            } catch (Exception ee) {
                                System.err.println("Catch Excepetion ee = " + ee);
                            } finally {
                                try {
                                    //<<<<<<<<<< Cerrar Conexi?n >>>>>>>>>
                                    if (rsEx != null) {
                                        rsEx.close();
                                        rsEx = null;
                                    }
                                } catch (Exception Error) {
                                    System.err.println("Error: "+Error);
                                }
                            }

            //<<<<<<<< Verificar si el request multipart (que se este enviando un file) >>>>>>
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            //System.out.println("Is multipart =" + isMultipart);

            //>>>>>>>>>>>>>
            DiskFileItemFactory factory = new DiskFileItemFactory();

            //<<<<<<<<<<<<< maxima size que sera guardada en memoria >>>>>>>>>><
            //factory.setSizeThreshold( 4096 );

            //<<<<<<<<< si se excede de la anterior talla, se ira guardando temporalmente, en la sgte direccion >>>>>>>>>>
            //factory.setRepository(new File("/opt/app/apache-tomcat-5.5.15/webapps/SubirArchivos/xls/"));
            factory.setRepository(new File(path));

            //<<<<<<<<<<<<<<
            ServletFileUpload upload = new ServletFileUpload(factory);
            List /* FileItem */ items = upload.parseRequest(request);

            Iterator iter = items.iterator();

                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();

                            String name = item.getFieldName();
                            String value = item.getString();

                        if (item.isFormField()) {


                            System.out.println("Form field " + name + " with value " + value + " detected.");
                            //<<<<<<<<< Parsea Los input que se envian por POST >>>>>>>>>>

                            //<<<<<<<< C?digo de Acceso >>>>>>>>
                            if (name.equalsIgnoreCase("Codigo")) {
                                CodigoAccesoUsr = value;

                                //<<<< Codigo de acceso incorrecto >>>>
                                if (!CodigoAccesoUsr.equalsIgnoreCase(CodigoCaptcha)) {
                                    System.out.println("Codigo correcto: "+CodigoCaptcha);
                                    return 2;
                                }
                            }

                             //<<<<<<<< Nombre de  Archivo >>>>>>>>
                            if (name.equalsIgnoreCase("Archivo")) {
                                NombreArchivo = value.toString();
                            }

                            if (name.equalsIgnoreCase("dsProyecto")) {
                                dsProyecto = value.toString();
                            }

                          /*  if (name.equalsIgnoreCase("tipoResp")) {
                                tipoR = value.toString();
                            }*/

                           /* if(NombreArchivo=="")
                                return 3;
                            else if(dsProyecto=="")
                                return 5;
                            */
                        } else {

                          
                                System.out.println("Validar Nombre Archivo");



                            int val = 0;
                            val = validaNombreArchivo();

                            if (val != 0) {
                                return val;
                            }
                            
                            // Process a file upload
                            //<<<<<<<<<<<< Obtener el Numero de Archivo xls >>>>>>>>>>>
                           /* ResultSet rsEx = UtileriasBDF.rsSQLNP("sp_tmkgcA_UploadBD ");

                            try {
                                if (rsEx.next()) {
                                    clUpload = rsEx.getString("clUpload");  //trae un identity de la tabla tmkgcA_BitacoraUpload
                                    path = rsEx.getString("Path");
                                }
                            } catch (Exception ee) {
                                System.err.println("Catch Excepetion ee = " + ee);
                            } finally {
                                try {
                                    //<<<<<<<<<< Cerrar Conexi?n >>>>>>>>>
                                    if (rsEx != null) {
                                        rsEx.close();
                                        rsEx = null;
                                    }
                                } catch (Exception Error) {
                                    System.err.println("Error: "+Error);
                                }
                            }//*/

                            //<<<<<<<<<<< Se guarda el file enviado en el servidor local >>>>>>>>>>
                            //System.out.println("NombreArchivo: " + NombreArchivo);
                            //fileName = fileName + extension_arch;   //se construye un nuevo nombre de archivo

                            // System.out.println("fileName: " + fileName); //--******************Comentar
                            //System.out.println("path: " + path);//--******************Comentar

                          item.write(new File(path, fileName));
                        }
                    }
            //path = path + "/File_" + clUpload + ".xls";
            path = path + "/" + fileName;
            //path     = path + "/File_1.xls";
            //clUpload = null;
            //factory  = null;

        } catch (Exception e) {
            System.out.println("escritura de temporal" +e.getMessage());
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

public int validaNombreArchivo() {
       System.out.println("Valida Nombre Archivo");
        int err = 0;
        String FRespuesta = "";
        //String nombre = "";

            System.out.println("sp_tmkgcA_getInfoReverso '" + dsProyecto + "'");
            ResultSet rs = UtileriasBDF.rsSQLNP("sp_tmkgcA_getInfoReverso '" + dsProyecto + "'");

                try {
                        if (rs.next()) {
                            FRespuesta = rs.getString("NombreArchivo");
                            StoreUpload = rs.getString("sp_Importa");
                            //detCobro = rs.getString("detalle");
                            //Columns = rs.getString("ncampos");
                            //MaxRegistros = rs.getString("MaxRegistros");
                            LoteTabla = rs.getString("Lote"); //Lote que indica la tabla TMKcProyectoCobros
                            //Sufijo = rs.getString("Sufijo");
                            //nCamposResp = rs.getString("nCamposResp");
                            extension_arch = rs.getString("Extension");
                            err = 0;
                            extension_arch = extension_arch.trim();
                        }
                        
                        if(err==0)
                        {
                            //nombre = FRespuesta.trim() + LoteTabla;                                System.out.println(nombre);
                            String[] aux = NombreArchivo.split("\\.");
                            System.out.println("\tCOntador de puntos: " + (aux.length-1));

                                        if (NombreArchivo.contains(FRespuesta) && (aux.length-1)==1 && NombreArchivo.contains(extension_arch)==true){
                                            fileName = NombreArchivo.substring(NombreArchivo.indexOf(FRespuesta));

                                            Lote = fileName.replace(FRespuesta, "");

                                            if (fileName.contains(LoteTabla)) {
                                                err = 0;    //System.out.println("Lote OK: file-" + Lote + " vs tabla-" + LoteTabla);
                                            } else {
                                                err = 4;    //System.out.println("Lote incorrecto: file-" + Lote + " vs tabla-" + LoteTabla);
                                            }
                                        } else {
                                            err = 3;    //System.out.println("Nombre del Archivo Incorrecto:" + NombreArchivo + " vs " + FRespuesta);
                                                if(!NombreArchivo.contains(extension_arch))
                                                    ErrSQL= "Extension de archivo invalida";
                                                else if((aux.length-1)!=1)
                                                    ErrSQL= "Doble o mas extensiones en el archivo";
                                                else
                                                    ErrSQL= "Nombre no valido";
                                        } 
                                aux= null;
                        }else
                            { System.out.println("Proyecto no encontrado "+dsProyecto + " error "+err); }

                        if (rs != null) {
                            rs.close();
                            rs = null;
                        }
                } catch (Exception e) {
                    System.err.println("UploadReverso.java - Error al validar Nombre del Archivo");
                    e.printStackTrace();
                    err = 3;
                }
      //  System.out.println("Path: "+path+ " file: "+ fileName +
              //  "\nActualiza ArchivoResp "+NombreArchivo.substring(NombreArchivo.indexOf("R_"))+" para "+dsProyecto);
            //UtileriasBDF.ejecutaSQLNP("update tmkgcA_ProyectoCobroMx set ArchivoResp= '"+NombreArchivo.substring(NombreArchivo.indexOf("R_"))+"' where dsProyecto='"+dsProyecto+"'");
        return err;
    }  
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
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
