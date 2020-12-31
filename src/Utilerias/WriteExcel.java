package Utilerias;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.StringTokenizer;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class WriteExcel {

  private WritableCellFormat arialBoldUnderline;
  private WritableCellFormat arial;
  private String inputFile;
  private static String query;
  private static int encabezado;
  private static int row;
  private static String error;

public String setOutputFile(String archivo) {
    String inputFile="";
    ResultSet rs= UtileriasBDF.rsSQLNP("sp_tmkgcA_UploadBD");
        try {
            if (rs.next()) {    inputFile = rs.getString("Path").toString();

                if(rs!=null){rs.close();}
                    rs= null;
            }
        }catch (SQLException ex) { ex.printStackTrace(); }

        if(archivo.equals(""))
            archivo= "excel_preval.xls";

    this.inputFile = inputFile+"/"+archivo;

    return this.inputFile;
  }

private void createFormat() throws WriteException {
        // Lets create a arial font
        WritableFont arial10pt = new WritableFont(WritableFont.ARIAL, 10);
            // Define the cell format
            arial = new WritableCellFormat(arial10pt);
            // Lets automatically wrap the cells
            arial.setWrap(true);

        // Create create a bold font with unterlines
        WritableFont arial10ptBoldUnderline = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false);
            arialBoldUnderline = new WritableCellFormat(arial10ptBoldUnderline);
            // Lets automatically wrap the cells
            arialBoldUnderline.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(arial);
        cv.setFormat(arialBoldUnderline);
        //cv.setAutoSize(true);
    }

  public void write(String sub) throws IOException {
    File file = new File(inputFile);
    WorkbookSettings wbSettings = new WorkbookSettings();

    wbSettings.setLocale(new Locale("en", "EN"));

    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
    workbook.createSheet(sub, 0);
    WritableSheet excelSheet = workbook.getSheet(0);

    contenido(excelSheet);

    workbook.write();
        try {   workbook.close(); } catch (WriteException ex) { ex.printStackTrace(); }
  }

  private void contenido(WritableSheet hoja)
  {   System.out.println("Contenido xls\n"+query);
      encabezado=1;
      row=0;
      ResultSet rs= UtileriasBDF.rsSQLNP(query);
        try {
                while (rs.next()){
                       error= rs.getString("layout").toString();
                               token(hoja, error);
                       encabezado++;
                    row++;
                }//WHILE
       } catch (SQLException ex){ ex.printStackTrace(); }
      finally{
                    try {   if(rs!=null)rs.close(); } catch(SQLException i){ i.printStackTrace(); }
      }
  }

  private void createContent(WritableSheet hoja, String text) throws WriteException, RowsExceededException {

    // Lets calculate the sum of it
    StringBuffer buf = new StringBuffer();
    buf.append("SUM(A2:A10)");
    Formula f = new Formula(0, 10, buf.toString());
    hoja.addCell(f);
    buf = new StringBuffer();
    buf.append("SUM(B2:B10)");
    f = new Formula(1, 10, buf.toString());
    hoja.addCell(f);

    // Now a bit of text

  }

  private void addCaption(WritableSheet hoja, int column, String s) throws RowsExceededException, WriteException {
    Label label;
    label = new Label(column, row, s, arialBoldUnderline);
    hoja.addCell(label);
  }

  private void addNumber(WritableSheet hoja, int column, int integer) throws WriteException, RowsExceededException {
    Number number;
    number = new Number(column, row, integer, arial);
    hoja.addCell(number);
  }

  private void addLabel(WritableSheet hoja, int column, String s) throws WriteException, RowsExceededException {
    Label label;
    label = new Label(column, row, s, arial);
    hoja.addCell(label);
  }

  private void token(WritableSheet hoja, String cadena)
  { int column=0;
            cadena= cadena.replace(" ", "[]");
            cadena= cadena.replace("|", " ");

      StringTokenizer token = new StringTokenizer(cadena);
      String textIn="";

                while(token.hasMoreTokens())
                {   textIn= token.nextToken().toString().replace("[]", " ");
                    try {
                        if(encabezado==1)
                            {   addCaption(hoja, column, textIn); }
                        else
                            {   addLabel(hoja, column, textIn);    }
                    } catch (WriteException ex) { ex.printStackTrace();}

                    column++;
                }
  }

  public static String excel(StringBuffer args, String sub) {
      WriteExcel test = new WriteExcel();
      String path="";
    query= args.toString();
            //"select layout prevalidacion from tmkgcA_ConcentradoPrevalMx";
    path= test.setOutputFile(sub);

        try{ test.createFormat(); }catch(WriteException e){ e.printStackTrace(); }

        try{ test.write(sub); } catch(IOException e){ e.printStackTrace(); }

    if(error.contains("Error: "))
        return error;
    else
        return sub;
  }
}
