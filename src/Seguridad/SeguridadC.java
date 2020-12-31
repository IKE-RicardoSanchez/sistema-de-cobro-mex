/*
 * Acceso.java
 *
 * Created on 13 de octubre de 2004, 01:44 PM
 */

package Seguridad;

import Utilerias.UtileriasBDF;
import java.sql.ResultSet;
import java.sql.Connection;

/**
 *
 * @author  cabrerar
 * @version
 */

public class SeguridadC{
    
     /** 
     *  Verifica los permisos que tiene el usuario en la página indicada
     *  @param pclPage , pclUsrApp
     */
    private SeguridadC(){
    }

    public static boolean[] VerificaC(int pclPage, int pclUsrApp){
        boolean[] blnReturn = new boolean[5];
        ResultSet rs = UtileriasBDF.rsSQLNP("Select sum(cast(alta as tinyint)), sum(cast(baja as tinyint)),sum(cast(cambio as tinyint)), sum(cast(consulta as tinyint)) from tmkgcA_AccesoGpoXPag AxP inner join tmkgcA_UsrxGpo UxG on (UxG.clGpoUsr = AxP.clGpoUsr) where UxG.clUsrApp = " + pclUsrApp + " and clPaginaweb = " + pclPage + " group by UxG.clUsrApp");
        try{
            if(rs.next()){
                blnReturn[0]=rs.getBoolean(1); //Alta
                blnReturn[1]=rs.getBoolean(2); //Baja
                blnReturn[2]=rs.getBoolean(3); //Cambio
                blnReturn[3]=rs.getBoolean(4); //Consulta
                blnReturn[4]=blnReturn[0] || blnReturn[1] || blnReturn[2];
            }else{
                blnReturn[0]=false;
                blnReturn[1]=false;
                blnReturn[2]=false;
                blnReturn[3]=false;
                blnReturn[4]=false;
            }
            rs.close();
        }
        catch (Exception e) {
            //Fallo carga driver JDBC/ODBC.;
            e.printStackTrace();
            blnReturn[0]=false;
            blnReturn[1]=false;
            blnReturn[2]=false;
            blnReturn[3]=false;
            blnReturn[4]=false;
        }
        return blnReturn;
    }
    
     /** 
     *  Verifica que el usuario se encuentre en horario valido
     *  @param intclUsrApp 
     */
    public static boolean verificaHorarioC(int intclUsrApp ){
        try{
            System.out.println("verifica horarario");;
          ResultSet rsHr = UtileriasBDF.rsSQLNP("sp_tmkgcA_sys_VerificaHorario " + intclUsrApp );
          boolean blnReturn=false;
          if(rsHr.next()){
              blnReturn=(rsHr.getInt("Permite")==1);
              rsHr.close();
              return (blnReturn);
          }else{
              rsHr.close();
              return false;
          }
        }catch(Exception e) {
            e.getMessage();
        }
        return false;
    }

    /** 
    *  Verifica que los datos tomados por Request
    *  @param Request
    */

    public static boolean verificaRequest(String Request){
        boolean blnValidaRequest = false;
       
        blnValidaRequest = true;
        if(Request != null) {
            if (Request.indexOf("<")!=-1 || Request.indexOf("%3C")!=-1 ){
                System.out.println("-------"+Request);
                blnValidaRequest =  false;
            } else {
                blnValidaRequest = true;
            }
        }
        return blnValidaRequest;
    }
    
     /** 
     *  Verifica que si la página maneja bitácora de cambios
     *  @param intclPage
     */

    public static String VerificaBitacoraC(int pclPage){
        String strReturn = "";
        ResultSet rs = UtileriasBDF.rsSQLNP("Select coalesce(TablaBitacora,'') TablaBitacora from tmkgcA_cPaginaWeb where clPaginaweb = " + pclPage);
        try{
            if(rs.next()){
                strReturn = rs.getString("TablaBitacora");
            }
            rs.close();
            return strReturn;
        }
        catch (Exception e) {
            //Fallo carga driver JDBC/ODBC.;
            e.printStackTrace();
            return strReturn;
        }
    }
    
}
