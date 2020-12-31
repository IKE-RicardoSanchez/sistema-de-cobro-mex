<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.ResultSet,Utilerias.UtileriasBDF,Seguridad.SeguridadC" errorPage="" %>
<html>
<head>
<title></title>
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

    	String StrclGpoUsr = "0";

      	if (request.getParameter("clGpoUsr")!= null)
      	{
            StrclGpoUsr= request.getParameter("clGpoUsr").toString(); 
       	}  
        session.setAttribute("clGpoUsr",StrclGpoUsr);
       	StringBuffer StrSql = new StringBuffer();
        StrSql.append("select clGpoUsr, dsGpoUsr, Administrador from tmkgcA_cGpoUsr ");
       	StrSql.append(" Where clGpoUsr= ").append(StrclGpoUsr);
       
       	String StrclPaginaWeb = "275";
	session.setAttribute("clPaginaWebP",StrclPaginaWeb);

	%><script>fnOpenLinks()</script><%

       	MyUtil.InicializaParametrosC(275,Integer.parseInt(strclUsr)); 
        ResultSet rs = UtileriasBDF.rsSQLNP( StrSql.toString());
        StrSql.delete(0,StrSql.length());
   %>    
	<%=MyUtil.doMenuAct("../servlet/Utilerias.EjecutaAccion","")%>
        <INPUT id='URLBACK' name='URLBACK' type='hidden' value='<%=request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/") + 1)%>GrupoDetalle.jsp?'>
        <%
        if (rs.next()) {
		%><INPUT id='clGpoUsr' name='clGpoUsr' type='hidden' value='<%=rs.getString("clGpoUsr")%>'>
		<%=MyUtil.ObjInput("Descripci�n de Grupo","dsGpoUsr",rs.getString("dsGpoUsr"),true,true,100,80,"",true,true,30)%>
                <%=MyUtil.ObjChkBox("Es Administrador","Administrador",rs.getString("Administrador"),true,true,350,80,"","Si","No","")%>
        <%
        }
	else{
		%><INPUT id='clGpoUsr' name='clGpoUsr' type='hidden' value='0'>
                <%=MyUtil.ObjInput("Descripci�n de Grupo","dsGpoUsr","",true,true,100,80,"",true,true,30)%>
		<%=MyUtil.ObjChkBox("Es Administrador","Administrador","",true,true,350,80,"","Si","No","")%>
		<%
	}			 
        rs.close();
        rs=null;
        
        %><%=MyUtil.DoBlock("Detalle del Grupo de Usuario",0,70)%>
	<%=MyUtil.GeneraScripts()%>
        
</body>
</html>
