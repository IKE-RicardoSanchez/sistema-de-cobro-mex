<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" import="Utilerias.UtileriasBDF,Seguridad.SeguridadC"%>
<html>
<head>
    <title>Bienvenido</title>
            <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
            <link href="StyleClasses/Global.css" rel="stylesheet" type="text/css">
            <link href="StyleClasses/StyleC.css" rel="stylesheet" type="text/css">
    <script src='Utilerias/Util.js'></script>
</head>
<body class="cssBody" >
<%	if (session.getAttribute("AccesoId")==null || session.getAttribute("AccesoId").toString().compareToIgnoreCase("0")==0){
        %>
            Debe iniciar sesion
            <script>
                top.location.href='index.html';
            </script>
        <%
            return;
	}
	if (SeguridadC.verificaRequest(request.getQueryString())==false){
            response.sendRedirect("ErrorPage.jsp");
            return;
	}

	String strclUsr ="0";
	if (session.getAttribute("clUsrApp")!=null){
            strclUsr = session.getAttribute("clUsrApp").toString();
	}else{
        %>
            Debe iniciar sesión
        <%
            return;
	}
	%>
	<%--
	if (SeguridadC.verifica__HorarioC(Integer.parseInt(strclUsr)) != true)
	 {%>            <p>&nbsp;</p>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<div align="center"><em><font color="#000066" size="3" face="Verdana, Arial, Helvetica, sans-serif"><strong>
				Fuera de Horario </strong></font></em></div>
				<% return;		}	 --%>
	<p>&nbsp;</p>	<p>&nbsp;</p>
	<p>&nbsp;</p>	<p>&nbsp;</p>
	<p>&nbsp;</p>

    <div align="center"><strong><font color="#000066" size="3" face="Verdana, Arial, Helvetica, sans-serif"><em>
    <table>
        <td><hr align="center" width="100" color="#FE7018"></td>
	<td><font color="#000066" size="3" face="Verdana, Arial, Helvetica, sans-serif"><strong>BIENVENIDO </strong></font></td><td><hr align="center" width="100" color="#FE7018"></td>
    </table>
    <BR><BR>
	<%=session.getAttribute("NombreUsuario").toString()%> </font></em></strong></div><BR>

    <div align="center">
        <font color="#000066" size="2" face="Verdana, Arial, Helvetica, sans-serif">
            Ha iniciado Exitosamente una sesión dentro del<BR>
            sistema Cobros de Iké Asistencia.<BR>
            Por razones de seguridad le suplicamos cerrar el<BR>
            navegador de internet en cuanto haya terminado.
        </font>
    </div>
    <BR>

    <%      if(session.getAttribute("tipoMenu").equals("3")==false && session.getAttribute("tipoMenu").equals("1")==false
                && session.getAttribute("tipoMenu").equals("2")==false)
            {
    %>
                <script>
                    setTimeout('block();',2000);
                    setTimeout('reporte()', 4000);

                    function reporte(){
                        location.href="cobros/ReportePrincipal.jsp?periodo=1&menu=0";
                    }
                 </script>

        <div id="loading" class="loading-invisible" style="position:absolute; top:0px;">
            <table border="0" width="100%" align="center" cellpadding="0" cellspacing="0">
                <tr><td height="500" valign="middle" class="textoPagPrecarga" align="center"><img src="Imagenes/cargando.gif" alt="Cargando ..." vspace="5"/></td></tr>
            </table>
        </div>
     <%}%>
</body>
</html>