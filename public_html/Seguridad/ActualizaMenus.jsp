<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.ResultSet,Utilerias.UtileriasBDF,Seguridad.SeguridadC" errorPage="" %>
<html>
<head>
<title>Actualización de Menús</title>
</head>
<body class="cssBody">
<link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
<script src='../Utilerias/Util.js'></script>
<jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>

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
            %>Debe iniciar sesion<%
            return; 
	}
        
        if (SeguridadC.verificaHorarioC(Integer.parseInt(StrclUsrApp)) != true) {
            %> Fuera de Horario <%
            return; 
	}
   
            StringBuffer StrSql2 = new StringBuffer();
            StrSql2.append("update tmkgcA_cUsrApp set  MenuConfig=0 ");
            StrSql2.append("where clUsrApp = ").append(StrclUsrApp);
            UtileriasBDF.ejecutaSQLNP(StrSql2.toString());
            StrSql2.delete(0,StrSql2.length());
            
       StringBuffer StrSql = new StringBuffer();
       StrSql.append("Select Nombre from tmkgcA_cUsrApp where clUsrApp='"+ StrclUsrApp + "'");
       ResultSet rs2 = UtileriasBDF.rsSQLNP( StrSql.toString());
       StrSql.delete(0,StrSql.length());
        if (rs2.next()) { 
        String StrNombreUsr = rs2.getString("Nombre");
        
   out.println("<p align='center'>");         
   out.println("<font class='TitResumen'> Los menús para el Usuario: </font><br><br><font class='cssazul'>" + StrNombreUsr + "<br>(Clave:" + StrclUsrApp + ")</font> <br><br><font class='TitResumen'>serán actualizados en la siguiente ocasión que ingrese al Sistema.");
        }   
%>  <br><br>
    <input type="button" name="btnCrr" id="btnCrr" onclick="window.close();" value="Cerrar Ventana"></input>
    </p>
    </body>
</html>
