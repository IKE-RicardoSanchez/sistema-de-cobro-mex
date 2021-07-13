package Utilerias;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.StringTokenizer;

import snaq.db.ConnectionPool;

/*
import net.sourceforge.jtds.jdbc.Driver;//JTDS Imports
import net.sourceforge.jtds.jdbcx.JtdsDataSource;
import net.sourceforge.jtds.jdbcx.JtdsObjectFactory;
 */
public class UtileriasBDF {

    /************** manejo de pool manual agregar siguientes 4 lineas */
    static ConnectionPool pool = null;
    static long timeout = 10000;  // 30 second expire timeout
    private static boolean isConnected = false;
    private static byte bytLanguaje = 2;
    /* hasta aqui */
    private static boolean logStatus = false;

    private UtileriasBDF() {
    }

    public synchronized static void StartLog() {
        logStatus = true;
    }

    public synchronized static void StopLog() {
        logStatus = false;
    }

    public static boolean getStatusLog() {
        return logStatus;
    }

    /* Pool para DBPool*/
    public static Connection getConnection() {
        Connection con = null;
        try {
            Connect();
            con = pool.getConnection(timeout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static ConnectionPool getPool() {
        return pool;
    }

    public synchronized static void Connect() {

        if (isConnected == false) {
            try {
                //Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //JDBC 2005
                // Class.forName("net.sourceforge.jtds.jdbc.Driver"); //JTDS
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
          System.out.println("Conectado UtileriasBDF");
          
          
        }
    }

    public static String LimpiaSentencia(String SQL){
        SQL = SQL.replaceAll("(?i)"+"waitfor delay"," ");
        SQL = SQL.replaceAll("(?i)"+"waitfor"," ");
        SQL = SQL.replaceAll("(?i)"+"delay"," ");

        return SQL;
    }
    
    public static void ejecutaSQLNP(String args) {
        /* M�todo que no regresa nada despu�s de ejecutar una sentencia SQL
        Nota: obtiene conexi�n y la cierra una vez ejecutada la sentencia */

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(args);
        final String SQL2 = StrSQLResp;

        Statement stmt = null;
        Connection con = null;

        try {
            con = UtileriasBDF.getConnection();
            if (con != null) {
                //stmt = con.createStatement();
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                try {
                    if (logStatus == true) {
                        long longTimeI = System.currentTimeMillis();
                        long longTimeF = 0;
                        StringBuffer strBuff = new StringBuffer();

                        stmt.execute("set dateformat mdy");
                        stmt.execute("set nocount on");
                        stmt.execute("set quoted_identifier off");
                        stmt.execute(SQL2);

                        longTimeF = System.currentTimeMillis();

                        strBuff.append(" set quoted_identifier off Insert into LogDB(Tiempo,Sentencia) values(");
                        strBuff.append((longTimeF - longTimeI) / 1000);
                        strBuff.append(",\"Con Con: ");
                        strBuff.append(SQL2);
                        strBuff.append("\")");
                        stmt.execute(strBuff.toString());
                        strBuff.delete(0, strBuff.length());
                        strBuff = null;

                    } else {
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
            sqle.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public static int actSQLNP(String args) {

        /*
        Metodo que regresa el umero de registros afectados
        Nota: obtiene conexi�n y la cierra una vez ejecutada la sentencia */

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(args);
        final String SQL2 = StrSQLResp;

        Statement stmt = null;
        int iResponse = 0;
        Connection con = null;

        try {
            con = UtileriasBDF.getConnection();

            if (con != null) {
                //stmt = con.createStatement();
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                try {
                    if (logStatus == true) {
                        long longTimeI = System.currentTimeMillis();
                        long longTimeF = 0;
                        StringBuffer strBuff = new StringBuffer();

                        stmt.execute("set dateformat mdy");
                        stmt.execute("set nocount on");
                        stmt.execute("set quoted_identifier off");
                        iResponse = stmt.executeUpdate(SQL2);

                        longTimeF = System.currentTimeMillis();

                        strBuff.append(" set quoted_identifier off  Insert into LogDB(Tiempo,Sentencia) values(");
                        strBuff.append((longTimeF - longTimeI) / 1000);
                        strBuff.append(",\"Con Con: ");
                        strBuff.append(SQL2);
                        strBuff.append("\")");
                        stmt.execute(strBuff.toString());
                        strBuff.delete(0, strBuff.length());
                        strBuff = null;

                    } else {
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
            sqle.printStackTrace();
            iResponse = -1;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (Exception ee) {
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
        try {
            if (con != null) {
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                stmtLog = con.createStatement();

                try {
                    if (logStatus == true) {
                        long longTimeI = System.currentTimeMillis();
                        long longTimeF = 0;
                        StringBuffer strBuff = new StringBuffer();
                        stmt.execute("set dateformat mdy");
                        stmt.execute("set nocount on");
                        stmt.execute("set quoted_identifier off");
                        rsObt = stmt.executeQuery(SQL2);

                        longTimeF = System.currentTimeMillis();
                        strBuff.append(" set quoted_identifier off  Insert into LogDB(Tiempo,Sentencia) values(");
                        strBuff.append((longTimeF - longTimeI) / 1000);
                        strBuff.append(",\"Con Con: ");
                        strBuff.append(SQL2);
                        strBuff.append("\")");
                        stmtLog.execute(strBuff.toString());
                        strBuff.delete(0, strBuff.length());
                        strBuff = null;

                    } else {
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
                if (con != null) {
                    con.close();
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            sqle.printStackTrace();
        } finally {
        }

        return rsObt;
    }

    public static ResultSet rsSQLNP(String SQL) {
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
        Connection con = null;

        try {
            con = UtileriasBDF.getConnection();
            if (con != null) {
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //stmtLog = con.createStatement();

                try {
                    if (logStatus == true) {
                        long longTimeI = System.currentTimeMillis();
                        long longTimeF = 0;
                        StringBuffer strBuff = new StringBuffer();
                        stmt.execute("set dateformat mdy");
                        stmt.execute("set nocount on");
                        stmt.execute("set quoted_identifier off");
                        rsObt = stmt.executeQuery(SQL2);

                        longTimeF = System.currentTimeMillis();
                        strBuff.append(" set quoted_identifier off  Insert into LogDB(Tiempo,Sentencia) values(");
                        strBuff.append((longTimeF - longTimeI) / 1000);
                        strBuff.append(",\"Con Con: ");
                        strBuff.append(SQL2);
                        strBuff.append("\")");
                        stmtLog.execute(strBuff.toString());
                        strBuff.delete(0, strBuff.length());
                        strBuff = null;

                    } else {
                        stmt.execute("set dateformat mdy");
                        stmt.execute("set nocount on");
                        stmt.execute("set quoted_identifier off");
                        rsObt = stmt.executeQuery(SQL2);
                    }

                    // Pendiente : Verificar que tan eficiente es CONCUR....
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("No me pude conectar");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            try {
                /*Cierro Resultset
                if (rsObt!=null){
                con.close();
                }
                //Cierro Statement
                if (stmt!=null){
                stmt.close();
                }
                 */
                // Cierro Conexion
                if (con != null) {
                    con.close();
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }

        return rsObt;
    }

    public static void rsTableNP(String pStrSQL, final StringBuffer StrSalida) {
        /* genera c�digo HTM de una tabla con los registros de la base de datos
         * Nota: Se cierra la conexi�n una vez extra�dos los datos
         */

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(pStrSQL);
        final String SQL2 = StrSQLResp;

        String strValue = null, style="padding: 3px 10px 3px 10px;";

        ResultSet rs = null;
        boolean blnRegistro = true;
        Connection con = null;
        Statement stmt = null;

        try {
            con = UtileriasBDF.getConnection();

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            stmt.execute("set dateformat mdy");
            stmt.execute("set nocount on");
            stmt.execute("set quoted_identifier off");
            rs = stmt.executeQuery(SQL2);

            if (rs.next()) {
                rs.last();
               // StrSalida.append("<th><font class=\"cssListaRegistros\">Registros Encontrados:").append(rs.getRow()).append("</font></th>");
                rs.first();

                ResultSetMetaData rsMetaDato = rs.getMetaData();
                int i;
                StrSalida.append("<div class='fixedHeaderTable'><table id='ObjTable' class='TablePlasma' border='0' cellpadding='0'>");
                StrSalida.append("<thead><tr class = 'cssAzul'>");
                for (i = 1; i <= rsMetaDato.getColumnCount(); i++) {
                    StrSalida.append("<th onClick='fnOrder(this.parentElement.parentElement,").append(String.valueOf(i - 1)).append(")'>").append(rsMetaDato.getColumnLabel(i)).append("</th>");
                }
                StrSalida.append("</tr></thead>");
                do {
                    // Checa que si el registro es par o non

                    if (blnRegistro) {
                        StrSalida.append("<tr class='R1Table'>");
                        blnRegistro = false;
                    } else {
                        StrSalida.append("<tr class='R2Table'>");
                        blnRegistro = true;
                    }
                    for (i = 1; i <= rsMetaDato.getColumnCount(); i++) {
                        strValue = rs.getString(i);
                        StrSalida.append("<td style=").append("\"").append(style).append("\">").append(strValue).append("</td>");
                        strValue = null;
                    }
                    StrSalida.append("</tr>");
                } while (rs.next());
                StrSalida.append("</table></div>");
            } else {
                //StrSalida.append("<th><font class=\"cssListaRegistros\">No se encontraron registros en esta consulta</font></th>");
                StrSalida.append("<font class=\"cssListaRegistros\">No se cuenta con registros.</font>");
            }
        } catch (Exception e) {
            StrSalida.delete(0, StrSalida.length());
            e.printStackTrace();
        } finally {
            strValue = null;
            try {

                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }

                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        strValue = null;
    }

    public static void rsTableNPacd(String pStrSQL, final StringBuffer StrSalida, String sTitulo, int min) {
        /* genera c�digo HTM de una tabla con los registros de la base de datos
         * Nota: Se cierra la conexi�n una vez extra�dos los datos
         */

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(pStrSQL);
        final String SQL2 = StrSQLResp;

        String strValue = null;

        ResultSet rs = null;
        boolean blnRegistro = true;
        Connection con = null;
        Statement stmt = null;

        try {
            con = UtileriasBDF.getConnection();
            if (con != null) {
                stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                stmt.execute("set dateformat mdy");
                stmt.execute("set nocount on");
                stmt.execute("set quoted_identifier off");
                rs = stmt.executeQuery(SQL2);

                if (rs.next()) {
                    rs.last();
                    StrSalida.append("<BR><BR>");
                    StrSalida.append("<th><font class=\"cssListaTitulo\">" + sTitulo).append("</font></th>");
                    rs.first();

                    ResultSetMetaData rsMetaDato = rs.getMetaData();
                    int i;
                    StrSalida.append("<table id='ObjTable' class='Table' border='0' cellpadding='0'>");
                    StrSalida.append("<tr class = 'TTable'>");
                    for (i = 1; i < rsMetaDato.getColumnCount(); i++) {
                        StrSalida.append("<th onClick='fnOrder(this.parentElement.parentElement,").append(String.valueOf(i - 1)).append(")'>").append(rsMetaDato.getColumnLabel(i)).append("</th>");
                    }
                    StrSalida.append("</tr>");
                    do {
                        // Checa que si el registro es par o non

                        if (blnRegistro) {
                            StrSalida.append("<tr class='R1Table'>");
                            blnRegistro = false;
                        } else {
                            StrSalida.append("<tr class='R2Table'>");
                            blnRegistro = true;
                        }
                        for (i = 1; i < rsMetaDato.getColumnCount(); i++) {
                            if (rsMetaDato.getColumnName(i).equalsIgnoreCase("imagen")) {
                                strValue = "<img src=\"" + rs.getString(i) + "\"> ";
                                System.out.println(strValue);
                            } else {
                                strValue = rs.getString(i);
                            }
                            StrSalida.append("<td>").append(strValue).append("</td>");
                            strValue = null;
                        }
                        StrSalida.append("</tr>");
                    } while (rs.next());

                }
            }
        } catch (Exception e) {
            StrSalida.delete(0, StrSalida.length());
            e.printStackTrace();
        } finally {
            strValue = null;
            try {

                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }

                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        strValue = null;
        StrSalida.append("</table>");
    }

    public static void rsTableViewContact(String pStrSQL, final int psColumnas, final StringBuffer StrSalida) {
        /* genera c�digo HTM de una tabla con los registros de la base de datos
         * Nota: Se cierra la conexi�n una vez extra�dos los datos
         */

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(pStrSQL);
        final String SQL2 = StrSQLResp;

        String strValue = null;

        ResultSet rs = null;
        boolean blnRegistro = true;
        Connection con = null;
        Statement stmt = null;
        int ColumnasBloques = psColumnas;
        int Registro = 1;
        boolean FirstTr = false;
        boolean FirstTrBloque = false;
        int Columnas = 1;
        int ColumansBloque = 2;

        try {
            con = UtileriasBDF.getConnection();

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            stmt.execute("set dateformat mdy");
            stmt.execute("set nocount on");
            stmt.execute("set quoted_identifier off");
            rs = stmt.executeQuery(SQL2);

            if (rs.next()) {
                //rs.last();
                //StrSalida.append("<th><font class=\"cssListaRegistros\">Registros Encontrados:").append(rs.getRow()).append("</font></th>");
                rs.first();

                ResultSetMetaData rsMetaDato = rs.getMetaData();

                int i;

                StrSalida.append("<table  border='0' cellpadding='0' cellspacing='30'>");
                do {
                    if (Registro <= ColumnasBloques) {
                        if (Registro == 1 && FirstTr == false) {
                            StrSalida.append("<tr>");
                            FirstTr = true;
                        }

                        //<<<<<<<<<<<<<<<<<< Bloque >>>>>>>>>>>>>>>>>>>
                        StrSalida.append("<td><table class=''  border='0'>");

                        for (i = 1; i <= rsMetaDato.getColumnCount(); i++) {
                            strValue = rs.getString(i);

                            if (i == 1) {
                                StrSalida.append("<tr><td colspan='2' class='cssListaTituloContacto'>").append(strValue).append("</td></tr>");
                            } else {


                                if (Columnas <= ColumansBloque) {
                                    if (Columnas == 1) {
                                        StrSalida.append("<tr>");
                                    }
                                    StrSalida.append("<td class='cssListaViewContacto'><font class='cssListaViewContactoT'>").append(rsMetaDato.getColumnLabel(i)).append(":</font>").append(strValue).append("</td>");
                                } else {
                                    Columnas = 1;
                                    StrSalida.append("</tr>");
                                    StrSalida.append("<tr> <td class='cssListaViewContacto'><font class='cssListaViewContactoT'>").append(rsMetaDato.getColumnLabel(i)).append(":</font>").append(strValue).append("</td>");
                                }

                                Columnas = Columnas + 1;

                            }
                            //StrSalida.append("<td>").append(strValue).append("</td>");

                            strValue = null;
                        }

                        //StrSalida.append("<tr><td>").append(Registro).append("</td></tr>");
                        StrSalida.append("</table></td>");
                    } else {

                        StrSalida.append("</tr>");
                        StrSalida.append("<tr><td>");
                        StrSalida.append("<table class=''  border='0'>");//<tr><td>").append(Registro).append("</td></tr></table></td>");

                        for (i = 1; i <= rsMetaDato.getColumnCount(); i++) {
                            strValue = rs.getString(i);

                            if (i == 1) {
                                StrSalida.append("<tr><td colspan='2' class='cssListaTituloContacto'>").append(strValue).append("</td></tr>");
                            } else {
                                //Columnas = Columnas + 1;

                                if (Columnas <= ColumansBloque) {
                                    if (Columnas == 1) {
                                        StrSalida.append("<tr>");
                                    }

                                    StrSalida.append("<td class='cssListaViewContacto'><font class='cssListaViewContactoT'>").append(rsMetaDato.getColumnLabel(i)).append(":</font>").append(strValue).append("</td>");
                                } else {
                                    Columnas = 1;
                                    StrSalida.append("</tr>");
                                    StrSalida.append("<tr><td class='cssListaViewContacto'><font class='cssListaViewContactoT'>").append(rsMetaDato.getColumnLabel(i)).append(":</font>").append(strValue).append("</td>");
                                }

                                Columnas = Columnas + 1;
                            }
                            //StrSalida.append("<td>").append(strValue).append("</td>");

                            strValue = null;

                        }

                        StrSalida.append("</table></td>");
                        Registro = 1;
                    }

                    Columnas = 1;
                    Registro = Registro + 1;

                } while (rs.next());
                StrSalida.append("</table>");
            }
        } catch (Exception e) {
            StrSalida.delete(0, StrSalida.length());
            e.printStackTrace();
        } finally {
            strValue = null;
            try {

                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }

                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        strValue = null;
        StrSalida.append("</table>");
    }

    public static void rsTablePlasmaNP(String pStrSQL, int NumRows, String strTitulo, final StringBuffer StrSalida) {
        /* genera c�digo HTM de una tabla con los registros de la base de datos
         * Nota: Se cierra la conexi�n una vez extra�dos los datos
         */

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(pStrSQL);
        final String SQL2 = StrSQLResp;

        ResultSet rs = null;
        boolean blnRegistro = true;

        Connection con = null;
        Statement stmt = null;

        try {
            con = UtileriasBDF.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            stmt.execute("set dateformat mdy");
            stmt.execute("set nocount on");
            stmt.execute("set quoted_identifier off");
            rs = stmt.executeQuery(SQL2);

            if (rs.next()) {
                /*rs.last();
                StrSalida.append("<p class='cssTitDetPlasma'>").append(strTitulo).append("        Total:").append(rs.getRow()).append("</p>");
                rs.first();*/

                ResultSetMetaData rsMetaDato = rs.getMetaData();
                int i;
                int iR;
                StrSalida.append("<table id='ObjTable' class='TablePlasma' border='0' cellpadding='0'>");
                StrSalida.append("<tr class = 'TTablePlasma'>");
                for (i = 1; i <= rsMetaDato.getColumnCount(); i++) {
                    StrSalida.append("<th onClick='fnOrder(this.parentElement.parentElement,").append(String.valueOf(i - 1)).append(")'>").append(rsMetaDato.getColumnLabel(i)).append("</th>");
                }
                StrSalida.append("</tr>");
                iR = 0;
                do {
                    // Checa que si el registro es par o non
                    if (blnRegistro) {
                        StrSalida.append("<tr class='R1TablePlasma'>");
                        blnRegistro = false;
                    } else {
                        StrSalida.append("<tr class='R2TablePlasma'>");
                        blnRegistro = true;
                    }
                    for (i = 1; i <= rsMetaDato.getColumnCount(); i++) {
                        StrSalida.append("<td>").append(rs.getObject(i)).append("</td>");
                    }
                    StrSalida.append("</tr>");
                    iR++;
                } while (rs.next() && iR < NumRows);
                StrSalida.append("</table>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (con != null) {
                    con.close();
                }

            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public static void rsTableACDNP(String pStrSQL, int NumRows, String strTitulo, final StringBuffer StrSalida, int min) {
        /* genera c�digo HTM de una tabla con los registros de la base de datos
         * Nota: Se cierra la conexi�n una vez extra�dos los datos
         */

        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(pStrSQL);
        final String SQL2 = StrSQLResp;

        ResultSet rs = null;
        boolean blnRegistro = true;

        Connection con = null;
        Statement stmt = null;

        try {
            con = UtileriasBDF.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            stmt.execute("set dateformat mdy");
            stmt.execute("set nocount on");
            stmt.execute("set quoted_identifier off");
            rs = stmt.executeQuery(SQL2);

            if (rs.next()) {
                /*rs.last();*/
                //StrSalida.append("<link href=\"../StyleClasses/Global.css\" rel=\"stylesheet\" type=\"text/css\">");
                StrSalida.append("<p class='cssTitDetPlasma'>").append(strTitulo).append("</p>");
                /*rs.first();*/

                ResultSetMetaData rsMetaDato = rs.getMetaData();
                int i;
                int iR;
                StrSalida.append("<table id='ObjTable' class='TablePlasmaXL' border='0' cellpadding='0'>");
                StrSalida.append("<tr class = 'TTable'>");
                for (i = 1; i <= rsMetaDato.getColumnCount(); i++) {
                    StrSalida.append("<th onClick='fnOrder(this.parentElement.parentElement,").append(String.valueOf(i - 1)).append(")'>").append(rsMetaDato.getColumnLabel(i)).append("</th>");
                }
                StrSalida.append("</tr>");
                iR = 0;
                do {

                    // Checa que si el registro es par o non
                    if (((rs.getInt("Duration") / 60) >= min) && ((rs.getInt("Duration") / 60) < (min * 2))) {
                        StrSalida.append("<tr class='cssNaranja'>");

                        blnRegistro = false;

                    } else {
                        if ((rs.getInt("Duration") / 60) > (min * 2)) {
                            StrSalida.append("<tr class='cssRojo'>");

                            blnRegistro = true;

                        } /*else {
                        if (blnRegistro) {
                        StrSalida.append("<tr class='R1TablePlasma'>");
                        blnRegistro = false;
                        } else {
                        StrSalida.append("<tr class='R2TablePlasma'>");
                        blnRegistro = true;
                        }
                        }*/
                    }
                    for (i = 1; i <= rsMetaDato.getColumnCount(); i++) {
                        StrSalida.append("<td>").append(rs.getObject(i)).append("</td>");
                    }

                    StrSalida.append("</tr>");
                    iR++;
                } while (rs.next() && iR < NumRows);
                StrSalida.append("</table>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (con != null) {
                    con.close();
                }

            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public static void rsCSVCNP(String pStrSQL, final StringBuffer StrSalida) {
        /* genera una cadena con los informaci�n separada por comas
         * Nota: Se cierra la conexi�n una vez extra�dos los datos
         */
        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(pStrSQL);
        final String SQL2 = StrSQLResp;

        ResultSet rs = null;
        boolean blnRegistro = true;

        Connection con = null;
        Statement stmt = null;

        try {
            con = UtileriasBDF.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            stmt.execute("set dateformat mdy");
            stmt.execute("set nocount on");
            stmt.execute("set quoted_identifier off");
            rs = stmt.executeQuery(SQL2);

            if (rs.next()) {

                ResultSetMetaData rsMetaDato = rs.getMetaData();
                int i;
                int iCol = rsMetaDato.getColumnCount();

                for (i = 1; i <= iCol; i++) {
                    StrSalida.append(rsMetaDato.getColumnLabel(i)).append(",");
                }
                StrSalida.append("\n\r");
                do {
                    for (i = 1; i <= iCol; i++) {
                        if (rs.getObject(i) != null) {
                            StrSalida.append(rs.getObject(i).toString().replaceAll(",|\n|\r\n?", " ")).append(",");
                        } else {
                            StrSalida.append(",");
                        }
                    }
                    StrSalida.append("\n");
                } while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (con != null) {
                    con.close();
                }

            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    public static void rsTableGRNP(String pStrSQL, final StringBuffer StrSalida) {
        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(pStrSQL);
        final String SQL2 = StrSQLResp;

        ResultSet rs = null;
        boolean blnRegistro = true;

        Connection con = null;
        Statement stmt = null;

        try {
            con = UtileriasBDF.getConnection();
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

            stmt.execute("set dateformat mdy");
            stmt.execute("set nocount on");
            stmt.execute("set quoted_identifier off");
            rs = stmt.executeQuery(SQL2);

            ResultSetMetaData rsMetaDato = rs.getMetaData();
            int i;
            /*       if(rs.next()){
            rs.last();
            StrSalida.append("<th class='cssTitDet'>Registros Encontrados:").append(rs.getRow()).append("</th>");
            rs.first();
            } */

            StrSalida.append("<table class='Table' border='1' cellpadding='0'>");
            StrSalida.append("<tr class = 'TTable'>");
            for (i = 1; i <= rsMetaDato.getColumnCount() - 1; i++) {
                StrSalida.append("<th>").append(rsMetaDato.getColumnLabel(i)).append("</th>");
            }
            StrSalida.append("</tr>");
            while (rs.next()) {
                // Checa el color del registro
                if (rs.getObject(rsMetaDato.getColumnCount()).equals("ROJO")) {
                    StrSalida.append("<tr class='Rojo'>");
                } else {
                    if (rs.getObject(rsMetaDato.getColumnCount()).equals("AMARILLO")) {
                        StrSalida.append("<tr class='Amarillo'>");
                    } else {
                        if (rs.getObject(rsMetaDato.getColumnCount()).equals("BLANCO")) {
                            StrSalida.append("<tr class='Blanco'>");
                        } else {
                            if (rs.getObject(rsMetaDato.getColumnCount()).equals("VERDE")) {
                                StrSalida.append("<tr class='Verde'>");
                            }
                        }
                    }
                }
                for (i = 1; i <= rsMetaDato.getColumnCount() - 1; i++) {
                    StrSalida.append("<td>").append(rs.getObject(i)).append("</td>");
                }
                StrSalida.append("</tr>");
            }
        } catch (Exception e) {
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    /////-----------------------------------------------------------------------------------------------------------------------
    //////------------
    public static void rsTableGridNP(String pTitulo, String pStrSQL, final StringBuffer StrSalida, int xPosition, int yPosition, int Ancho, int Alto, String Indice, int Numerado) {
        String StrSQLResp;
        StrSQLResp = LimpiaSentencia(pStrSQL);
        final String SQL2 = StrSQLResp;

        String strValue = null;
        String strNombreCampo = null;

        boolean blnRegistro = true;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = UtileriasBDF.getConnection();

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            stmt.execute("set dateformat mdy");
            stmt.execute("set nocount on");
            stmt.execute("set quoted_identifier off");
            rs = stmt.executeQuery(SQL2);

            //rs = rsSQLP(con,pStrSQL);


            if (Numerado == 0) {

                if (rs.next()) {

                    ResultSetMetaData rsMetaDato = rs.getMetaData();
                    int i;

                    StrSalida.append("<div style=' z-index:").append(Indice).append(" ;position:absolute; top:").append(yPosition).append("px; left:").append(xPosition).append("px;' class=Ftable>");
                    StrSalida.append("<th><b>").append(pTitulo).append("</b></th>");
                    StrSalida.append("<div style= ' width:").append(Ancho).append("% ; height:").append(Alto).append("%; overflow:auto;'>");
                    StrSalida.append("<table id='ObjTable' class='Table' border='0' cellpadding='0' >");
                    StrSalida.append("<tr class = 'TTable'>");
                    for (i = 1; i <= rsMetaDato.getColumnCount(); i++) {
                        StrSalida.append("<th onClick='fnOrder(this.parentElement.parentElement,").append(String.valueOf(i - 1)).append(")'>").append(rsMetaDato.getColumnLabel(i)).append("</th>");
                    }
                    StrSalida.append("</tr>");
                    do {
                        // Checa que si el registro es par o non
                        if (blnRegistro) {
                            StrSalida.append("<tr class='R1Table'>");
                            blnRegistro = false;
                        } else {
                            StrSalida.append("<tr class='R2Table'>");
                            blnRegistro = true;
                        }
                        for (i = 1; i <= rsMetaDato.getColumnCount(); i++) {
                            strValue = rs.getString(i);
                            StrSalida.append("<td>").append(strValue).append("</td>");
                            strValue = null;
                        }
                        StrSalida.append("</tr>");
                    } while (rs.next());
                }
            } else {
                if (rs.next()) {

                    ResultSetMetaData rsMetaDato = rs.getMetaData();
                    int i;
                    int j = 1;
                    StrSalida.append("<div style=' z-index:").append(Indice).append(" ;position:absolute; top:").append(yPosition).append("px; left:").append(xPosition).append("px;' class=Ftable>");
                    StrSalida.append("<th>").append(pTitulo).append("</th>");
                    StrSalida.append("<div style= ' width:").append(Ancho).append("px ; height:").append(Alto).append("px; overflow:auto;'>");
                    StrSalida.append("<table id='ObjTable' class='Table' border='0' cellpadding='0' >");
                    StrSalida.append("<tr class = 'TTable'>").append("<th>NO.</th>");
                    for (i = 1; i <= rsMetaDato.getColumnCount(); i++) {
                        StrSalida.append("<th onClick='fnOrder(this.parentElement.parentElement,").append(String.valueOf(i - 1)).append(")'>").append(rsMetaDato.getColumnLabel(i)).append("</th>");
                    }
                    StrSalida.append("</tr>");
                    do {
                        // Checa que si el registro es par o non
                        if (blnRegistro) {
                            StrSalida.append("<tr class='R1Table'>");
                            blnRegistro = false;
                        } else {
                            StrSalida.append("<tr class='R2Table'>");
                            blnRegistro = true;
                        }
                        StrSalida.append("<td align='center'>").append(j).append("</td>");
                        StrSalida.append("<input type='hidden' name= 'Num").append(pTitulo).append(j).append("' value='").append(j).append("'>");
                        for (i = 1; i <= rsMetaDato.getColumnCount(); i++) {
                            //
                            strNombreCampo = rsMetaDato.getColumnLabel(i);
                            strValue = rs.getString(i);

                            StrSalida.append("<td>").append(strValue).append("</td>");
                            StrSalida.append("<input type='hidden' name='").append(strNombreCampo).append(j).append("' value='").append(strValue).append("'></input>");
                            strValue = null;
                        }
                        StrSalida.append("</tr>");
                        j = j + 1;
                    } while (rs.next());
                } else {    //si no trae nada el qry...

                    ResultSetMetaData rsMetaDato = rs.getMetaData();
                    int i;
                    int j = 1;
                    StrSalida.append("<div style=' z-index:").append(Indice).append(" ;position:absolute; top:").append(yPosition).append("px; left:").append(xPosition).append("px;' class=Ftable>");
                    StrSalida.append("<th>").append(pTitulo).append("</th>");
                    StrSalida.append("<div style= ' width:").append(Ancho).append("px ; height:").append(Alto).append("px; overflow:auto;'>");
                    StrSalida.append("<table id='ObjTable' class='Table' border='0' cellpadding='0' >");
                    StrSalida.append("<tr class = 'TTable'>").append("<th>NO.</th>");
                    for (i = 1; i <= rsMetaDato.getColumnCount(); i++) {
                        StrSalida.append("<th >").append(rsMetaDato.getColumnLabel(i)).append("</th>");
                    }
                    StrSalida.append("</tr>");

                }


            }
            //-------------------------------------------------

        } catch (Exception e) {
            StrSalida.delete(0, StrSalida.length());
            e.printStackTrace();
        } finally {
            strValue = null;
            try {

                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }

                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        strValue = null;
        StrSalida.append("</table></div></div>");
    }
}
