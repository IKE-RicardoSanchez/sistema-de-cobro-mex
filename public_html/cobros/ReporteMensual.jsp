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
            
          <title>JSP Page</title>
    </head>
    <body class="cssBody">
       
        <script src='../Utilerias/Util.js'>
            block();
        </script>
  
        <%      
        
         String StrclUsrApp ="0", strclUsr ="0";
        
         
         if (session.getAttribute("clUsrApp")!=null){
              strclUsr = session.getAttribute("clUsrApp").toString();
            }else{
                %>Debe iniciar sesión
             <script>
                
                setTimeout("top.location.href='../index.html';", 3000);
             </script> <%
               return;
            }

            if (SeguridadC.verificaHorarioC(Integer.parseInt(strclUsr)) != true)
             {
           %>

        <p>&nbsp;</p>        <p>&nbsp;</p>
        <div align="center">
            <em>
                <font color="#000066" size="3" face="Verdana, Arial, Helvetica, sans-serif">
                    <strong>Fuera de Horario </strong>
                </font>
            </em>
        </div>

          <% return;
        
          }
   
        String StrclpaginaWeb = "280";
        session.setAttribute("clpaginawebp", StrclpaginaWeb);

        MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));

        StringBuffer StrSql = new StringBuffer();
        StringBuffer StrSql1 = new StringBuffer();
            System.out.println(StrSql.toString());
      
        StrSql.append("st_TMKGCA_ReporteMes ").append(1);
            System.out.println(StrSql.toString());
      
        StringBuffer StrSalida = new StringBuffer();
        
        UtileriasBDF.rsTableNP(StrSql.toString(), StrSalida);
        
      
        String complemento="";
        StringBuffer SQL = new StringBuffer();
                SQL.append("st_TMKGCA_ReporteMes ").append(0);
                ResultSet query= UtileriasBDF.rsSQLNP(SQL.toString());

                    while(query.next()){
                        complemento= query.getString("Mes").toString();
                    }
        
        
           %>
        
           <center><script>  unblock();</script>
      
             
            <h3>Reporte del Mes</h3>
            <h1><%=complemento%></h1>
                <br>
              
                <div id="sql" style="margin-top:100px; margin-right:auto; margin-left:auto;">
                   <%
                    
                        if(!StrSql.toString().contains("Ingrese el periodo a consultar"))
                            { %>
                               <%=StrSalida%>
                       <%}else {%>
                             
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
    </body>
</html>
