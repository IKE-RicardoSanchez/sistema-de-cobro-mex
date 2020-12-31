<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="UtlHash.LoadPagina,Utilerias.GeneraRpt,java.sql.ResultSet,Utilerias.UtileriasBDF,Seguridad.SeguridadC" errorPage="" %>
<html>    
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <script src='../Utilerias/Util.js'></script>
    <title>Estatus</title>
  </head>
  <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
  <body class='cssBody'>
<% 
	if (session.getAttribute("AccesoId")==null){
            %>Debe iniciar sesion<%
            return;
        }else{
            if (session.getAttribute("AccesoId").toString().compareToIgnoreCase("0")==0){
                %>Debe iniciar sesion<%
                return;
            }
        }

    if (SeguridadC.verificaRequest(request.getQueryString())==false){ 
        response.sendRedirect("ErrorPage.jsp");
        return;
    }    
      
        
        String StrclUsrApp="0";

        if (session.getAttribute("clUsrApp")!= null) {
            StrclUsrApp = session.getAttribute("clUsrApp").toString();
       	}else{
            %>Debe iniciar sesión<%
            return; 
	}
        if (SeguridadC.verificaHorarioC(Integer.parseInt(StrclUsrApp)) != true) {
            %> Fuera de Horario <%
            return; 
	}
        
        StrclUsrApp = null;
%>
      
  <form action='../servlet/Seguridad.StartStopServices' method='get'>
  <input name='IDProcess' id='IDProcess' type='hidden'>
  <input name='Action' id='Action' type='hidden'>
  
  <table>
  
    <tr><td>Envío de Reportes</td><td><input onClick='document.all.IDProcess.value=1;document.all.Action.value=1' type='submit' value='Iniciar'></input><input onClick='document.all.IDProcess.value=1;document.all.Action.value=0' type='submit' value='Detener'></input></td><td>Estatus: 
    <% if (Utilerias.GeneraRpt.getEstatus()==true){ %>
        <%="Iniciado"%>
        <%}else{%><%="Detenido"%> <%}%> 
        </td></tr>
    <tr><td>Registro de Log</td><td><input onClick='document.all.IDProcess.value=2;document.all.Action.value=1' type='submit' value='Iniciar'></input><input onClick='document.all.IDProcess.value=2;document.all.Action.value=0' type='submit' value='Detener'></input></td><td>Estatus: 
    <% if (UtileriasBDF.getStatusLog()==true){ %>
        <%="Iniciado"%>
        <%}else{%><%="Detenido"%> <%}%> 
        </td></tr>
    <tr><td>Load Pagina</td><td><input onClick='document.all.IDProcess.value=3;document.all.Action.value=1' type='submit' value='Reload'></input></td><td>
        </td></tr>

  </table>
  </form>
  </body>
</html>
