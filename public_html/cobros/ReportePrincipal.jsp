<%--
    Document   : ReportePrincipal
    Created on : 28/05/2013, 06:00:36 PM
    Author     : rarodriguez
--%>

<%@page import="org.omg.PortableInterceptor.SYSTEM_EXCEPTION"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage=""
         import="Utilerias.UtileriasBDF,Seguridad.SeguridadC, java.sql.ResultSet, java.util.Date, java.text.SimpleDateFormat"%>
<jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
         
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
            <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
            <link href="../StyleClasses/styleCB.css" rel="stylesheet" type="text/css">
            <link href="../StyleClasses/StyleC.css" rel="stylesheet" type="text/css">
            <link href="../plugin/jquery-ui.css" rel="stylesheet" type="text/css">
            <link href="../plugin/jquery-ui.min.css" rel="stylesheet" type="text/css">
            <link href="../plugin/jquery.ui.theme.css" rel="stylesheet" type="text/css">
            
            <script src="../plugin/jquery-1.9.1.js"></script>
            <script src="../plugin/jquery-ui.js"></script>

                <script>
                    var inicio, fin, val;
                $(function() {
                    $("#datepickerI").datepicker({onSelect: function(texto, objDatepicker){
                        inicio=texto;
                    }});
                    $( "#datepickerI" ).datepicker("option", "dateFormat", "yy-mm-dd" );

                });

                $(function() {
                    $( "#datepickerF" ).datepicker({onSelect: function(texto, objDatepicker){
                        fin=texto;
                    }});
                    $( "#datepickerF" ).datepicker("option", "dateFormat", "yy-mm-dd" );
                });

                </script>
            <title>JSP Page</title>
    </head>
    <body class="cssBody">
        <script src='../Utilerias/Util.js'></script>
        <%      System.out.println("Reporte Principal...");
         String StrclUsrApp ="0", strclUsr ="0", fechaI="", fechaF="", periodo="1", opcion="0", error="", bandera="1";
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            if (session.getAttribute("clUsrApp")!=null){
              strclUsr = session.getAttribute("clUsrApp").toString();
            }else{
                %>Debe iniciar sesi�n
             <script>
                setTimeout("top.location.href='../index.html';", 3000);
             </script> <%
               return;
            }

            if (SeguridadC.verificaHorarioC(Integer.parseInt(strclUsr)) != true)
             {
           %>

        <p>&nbsp;</p>        <p>&nbsp;</p>
        <div align="center"><em><font color="#000066" size="3" face="Verdana, Arial, Helvetica, sans-serif"><strong>Fuera de Horario </strong></font></em></div>

          <% return;
         }
                if(request.getParameter("menu")!=null)
                 {  opcion= request.getParameter("menu").toString(); }

            if(opcion.equals("TD"))
               {    session.setAttribute("Reporte", "TD");  }
            else if(opcion.equals("1"))
               {    session.setAttribute("Reporte", "");    }


                if(request.getParameter("fechai")!=null)
                     {  fechaI= request.getParameter("fechai").toString();
                                Date fi= new Date();
                                Date ff= new Date();
                                bandera="2";
                        try{
                            fi= format.parse(fechaI);
                                if(fi.compareTo(format.parse(format.format(date)))==1)
                                    {
                                        error="La fecha inicio no puede ser mayor al d�a de hoy";
                                        opcion="1";
                                    }
                        }catch(Exception e){ e.printStackTrace();}

                         if(request.getParameter("fechaf")!=null)
                         {  fechaF= request.getParameter("fechaf").toString();

                            try{
                                ff= format.parse(fechaF);
                                    if(ff.compareTo(format.parse(format.format(date)))==1)
                                        {
                                            if(error.equals("")){
                                                    error="La fecha fin es mayor al d�a de hoy, se calcular� al d�a de hoy";
                                                    fechaF= format.format(date);
                                            }
                                        }
                            }catch(Exception e){ e.printStackTrace();}
                         }
                      }

                if(request.getParameter("periodo")!=null)
                 {  periodo= request.getParameter("periodo").toString();  }


                String StrclpaginaWeb = "280";
                session.setAttribute("clpaginawebp", StrclpaginaWeb);

                MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));
                    StringBuffer SQL = new StringBuffer();

                String query="";

                    if(!session.getAttribute("Reporte").toString().equals("TD"))
                    {
                        query="sp_tmkgcA_Reportes '"+fechaI+"','"+fechaF+"',"+periodo+", "+bandera+",'"+session.getAttribute("PermisoConf").toString()+"'";
                    }else if(session.getAttribute("Reporte").toString().equals("TD"))
                        {
                            query="sp_tmkgcA_ResumenTD '"+fechaI+"','"+fechaF+"'";
                        }
