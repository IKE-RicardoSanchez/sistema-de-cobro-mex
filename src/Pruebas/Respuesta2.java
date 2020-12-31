/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pruebas;

import Utilerias.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import javax.swing.*;
import java.sql.ResultSet;
import java.util.regex.*;
import java.text.*;
import java.util.Date;

/**
 * @author rarodriguez
 */
public class Respuesta2 extends JPanel
{
  
   public String actionFile(String proy, int lote, int flag) throws  SQLException {
       System.out.println("\tJAVA ActionFile");
    JFileChooser chooser=null;
   String choosertitle="Selección de archivo";

   String archivo="", salida="Archivo No valido<br>No corresponde ";
   
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

        boolean match=false;

          String fecha="";
            fecha = format.format(date);

                  //System.out.println(fecha);
                  Pattern fech = Pattern.compile(fecha);
                  Pattern resp = Pattern.compile("R_");
                  Pattern sufijo = Pattern.compile(proy);
                  Pattern clrescobro = Pattern.compile("L"+lote);


              Matcher m = resp.matcher(archivo);
              match=m.find();
                if(match)
                { System.out.println("Respuesta: Ok"); 
                  

                      m = sufijo.matcher(archivo);
                      match=m.find();
                        if(match)
                        { //System.out.println("Sufijo: Ok");
                         
                           m = clrescobro.matcher(archivo);
                           match=m.find();
                            if(match)
                            { //System.out.println("Lote: Ok");
                              
                               m = fech.matcher(archivo);
                               match=m.find();
                                if(match)
                                { //System.out.println("Fecha: Ok");
                                        
                                    salida =archivo.replace("\\", "\\\\");
                                       // System.out.println("salida cargaT: "+salida);
                                }else
                                      salida= salida +"la Fecha";
                            }else
                                salida= salida +"el Lote";
                        }else
                            salida= salida +"a la Cuenta";
                }else
                    {  salida="Archivo No valido<br>No es una Respuesta de cobro"; }

              //System.out.println(salida+"\n"+m.toString()+"\n"+m.matches()+"\n"+m.find());
              fech=null;
              clrescobro=null;
              sufijo=null;
              resp=null;
                 if (match) 
                    { m.end(); }
              m=null;
            }
    }catch(Exception e){ System.out.println("ActionF: "+e); salida= "Error";    }
    finally {
                try
                    { 
                        if (chooser!=null)
                        {   chooser=null;
                                System.out.println("Nullea Chooser");}
                    }catch(Exception e){ System.out.println("Chooser "+e);}
            }
    //System.out.println(salida);
   return salida;
   }

   public String CargaTabla(String file, String proy, int lote, int flag) throws SQLException, FileNotFoundException{
       System.out.println("\tJAVA CargaTabla");

       File f = new File(file);
           BufferedReader entrada=null;
               StringBuffer SQL=new StringBuffer();
               StringBuffer query= new StringBuffer();
              String linea="",  result="..";
      ResultSet rs=null;

       proy= "'"+ proy +"'";
           try {
                entrada = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));
                int index=1;

                    while(entrada.ready())
                        {   linea = entrada.readLine();

                            try{
                            query.append("sp_tmkgcA_cargaTablaResp ").append(proy).append(",").append(lote).append(", '").append(linea).append(";;', ").append(flag);
                                //System.out.println("index: "+ index+" "+query.toString());
                                //System.out.println("index: "+ index);

                               rs= UtileriasBDF.rsSQLNP(query.toString());
                                    while (rs!=null && rs.next())
                                    {   //System.out.println(rs.wasNull());
                                        result= rs.getString("salida").toString();
                                    }
                            }catch(SQLException es){ es.printStackTrace(); }

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
                    try {
                            query.delete(0, query.length());
                            SQL.delete(0, SQL.length());
                            query=null;
                            SQL=null;
                            entrada.close();
                            System.out.println("Null "+ query+" "+SQL+"  "+entrada);
                    }catch(IOException ex){ ex.printStackTrace();}

                    try{
                        rs.close();
                    }catch(SQLException es){ es.printStackTrace(); }
                    }

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
                        }
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
