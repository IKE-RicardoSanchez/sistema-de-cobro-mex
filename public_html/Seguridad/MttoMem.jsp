<%@ page contentType="text/html;charset=windows-1252" import="java.sql.ResultSet,Seguridad.SeguridadC,Utilerias.UtileriasBDF"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Mtto Catálogos de Memoria</title>
    <script src='../Utilerias/Util.js'></script>
  </head>
  <body>
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
 try{
    
        if (session.getAttribute("clUsrApp")!= null) {
            StrclUsrApp = session.getAttribute("clUsrApp").toString();
       	}else{
            %>Debe iniciar sesión<%
            return; 
	}

    if (SeguridadC.verificaHorarioC(Integer.parseInt(StrclUsrApp)) != true) 
     {%>
       Fuera de Horario
       <%return;  
     } 
%>
    <input type="button" onClick="fnReload('cbEntidad')" value="Entidades, Municipios y Colonias"><br>
    <input type="button" onClick="fnReload('cbAMIS')" value="Marcas y Tipos de Auto"><br>
    <input type="button" onClick="fnReload('')" value=""><br>
    <input type="button" onClick="fnReload('')" value=""><br>
    <input type="button" onClick="fnReload('')" value=""><br>
    
<%
  }catch(Exception e){
  }
%>
<script> 
  function fnReload(strClass){
    window.open("ReloadMem.jsp?class=cbEntidad","")
  }
</script>

  </body>
</html>
