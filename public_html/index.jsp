<%@page contentType="text/html; charset=iso-8859-1" import="Seguridad.SeguridadC"%>

<%
    if (SeguridadC.verificaRequest(request.getQueryString())==false){ 
        response.sendRedirect("ErrorPage.jsp");
        return;
    }
%>
<html>
    <head><title>JSP Page</title></head>
    <body>
    </body>
</html>
