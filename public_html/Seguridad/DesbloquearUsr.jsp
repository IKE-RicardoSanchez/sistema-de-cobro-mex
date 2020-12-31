<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.ResultSet,Utilerias.UtileriasBDF,Seguridad.SeguridadC" errorPage="" %>
<html>
<head>
<title>Actualización de Menús</title>
</head>
<body class="cssBody">
<link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
<jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
<script src='../Utilerias/Util.js'></script>
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
           String StrNombreUsr="";
           if (request.getParameter("clUsrApp")!=null)
           {
            StrclUsrApp = request.getParameter("clUsrApp");
           }

   
            StringBuffer StrSql2 = new StringBuffer();
            StrSql2.append("sp_tmkgcA_sys_DesbloqueaUsr '");
            StrSql2.append(StrclUsrApp).append("'");
            System.out.println(StrSql2);
            UtileriasBDF.ejecutaSQLNP(StrSql2.toString());
            StrSql2.delete(0,StrSql2.length());
            
       StringBuffer StrSql = new StringBuffer();
       StrSql.append("Select Nombre from tmkgcA_cUsrApp where clUsrApp='"+ StrclUsrApp + "'");
       ResultSet rs2 = UtileriasBDF.rsSQLNP( StrSql.toString());
       StrSql.delete(0,StrSql.length());
        if (rs2.next()) { 
        StrNombreUsr = rs2.getString("Nombre");
        
   out.println("<p align='center'>");         
   out.println("<font class='TitResumen'> El Usuario: </font><br><br><font class='cssazul'>" + StrNombreUsr + "<br>(Clave:" + StrclUsrApp + ")</font> <br><br><font class='TitResumen'>fue desbloqueado.<BR>Su contraseña para entrar al Sistema es '12345678'.");
        }   
        StrclUsrApp=null;
        rs2=null;
        StrSql2=null;
        StrSql=null;
        StrNombreUsr=null;
        
        
%>  <br><br>
    <input type="button" name="btnCrr" id="btnCrr" onclick="window.close();" value="Cerrar Ventana"></input>
    </p>
    </body>
</html>