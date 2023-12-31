/*
 * EjecutaAccion.java
 *
 * Created on 3 de diciembre de 2004, 04:27 PM
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
import java.sql.SQLException;


/**
 *
 * @author  cabrerar
 * @version
 */
public class EjecutaAccion extends HttpServlet {
    
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
        StringBuffer StrSentence = new StringBuffer();
        String strTablaBitacora ="";
        StringBuffer StrFields = new StringBuffer();
        StringBuffer StrVals = new StringBuffer();
        String StrType ="";
        StringBuffer StrWhere = new StringBuffer();
        String strBack="";
        String strLlave="";     
        String strNamIdentity="";
        
        Connection con = null;
        
        boolean blnBackId=false;
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Prueba de Servicio Seguridad</title>");
        out.println("</head>");
        out.println("<body>");
        
        if (request.getParameter("Action")==null){
            out.println("Problema con la definición de la acción a realizar, por favor vuelva a intentarlo");
            out.println("</body>");            
            out.println("</html>");  
            out.close();
            return;            
        }

        if (sessionH.getAttribute("clPaginaWebP")==null){
            out.println("No se definió variable de session para la página");
            out.println("</body>");            
            out.println("</html>");            
            out.close();
            return;            
        }
        
        con = UtileriasBDF.getConnection();
        ResultSet rs = null;
        ResultSet rsInfo = null;
        ResultSet rsLlave = null;
        
