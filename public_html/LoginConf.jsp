<%-- 
    Document   : LoginConf
    Created on : 9/07/2013, 03:39:26 PM
    Author     : rarodriguez
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" import="Seguridad.SeguridadC"%>

    <%
	if (session.getAttribute("AccesoId")==null || session.getAttribute("AccesoId").toString().compareToIgnoreCase("0")==0){
            %>Debe iniciar sesion
           <script>
                top.location.href='index.html';
            </script><%
            return;
        }

    if (SeguridadC.verificaRequest(request.getQueryString())==false){
        response.sendRedirect("ErrorPage.jsp");
        return;
    }
        String strclUsrApp = "";
        if (session.getAttribute("clUsrApp")==null){
             strclUsrApp = request.getParameter("clUsr");
        }else{
            strclUsrApp = session.getAttribute("clUsrApp").toString();
        }

     %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <<script type="text/javascript" src='Utilerias/Util.js'></script>
    </head>
    <body>
        <form action="" method="post" autocomplete="off">
            <table border="0">
                <tr>
                    <th width="50">Usuario</th><td><input type="text" id="usr" name="usr" value="" onblur="fnReplaceScripting(this.value,this.id);"/></td>
                </tr>
                <tr>
                    <th width="50">Contraseña</th><td><input type="text" id="pss" name="pss" value="" onblur="fnReplaceScripting(this.value,this.id);"/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Comprobar"/></td><td/>
                </tr>
            </table>
        </form>

    </body>
</html>
