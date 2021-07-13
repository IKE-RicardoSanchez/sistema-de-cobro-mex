/*
 * DAOBASE.java
 *
 * Created on 22 de marzo de 2006, 05:31 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.ike.model;

import Utilerias.UtileriasBDF;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.Collection;
import java.util.ArrayList;

import snaq.db.ConnectionPool;
/*
import net.sourceforge.jtds.jdbc.Driver;//JTDS Import
import net.sourceforge.jtds.jdbcx.JtdsDataSource;
import net.sourceforge.jtds.jdbcx.JtdsObjectFactory;
 */

/**
 * @author cabrerar
 */
public class DAOBASE {
    static ConnectionPool pool = null;
    static long timeout = 10000;  // 30 second expire timeout
    private static boolean isConnected = false;
    private static byte bytLanguaje = 2;
    
/*  public static Connection getConnection() throws DAOException
  {
      Context ctx = null;
      DataSource ds = null;
      Connection con=null;
 
      try {
        ctx = new InitialContext();
        // java:comp/env siempre para indicar que se busque en el contexto de jndi
        ds = (DataSource)ctx.lookup("java:comp/env/jdbc/IkeProduccion");
        con = ds.getConnection();
 
      }catch(NamingException NE){
          throw new DAOException(NE.getMessage(), NE);
      }catch(SQLException SE){
          throw new DAOException(SE.getMessage(), SE);
      }
      finally{
          try{
              if (ctx!=null){
                  ctx.close();
              }
          }
          catch(NamingException NEb){
              NEb.printStackTrace();
          }
      }
      return con;
  }
 */
    
