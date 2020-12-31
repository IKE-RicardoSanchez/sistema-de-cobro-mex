/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pruebas;

import Utilerias.*;
import java.awt.HeadlessException;
import java.io.*;
import java.sql.SQLException;
import javax.swing.*;
import java.sql.ResultSet;
import java.util.regex.*;
import java.text.*;
import java.util.Date;

/**
 * @author rarodriguez
 */
public class Respuesta1 extends JPanel
{
  
   public String actionFile(String proy, int lote, int flag) throws  SQLException {
       System.out.println("\tJAVA ActionFile");
    JFileChooser chooser=null;
   String choosertitle="Selección de archivo";

   String archivo="", salida="";
    Respuesta1 file1 = new Respuesta1();
    file1=null;

    try{
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle(choosertitle);
            chooser.setVisible(true);
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                System.out.println("Ruta completa: "  +  chooser.getSelectedFile());
                archivo= chooser.getSelectedFile().toString();
            }
            else {
               //System.out.println("No Selecionado");
                archivo= "No Selecionado";
              }

            if(archivo.equals("No Selecionado"))
            {  System.out.println("No Selecionadooo!!!");
               salida= archivo;
             }
            else
            { System.out.println("Procesacarga");

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");

          String fecha="";
            fecha = format.format(date);

                  System.out.println(fecha);
                  Pattern fech = Pattern.compile(fecha);
                  Pattern resp = Pattern.compile("R_");
                  Pattern sufijo = Pattern.compile(proy);
                  Pattern clrescobro = Pattern.compile("L"+lote);


              Matcher m = resp.matcher(archivo);
                if(m.find())
                { System.out.println("Respuesta: Ok"); 
                  m.reset();

                      m = sufijo.matcher(archivo);
                        if(m.find())
                        { System.out.println("Sufijo: Ok");
                          m.reset();

                           m = clrescobro.matcher(archivo);
                            if(m.find())
                            { System.out.println("Lote: Ok");
                              m.reset();

                               m = fech.matcher(archivo);
                               //System.out.println("find "+ m.find());
                                if(m.find())
                                {
                                    System.out.println("Fecha: Ok");

                                    System.out.println(" "+proy+" "+lote+"  "+flag);

                                        //try{
                                             salida= file1.CargaTabla("D:\\rarodriguez\\Desktop\\Respaldo_130531\\R_BMXD_prueba_respuesta_L1736_130605.txt", proy, lote, flag);
                                        //}catch(SQLException i){ System.out.println("CargaT "+i);}
                                    salida ="Ok";
                                        System.out.println("salida cargaT: "+salida);

                                }
                                else
                                      salida="Archivo No valido: Fecha";
                            }
                            else
                                salida="Archivo No valido: Lote";
                        }
                        else
                            salida= "Archivo No valido: Cuenta";
                }
                else
                    {  salida="Archivo No valido"; }
            }
    }catch(Exception e){ System.out.println("CargaTabla: "+e); salida= "Error";    }
    finally {
                try
                    { 
                        if (chooser!=null)
                        {   chooser=null;
                                System.out.println("Nullea Chooser");}
                    }catch(Exception e){ System.out.println("Chooser "+e);}

                try
                {
                    if(file1!=null)
                        {   file1=null;
                            System.out.println("Nullea file1java "+ file1.toString());}
                    else
                        {   System.out.println("File1 null"); }
                }catch(Exception e) {    System.out.println("file1 :"+e); }
            }
    System.out.println(salida);
   return salida;
   }

   public String CargaTabla(String file, String proy, int lote, int flag){
       System.out.println("\tJAVA CargaTabla");
       File f = new File(file);
           BufferedReader entrada=null;
               StringBuffer SQL=new StringBuffer();
               StringBuffer query= new StringBuffer();
               String linea="",  result="..";
  /*    ResultSet rs=null;

       proy= "'"+ proy +"'";
   /*         try {
                entrada = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
                int index=1;

                    while(entrada.ready())
                        {   linea = entrada.readLine();

                            try{
                            query.append("sp_tmkgcA_cargaTablaResp ").append(proy).append(",").append(lote).append(", '").append(linea).append(";;', ").append(flag);
                                //System.out.println("index: "+ index+" "+query.toString());
                                System.out.println("index: "+ index);

                     /*               rs= UtileriasBDF.rsSQLNP(query.toString());


                                    while (rs!=null && rs.next())
                                    {   System.out.println(rs.wasNull());
                                        result= rs.getString("salida").toString();
                                    }
                            }catch(SQLException es){ es.printStackTrace(); }
*
                                System.out.println(result);

                                        if (result.equals("Longitud Incompleta") || result.equals("No se tiene datos"))
                                        {   result = "Respuesta con errores: "+result+"<br>  Fila: "+index;
                                            break;
                                        }
                            query.delete(0, query.length());
                            SQL.delete(0, SQL.length());
                            index++;
                        }
            }catch (IOException e) {  System.out.println("catch "+e); linea= e.toString(); }
            finally {
                 */   try {
                        /*    query.delete(0, query.length());
                            SQL.delete(0, SQL.length());
                            query=null;
                            SQL=null;
                           */ entrada.close();
                            System.out.println("Null "+ query+" "+SQL+"  "+entrada);
                    }catch(IOException ex){ ex.printStackTrace();}
/*
                    try{
                        rs.close();
                    }catch(SQLException es){ es.printStackTrace(); }
                    }
*/
                System.out.println("salida CargaTabla \n-->"+result +"\n\n\n");
    return result;
   }

   public String ProcesaTabla(String proy, int lote) throws SQLException {
      System.out.println("\tJAVA ProcesaTabla");
       String result=".",
               query= "sp_tmkgcA_cargaRespuesta '"+proy+"',"+lote;
       ResultSet rs=null;

                try
                {
                    rs= UtileriasBDF.rsSQLNP(query);

                        while(rs!=null && rs.next())
                        {   System.out.println("WasN "+rs.wasNull());
                            result= rs.getString("salida").toString();
                            System.out.println("result -->"+result);
                        }
                         System.out.println(result);
                }catch(SQLException e){result="Error"; System.out.println("E "+result); System.out.println("ProcesaTable: "+ e); }
                finally
                {
                    try {
                            rs.close();
                        }catch (SQLException ex) { System.out.println("rsC "+ex); }
                }
        System.out.println("Final\n-->"+result);
   return result;
   }
}