System.out.println(query);
                         UtileriasBDF.rsTableNP(query, SQL);
        %>

        <center><script> unblock();</script>
            <h3>Reporte de Cobranza</h3>
            <br>
            <br>
            <div id="rangos" style="visibility:visible; width:500px; margin-left:auto; margin-right:auto;">
                <div style="position:relative; z-index:1; left:80px; top:20px; float:left; ">
                    <p>Fecha incio:</p> <input type="text" id="datepickerI" title="Fecha inicio" value="<%=fechaI%>"/>
                </div>
                <div style="position:relative; z-index:2; left:100px; top:20px; float:left">
                    <p>Fecha fin:</p> <input type="text" id="datepickerF" title="Fecha fin" value="<%=fechaF%>"/>
                </div><br>
                <div style="position:relative; z-index:3; left:100px; top:20px;">
                    <input id="periodo" type="checkbox" title="Por defecto es mensual." style="visibility:hidden;"><a title="Por defecto es mensual."></a>
                    <input id="reporte" type="button" value="Generar" onclick="generaRep()"/>
                </div>
            </div>
                <div id="sql" style="margin-top:100px; margin-right:auto; margin-left:auto;">
                   <%
                        if(opcion.contains("0"))
                         {      fechaF = format.format(date).toString();
                                fechaI= format.format(date).toString().substring(0, 8)+"01";
                            }

                        if(!SQL.toString().contains("Ingrese el periodo a consultar"))
                            { %>
                            <p>Periodo de<font color="blue"> <%=fechaI%> a <%=fechaF%></font> .</p>
                                <%=SQL%>
                       <%}else {%>
                                <p> Ingrese el periodo a consultar. </p>
                       <%}%>
                </div>
                <div id="loading" class="loading-invisible" style="position:absolute; top:0px;">
            <table border="0" width="100%" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="500" valign="middle" class="textoPagPrecarga" align="center"><img src="../Imagenes/cargando.gif" alt="Cargando ..." vspace="5"/></td>
                </tr>
            </table>
        </div>
        </center>

       <script>
            function generaRep(){
                var texto='Falta informar: ', fechai, fechaf, periodo;
                if(document.getElementById("datepickerI").value=="")
                    {   texto= texto + ' Fecha inicio. ';
                    }else {fechai= inicio; }

                if(document.getElementById("datepickerF").value=="")
                    {   texto= texto + ' Fecha fin.';
                    }else { fechaf= fin; }

                 if(document.getElementById("periodo").checked)
                     {periodo=0;}
                 else
                     {periodo=1;}

                if(texto!="Falta informar: ")
                    {   alert(texto); val=1;}
                 else
                 {  val=2;      //alert('ReportePrincipal.jsp?menu='+val+'&fechai='+fechai+'&fechaf='+fechaf+'&periodo='+periodo+'');
                    if(inicio>fin)
                        { alert('Favor de validar el periodo... \n(Fecha inicio mayor a Fecha fin.)');
                   }else {   block();
                             //location.href='ReportePrincipal.jsp?menu='+val+'&fechai='+fechai+'&fechaf='+fechaf+'&periodo='+periodo+''; }
                             location.href='ReportePrincipal.jsp?fechai='+fechai+'&fechaf='+fechaf+'&periodo='+periodo+''; }
                 }
            }//function generaRep
             <%--
                if(opcion.contains("1")==true || opcion.contains("2") || opcion.equals("TD")==true)
                 { %>
                        document.getElementById("rangos").style.visibility="visible";

                 <%
             --%><%             if(!error.equals(""))
                            {%>  alert('<%=error%>');   <%}%><%--
                 }else{%>
                       // document.getElementById("sql").style.marginTop="50px";
                 <%}
            --%><%
                date=null;
                format= null;%>
        </script>
    </body>
</html>
