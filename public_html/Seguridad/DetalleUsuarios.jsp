<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.ResultSet,Seguridad.SeguridadC,Utilerias.UtileriasBDF" errorPage="" %>

<%
    if (SeguridadC.verificaRequest(request.getQueryString())==false){ 
        response.sendRedirect("ErrorPage.jsp");
        return;
    }    
       
%>    
    
<html>
<head>
<title></title>
</head>
<body class="cssBody">
<link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
<jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
<script src='../Utilerias/Util.js'></script>
<script>
    function fnCambiaPwd() {
        WSave=window.open('CambiaPwd.jsp?clUsrApp=' + document.all.clUsrApp.value,'WSave','resizable=yes,menubar=0,status=1,toolbar=0,height=270,width=370,screenX=1,screenY=1');
        if (WSave != null) {
            if (WSave.opener == null)
                WSave.opener = self;
        }		
        //WSave.opener.focus();
    }
   function fnActualizaMenus() {
        WSave=window.open('ActualizaMenus.jsp?clUsrApp=' + document.all.clUsrApp.value,'WSave','resizable=no,menubar=0,status=1,toolbar=0,height=200,width=250,screenX=1,screenY=1');
        if (WSave != null) {
            if (WSave.opener == null)
                WSave.opener = self;
        }		
        //WSave.opener.focus();
    }

 function fnDesbloquearUsr() {
        WSave=window.open('DesbloquearUsr.jsp?clUsrApp=' + document.all.clUsrApp.value,'WSave','resizable=no,menubar=0,status=1,toolbar=0,height=200,width=250,screenX=1,screenY=1');
        if (WSave != null) {
            if (WSave.opener == null)
                WSave.opener = self;
        }		
        //WSave.opener.focus();
    }
