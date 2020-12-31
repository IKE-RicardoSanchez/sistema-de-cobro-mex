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
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.util.*;

/**
 *
 * @author bsanchez
 */
public class Upload_bkp extends HttpServlet {

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
    private static String idProyecto = "";
    private static String StoreUpload = "";
    private static String CodProyecto = "";
    private static String Lote = "";
    private static String LoteTabla = "";
    private static String Sufijo = "";
    private static String encabezado = "";
    private static String extencion_arch = "";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Upload Java");
        HttpSession sessionH = request.getSession(false);

        //<<<<<<<<<<<<  Obtener el usuario que Restra >>>>>>>>>>>
        String clUsrApp = "";
        String clTipoArchivoUpload = "";

        if (sessionH.getAttribute("clUsrApp") != null) {
            clUsrApp = sessionH.getAttribute("clUsrApp").toString();
        }

        if (sessionH.getAttribute("CodigoLlave") != null) {
            CodigoCaptcha = sessionH.getAttribute("CodigoLlave").toString();
        }


        int Error = 0;
        String strUrlBack = "";
        strUrlBack = "../CargaBD/Upload.jsp";

        Error = getNSaveFile(request);

        String registros = "0";
        String strdsError = "";


        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>GUARDA Y ACTUALIZA (GENERICO)</title>");
        out.println("</head>");

        if (Error == 0) {

            boolean Proceso = false;
            int IntColumns = 0, IntMaxRegistros = 0;

            System.out.println("Transferencia del Archivo...");
            UtileriasBDF.ejecutaSQLNP("insert into tmkgcA_BitacoraUpload (clUsrApp,clTipoArchivoUpload) values (" + clUsrApp + ", 1)");

            System.out.println("Proceso antes de crear objetos.." + Proceso);

            if (extencion_arch.equals(".xls")) {
                //System.out.println("entra a .xls ...");
                //Proceso = true;
                //<<<<<<<<<<< Procesar Archivo >>>>>>>>>>
/*                ProcesaFile ProcesoXLS = new ProcesaFile();

                IntColumns = Integer.parseInt(Columns);
                IntMaxRegistros = Integer.parseInt(MaxRegistros);

                Proceso = ProcesoXLS.ProcesaArchivo(path, clUsrApp, IntColumns, IntMaxRegistros, StoreUpload, NombreArchivo, Lote, Sufijo);
                registros = String.valueOf(ProcesoXLS.filas); //Registros Procesados Correctamente
                strdsError = ProcesoXLS.dsError; //Descripci�n de error

            } else if (extencion_arch.equals(".txt")) {
                //System.out.println("entra a .txt ...");
                //Proceso = true;

                ProcesaFileTxt ProcesaTxt = new ProcesaFileTxt();

                IntMaxRegistros = Integer.parseInt(MaxRegistros);//Se las paso del Upload.JSP

                Proceso = ProcesaTxt.ProcesaArchivoTexto_BufferedReader(path, clUsrApp, IntMaxRegistros, StoreUpload, NombreArchivo, Lote, Sufijo, encabezado);
                strdsError = ProcesaFileTxt.dsError;
*/
            }

            if (Proceso) {
                // System.out.println("proceso ok.." + Proceso);

                //<<<<<<<<<<<<< Actualiza Tabla >>>>>>>>>>>>>>
                UtileriasBDF.ejecutaSQLNP("sp_ActualizaEstatusResp '" + Sufijo + "','" + Lote + "'");
                out.println("<script>alert('" + registros + "  Registros Procesados -  Archivo Procesado Correctamente.')</script>");
                out.println("<script>window.opener.fnValidaResponse(1,'" + strUrlBack + "')</script>");

            } else {

                System.out.println("proceso nop ok.." + Proceso);
                out.println("<script>alert('No se Proceso el Archivo (Consulte a su administrador) - " + strdsError + "')</script>");
                out.println("<script>window.opener.fnProcesoUpload(2)</script>");
                out.println("<script>window.close()</script>");
            }

        } else {

            // System.out.println("Error!!! "+ Error);

            if (Error == 1) {
                out.println("<script>alert('Fallo la transferencia de archivo ..')</script>");
            }

            if (Error == 2) {
                out.println("<script>alert('Codigo de Acceso Incorrecto ..')</script>");
            }

            if (Error == 3) {
                out.println("<script>alert('Nombre de Archivo Incorrecto ..')</script>");
            }

            if (Error == 4) {
                out.println("<script>alert('Lote del Archivo Incorrecto ..')</script>");
            }

            out.println("<script>window.opener.fnProcesoUpload(2)</script>");
            out.println("<script>window.close()</script>");

        }

        //<<<<<<<<<<<< Limpiar Variables >>>>>>>>>>>>
        path = null;
        fileName = null;
        strUrlBack = null;
        registros = null;
        strdsError = null;