        try{
            rs = UtileriasBDF.rsSQLNP( "select Tabla, coalesce(TablaBitacora,'') TablaBitacora from tmkgcA_cPaginaWeb where clPaginaWeb = " + sessionH.getAttribute("clPaginaWebP"));
            String strNameF="";
            
            if(rs.next()){
                strTablaBitacora = rs.getString("TablaBitacora");                
                rsInfo = UtileriasBDF.rsSQLNP("sp_tmkgcA_sys_GetInfoTabla '" + rs.getString("Tabla") + "' ");
                
                if (Integer.parseInt(request.getParameter("Action"))==1){
                    StrSentence.append("Insert into ").append(rs.getString("Tabla"));
                    
                    while(rsInfo.next()){
                        strNameF = rsInfo.getString("NameF");
                        if (request.getParameter(strNameF)!=null){
                            // No es un campo identity, en un insert se debe omitir
                            if (rsInfo.getString("Identit").equalsIgnoreCase("No")){
                                if (StrFields.toString().compareToIgnoreCase("")!=0){
                                    StrFields.append(",");
                                    StrVals.append(",");
                                }
                                StrFields.append(strNameF);
                                
                                StrType = rsInfo.getString("TypeData").toString();
                                if (StrType.equalsIgnoreCase("tinyint") || StrType.equalsIgnoreCase("bigint") || StrType.equalsIgnoreCase("binary") || StrType.equalsIgnoreCase("bit") || StrType.equalsIgnoreCase("decimal") || StrType.equalsIgnoreCase("float") || StrType.equalsIgnoreCase("int") || StrType.equalsIgnoreCase("money") || StrType.equalsIgnoreCase("numeric") || StrType.equalsIgnoreCase("real") || StrType.equalsIgnoreCase("smallint") || StrType.equalsIgnoreCase("smallmoney") || StrType.equalsIgnoreCase("uniqueidentifier") || StrType.equalsIgnoreCase("varbinary")){
                                    if(request.getParameter(strNameF).toString().equalsIgnoreCase("")){
                                        StrVals.append("null");                                                                        
                                    }else{
                                        StrVals.append(request.getParameter(strNameF));
                                    }
                                }else{
                                    if(request.getParameter(strNameF).toString().equalsIgnoreCase("") && (rsInfo.getString("nullable").equalsIgnoreCase("Si")) ){
                                        StrVals.append("null");
                                    }else{
                                        StrVals.append("'").append(request.getParameter(strNameF).replaceAll("'"," ")).append("'");
                                    }
                                }
                                if (rsInfo.getString("LlavePrimaria").equalsIgnoreCase("Si")){
                                    if(strBack.equalsIgnoreCase("")){
                                        strBack=strNameF +"=" + request.getParameter(strNameF);
                                    }else{
                                        strBack=strBack + "&" + strNameF +"=" + request.getParameter(strNameF);
                                    }
                                }
                            }
                            else{
                                strBack=strNameF;
                                strNamIdentity = strNameF;
                                blnBackId=true;
                            }
                        }
                        else{
                            if(rsInfo.getString("LlavePrimaria").equalsIgnoreCase("Si") && rsInfo.getString("Identit").equalsIgnoreCase("No")){
                                out.println("La llave primaria no debe quedar en blanco, por favor vuelva a intentarlo");
                                out.close();
                                strNameF=null;
                                return;
                            }
                        }
                    }
                    StrSentence.append("(").append(StrFields).append(") values (").append(StrVals).append(")");
                }
                
                if (Integer.parseInt(request.getParameter("Action"))==2){
                    StrSentence.append("Update ").append(rs.getString("Tabla")).append(" set ");
                    while(rsInfo.next()){
                        if (request.getParameter(rsInfo.getString("NameF"))!=null){
                            // No es un campo identity, en un insert se debe omitir
                            if (rsInfo.getString("LlavePrimaria").equalsIgnoreCase("No") && rsInfo.getString("Identit").equalsIgnoreCase("No")){
                                if (StrFields.toString().compareToIgnoreCase("")!=0){
                                    StrFields.append(",");
                                }
                                StrFields.append(rsInfo.getString("NameF"));
                                StrType = rsInfo.getString("TypeData").toString();
                                if (StrType.equalsIgnoreCase("tinyint") || StrType.equalsIgnoreCase("bigint") || StrType.equalsIgnoreCase("binary") || StrType.equalsIgnoreCase("bit") || StrType.equalsIgnoreCase("decimal") || StrType.equalsIgnoreCase("float") || StrType.equalsIgnoreCase("int") || StrType.equalsIgnoreCase("money") || StrType.equalsIgnoreCase("numeric") || StrType.equalsIgnoreCase("real") || StrType.equalsIgnoreCase("smallint") || StrType.equalsIgnoreCase("smallmoney") || StrType.equalsIgnoreCase("uniqueidentifier") || StrType.equalsIgnoreCase("varbinary")){
                                    if(request.getParameter(rsInfo.getString("NameF")).toString().equalsIgnoreCase("")){
                                        StrFields.append("=null");
                                    }else{
                                        StrFields.append("=").append(request.getParameter(rsInfo.getString("NameF")));
                                    }
                                }else{
                                    if(request.getParameter(rsInfo.getString("NameF")).toString().equalsIgnoreCase("") && (rsInfo.getString("nullable").equalsIgnoreCase("Si")) ){
                                        StrFields.append("=null");
                                    }else{
                                        StrFields.append("='").append(request.getParameter(rsInfo.getString("NameF")).replaceAll("'"," ")).append("'");
                                    }
                                }
                            }
                            if (rsInfo.getString("LlavePrimaria").equalsIgnoreCase("Si")){
                                if(strBack.equalsIgnoreCase("")){
                                    strBack=strBack + rsInfo.getString("NameF") +"=" + request.getParameter(rsInfo.getString("NameF"));
                                }else{
                                    strBack=strBack + "&" + rsInfo.getString("NameF") +"=" + request.getParameter(rsInfo.getString("NameF"));
                                }
                                if (StrWhere.toString().equalsIgnoreCase("")){
                                    StrWhere.append("Where ");
                                }
                                else{
                                    StrWhere.append(" and ");
                                }
                                StrWhere.append(rsInfo.getString("NameF")).append("=").append(request.getParameter(rsInfo.getString("NameF")));
                            }
                        }
                        else{
                            if(rsInfo.getString("LlavePrimaria").equalsIgnoreCase("Si") && rsInfo.getString("Identit").equalsIgnoreCase("No")){
                                out.println("La llave primaria no debe quedar en blanco, por favor vuelva a intentarlo");
                                out.close();
                                strNameF=null;
                                return;
                            }
                        }
                    }
                    if(StrWhere.toString().equalsIgnoreCase("")){
                        out.println("La llave primaria no debe quedar en blanco, verifique con su administrador");
                        out.close();
                        strNameF=null;
                        return;
                    }
                    StrSentence.append(" ").append(StrFields).append(" ").append(StrWhere);
                }

                if (Integer.parseInt(request.getParameter("Action"))==3){
                    StrSentence.append("Delete from ").append(rs.getString("Tabla"));
                    while(rsInfo.next()){
                        if (request.getParameter(rsInfo.getString("NameF"))!=null){
                            if (rsInfo.getString("LlavePrimaria").equalsIgnoreCase("Si")){
                                if(strBack.equalsIgnoreCase("")){
                                    strBack=strBack + rsInfo.getString("NameF") +"=" + request.getParameter(rsInfo.getString("NameF"));
                                }else{
                                    strBack=strBack + "&" + rsInfo.getString("NameF") +"=" + request.getParameter(rsInfo.getString("NameF"));
                                }
                                
                                if (StrWhere.toString().equalsIgnoreCase("")){
                                    StrWhere.append("Where ");
                                }
                                else{
                                    StrWhere.append(" and ");
                                }
                                StrWhere.append(rsInfo.getString("NameF")).append("=").append(request.getParameter(rsInfo.getString("NameF")));
                            }
                        }
                        else{
                            if(rsInfo.getString("LlavePrimaria").equalsIgnoreCase("Si")){
                                out.println("La llave primaria no debe quedar en blanco, por favor vuelva a intentarlo");
                            }
                        }
                    }
                    if(StrWhere.toString().equalsIgnoreCase("")){
                        out.println("La llave primaria no debe quedar en blanco, verifique con su administrador");
                        out.close();
                        return;
                    }
                    StrSentence.append(" ").append(StrWhere);
                }

                if ((Integer.parseInt(request.getParameter("Action"))==1)&&(blnBackId==true)){                
//                    try{
                        rsLlave = UtileriasBDF.rsSQLNP( StrSentence.append(" Select @@Identity Llave ").toString());
                        if(rsLlave.next()){
                            strLlave=rsLlave.getString("Llave");
                            if  (rs.getString("Tabla").equalsIgnoreCase("Expediente")){
                                UtileriasBDF.ejecutaSQLNP( "Update Expediente set clExpedienteOrigen="+ strLlave + " where clExpediente=" + strLlave);   
                            }
                            strBack=strBack + "=" + strLlave;
                        }
/*                    }catch(Exception e){
                        out.close();
                        e.printStackTrace();
                    }*/
                }else{
                   UtileriasBDF.ejecutaSQLNP( StrSentence.toString());                                            
                }

                if (!strTablaBitacora.equalsIgnoreCase("")){
                    InsertaBitacora(request,strTablaBitacora, strLlave, strNamIdentity, con);
                }
                String strUrlBack="";
                if(request.getParameter("URLBACK")!=null){
                    strUrlBack = request.getParameter("URLBACK");
                    //out.println("<script> //'"+ strUrlBack + strBack +"'</script>");             
                    out.println("<script> window.opener.fnValidaResponse(1,'"+ strUrlBack + strBack +"')</script>");                    
                }
                
            }else{
                out.println("Problemas al obtener información de la página solicitada");
            }
        }catch(Exception e){
            e.printStackTrace();
            out.println("<script> window.opener.fnValidaResponse(-1,'')</script>");                            
            out.println("Problema al registrar el movimiento (verifique formato de fechas y campos numéricos sin separador de coma)");
        }finally{
          out.println("</body>");            
          out.println("</html>");            
          StrSentence.delete(0,StrSentence.length());
          strTablaBitacora =null;
          StrFields.delete(0,StrFields.length());
          StrVals.delete(0,StrVals.length());
          StrType =null;
          StrWhere.delete(0,StrWhere.length());
          strBack=null;
          strLlave=null;     
          strNamIdentity=null;

          try 
          {
            if (rsInfo!=null){
                rsInfo.close();
            }
            if (rsLlave!=null){
                rsLlave.close();
                rsLlave=null;
            }
            if (rs!=null)
            {
              rs.close();
              rs=null;
            }
            if (con!=null)
            {
              con.close();
            }
          }catch(Exception ee)
          {
            ee.printStackTrace();
          }
          out.close();
        }
    }
    
    private void InsertaBitacora (HttpServletRequest request, String pstrTabla, String pstrValIdentity, String pStrNamIdentity, Connection con)
    throws ServletException, IOException {
        HttpSession sessionH = request.getSession(false);
        String StrSentence = "";                    
        String StrFields = "";
        String StrVals = "";
        String StrType ="";
        String strNameF="";        
        
        if(request.getParameter("Bitacora")!=null){
            if (request.getParameter("Bitacora").toString().compareToIgnoreCase("")==0){
                return;
            }
        }
        try{
                ResultSet rsInfo = UtileriasBDF.rsSQLNP( "sp_tmkgcA_sys_GetInfoTabla " + pstrTabla);
                                
                StrSentence = "Insert into " + pstrTabla;                                        
                
                while(rsInfo.next()){
                    strNameF=rsInfo.getString("NameF");
                    
                    if (request.getParameter(strNameF)!=null){
                        // No es un campo identity, en un insert se debe omitir
                        if (rsInfo.getString("Identit").equalsIgnoreCase("No")){
                            if (StrFields!=""){
                                StrFields=StrFields+",";
                                StrVals=StrVals+",";
                            }
                            StrFields = StrFields + strNameF;
                            StrType = rsInfo.getString("TypeData").toString();
                            if (StrType.equalsIgnoreCase("tinyint") || StrType.equalsIgnoreCase("bigint") || StrType.equalsIgnoreCase("binary") || StrType.equalsIgnoreCase("bit") || StrType.equalsIgnoreCase("decimal") || StrType.equalsIgnoreCase("float") || StrType.equalsIgnoreCase("int") || StrType.equalsIgnoreCase("money") || StrType.equalsIgnoreCase("numeric") || StrType.equalsIgnoreCase("real") || StrType.equalsIgnoreCase("smallint") || StrType.equalsIgnoreCase("smallmoney") || StrType.equalsIgnoreCase("uniqueidentifier") || StrType.equalsIgnoreCase("varbinary")){
                                if ((Integer.parseInt(request.getParameter("Action"))==1)&&(pStrNamIdentity.compareToIgnoreCase(strNameF)==0)){                
                                    if(pstrValIdentity.equalsIgnoreCase("")){
                                        StrVals = StrVals + "null";                                                                        
                                    }else{
                                        StrVals  = StrVals + pstrValIdentity;
                                    }
                                }else{
                                    if(request.getParameter(strNameF).toString().equalsIgnoreCase("")){
                                        StrVals = StrVals + "null";                                                                        
                                    }else{
                                        StrVals  = StrVals + request.getParameter(strNameF);                                    
                                    }
                                }
                            }else{
                                if ((Integer.parseInt(request.getParameter("Action"))==1)&&(pStrNamIdentity.compareToIgnoreCase(strNameF)==0)){                
                                    if(pstrValIdentity.equalsIgnoreCase("") && (rsInfo.getString("nullable").equalsIgnoreCase("Si")) ){
                                        StrVals = StrVals + "null";                                                                        
                                    }else{
                                        StrVals = StrVals + "'" + pstrValIdentity.replaceAll("'"," ") + "'";
                                    }
                                }else{
                                    if(request.getParameter(strNameF).toString().equalsIgnoreCase("") && (rsInfo.getString("nullable").equalsIgnoreCase("Si")) ){
                                        StrVals = StrVals + "null";                                                                        
                                    }else{
                                        StrVals = StrVals + "'" + request.getParameter(strNameF).replaceAll("'"," ") + "'";
                                    }
                                }
                            }
                        }
                    }
                    else { 
                        if (sessionH.getAttribute(rsInfo.getString("NameF"))!=null){
                            // No es un campo identity, en un insert se debe omitir
                            if (rsInfo.getString("Identit").equalsIgnoreCase("No")){
                                if (StrFields!=""){
                                    StrFields=StrFields+",";
                                    StrVals=StrVals+",";
                                }
                                StrFields = StrFields + rsInfo.getString("NameF");
                                StrType = rsInfo.getString("TypeData").toString();
                                if (StrType.equalsIgnoreCase("tinyint") || StrType.equalsIgnoreCase("bigint") || StrType.equalsIgnoreCase("binary") || StrType.equalsIgnoreCase("bit") || StrType.equalsIgnoreCase("decimal") || StrType.equalsIgnoreCase("float") || StrType.equalsIgnoreCase("int") || StrType.equalsIgnoreCase("money") || StrType.equalsIgnoreCase("numeric") || StrType.equalsIgnoreCase("real") || StrType.equalsIgnoreCase("smallint") || StrType.equalsIgnoreCase("smallmoney") || StrType.equalsIgnoreCase("uniqueidentifier") || StrType.equalsIgnoreCase("varbinary")){
                                    if(sessionH.getAttribute(rsInfo.getString("NameF")).toString().equalsIgnoreCase("")){
                                        StrVals = StrVals + "null";                                                                        
                                    }else{
                                        StrVals  = StrVals + sessionH.getAttribute(rsInfo.getString("NameF"));                                    
                                    }
                                }else{
                                    if(sessionH.getAttribute(rsInfo.getString("NameF")).toString().equalsIgnoreCase("") && (rsInfo.getString("nullable").equalsIgnoreCase("Si")) ){
                                        StrVals = StrVals + "null";                                                                        
                                    }else{
                                        StrVals = StrVals + "'" + sessionH.getAttribute(rsInfo.getString("NameF")) + "'";
                                    }
                                }
                            }
                         }
                    }
                }
                rsInfo.close();
                rsInfo=null;
                StrSentence = StrSentence + "(" + StrFields + ") values (" + StrVals + ")";
                UtileriasBDF.ejecutaSQLNP( StrSentence);                                            
                StrSentence = null;                    
                StrFields = null;
                StrVals = null;
                StrType =null;
                strNameF=null;        
        }catch(Exception e){
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
