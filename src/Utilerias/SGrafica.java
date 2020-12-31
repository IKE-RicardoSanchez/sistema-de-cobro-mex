package Utilerias;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;
import java.sql.ResultSet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class SGrafica extends HttpServlet {
    private String StrDataSetX = "";
    private ResultSet rsEx = null;
    private double intDataSetY = 0;
    private double intDataSetl = 0;
    private double intDataSet2 = 0;
    private double intDataSet3 = 0;
    private double intDataSet4 = 0;
    private double intDataSet5 = 0;
    private double intDataSet6 = 0;
    private double intDataSet7 = 0;
    private double intDataSet8 = 0;
    private double intDataSet9 = 0;
    private double intDataSet10 = 0;
    private double intDataSet11 = 0;
    private double intDataSet12 = 0;
    private double intDataSet13 = 0;
    private String sentenciaSQLG = "";
    private String dsCampo ="";
    private String dsx ="";
    private String dsCampoCan="";
    private StringBuffer strSQL = new StringBuffer();
    public SGrafica() {
        
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        OutputStream out = response.getOutputStream();
        try {
            String type = request.getParameter("type");
            String dsCampoL = request.getParameter("dsCampo").toString().trim();
            String dsCampoC = request.getParameter("dsCampoCan").toString().trim();
            String TipoG = request.getParameter("Tipo").toString().trim();
            String Pagina = request.getParameter("Pagina").toString().trim(); /*Agredado*/
            sentenciaSQLG= type;
            dsCampo= dsCampoL;
            dsCampoCan= dsCampoC;
            JFreeChart chart = null;
            if (TipoG.equalsIgnoreCase("1")) {
                if(Pagina.equalsIgnoreCase("40")||Pagina.equalsIgnoreCase("56")||Pagina.equalsIgnoreCase("43")||Pagina.equalsIgnoreCase("59")){
                    chart = crearChartM();
                } else{
                    chart = crearChart();
                    /*Sintaxis para convertir graficos a pdf*/
                /*ConvertPDF pdf = new ConvertPDF();
                org.jfree.text.TextUtilities.setUseDrawRotatedStringWorkaround(false);
                pdf.convertToPdf(crearChart(), 400, 600, "GraficoPie.pdf");*/
                }
            }
            
            if (TipoG.equalsIgnoreCase("2")) {
                if(Pagina.equalsIgnoreCase("23")){
                    chart = createBarChartM();
                } else{
                    if(Pagina.equalsIgnoreCase("42")||Pagina.equalsIgnoreCase("44")||Pagina.equalsIgnoreCase("46")||Pagina.equalsIgnoreCase("58")||Pagina.equalsIgnoreCase("60")||Pagina.equalsIgnoreCase("62")){
                        chart = createBarChartR();
                    } else{
                        if(Pagina.equalsIgnoreCase("17")||Pagina.equalsIgnoreCase("19")||Pagina.equalsIgnoreCase("20")||Pagina.equalsIgnoreCase("21")){
                            chart = createBarChartMultipl();
                        } else{
                            chart = createBarChart();
                        }
                    }
                }
            }
            if (TipoG.equalsIgnoreCase("3")) {
                chart = createLineChart();
            }
            
            if (chart != null) {
                if(Pagina.equalsIgnoreCase("23")){
                    response.setContentType("image/jpeg");
                    ChartUtilities.writeChartAsJPEG(out, chart, 680, 540);
                }else {
                    if(Pagina.equalsIgnoreCase("17")||Pagina.equalsIgnoreCase("19")||Pagina.equalsIgnoreCase("20")||Pagina.equalsIgnoreCase("21")){
                        response.setContentType("image/jpeg");
                        ChartUtilities.writeChartAsJPEG(out, chart, 700, 600);
                    } else{
                        response.setContentType("image/jpeg");
                        ChartUtilities.writeChartAsJPEG(out, chart, 550, 320);
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println(e.toString());
        } finally {
            out.close();
        }
    }
    
    private JFreeChart crearChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            //rsEx = UtileriasBDF.rsSQLNP(sentenciaSQLG);
            strSQL.append(sentenciaSQLG);
            rsEx = UtileriasBDF.rsSQLNP(strSQL.toString());
            strSQL.delete(0,strSQL.length());
            while(rsEx.next()) {
                if(!rsEx.getString(dsCampo).equalsIgnoreCase("TOTALES")){
                    StrDataSetX = rsEx.getString(dsCampo);
                    intDataSetY = rsEx.getDouble(dsCampoCan);
                    dataset.setValue(StrDataSetX, intDataSetY);
                }
            }
            StrDataSetX =null;
            dsCampo =null;
            dsCampoCan=null;
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try{
                if (rsEx!=null) {
                    rsEx.close();
                    rsEx=null;
                }
            } catch(Exception ee) {
                ee.printStackTrace();
            }
        }
        
        JFreeChart chart = ChartFactory.createPieChart3D("",dataset, true,true,true);
        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("Sin Imagen");
        return chart;
    }
    
    private JFreeChart createBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            //rsEx = UtileriasBDF.rsSQLNP(sentenciaSQLG);
            strSQL.append(sentenciaSQLG);
            rsEx = UtileriasBDF.rsSQLNP(strSQL.toString());
            strSQL.delete(0,strSQL.length());
            while(rsEx.next()) {
                StrDataSetX = rsEx.getString(dsCampo);
                intDataSetl = rsEx.getDouble(dsCampoCan);
                dataset.addValue(intDataSetl, StrDataSetX, dsCampo);
            }
            StrDataSetX =null;
            dsCampo =null;
            dsCampoCan=null;
        } catch(Exception e){
            e.printStackTrace();
            this.destroy();
        } finally {
            try{
                if (rsEx!=null) {
                    rsEx.close();
                    rsEx=null;
                }
            } catch(Exception ee) {
                ee.printStackTrace();
            }
        }
        JFreeChart chart = ChartFactory.createBarChart3D("","","Porcentaje",dataset,PlotOrientation.VERTICAL,true,true,false);
        return chart;
    }
    
    private JFreeChart createBarChartM() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            //rsEx = UtileriasBDF.rsSQLNP(sentenciaSQLG);
            strSQL.append(sentenciaSQLG);
            rsEx = UtileriasBDF.rsSQLNP(strSQL.toString());
            strSQL.delete(0,strSQL.length());
            while(rsEx.next()) {
                StrDataSetX/*Campo*/ = rsEx.getString(dsCampo);
                intDataSetl/*Valor*/ = rsEx.getDouble("Reparacion");
                intDataSet2/*Valor*/ = rsEx.getDouble("Staff");
                intDataSet3/*Valor*/ = rsEx.getDouble("Estacionamiento");
                intDataSet4/*Valor*/ = rsEx.getDouble("Limpieza");
                intDataSet5/*Valor*/ = rsEx.getDouble("Entrega_Tiempo");
                intDataSet6/*Valor*/ = rsEx.getDouble("Comunicacion");
                intDataSet7/*Valor*/ = rsEx.getDouble("Desempeno");
                intDataSet8/*Valor*/ = rsEx.getDouble("Recomendaria");
                
                dataset.addValue(intDataSetl, StrDataSetX, "Reparacion");
                dataset.addValue(intDataSet2, StrDataSetX, "Staff");
                dataset.addValue(intDataSet3, StrDataSetX, "Estacionamiento");
                dataset.addValue(intDataSet4, StrDataSetX, "Limpieza");
                dataset.addValue(intDataSet5, StrDataSetX, "Entrega_Tiempo");
                dataset.addValue(intDataSet6, StrDataSetX, "Comunicacion");
                dataset.addValue(intDataSet7, StrDataSetX, "Desempeno");
                dataset.addValue(intDataSet8, StrDataSetX, "Recomendaria");
            }
            StrDataSetX =null;
            dsCampo =null;
            dsCampoCan=null;
            StrDataSetX=null;
        } catch(Exception e){
            e.printStackTrace();
            this.destroy();
        } finally {
            try{
                if (rsEx!=null) {
                    rsEx.close();
                    rsEx=null;
                    
                }
            } catch(Exception ee) {
                ee.printStackTrace();
            }
        }
        JFreeChart chart = ChartFactory.createBarChart3D("","","Porcentaje",dataset,PlotOrientation.HORIZONTAL,true,true,false);
        return chart;
    }
    private JFreeChart createBarChartMultipl() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            //rsEx = UtileriasBDF.rsSQLNP(sentenciaSQLG);
            strSQL.append(sentenciaSQLG);
            rsEx = UtileriasBDF.rsSQLNP(strSQL.toString());
            strSQL.delete(0,strSQL.length());
            while(rsEx.next()) {
                if(!rsEx.getString(dsCampo).equalsIgnoreCase("MEDIA PROMEDIO")){
                    StrDataSetX = rsEx.getString(dsCampo);
                    intDataSetl = rsEx.getDouble(dsCampoCan);
                    dataset.addValue(intDataSetl, StrDataSetX, "");
                }
            }
            StrDataSetX =null;
            dsCampo =null;
            dsCampoCan=null;
        } catch(Exception e){
            e.printStackTrace();
            this.destroy();
        } finally {
            try{
                if (rsEx!=null) {
                    rsEx.close();
                    rsEx=null;
                    
                }
            } catch(Exception ee) {
                ee.printStackTrace();
            }
        }
        JFreeChart chart = ChartFactory.createBarChart3D("","","Porcentaje",dataset,PlotOrientation.HORIZONTAL,true,true,false);
        return chart;
    }
    
    
    private JFreeChart createLineChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            //rsEx = UtileriasBDF.rsSQLNP(sentenciaSQLG);
            strSQL.append(sentenciaSQLG);
            rsEx = UtileriasBDF.rsSQLNP(strSQL.toString());
            strSQL.delete(0,strSQL.length());
            while(rsEx.next()) {
                if(!rsEx.getString(dsCampo).equalsIgnoreCase("MEDIA PROMEDIO")){
                    StrDataSetX = rsEx.getString(dsCampo);
                    intDataSetl = rsEx.getDouble(dsCampoCan);
                    dataset.addValue(intDataSetl,"", StrDataSetX);
                }
            }
            StrDataSetX =null;
            dsCampo =null;
            dsCampoCan=null;
        } catch(Exception e){
            e.printStackTrace();
            this.destroy();
        } finally {
            try{
                if (rsEx!=null) {
                    rsEx.close();
                    rsEx=null;
                    
                }
            } catch(Exception ee) {
                ee.printStackTrace();
            }
        }
        JFreeChart chart = ChartFactory.createLineChart("","","Porcentaje",dataset,PlotOrientation.VERTICAL,true,true,false);
        return chart;
    }
    
    
    private JFreeChart createBarChartR() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            //rsEx = UtileriasBDF.rsSQLNP(sentenciaSQLG);
            strSQL.append(sentenciaSQLG);
            rsEx = UtileriasBDF.rsSQLNP(strSQL.toString());
            strSQL.delete(0,strSQL.length());
            java.sql.ResultSetMetaData metaDato =   null;
            metaDato = rsEx.getMetaData();
            while(rsEx.next()) {
                for (int i= 2 ; i<=metaDato.getColumnCount(); i++){
                    dataset.addValue(rsEx.getDouble(metaDato.getColumnLabel(i)),metaDato.getColumnLabel(i), metaDato.getColumnLabel(i));
                }
            }
            metaDato=null;
        } catch(Exception e){
            e.printStackTrace();
            this.destroy();
        } finally {
            try{
                if (rsEx!=null) {
                    rsEx.close();
                    rsEx=null;
                }
            } catch(Exception ee) {
                ee.printStackTrace();
            }
        }
        
        JFreeChart chart = ChartFactory.createBarChart3D("","",dsx,dataset,PlotOrientation.VERTICAL,true,true,false);
        String dsx = null;
        rsEx = null;
        return chart;
    }
    
    
    private JFreeChart crearChartM() {
        //DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            strSQL.append(sentenciaSQLG);
            rsEx = UtileriasBDF.rsSQLNP(strSQL.toString());
            strSQL.delete(0,strSQL.length());
            java.sql.ResultSetMetaData metaDato =   null;
            metaDato = rsEx.getMetaData();
            while(rsEx.next()) {
                for (int i= 2 ; i<=metaDato.getColumnCount(); i++){
                    dataset.setValue(metaDato.getColumnLabel(i),rsEx.getDouble(i));
                }
            }
            //String sentenciaSQLG = null;
            metaDato=null;
        } catch(Exception e){
            e.printStackTrace();
            this.destroy();
        } finally {
            try{
                if (rsEx!=null) {
                    rsEx.close();
                    rsEx=null;
                }
            } catch(Exception ee) {
                ee.printStackTrace();
            }
        }
        JFreeChart chart = ChartFactory.createPieChart3D("",dataset, true,true,true);
        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("Sin Imagen");
        return chart;
    }
}