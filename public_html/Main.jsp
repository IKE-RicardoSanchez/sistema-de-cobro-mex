    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" import="Seguridad.SeguridadC"%>

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
        String strclUsrApp = "";
//        String strFinalizaSession = "";
        if (session.getAttribute("clUsrApp")==null){
             strclUsrApp = request.getParameter("clUsr");
        }else{
            strclUsrApp = session.getAttribute("clUsrApp").toString();
        }
        
	   String ValidaSessionR="";
        
        if (session.getAttribute("ValidaSessionR")!=null){
            ValidaSessionR = session.getAttribute("ValidaSessionR").toString();
           // System.out.println("Request Valor:"+ValidaSessionR);
            session.removeAttribute("ValidaSessionR");
        }
           
     String ValidaSessionS = "0";
    
     if (session.getAttribute("ValidaSessionS")!=null){
        ValidaSessionS = session.getAttribute("ValidaSessionS").toString();
     }

       System.out.println("Valida Request Valor:"+ ValidaSessionR);
       System.out.println("Valida Session Valor:"+ ValidaSessionS);   
        
      //<<<<<<<<<<<<<<<< Valida Session de Logeo por  Usuario >>>>>>>>>>>>>  
      if  (!ValidaSessionR.equals(ValidaSessionS)){
         String redirectURL = request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/") + 1);
         response.sendRedirect(redirectURL + "index.html");
      }
       
      else {
      
     %>
     
<html>
<head>
    <title>Sistema de Cobros</title>
    <script src='Utilerias/Util.js'></script>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link href="StyleClasses/StyleC.css" rel="stylesheet" type="text/css">
   <!-- <script>window.open('MensajeCambiopwd.jsp?','newWin2','scrollbars=yes,status=yes,width=600,height=600');</script>  -->
</head>

	<frameset framespacing='0' noresize frameborder='no' id='topm' rows='70,*'>
            <frame frameborder='no' name='encabezado' noresize id='encabezado' src='FrameEncabezado.jsp' scrolling='no'/>
                <frameset framespacing='0' noresize frameborder='no' id='topPO' cols='250,*'>
                   <frameset framespacing='0' noresize frameborder='no' id='leftPO' rows='80,*,0'>
                       <frame frameborder='no' scrolling='no' name='DatosUsuario' noresize id='DatosUsuario' src='DatosUsuario.jsp' />
                        <frame frameborder='no' name='Menu' noresize id='Menu' src='Menu.jsp' />
              <!--     <frame frameborder='no' name='InfoRelacionada' noresize id='InfoRelacionada' src='InfoRelacionada.jsp?Load=0' ></frame>	-->
                    </frameset>
                   <frame frameborder='no' name='Contenido' noresize id='Contenido' src='Bienvenido.jsp' />
                  <!-- <frame frameborder='no' name='Contenido' noresize id='Contenido' src='prueba.jsp' ></frame>-->
                </frameset>
            <div id="loading" class="loading-visible" style="position:absolute; top:0px;">
                <table border="0" width="100%" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td height="500" valign="middle" class="textoPagPrecarga" align="center"><img src="Imagenes/cargando.gif" alt="Cargando ..." vspace="5"/></td>
                    </tr>
                </table>
            </div>
	</frameset>

<%--
<body class="cssBody" >
    <div id="main" style="position:absolute; z-index:1; left:0px; width:100%; height:100%; top:0px;">
        <iframe src="DivMain.jsp" name="div" height="100%" width="100%" />
    </div>
    <div id="bloqueo" style="height:100%; width:100%; left:0px; top:0px;" class="loading-invisible"/>
--%>
</html>

<% }   %>