</script>

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
    
    String StrclUsrApp = "0";
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
    
    String strclUsrAppParam = "0";
    if (request.getParameter("clUsrApp")!= null){
        strclUsrAppParam = request.getParameter("clUsrApp").toString(); 
    }

    session.setAttribute("clUsrAppParam",strclUsrAppParam);
    
    %><script>fnOpenLinks() </script><%
    
    StringBuffer StrSQL =  new StringBuffer();
    StrSQL.append("select Usr.clUsrApp, Usr.Usuario, Usr.Password, Usr.Nombre, Usr.Activo, Usr.RestringeClie , Usr.RestringeProv, Usr.FechaCambioPwd, coalesce(Usr.Agente,'') Agente , ");
    StrSQL.append("Usr.FechaUltAcceso, Usr.NumAcces, Usr.FechaAlta, TH.TipoHorario, Usr.NumEmpleado, coalesce(Usr.ExtAgente,'') ExtAgente, coalesce(Usr.PwdAgente,'') PwdAgente, coalesce(Usr.CodigoLlamada,'') CodigoLlamada ");
    StrSQL.append("from tmkgcA_cUsrApp Usr " );
    StrSQL.append("inner join cTipoHorario TH on (TH.clTipoHorario = Usr.clTipoHorario)");
    StrSQL.append("Where Usr.clUsrApp = ").append(strclUsrAppParam);

    ResultSet rs = UtileriasBDF.rsSQLNP( StrSQL.toString());
    StrSQL.delete(0, StrSQL.length());
    
    String StrclPaginaWeb = "30";
    session.setAttribute("clPaginaWebP",StrclPaginaWeb);
    
    
    MyUtil.InicializaParametrosC(30,Integer.parseInt(StrclUsrApp)); 
    %><%=MyUtil.doMenuAct("../servlet/Utilerias.EjecutaAccion","")%>
    <%--=MyUtil.doMenuAct("../../servlet/Telemarketing.RegistraLlamadaTMKPM","")--%>
    <INPUT id='URLBACK' name='URLBACK' type='hidden' value='<%=request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/") + 1)%>DetalleUsuarios.jsp?'>
    <%
    if (rs.next()) {
        %><%=MyUtil.ObjInput("Clave de Usuario","clUsr",rs.getString("clUsrApp"),false,false,50,100,"",false,false,10)%>
        <INPUT id='clUsrApp' name='clUsrApp' type='hidden' value='<%=rs.getString("clUsrApp")%>'>
        <%=MyUtil.DoBlock("Usuarios")%>
        <%=MyUtil.ObjInput("Usuario","Usuario",rs.getString("Usuario"),false,false,50,200,"",false,false,15)%>
        <%=MyUtil.ObjInput("Nombre","Nombre",rs.getString("Nombre"),true,true,145,200,"",true,true,42)%>
        <%=MyUtil.ObjInput("Apellido Paterno","ApePa","",true,false,380,200,"",true,false,40)%>
        <%=MyUtil.ObjInput("Apellido Materno","ApeMa","",true,false,605,200,"",true,false,40)%>
        
        <%=MyUtil.ObjInput("Fecha Cambio de Password","FechaCambioPwd",rs.getString("FechaCambioPwd"),false,false,320,240,"",false,false,25)%>
        <%=MyUtil.ObjComboC("Tipo de Horario","clTipoHorario",rs.getString("TipoHorario"),true,true,50,240,"","select clTipoHorario, TipoHorario from cTipoHorario","","",40,true,true)%>
        <%=MyUtil.ObjChkBox("Activo","Activo",rs.getString("Activo"),true,true,50,280,"","SI","NO","")%>
        <%=MyUtil.ObjChkBox("Restringe Proveedor","RestringeProv",rs.getString("RestringeProv"),true,true,150,280,"","SI","NO","")%>
        <%=MyUtil.ObjChkBox("Restringe Cliente","RestringeClie",rs.getString("RestringeClie"),true,true,300,280,"","SI","NO","")%>
        <%=MyUtil.ObjInput("Agente","Agente",rs.getString("Agente"),false,false,450,280,"",false,false,10)%>
        <%=MyUtil.ObjInput("Fecha de Alta","FechaAlta",rs.getString("FechaAlta"),false,false,50,320,"",false,false,20)%>
        <%=MyUtil.ObjInput("Fecha Ultimo Acceso","FechaUltAcceso",rs.getString("FechaUltAcceso"),false,false,240,320,"",false,false,25)%>        
        <%=MyUtil.ObjInput("ExtAgente","ExtAgente",rs.getString("ExtAgente"),true,true,410,320,"",false,false,10)%>
        <%=MyUtil.ObjInput("PwdAgente","PwdAgente",rs.getString("PwdAgente"),true,true,500,320,"",false,false,25)%>
        <%=MyUtil.ObjInput("CodigoLlamada","CodigoLlamada",rs.getString("CodigoLlamada"),true,true,670,320,"",false,false,25)%>
        
        <INPUT id='btnCambiaPwd' name='btnCambiaPwd' type='button' value='Cambia Password' onclick='fnCambiaPwd();'>
        <INPUT id='btnActMnu' name='btnActMnu' type='button' value='Actualizar Menús' onclick='fnActualizaMenus();'>
        <INPUT id='btnActMnu' name='btnDblUsr' type='button' value='Desbloquear Cuenta' onclick='fnDesbloquearUsr();'>
        <INPUT id='NumAcces' name='NumAcces' type='hidden' value='0'>
        <%=MyUtil.DoBlock("Detalle de Usuarios del Sistema")%><%
    } else {
        %><%=MyUtil.ObjInput("Clave de Usuario","clUsr","",false,false,50,100,"",false,false,10)%>
        <INPUT id='clUsrApp' name='clUsrApp' type='hidden' value='0'>
        <%=MyUtil.DoBlock("Usuarios")%>
        <%=MyUtil.ObjInput("Usuario","Usuario","",true,false,50,200,"",true,false,10)%>
        <%=MyUtil.ObjInput("Nombre","Nombre","",true,true,170,200,"",true,true,49)%>
        <%=MyUtil.ObjInput("Fecha Cambio de Password","FechaCambioPwd","",false,false,320,240,"",false,false,20)%>
        <%=MyUtil.ObjComboC("Tipo de Horario","clTipoHorario","",true,true,50,240,"","select clTipoHorario, TipoHorario from cTipoHorario","","",40,true,true)%>
        <%=MyUtil.ObjChkBox("Activo","Activo","",true,true,50,280,"","SI","NO","")%>
        <%=MyUtil.ObjChkBox("Restringe Proveedor","RestringeProv","",true,true,150,280,"","SI","NO","")%>
        <%=MyUtil.ObjChkBox("Restringe Cliente","RestringeClie","",true,true,300,280,"","SI","NO","")%>
        <%=MyUtil.ObjInput("Agente","Agente","",false,false,450,280,"",false,false,10)%>
        <%=MyUtil.ObjInput("Fecha de Alta","FechaAlta","",false,false,50,320,"",false,false,20)%>
        <%=MyUtil.ObjInput("Fecha Ultimo Acceso","FechaUltAcceso",rs.getString("FechaUltAcceso"),false,false,240,320,"",false,false,25)%>        
        <%=MyUtil.ObjInput("ExtAgente","ExtAgente",rs.getString("ExtAgente"),true,true,410,320,"",false,false,10)%>
        <%=MyUtil.ObjInput("PwdAgente","PwdAgente",rs.getString("PwdAgente"),true,true,500,320,"",false,false,25)%>
        <%=MyUtil.ObjInput("CodigoLlamada","CodigoLlamada",rs.getString("CodigoLlamada"),true,true,670,320,"",false,false,25)%>
        <INPUT id='NumAcces' name='NumAcces' type='hidden' value='0'>
        <%=MyUtil.DoBlock("Detalle de Usuarios del Sistema")%><%
    }%>
    <%  rs.close();
        rs=null;
        
    %>
    <%=MyUtil.GeneraScripts()%>

</body>
</html>