    public static Connection getConnection() {
        Connection con = null;
        try {
            Connect();
            con = pool.getConnection(timeout);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    
    public synchronized static void Connect(){
        
        if (isConnected == false){
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//JDBC 2005
            } catch (Exception e) {
                //Fallo carga driver JDBC/ODBC.;
                e.printStackTrace();
            }
            
          pool =  new ConnectionPool("local",
                                   200,
                                   1200,
                                   10000,  // milliseconds
                                   //"jdbc:sqlserver://172.21.17.10:1433;SelectMethod=cursor;DatabaseName=TELEMARKETING_PROD",
                                    //"dessiaikeMX","dess1ike.TMK.2011"); //PROD
                                   "jdbc:sqlserver://172.21.10.88:1533;SelectMethod=cursor;DatabaseName=TELEMARKETING_DEV", //DESA
                                   "DesaCobro_MX_Test","D3s4MX.2019"); //DESA
                                    //"jdbc:sqlserver://172.21.10.83:1433;SelectMethod=cursor;DatabaseName=TELEMARKETING_PROD",                                
                                   //"desacobros","D3s4C0br0s.2013");
                                   //"caquiroz","chq86v(10)");//USUARIO Y CONTRASEÑA
                                   //"DesaCobro_MX_Test","D3s4MX.2017");
                                   
                                   //"AdmCobroTest","4dmc@br@t3st");
          pool.init(200); 
          isConnected = true;
          System.out.println("Conectado DAOBASE");
        }
    }
    
    public static String LimpiaSentencia(String SQL){
        SQL = SQL.replaceAll("(?i)"+"waitfor delay"," ");
        SQL = SQL.replaceAll("(?i)"+"waitfor"," ");
        SQL = SQL.replaceAll("(?i)"+"delay"," ");

        return SQL;
    }
  
    protected Collection rsSQLNP(String SQL,LlenaDatos Lld) throws DAOException{
      /*
       *      M�todo que regresa un record set
       *      Nota: Una vez ejecutada la sentencia no se cierra la conexi�n
       */

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(SQL);
        final String SQL2 = StrSQLResp;

        Connection con=null;
        Statement stmt = null;
        Statement stmtDF = null;
        ResultSet rsObt = null;
        Collection col= new ArrayList();
        
        
        try{
            con = getConnection();
            stmt = con.createStatement();
            stmtDF = con.createStatement();
            stmtDF.execute("set dateformat mdy");
            stmtDF.execute("set nocount on");
            stmtDF.execute("set quoted_identifier off");
//System.out.println("DAOBASE: " + SQL2);
            rsObt = stmt.executeQuery(SQL2);
            while (rsObt.next()){
                col.add(Lld.llena(rsObt));
            }
            
        } catch (Throwable t) {
            throw new DAOException(t.getMessage(),t);
        } finally{
            try {
                //Cierro Resultset
                if (rsObt!=null){
                    rsObt.close();
                }
                //Cierro Statement
                if (stmt!=null){
                    stmt.close();
                }
                //Cierro Statement
                if (stmtDF!=null){
                    stmtDF.close();
                }
                // Cierro Conexion
                if (con!=null){
                    con.close();
                }
            }catch(Exception ee){
                ee.printStackTrace();
            }
        }
        
        return col;
    }
    
    
    
    
    
    /* FALTANTES ---------------------------------------------------- */
    
    
    private static boolean logStatus=false;
    
    
    public synchronized static void StartLog() {
        logStatus=true;
    }
    
    public synchronized static void StopLog() {
        logStatus=false;
    }
    
    public static boolean getStatusLog() {
        return logStatus;
    }
    
    
    public static void ejecutaSQLNP(String args) {
    /* M�todo que no regresa nada despu�s de ejecutar una sentencia SQL
       Nota: obtiene conexi�n y la cierra una vez ejecutada la sentencia */

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(args);
        final String SQL2 = StrSQLResp;

        Statement stmt = null;
        Connection con=null;
        
        try{
            con = UtileriasBDF.getConnection();
            if (con != null) {
                stmt = con.createStatement();
                try {
                    if (logStatus==true) {
                        long longTimeI = System.currentTimeMillis();
                        long longTimeF = 0;
                        StringBuffer strBuff = new StringBuffer();
                        
                        stmt.execute("set dateformat mdy");
                        stmt.execute("set nocount on");
                        stmt.execute("set quoted_identifier off");
                        stmt.execute(SQL2);
                        
                        longTimeF = System.currentTimeMillis();
                        
                        strBuff.append(" set quoted_identifier off Insert into LogDB(Tiempo,Sentencia) values(");
                        strBuff.append((longTimeF-longTimeI)/1000);
                        strBuff.append(",\"Con Con: ");
                        strBuff.append(SQL2);
                        strBuff.append("\")");
                        stmt.execute(strBuff.toString());
                        strBuff.delete(0,strBuff.length());
                        strBuff=null;
                        
                    }else {
                        stmt.execute("set dateformat mdy");
                        stmt.execute("set nocount on");
                        stmt.execute("set quoted_identifier off");
                        stmt.execute(SQL2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No me pude conectar");
            }
        } catch (SQLException sqle) {
            do {
                System.out.println("ERROR DAO : ejecutaSQLNP");
                System.out.println(SQL2.toString());
                System.out.println("SQL STATE: " + sqle.getSQLState());
                System.out.println("ERROR CODE: " + sqle.getErrorCode());
                System.out.println("MESSAGE: " + sqle.getMessage());
                System.out.println();
                sqle = sqle.getNextException();
            } while (sqle != null);
            
            sqle.printStackTrace();
        }finally{
            try {
                if (stmt!=null){
                    stmt.close();
                }
                if (con!=null){
                    con.close();
                }
            }catch(Exception ee){
                ee.printStackTrace();
            }
        }
    }
    
    public static int actSQLNP(String args) {
        
    /*
     Metodo que regresa el numero de registros afectados
     Nota: obtiene conexi�n y la cierra una vez ejecutada la sentencia */

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(args);
        final String SQL2 = StrSQLResp;

        Statement stmt = null;
        int iResponse=0;
        Connection con=null;
        
        try{
            con = UtileriasBDF.getConnection();
            
            if (con != null) {
                stmt = con.createStatement();
                try {
                    if (logStatus==true) {
                        long longTimeI = System.currentTimeMillis();
                        long longTimeF = 0;
                        StringBuffer strBuff = new StringBuffer();
                        
                        stmt.execute("set dateformat mdy");
                        stmt.execute("set nocount on");
                        stmt.execute("set quoted_identifier off");
                        iResponse = stmt.executeUpdate(SQL2);
                        
                        longTimeF = System.currentTimeMillis();
                        
                        strBuff.append(" set quoted_identifier off  Insert into LogDB(Tiempo,Sentencia) values(");
                        strBuff.append((longTimeF-longTimeI)/1000);
                        strBuff.append(",\"Con Con: ");
                        strBuff.append(SQL2);
                        strBuff.append("\")");
                        stmt.execute(strBuff.toString());
                        strBuff.delete(0,strBuff.length());
                        strBuff=null;
                        
                    }else {
                        stmt.execute("set dateformat mdy");
                        stmt.execute("set nocount on");
                        stmt.execute("set quoted_identifier off");
                        iResponse = stmt.executeUpdate(SQL2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No me pude conectar");
            }
        } catch (SQLException sqle) {
            do {
                System.out.println("ERROR DAO : actSQLNP ");
                System.out.println(SQL2.toString());
                System.out.println(" SQL STATE: " + sqle.getSQLState());
                System.out.println(" ERROR CODE: " + sqle.getErrorCode());
                System.out.println(" MESSAGE: " + sqle.getMessage());
                System.out.println();
                sqle = sqle.getNextException();
            } while (sqle != null);
            
            sqle.printStackTrace();
            iResponse = -1;
        }finally{
            try {
                if (stmt!=null){
                    stmt.close();
                }
                if (con!=null){
                    con.close();
                }
                
            }catch(Exception ee){
                ee.printStackTrace();
            }
        }
        return iResponse;
    }
    
    public static ResultSet rsSQLP(Connection con, String SQL) {
      /*
       *      M�todo que regresa un record set
       *      Nota: Una vez ejecutada la sentencia no se cierra la conexi�n
       */

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(SQL);
        final String SQL2 = StrSQLResp;

        Statement stmt = null;
        Statement stmtLog = null;
        ResultSet rsObt = null;
        try{
            if (con != null) {
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmtLog = con.createStatement();
                
                try {
                    if (logStatus==true) {
                        long longTimeI = System.currentTimeMillis();
                        long longTimeF = 0;
                        StringBuffer strBuff = new StringBuffer();
                        stmt.execute("set dateformat mdy");
                        stmt.execute("set nocount on");
                        stmt.execute("set quoted_identifier off");
                        rsObt = stmt.executeQuery(SQL2);
                        
                        longTimeF = System.currentTimeMillis();
                        strBuff.append(" set quoted_identifier off  Insert into LogDB(Tiempo,Sentencia) values(");
                        strBuff.append((longTimeF-longTimeI)/1000);
                        strBuff.append(",\"Con Con: ");
                        strBuff.append(SQL2);
                        strBuff.append("\")");
                        stmtLog.execute(strBuff.toString());
                        strBuff.delete(0,strBuff.length());
                        strBuff=null;
                        
                    }else {
                        stmt.execute("set dateformat mdy");
                        stmt.execute("set nocount on");
                        stmt.execute("set quoted_identifier off");
                        rsObt = stmt.executeQuery(SQL2);
                    }
                    
                    // Pendiente : Verificar que tan eficiente es CONCUR....
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            } else {
                System.out.println("No me pude conectar");
            }
        } catch (SQLException sqle) {
            try {
                if (con!=null){
                    con.close();
                }
            }catch(Exception ee){
                ee.printStackTrace();
            }
            
            do {
                System.out.println("ERROR DAO : rsSQLP ");
                System.out.println(SQL2.toString());
                System.out.println("SQL STATE: " + sqle.getSQLState());
                System.out.println("ERROR CODE: " + sqle.getErrorCode());
                System.out.println("MESSAGE: " + sqle.getMessage());
                System.out.println();
                sqle = sqle.getNextException();
            } while (sqle != null);
            
            sqle.printStackTrace();
        } finally {
        }
        
        return rsObt;
    }
    
    
    public static void rsTableNP(String pStrSQL, final StringBuffer StrSalida){
    /* genera c�digo HTM de una tabla con los registros de la base de datos
     * Nota: Se cierra la conexi�n una vez extra�dos los datos
     */
        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(pStrSQL);
        final String SQL2 = StrSQLResp;

        String strValue=null;
        
        ResultSet rs = null;
        boolean blnRegistro = true;
        Connection con=null;
        
        try{
            con = UtileriasBDF.getConnection();
            
            rs = rsSQLP(con,SQL2);
            if (rs.next()){
                rs.last();
                StrSalida.append("<th class='cssTitDet'>Registros Encontrados:").append(rs.getRow()).append("</th>");
                rs.first();
                
                ResultSetMetaData rsMetaDato = rs.getMetaData();
                int i;
                StrSalida.append("<table id='ObjTable' class='Table' border='0' cellpadding='0'>");
                StrSalida.append("<tr class = 'TTable'>");
                for ( i=1; i<=rsMetaDato.getColumnCount(); i++){
                    StrSalida.append("<th style=\"padding: 10px 10px 10px 10px;\" onClick='fnOrder(this.parentElement.parentElement,").append(String.valueOf(i-1)).append(")'>").append(rsMetaDato.getColumnLabel(i)).append("</th>");
                }
                StrSalida.append("</tr>");
                do {
                    // Checa que si el registro es par o non
                    if (blnRegistro){
                        StrSalida.append("<tr class='R1Table'>");
                        blnRegistro = false;
                    } else {
                        StrSalida.append("<tr class='R2Table'>");
                        blnRegistro = true;
                    }
                    for ( i=1; i<=rsMetaDato.getColumnCount(); i++){
                        strValue = rs.getString(i);
                        StrSalida.append("<td style=\"padding: 10px 10px 10px 10px;\" >").append(strValue).append("</td>");
                        strValue=null;
                    }
                    StrSalida.append("</tr>");
                } while(rs.next());
            }
        }catch(Exception e){
            StrSalida.delete(0,StrSalida.length());
            e.printStackTrace();
        } finally {
            strValue=null;
            try {
                if (rs!=null){
                    rs.close();
                    rs = null;
                }
                if (con!=null){
                    con.close();
                }
            }catch(Exception ee){
                ee.printStackTrace();
            }
        }
        strValue=null;
        StrSalida.append("</table>");
    }
    
    public static void rsTablePlasmaNP(String pStrSQL, int NumRows, String strTitulo, final StringBuffer StrSalida){
    /* genera c�digo HTM de una tabla con los registros de la base de datos
     * Nota: Se cierra la conexi�n una vez extra�dos los datos
     */

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(pStrSQL);
        final String SQL2 = StrSQLResp;

        ResultSet rs = null;
        boolean blnRegistro = true;
        
        Connection con=null;
        
        try{
            con = UtileriasBDF.getConnection();
            
            rs = rsSQLP(con,SQL2);
            if (rs.next()){
                rs.last();
                StrSalida.append("<p class='cssTitDetPlasma'>").append(strTitulo).append("        Total:").append(rs.getRow()).append("</p>");
                rs.first();
                
                ResultSetMetaData rsMetaDato = rs.getMetaData();
                int i;
                int iR;
                StrSalida.append("<table id='ObjTable' class='TablePlasma' border='0' cellpadding='0'>");
                StrSalida.append("<tr class = 'TTablePlasma'>");
                for ( i=1; i<=rsMetaDato.getColumnCount(); i++){
                    StrSalida.append("<th onClick='fnOrder(this.parentElement.parentElement,").append(String.valueOf(i-1)).append(")'>").append(rsMetaDato.getColumnLabel(i)).append("</th>");
                }
                StrSalida.append("</tr>");
                iR=0;
                do {
                    // Checa que si el registro es par o non
                    if (blnRegistro){
                        StrSalida.append("<tr class='R1TablePlasma'>");
                        blnRegistro = false;
                    } else {
                        StrSalida.append("<tr class='R2TablePlasma'>");
                        blnRegistro = true;
                    }
                    for ( i=1; i<=rsMetaDato.getColumnCount(); i++){
                        StrSalida.append("<td>").append(rs.getObject(i)).append("</td>");
                    }
                    StrSalida.append("</tr>");
                    iR++;
                } while(rs.next() && iR< NumRows);
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (con!=null){
                    con.close();
                }
                if (rs!=null){
                    rs.close();
                    rs = null;
                }
                
            }catch(Exception ee){
                ee.printStackTrace();
            }
        }
    }
    
    public static void rsCSVCNP(String pStrSQL, final StringBuffer StrSalida){
    /* genera una cadena con los informaci�n separada por comas
     * Nota: Se cierra la conexi�n una vez extra�dos los datos
     */

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(pStrSQL);
        final String SQL2 = StrSQLResp;

        ResultSet rs = null;
        boolean blnRegistro = true;
        
        Connection con=null;
        try{
            con = UtileriasBDF.getConnection();
            rs = rsSQLP(con,SQL2);
            if (rs.next()){
                ResultSetMetaData rsMetaDato = rs.getMetaData();
                int i;
                int iCol=rsMetaDato.getColumnCount();
                
                for ( i=1; i<=iCol; i++){
                    StrSalida.append(rsMetaDato.getColumnLabel(i)).append(",");
                }
                StrSalida.append("\n\r");
                do {
                    for ( i=1; i<=iCol; i++){
                        if (rs.getObject(i)!=null){
                            StrSalida.append(rs.getObject(i).toString().replaceAll(",|\n|\r\n?"," ")).append(",");
                        }else {
                            StrSalida.append(",");
                        }
                    }
                    StrSalida.append("\n");
                } while(rs.next());
            }
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (con!=null){
                    con.close();
                }
                if (rs!=null){
                    rs.close();
                    rs = null;
                }
                
            }catch(Exception ee){
                ee.printStackTrace();
            }
        }
    }
    
    public static void rsTableGRNP(String pStrSQL, final StringBuffer StrSalida){

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(pStrSQL);
        final String SQL2 = StrSQLResp;

        ResultSet rs = null;
        boolean blnRegistro = true;
        
        Connection con=null;
        try{
            con = UtileriasBDF.getConnection();
            rs = rsSQLP(con,SQL2);
            ResultSetMetaData rsMetaDato = rs.getMetaData();
            int i;
            if(rs.next()){
                rs.last();
                StrSalida.append("<th class='cssTitDet'>Registros Encontrados:").append(rs.getRow()).append("</th>");
                rs.first();
            }
            StrSalida.append("<table class='Table' border='1' cellpadding='0'>");
            StrSalida.append("<tr class = 'TTable'>");
            for ( i=1; i<=rsMetaDato.getColumnCount()-1; i++){
                StrSalida.append("<th>").append(rsMetaDato.getColumnLabel(i)).append("</th>");
            }
            StrSalida.append("</tr>");
            while (rs.next()){
                // Checa el color del registro
                if (rs.getObject(rsMetaDato.getColumnCount()).equals("ROJO")){
                    StrSalida.append("<tr class='Rojo'>");
                } else {
                    if (rs.getObject(rsMetaDato.getColumnCount()).equals("AMARILLO")){
                        StrSalida.append("<tr class='Amarillo'>");
                    } else {
                        if (rs.getObject(rsMetaDato.getColumnCount()).equals("BLANCO")){
                            StrSalida.append("<tr class='Blanco'>");
                        } else {
                            if (rs.getObject(rsMetaDato.getColumnCount()).equals("VERDE")){
                                StrSalida.append("<tr class='Verde'>");
                            }
                        }
                    }
                }
                for ( i=1; i<=rsMetaDato.getColumnCount()-1; i++){
                    StrSalida.append("<td>").append(rs.getObject(i)).append("</td>");
                }
                StrSalida.append("</tr>");
            }
        }catch(Exception e){
        } finally {
            try {
                if (rs!=null){
                    rs.close();
                    rs = null;
                }
                if (con!=null){
                    con.close();
                }
            }catch(Exception ee){
                ee.printStackTrace();
            }
        }
    }
    
}