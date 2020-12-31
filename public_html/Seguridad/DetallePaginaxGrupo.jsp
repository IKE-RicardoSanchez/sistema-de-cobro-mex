<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.ResultSet,Seguridad.SeguridadC,Utilerias.UtileriasBDF" errorPage="" %>
<html>
<head>
<title></title>
</head>
<body class="cssBody">
<link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
<jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
<script src='../Utilerias/Util.js' ></script>
<script src='../Utilerias/UtilServicio.js' ></script>

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
        
    String StrclGpoUsr = "0";
    String StrclPaginaWebL = "0";
    StringBuffer StrSql = new StringBuffer();
    
    if (request.getParameter("clGpoUsr") != null)
    {
      //out.println("SI TRAIGO PARAMETRO GRUPO");
      StrclGpoUsr = request.getParameter("clGpoUsr");
      //out.println(StrclGpoUsr);      
    }    
        
    if (request.getParameter("clPaginaWeb") != null)
    {
      StrclPaginaWebL = request.getParameter("clPaginaWeb");
    }
    
    StrSql.append("select clGpoUsr, dsGpoUsr From tmkgcA_cGpoUsr  Where clGpoUsr=").append(StrclGpoUsr);
    ResultSet rsGpo = UtileriasBDF.rsSQLNP( StrSql.toString());
    StrSql.delete(0,StrSql.length());
    
    if (rsGpo.next()){};
    
       StrSql.append("select G.clGpoUsr, G.dsGpoUsr, P.clPaginaWeb, rtrim(P.NombreLogicoWeb), A.Alta, A.Baja, A.Cambio, A.Consulta from tmkgcA_AccesoGpoxPag A ");
       StrSql.append(" inner join tmkgcA_cGpoUsr G on G.clGpoUsr = A.clGpoUsr ");
       StrSql.append(" inner join tmkgcA_cPaginaWeb P on P.clPaginaWeb = A.clPaginaWeb ");
       StrSql.append(" Where A.clGpoUsr=").append(StrclGpoUsr).append(" and A.clPaginaWeb = ").append(StrclPaginaWebL);
       
       ResultSet rs = UtileriasBDF.rsSQLNP( StrSql.toString());
       StrSql.delete(0,StrSql.length());
       
       String StrclPaginaWeb = "7";
       session.setAttribute("clPaginaWebP",StrclPaginaWeb);

       MyUtil.InicializaParametrosC(7,Integer.parseInt(StrclUsrApp));    // se checan permisos de alta,baja,cambio,consulta de la pag. DETALLE PAGINA x GRUPO    
       %><%=MyUtil.doMenuAct("../servlet/Utilerias.EjecutaAccion","fnLlenaPaginas()")%>
       <INPUT id='URLBACK' name='URLBACK' type='hidden' value='<%=request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/") + 1)%>DetallePaginaxGrupo.jsp?'>
       
       <%
         if (rs.next()) {
            //out.println("Si traigo pagina"); 
            StrSql.append("select ").append(rs.getString(3)).append(", '").append(rs.getString(4)).append("'"); 
            // El siguiente campo LLAVE no se mete con MyUtil.ObjInput
            %><INPUT id='clGpoUsr' name='clGpoUsr' type='hidden' value='<%=rs.getString("clGpoUsr")%>'>
            <%=MyUtil.ObjInput("Grupo","dsGpoUsr",rsGpo.getString(2),false,false,100,70,rsGpo.getString(2),true,true,20)%>
            <%=MyUtil.ObjComboC("Pagina","clPaginaWeb",rs.getString(4),true,false,250,70,"",StrSql.toString(),"","",60,true,true)%>
            <%=MyUtil.ObjChkBox("Alta","Alta",rs.getString(5),true,true,100,120,"0","SI","NO","")%>
            <%=MyUtil.ObjChkBox("Baja","Baja",rs.getString(6),true,true,190,120,"0","SI","NO","")%>
            <%=MyUtil.ObjChkBox("Cambio","Cambio",rs.getString(7),true,true,290,120,"0","SI","NO","")%>
            <%=MyUtil.ObjChkBox("Consulta","Consulta",rs.getString(8),true,true,390,120,"0","SI","NO","")%>
            <%
       } else {
            //out.println("No traigo pagina");           
            // El siguiente campo LLAVE no se mete con MyUtil.ObjInput
           %>
            <INPUT id='clGpoUsr' name='clGpoUsr' type='hidden' value='<%=rsGpo.getString("clGpoUsr")%>'>
            <%=MyUtil.ObjInput("Grupo","dsGpoUsr",rsGpo.getString("dsGpoUsr"),false,false,100,70,rsGpo.getString("dsGpoUsr"),true,true,20)%>
            <%=MyUtil.ObjComboC("Pagina","clPaginaWeb","",true,false,250,70,"","select 1,1","","",60,true,true)%>
            <%=MyUtil.ObjChkBox("Alta","Alta","0",true,true,100,120,"0","SI","NO","")%>
            <%=MyUtil.ObjChkBox("Baja","Baja","0",true,true,190,120,"0","SI","NO","")%>
            <%=MyUtil.ObjChkBox("Cambio","Cambio","0",true,true,290,120,"0","SI","NO","")%>
            <%=MyUtil.ObjChkBox("Consulta","Consulta","0",true,true,390,120,"0","SI","NO","")%>
            <%
        }
         rs.close();
         rs=null;
         rsGpo.close();
         rsGpo=null;
         
        %><%=MyUtil.DoBlock("Detalle de Permisos por Página",80,0)%>
        <%=MyUtil.GeneraScripts()%>
 

</body>
</html>
