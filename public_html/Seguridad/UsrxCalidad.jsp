<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="Seguridad.SeguridadC,com.ike.tmk.DAOUsuarioCalidad,com.ike.tmk.UsuarioCalidad,com.ike.tmk.ValidaGpoUsrCalidad" errorPage="" %>
<html>
    <head><title>Agrega usuarios a proyectos de calidad</title>
        <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
    </head>
    <body class="cssBody">
        <%-- <jsp:useBean id="beanInstanceName" scope="session" class="beanPackage.BeanClassName" /> --%>
        <%-- <jsp:getProperty name="beanInstanceName"  property="propertyName" /> --%>
        <jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj" /> 
        <script src='../Utilerias/Util.js'></script>
        <script src='../Utilerias/UtilMask.js'></script>
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
        String StrclUsrAppParam="0";
        String StrclPaginaWeb="0";
        String strclProyecto="0";
        DAOUsuarioCalidad daos=null;
        UsuarioCalidad usuarioCalidad=null;
        ValidaGpoUsrCalidad usuariogpocalidad=null;
        
        
        if (session.getAttribute("clUsrApp")!= null) {
            StrclUsrApp = session.getAttribute("clUsrApp").toString();
       	}else{
            %>Debe iniciar sesión<%
            return; 
	}
            
        if (session.getAttribute("clUsrAppParam")!= null){
            StrclUsrAppParam= session.getAttribute("clUsrAppParam").toString();
        }
        
        
        
        if (request.getParameter("strclProyecto")!=null){
            strclProyecto = request.getParameter("strclProyecto").toString();
        }
        
        if (SeguridadC.verificaHorarioC(Integer.parseInt(StrclUsrApp)) != true) {
        %>
        Fuera de Horario
        <% 
        return;
        }
        if (StrclUsrApp.compareToIgnoreCase("0")!=0) {
            daos = new DAOUsuarioCalidad();
            usuariogpocalidad =  daos.getUsrxGpoCalidad(StrclUsrAppParam);
            }
        String StrPertenece=usuariogpocalidad!=null ? usuariogpocalidad.getStrPertenece() : "";
        if (StrPertenece.equalsIgnoreCase("0")) {
        %>
        <script>alert("No esta asigando a un grupo de calidad")</script>
        <%
        return;
        } else{
            usuarioCalidad =  daos.getUsrCalidad(StrclUsrAppParam,strclProyecto);
            //return;
        %>
        <script>fnOpenLinks()</script> 
        <%
        StrclPaginaWeb = "114";
        MyUtil.InicializaParametrosC(114,Integer.parseInt(StrclUsrApp));    // se checan permisos de alta,baja,cambio,consulta de esta pagina
        session.setAttribute("clPaginaWebP",StrclPaginaWeb);
        %>
        <%=MyUtil.doMenuAct("../servlet/Utilerias.EjecutaAccion","","")%>
        <INPUT id='URLBACK' name='URLBACK' type='hidden' value='<%=request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/") + 1)%>../servlet/Utilerias.Lista?P=113&I=0&StrclUsrAppParam='+ StrclUsrAppParam>
               <%=MyUtil.ObjInput("Clave de Usuario","clUsrApp",usuarioCalidad!=null ? usuarioCalidad.getStrclUsrApp() : StrclUsrAppParam,false,false,20,80,usuarioCalidad!=null ? usuarioCalidad.getStrclUsrApp() : StrclUsrAppParam,false,false,20)%>
               <%=MyUtil.ObjComboC("Proyecto","clProyecto",usuarioCalidad!=null ? usuarioCalidad.getStrdsproyecto().trim() : "",true,false,250,80, usuarioCalidad!=null ? usuarioCalidad.getStrdsproyecto().trim() : "","SELECT clProyecto, dsProyecto FROM TmkcProyecto","","",40,true,true)%>
               <%=MyUtil.ObjChkBox("Administrador de Calidad","AdminCalidad",usuarioCalidad!=null ? usuarioCalidad.getStrAdmincalidad() : "",true,false,20,130,"0","SI","NO","")%>             
               <%=MyUtil.ObjChkBox("Operador de Calidad","cTipoOperador",usuarioCalidad!=null ? usuarioCalidad.getStrctipoOperador() : "",true,false,250,130,"0","SI","NO","")%> 
               <%=MyUtil.DoBlock("Usuario de Calidad x Proyecto",100,25)%>
               <%
               StrclUsrApp=null;
               StrclPaginaWeb=null;
               daos=null;
               usuarioCalidad=null;
               usuariogpocalidad=null;
               %>
               <%=MyUtil.GeneraScripts()%>
               <%}%>
           </body>
</html>