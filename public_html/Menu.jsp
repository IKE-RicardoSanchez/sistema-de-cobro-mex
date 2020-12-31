<%@page contentType="text/html; charset=iso-8859-1" import="UtlHash.Usuario, UtlHash.LoadUsuario, Seguridad.SeguridadC,java.sql.ResultSet"%>

<html>
<head><title></title></head>
<link href="StyleClasses/Global.css" rel="stylesheet" type="text/css">
<link href="StyleClasses/StyleC.css" rel="stylesheet" type="text/css">

<body topmargin="5" leftmargin="5" background="Imagenes/bgMenu.jpg" bgproperties="fixed">
            <script src='Utilerias/Util.js'></script>

<table class="Table" width='240' cellspacing="0" cellpadding="5">
    <tr ><td class="TTable" colspan="1">Menú de Opciones</td></tr>
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
        
    String strclUsr ="0";
    if (session.getAttribute("clUsrApp")!=null){
      strclUsr = session.getAttribute("clUsrApp").toString();
       	}else{
            %>Debe iniciar sesión<%
            return; 
	}
    
    if (SeguridadC.verificaHorarioC(Integer.parseInt(strclUsr)) != true) 
     {
        %>Fuera de Horario<%
        return; 
     }     
      %>
</table>

<% 

 
  //Usuario UsuarioI = 
  StringBuffer strMenus = LoadUsuario.getstrMenus(strclUsr);

  //strMenus = UsuarioI.getstrMenus();
  %><%=strMenus.toString()%><%
  //UsuarioI=null; 
  strMenus.delete(0,strMenus.length());
  strMenus=null;

     /*   String DB="";
     ResultSet rs= Utilerias.UtileriasBDF.rsSQLNP("select DB_NAME() [DataBase]");

        if(rs.next())
            DB= rs.getString("DataBase").toString();*/
  %>

        <%--<center><b><font color="yellow"> !! D E S A R R O L L O !!<br><%=DB%> </font></b></center>--%>
        <div id="loading" class="loading-invisible" style="position:absolute; top:0px; width:250px;"/>
        
  
</body>
</html>



