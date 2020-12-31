<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="Seguridad.SeguridadC,java.sql.ResultSet,Utilerias.UtileriasBDF" errorPage="" %>
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
        
    	String StrcvePaginaWeb = "0";
    	String strclUsr = "";

        if (session.getAttribute("clUsrApp")!= null)
      	{
       		strclUsr = session.getAttribute("clUsrApp").toString(); 
       	}else{
            %>Debe iniciar sesión<%
            return; 
	}
        
        if (SeguridadC.verificaHorarioC(Integer.parseInt(strclUsr)) != true) {
            %> Fuera de Horario <%
            return; 
	}
        
      	if (request.getParameter("clPaginaWeb")!= null)
      	{
            StrcvePaginaWeb= request.getParameter("clPaginaWeb").toString(); 
      	}  

        StringBuffer StrSql = new StringBuffer();
        StrSql.append("SELECT P.clPaginaWeb,P.NombrePaginaWeb,P.clModulo,P.NombreLogicoWeb, coalesce(P.SentenciaRPT,'') 'SentenciaRPT', ");
        StrSql.append("coalesce(P.TituloRPT,'') 'TituloRPT',coalesce(P.PaginaDetalle,'') 'PaginaDetalle',COALESCE(P.Tabla,'') 'Tabla',");
        StrSql.append(" M.dsModulo " );
        StrSql.append(" FROM tmkgcA_cPaginaWeb P " );
        StrSql.append(" INNER JOIN cmodulo M ON(P.clModulo=M.clmodulo)");
        StrSql.append( " Where P.clPaginaWeb=").append(StrcvePaginaWeb) ;
       
       	String StrclPaginaWeb = "2";
	session.setAttribute("clPaginaWebP",StrclPaginaWeb);
       %>
        <SCRIPT>fnCloseLinks()</script>
       <%
       	MyUtil.InicializaParametrosC(2,Integer.parseInt(strclUsr)); 
        ResultSet rs = UtileriasBDF.rsSQLNP( StrSql.toString());%>
	<%=MyUtil.doMenuAct("../servlet/Utilerias.EjecutaAccion","")%>
        <INPUT id='URLBACK' name='URLBACK' type='hidden' value='<%=request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/") + 1) + "PaginasWeb.jsp?"%>'>
       <% if (rs.next()) {%>
		<INPUT id='clPaginaWeb' name='clPaginaWeb' type='hidden' value='<%=rs.getString("clPaginaWeb")%>'>
		<%=MyUtil.ObjInput("Nombre Lógico Web","NombreLogicoWeb",rs.getString("NombreLogicoWeb"),true,true,25,80,"",true,true,50)%>
		<%=MyUtil.ObjInput("Nombre Página Web","NombrePaginaWeb",rs.getString("NombrePaginaWeb"),true,true,375,80,"",true,true,50)%>
                <%=MyUtil.ObjComboC("Modulo","clModulo",rs.getString("dsModulo"),true,false,25,120,"","SELECT * FROM CMODULO ORDER BY DSMODULO","","",100,true,true)%>
		<%=MyUtil.ObjInput("Titulo","TituloRPT",rs.getString("TituloRPT"),true,true,375,120,"",true,true,40)%>                
		<%=MyUtil.ObjInput("Página Detalle","PaginaDetalle",rs.getString("PaginaDetalle"),true,true,25,160,"",false,false,40)%>
		<%=MyUtil.ObjInput("Tabla","Tabla",rs.getString("Tabla"),true,true,375,160,"",false,false,40)%>                
		<%=MyUtil.ObjInput("Sentencia","SentenciaRPT",rs.getString("SentenciaRPT"),true,true,25,200,"",false,false,40)%>
       <% }
	else{%>
		<INPUT id='clPaginaWeb' name='clPaginaWeb' type='hidden' value=''>
		<%=MyUtil.ObjInput("Nombre Lógico Web","NombreLogicoWeb","",true,true,25,80,"",true,true,50)%>
		<%=MyUtil.ObjInput("Nombre Página Web","NombrePaginaWeb","",true,true,375,80,"",true,true,50)%>                
                <%=MyUtil.ObjComboC("Modulo","clModulo","",true,false,25,120,"","SELECT * FROM CMODULO ORDER BY DSMODULO","","",100,true,true)%>
		<%=MyUtil.ObjInput("Titulo","TituloRPT","",true,true,375,120,"",true,true,40)%>                
		<%=MyUtil.ObjInput("Página Detalle","PaginaDetalle","",true,true,25,160,"",false,false,40)%>
		<%=MyUtil.ObjInput("Tabla","Tabla","",true,true,375,160,"",false,false,40)%>                
		<%=MyUtil.ObjInput("Sentencia","SentenciaRPT","",true,true,25,200,"",false,false,40)%>
	<%}%>
			 
        <%=MyUtil.DoBlock("Detalle de Páginas Web",200,0)%>
	<%=MyUtil.GeneraScripts()%>
	<% rs.close();
           rs=null;
           StrSql=null;
           strclUsr = null;
           StrclPaginaWeb = null;
          %>
</body>
</html>