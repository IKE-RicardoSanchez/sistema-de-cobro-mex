<%-- 
    Document   : AdminMenu
    Created on : 18/07/2013, 06:15:10 PM
    Author     : rarodriguez
--%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.ResultSet,Utilerias.UtileriasBDF,Seguridad.SeguridadC,Utilerias.Captcha,Utilerias.Upload" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
        <link href="../StyleClasses/StyleC.css" rel="stylesheet" type="text/css">
        <title>Administ</title>
    </head>
    <body class="cssBody">
        <jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
        
        <%

        String clUsrApp = "0";
        String StrclPaginaWeb = "0";

        if (session.getAttribute("clUsrApp") != null) {
            clUsrApp = session.getAttribute("clUsrApp").toString();
        }else{
            %>Debe iniciar sesión
            <script>block();
                setTimeout("top.location.href='../index.html';", 3000);
             </script> <%
            return;
        }

        StrclPaginaWeb = "281";
        session.setAttribute("clPaginaWebP", StrclPaginaWeb);
        MyUtil.InicializaParametrosC(Integer.parseInt(StrclPaginaWeb), Integer.parseInt(clUsrApp));

        String complemento="", tipo="";
        StringBuffer SQL = new StringBuffer();
        StringBuffer resp = new StringBuffer();
                    SQL.append("sp_tmkgcA_Administ_SCA ").append(clUsrApp);

                UtileriasBDF.rsTableNP(SQL.toString(), resp);

        %>
        <h2 align="center">Administración de Usuarios.</h2>
        <h4 align="center">Sistema de Cobranza Adquirente.</h4>
        <br><center>
            <%=resp%>
        </center>
    </body>
</html>
