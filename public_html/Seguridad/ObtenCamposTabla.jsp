<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.ResultSet,Utilerias.UtileriasBDF,Seguridad.SeguridadC" errorPage="" %>
<html>
<head>
<title>Construye Reporte</title>
</head>
<body class="cssBody" topMargin="70">
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
            %>Debe iniciar sesi�n<%
            return; 
	}
        if (SeguridadC.verificaHorarioC(Integer.parseInt(StrclUsrApp)) != true) {
            %> Fuera de Horario <%
            return; 
	}
        
    String strclTabla = "0";
    if (request.getParameter("strclTabla")!=null){
      strclTabla=request.getParameter("strclTabla");
    }
    String strObject = "";
    if (request.getParameter("strObject")!=null){
      strObject=request.getParameter("strObject");
    }

    StringBuffer StrHTML = new StringBuffer();
    
    ResultSet rs=null;
    rs=UtileriasBDF.rsSQLNP("Select clCampoTabla, dsCampoTabla, Alias from cCampoTabla where clTabla = " + strclTabla);
    
    if(rs.next()){
      rs.first();
      StrHTML.append("<table>");
      do {  
          StrHTML.append("<tr><td><input value='").append(rs.getString("dsCampoTabla")).append("'></td></tr>");
      } while(rs.next());
      StrHTML.append("</table>");
    }
    try{
      rs.close();
      
    }
    catch(Exception e){
    }
    %>
<script>
  window.opener.fnAddFields("<%=strObject%>","<%=StrHTML.toString()%>");
  <%
    StrHTML.delete(0,StrHTML.length());
  %>
  window.close();
</script>
</body>
</html>