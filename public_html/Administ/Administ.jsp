<%-- 
    Document   : Administ
    Created on : 18/07/2013, 05:25:03 PM
    Author     : rarodriguez
--%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.ResultSet,Utilerias.UtileriasBDF,Seguridad.SeguridadC,Utilerias.Captcha,Utilerias.Upload" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
 <%

        String clUsrApp = "0";
        String StrclPaginaWeb = "0";

        StrclPaginaWeb = "281";
        session.setAttribute("clPaginaWebP", StrclPaginaWeb);
        MyUtil.InicializaParametrosC(Integer.parseInt(StrclPaginaWeb), Integer.parseInt(clUsrApp));
%>

<html>
    <head>
        <title>Administ</title>
    </head>

    <frameset framespacing='0' noresize frameborder='no' id='admin' rows='25%,75%'>
        <frame frameborder='no' name='adminmenu' noresize id='adminmenu' src='AdminMenu.jsp' scrolling='si'/>
        <frame frameborder='no' name='admindet' noresize id='admindet' src='AdminDetalle.jsp' scrolling='si'/>
    </frameset>
    
</html>