        out.println("");
        out.println("");
        out.close();
    }

    public int getNSaveFile(HttpServletRequest request) {
        try {
            //<<<<<<<<<<<<<<<<<<<<<<< Path  >>>>>>>>>>>>>>>>>>>
            System.out.println("Entra: getNSaveFile");
            path = "C:/Users/rarodriguez/Documents/NetBeansProjects/COBROS/public_html/CargaBD/Files";

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
            String clUpload = "";
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString();

                    System.out.println("Form field " + name + " with value " + value + " detected.");
                    //<<<<<<<<< Parsea Los input que se envian por POST >>>>>>>>>>

                    //<<<<<<<< C?digo de Acceso >>>>>>>>
                    if (name.equalsIgnoreCase("Codigo")) {
                        CodigoAccesoUsr = value;

                        //<<<< Codigo de acceso incorrecto >>>>
                        if (!CodigoAccesoUsr.equalsIgnoreCase(CodigoCaptcha)) {
                            return 2;
                        }
                    }

                   /*  //<<<<<<<< Nombre de  Archivo >>>>>>>>
                    if (name.equalsIgnoreCase("Archivo")) {
                    NombreArchivo = value.toString();

                    }*/

                    if (name.equalsIgnoreCase("idProyecto")) {
                        idProyecto = value.toString();
                    }


                } else {
                    System.out.println("Ahh");

                    int val = 0;
                    val = validaNombreArchivo();

                    if (val != 0) {
                        return val;
                    }

                    // Process a file upload
                    //<<<<<<<<<<<< Obtener el Numero de Archivo xls >>>>>>>>>>>
                    ResultSet rsEx = UtileriasBDF.rsSQLNP("sp_tmkgcA_UploadBD ");

                    try {
                        if (rsEx.next()) {
                            clUpload = rsEx.getString("clUpload");  //trae un identity de la tabla tmkgcA_BitacoraUpload
                            path = rsEx.getString("Path"); //       C:\Users\fcerqueda\Documents\TMK\Proyectos_TMK\COBRA_VE\CargaVE
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
                            System.err.println(Error);
                        }
                    }
                    //<<<<<<<<<<< Se guarda el file enviado en el servidor local >>>>>>>>>>
                    //System.out.println("NombreArchivo: " + NombreArchivo);
                    fileName = fileName + "_" + clUpload + extencion_arch;   //se construye un nuevo nombre de archivo

                    // System.out.println("fileName: " + fileName); //--******************Comentar
                    //System.out.println("path: " + path);//--******************Comentar

                    item.write(new File(path, fileName));///////////////////////////////////////////////descomentrar ok
                }
            }
            //path = path + "/File_" + clUpload + ".xls";
            path = path + "/" + fileName;
            //path     = path + "/File_1.xls";
            //clUpload = null;
            //factory  = null;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return 1;
        }

        return 0;
    }

    public int validaNombreArchivo() {
        int err = 0;

        String FRespuesta = "";
        String nombre = "";

        //System.out.println("antes del rs " + idProyecto);

        ResultSet rs = UtileriasBDF.rsSQLNP("st_CAgetInfoProyecto " + idProyecto);

        try {
            if (rs.next()) {
                FRespuesta = rs.getString("FormatoRespuesta");   //R_CB_VISA_L    ó   R_CB_AMEX_L
                StoreUpload = rs.getString("sp_Importa");   /*no existe, no se ocupa*/
                CodProyecto = rs.getString("CodProyecto");
                Columns = rs.getString("RespColumnas");
                MaxRegistros = rs.getString("RespMaxRegistros");
                LoteTabla = rs.getString("Lote"); //Lote que indica la tabla TMKcProyectoCobros
                Sufijo = rs.getString("Sufijo");
                encabezado = rs.getString("encabezado");
                extencion_arch = rs.getString("tipo_archivo");
                extencion_arch = extencion_arch.trim();
            }
            NombreArchivo = FRespuesta + LoteTabla;

            /* System.out.println("despues del rs " + FRespuesta); //R_CB_VISA_L
            System.out.println("despues del rs " + StoreUpload);//st_ConcentradoRespCB
            System.out.println("despues del rs " + CodProyecto);//CBVC
            System.out.println("despues del rs " + Columns);    //8
            System.out.println("despues del rs " + MaxRegistros);//60000
            System.out.println("despues del rs " + LoteTabla);//84
            System.out.println("despues del rs " + Sufijo);//CB
            System.out.println("despues del rs " + encabezado);//0
            System.out.println("despues del rs " + idProyecto);//8
            System.out.println("despues del rs " + extencion_arch);//.txt
            System.out.println("Nombre Archivo:" + NombreArchivo);//*/

            if (NombreArchivo.contains(FRespuesta)) {//tconstais devuelve true o false
                fileName = NombreArchivo.substring(NombreArchivo.indexOf(FRespuesta));//R_CB_VISA_L    ó   R_CB_AMEX_L algo asi va a traer  indexOf devuelve posicion de cadena buscada o -1 sino
                Lote = fileName.replace(FRespuesta, "");

                // System.out.println("nombre OK:" + NombreArchivo + " vs " + fileName + " lote:" + Lote);

                if (fileName.contains(LoteTabla)) {
                    err = 0;
                    //System.out.println("Lote OK: file-" + Lote + " vs tabla-" + LoteTabla);
                } else {
                    err = 4;
                    //System.out.println("Lote incorrecto: file-" + Lote + " vs tabla-" + LoteTabla);
                }

            } else {
                err = 3;
                //System.out.println("Nombre del Archivo Incorrecto:" + NombreArchivo + " vs " + FRespuesta);

            }

            if (rs != null) {
                rs.close();
                rs = null;
            }

        } catch (Exception e) {
            System.err.println("Upload.java - Error al validar Nombre del Archivo");
            e.printStackTrace();
            err = 3;
        }

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
