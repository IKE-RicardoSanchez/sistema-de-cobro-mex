<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" import="Seguridad.SeguridadC"%>

<%
    if (SeguridadC.verificaRequest(request.getQueryString())==false){ 
        response.sendRedirect("ErrorPage.jsp");
        return;
    }
%>
<html>
<head>
<title>Encabezado</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
 <script src='Utilerias/Util.js'></script>
</head>
<!--<frameset  framespacing='0' noresize frameborder='no' id='topm' cols='1,*,430'>-->
   <!-- <frame frameborder='no' name='funcion' noresize id='funcion' src='./Operacion/KM0/AlertaExpEntrantes.jsp?&Apartado=S' scrolling='no'></frame> -->
   <frameset  framespacing='0' noresize frameborder='no' id='topm' cols='100%'>
 <!--  <frame frameborder='no' name='phone' noresize id='phone' src='' scrolling='no'></frame>    -->

    <frame frameborder='no' name='encabeza' noresize id='encabeza' src='./Encabezado.jsp?&Apartado=S' scrolling='no'></frame>
</frameset>
</html>
