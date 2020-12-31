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
              
        
    String StrclUsrApp = "0";
    String StrclUsrAppParam = "0";

    if (session.getAttribute("clUsrApp")!= null){
        StrclUsrApp= session.getAttribute("clUsrApp").toString(); 
    }else{
        %>Debe iniciar sesión<%
        return; 
    }
        
        if (session.getAttribute("clUsrAppParam")!= null){
        StrclUsrAppParam= session.getAttribute("clUsrAppParam").toString(); 
    }
    if (SeguridadC.verificaHorarioC(Integer.parseInt(StrclUsrApp)) != true){
        %>Fuera de Horario<%
        return;
    }
    
    String strclGpoUsr = "0";
    if (request.getParameter("clGpoUsr")!= null){
        strclGpoUsr = request.getParameter("clGpoUsr").toString(); 
    }
    StringBuffer StrSQL = new StringBuffer();
    StrSQL.append("select UxG.clUsrApp, UxG.clGpoUsr, G.dsGpoUsr ");
    StrSQL.append("from tmkgcA_UsrxGpo UxG ");
    StrSQL.append("inner join tmkgcA_cGpousr G on (G.clGpoUsr = UxG.clGpoUsr) ");
    StrSQL.append("Where UxG.clGpoUsr = ").append(strclGpoUsr).append(" and UxG.clUsrApp = ").append(StrclUsrAppParam);
    
    ResultSet rs = UtileriasBDF.rsSQLNP( StrSQL.toString());
    StrSQL.delete(0,StrSQL.length());
    
    String StrclPaginaWeb = "71";
    session.setAttribute("clPaginaWebP",StrclPaginaWeb);
    
    MyUtil.InicializaParametrosC(71,Integer.parseInt(StrclUsrApp)); 
    %><%=MyUtil.doMenuAct("../servlet/Utilerias.EjecutaAccion","")%>
    <INPUT id='URLBACK' name='URLBACK' type='hidden' value='<%=request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/") + 1)%>UsrxGpo.jsp?'>
    <%
    if (rs.next()) {
        %><INPUT id='clUsrApp' name='clUsrApp' type='hidden' value='<%=rs.getString("clUsrApp")%>'>
        <%=MyUtil.ObjComboC("Grupo","clGpoUsr",rs.getString("dsGpoUsr"),true,true,50,100,"","select clGpoUsr, dsGpoUsr from tmkgcA_cGpoUsr order by dsGpoUsr","","",30,true,true)%>
        <%
    } else {
        %><INPUT id='clUsrApp' name='clUsrApp' type='hidden' value='<%=StrclUsrAppParam%>'>
        <%=MyUtil.ObjComboC("Grupo","clGpoUsr","",true,true,50,100,"","select clGpoUsr, dsGpoUsr from tmkgcA_cGpoUsr order by dsGpoUsr","","",30,true,true)%>
        <%
    }
    rs.close();
    rs=null;
    
    %><%=MyUtil.DoBlock("Grupos")%>
    <%=MyUtil.GeneraScripts()%>
</body>
</html>
