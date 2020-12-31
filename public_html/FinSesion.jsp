<%@page contentType="text/html; charset=iso-8859-1" import="Utilerias.UtileriasBDF,Seguridad.SeguridadC,Utilerias.SessionCobros"%>
<html>
<head><title>Cierra Session</title></head>
<body>
<%
    if (SeguridadC.verificaRequest(request.getQueryString())==false || session.getAttribute("clUsrApp") == null){
        %> <script> top.location.href='index.html'; </script> <%
           return;
    }else{
        //System.out.println("sp_tmkgcA_sys_ADM_Salir "+session.getAttribute("clUsrApp").toString());

        if(request.getSession(false).getAttribute("path")!=null)
                SessionCobros.ValidaPath(request.getSession(false));

    UtileriasBDF.actSQLNP("sp_tmkgcA_sys_ADM_Salir "+session.getAttribute("clUsrApp").toString());

    session.invalidate();
    response.sendRedirect(request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/") + 1) + "index.html");
    }
%>
<script>
window.close()
</script>
</body>
</html>
