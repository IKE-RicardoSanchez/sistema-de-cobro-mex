/*
 * ProcesaFile.java
 *
 * Created on 10 de diciembre de 2010, 09:18 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package Utilerias;

import java.io.File;
import java.sql.ResultSet;
import jxl.*;

/**
 *
 * @author bsanchez
 */
public class ProcesaFile {

    static final int BUFFER = 10000000;
    static public int filas = 0;
    static public String dsError = "";

    /** Creates a new instance of ProcesaFile */
    public ProcesaFile() {
    }

    public boolean ProcesaArchivo(String Path, String clUsrApp, int MaxColumns, int MaxRegistros, String StoreUpload,
                                    String NombreArchivo, String Lote, String proyecto, String detalle) {
        String nameArchivo = "";
        try {

                    if(Lote.equals(""))
                        { Lote= "'"; }
                    else
                        { Lote= "',"+Lote; }

            StoreUpload= StoreUpload +" 0,'"+ proyecto+Lote;
            System.out.println("Stored: " + StoreUpload);

            File file = new File(Path);

            file.length();
            nameArchivo = file.getName().toString();
            Workbook workbook=null;

            workbook= Workbook.getWorkbook(new File(Path));

            //<<<<<<<<< Se toma la primera hoja >>>>>>>>>
            Sheet sheet = workbook.getSheet(0);

            //<<<<<<<<<<<<<  Variable tipo celda >>>>>>>>>
            Cell cel1 = null;

            //<<<<<<<<<   Variable para obtener el contenido de las celda >>>>>>>>
            String DatoColumn = "";
            String SentenciaSQL = "";
                int i = 1, Columna = 0, TotalRows = sheet.getRows()-1;
                System.out.println("rows "+TotalRows);

                if(TotalRows!=Integer.parseInt(detalle))
                {   dsError="Total de registros no coinciden. Enviados: "+detalle+" Recibidos: "+TotalRows;
                    sheet= null;
                    workbook.close();
                    workbook=null;
                    TotalRows=0;
                    file=null;
                    return false;}

            ResultSet rs = null;
            String Error = "0";

            while (i != -1) {
                String ValidaUltimaCol = "";
                //<<<<<<<<<< Verificar el número de filas  >>>>>>>>>
                if (i > TotalRows) {
                    System.out.println(i +" i >= TotalRows");
                    i = -1;
                } else {

                    //<<<<<<<<< Obtener los datos de las celdas >>>>>>>>>
                    for (Columna = 0; Columna < MaxColumns; Columna++) {
                        //System.out.print("Inicia lectura xls Fila: "+filas +" :: Column: "+Columna+"  -->");
                        cel1 = sheet.getCell(Columna, i);

                        //<<<<<<<<< Copiar el Contenido de las Celdas a las Variables >>>>>>>
                        //System.out.println(cel1.getContents());
                        DatoColumn = cel1.getContents();

                        //<<<<<<<<<<<<< Valida  la columna Nombre >>>>>>>>>>>>>
                        if (Columna == 1) {
                            DatoColumn = DatoColumn.replace("'", "");
                        }

                        //if (Columna != MaxColumns - 1) {
                            SentenciaSQL = SentenciaSQL + ",'" + DatoColumn.replace("'", ":") + "'";
                        //} else {SentenciaSQL = SentenciaSQL + "'" + DatoColumn + "'";}

                        cel1 = null;
                        ValidaUltimaCol = ValidaUltimaCol + DatoColumn;
                    }

                    //<<<<<<<<<<<<< Guardar Registro >>>>>>>>>>>>>>
                    //System.out.println(StoreUpload+""+SentenciaSQL);
                    rs = UtileriasBDF.rsSQLNP(StoreUpload + "" + SentenciaSQL);
                    if (rs.next()) {
                        Error = rs.getString("error");
                        if (Error.equalsIgnoreCase("1")) {
                            dsError = rs.getString("descError");
                            return false;
                        }
                    }
                    if(i%Math.floor(Integer.parseInt(detalle)*.1)==0 ||i==1)
                        System.out.println(i);

                    rs = null;
                    SentenciaSQL = "";
                    i = i + 1;
                    filas = filas + 1;
                    // System.out.println("Filas: "+ filas);
                    if (i >= MaxRegistros) {
                        System.out.println(i +" i >= MaxRegistros");
                        i = -1;
                    }

                    if (ValidaUltimaCol.equalsIgnoreCase("")) {
                        System.out.println(i +" ValidaUltimaCol.equalsIgnoreCase");
                        i = -1;
                        System.out.println("Acaba de Procesar ");
                    }
                }
            }

            //<<<<<<<<<<<< Limpiar variables >>>>>>>>
            DatoColumn = null;
            cel1 = null;
            //<<<<<<<<<<<<<<<<<<<< >>>>>>>>>>>>>>>>>>>
        } catch (Exception E) {
            System.err.println("Error al Leer el Archivo de Excel.");
            dsError= "Error al Leer el Archivo de Excel." + dsError;
                //E.printStackTrace();
            return false;
        }
        System.out.println("Fin Proceso xls");
        return true;
    } // ProcesaArchivo
}
