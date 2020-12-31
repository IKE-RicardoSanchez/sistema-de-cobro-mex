/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilerias;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rarodriguez
 */
public class DownLoad extends HttpServlet {

    public static void Descarga(HttpSession se, HttpServletRequest request, HttpServletResponse response){
        System.out.println("Entra a Descarga");

        String tipo="", path="", extension="";
        boolean Proceso= false;

         if (se.getAttribute("proyecto") != null && se.getAttribute("lote") != null && se.getAttribute("archivo") != null){
                    System.out.println("Cuenta: "+se.getAttribute("nombre"));

//                    concat= se.getAttribute("concat").toString();
                    tipo= se.getAttribute("pre").toString();

		StringBuffer SQL = new StringBuffer();
		
                ResultSet rs=null;

                if(tipo==""){
                        tipo="0";
                            System.out.println("LayoutMx "+ se.getAttribute("proyecto") +"\nsp_tmkgcA_LayoutCobroMx '"+se.getAttribute("proyecto")+"',"+se.getAttribute("lote"));
                            SQL.append("sp_tmkgcA_LayoutCobroMx '").append(se.getAttribute("proyecto")).append("',").append(se.getAttribute("lote"));
                    }else{
                        tipo="1";
                        System.out.println("Pre validación");

                        SQL.append("sp_tmkgcA_PrevalidacionCobroMx '").append(se.getAttribute("proyecto")).append("',"+se.getAttribute("clUsrApp").toString());
                    }

                    try{
                        System.out.println("sp_tmkgcA_getInfoResp '"+se.getAttribute("proyecto")+"',"+tipo);
                            rs= UtileriasBDF.rsSQLNP("sp_tmkgcA_getInfoResp '"+se.getAttribute("proyecto")+"',"+tipo);
                                if(rs.next())
                                    extension= rs.getString("TipoArchivoSal").toString();
                    }catch(SQLException sql){System.out.println("Error al obtener extension:: "+ sql);   }

                    System.out.println("Extensión: "+extension);

                    if (se.getAttribute("archivo").toString().equals("sinNombre.txt")){
                                    tipo = se.getAttribute("proyecto") + "_Lote" + se.getAttribute("lote") + ".txt";
                                }else{    tipo = se.getAttribute("archivo").toString(); }

                        se.setAttribute("archivo", null);

                    DescargaAdicional(SQL, tipo, extension, request, response);
                            
         }//IF SESSION

    }//DESCARGA
    
    public static void DescargaAdicional(StringBuffer query, String Archivo, String extension, HttpServletRequest request, HttpServletResponse response){
        System.out.println("Entra Descarga Gral\n\tArchivo: "+ Archivo);
            String path="";
            HttpSession sesion= request.getSession(false);

            if(sesion.getAttribute("path")!=null)
                    SessionCobros.ValidaPath(sesion);

            if(extension.equals(".xls"))
                path= DownExcel(query, Archivo, response);
            else
                path= DownText(query, Archivo, request.getSession(false).getAttribute("concat").toString(), response);

            
                sesion.setAttribute("path", path);
    }//DESCARGAADICIONAL

    private static String DownExcel(StringBuffer SQL, String Nombre, HttpServletResponse response){
        System.out.println("Entra DownExcel");

        String path="";
         path= WriteExcel.excel(SQL, Nombre);

            return LanzaArchivo(path, Nombre, response);
    }//DOWNEXCEL

    private static String DownText(StringBuffer SQL, String Nombre, String concat, HttpServletResponse response){
        System.out.println("Entra DownText");
        
        ResultSet rs=null;
        StringBuffer Datos = new StringBuffer();
        String direccion= "";

        try{
                rs= UtileriasBDF.rsSQLNP("sp_tmkgcA_UploadBD");
                    if(rs.next())
                        direccion= rs.getString("Path").toString()+"/";
        }catch(SQLException sql){System.out.println("Error al obtener el path:: "+ sql);}
                //System.out.println(direccion);
        try{
               rs = UtileriasBDF.rsSQLNP(SQL.toString());
                    if (concat.equalsIgnoreCase("1")){
                                while (rs.next()){  Datos = Datos.append(rs.getString("Layout").toString());    }
                        }else{
                                 while (rs.next()){ Datos = Datos.append(rs.getString("Layout").toString()).append("\n"); }
                        }
        }catch(SQLException sql){System.out.println("Error al obtener layout:: "+ sql);}
        finally{
                    SQL.delete(0, SQL.length());
                    SQL = null;
        }

        try{
            PrintStream sp = new PrintStream(direccion + Nombre);
                sp.print(Datos);
                sp.flush();
                sp.close();
        }catch(FileNotFoundException file){System.out.println("Archivo descarga no creado:: "+ file);}

        return LanzaArchivo(Nombre, Nombre, response);
    }//DOWNTEXT


    public static String LanzaArchivo(String path, String Nombre, HttpServletResponse response){
        System.out.println("\nEntra LanzaArchivo");

        ResultSet rs= UtileriasBDF.rsSQLNP("sp_tmkgcA_UploadBD");

            try{
                    if(rs.next())
                        path= rs.getString("Path").toString()+"/" +path;
            }catch(SQLException e){ System.out.println("Error path: "+e);}
         //System.out.println("path: "+path);

        FileInputStream FileOut;
        try{
            try (PrintWriter out = response.getWriter()) {
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition","attachment;filename=\""+ Nombre+"\"");
                FileOut = new FileInputStream(new File(path));
                int i;
                while((i=FileOut.read())!=-1)
                {   out.write(i);   }
                out.flush();
                out.close();
                //response.getOutputStream().close();
                response.getWriter().close();
            }
                FileOut.close();
        }catch(IOException io){System.out.println("Error de IO LanzaArchivo::"+ io);}

            return path;
    }//LANZAARCHIVO
}//CLASS