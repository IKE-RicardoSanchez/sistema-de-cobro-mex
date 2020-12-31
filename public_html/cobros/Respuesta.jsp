<%-- 
    Document   : Respuesta
    Created on : 16/05/2013, 01:05:54 PM
    Author     : rarodriguez
--%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage=""
         import="Utilerias.UtileriasBDF,Seguridad.SeguridadC, java.sql.ResultSet"%>
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

    <iframe id="subir" name="subir" src="SubirRespuesta.jsp" scrolling="auto" height="800" width="100%" frameborder="0"></iframe>
   <!-- <iframe id="carga" name="carga" src="CargaRespuesta.jsp" scrolling="auto" height="2" width="100%" frameborder="0"></iframe>
    <iframe id="muestra" name="muestra" src="MuestraRespuesta.jsp" scrolling="auto" height="600" width="100%" frameborder="0"></iframe>
-->
    <body class="cssBody">
        <script src="../Utilerias.Util.js"></script>
        <%
     String StrclUsrApp ="0";
        String strclUsr ="0";
        if (session.getAttribute("clUsrApp")!=null){
          strclUsr = session.getAttribute("clUsrApp").toString();
        }else{
            %>Debe iniciar sesión
            <script>
                setTimeout("top.location.href='../index.html';", 3000);
             </script>
            <%
            return;
        }

        if (SeguridadC.verificaHorarioC(Integer.parseInt(strclUsr)) != true)
         {
       %>

        <p>&nbsp;</p>        <p>&nbsp;</p>
        <div align="center"><em><font color="#000066" size="3" face="Verdana, Arial, Helvetica, sans-serif"><strong>Fuera de Horario </strong></font></em></div>

          <% return;
         }
                String StrclpaginaWeb = "280";
                session.setAttribute("clpaginawebp", StrclpaginaWeb);

                MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));
            %>
        
    </body>
</html>
