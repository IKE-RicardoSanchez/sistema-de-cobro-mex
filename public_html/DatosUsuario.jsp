<%@page contentType="text/html; charset=iso-8859-1" import="Utilerias.UtileriasBDF,Seguridad.SeguridadC"%>
<html>
<head><title></title></head>
<link href="StyleClasses/Global.css" rel="stylesheet" type="text/css">
<link href="StyleClasses/StyleC.css" rel="stylesheet" type="text/css">

<body topmargin="2" leftmargin="5" background="Imagenes/bgMenu.jpg" bgproperties="fixed">
		<script src='Utilerias/Util.js'></script>
<table class="Table" width='220' cellspacing="0" cellpadding="0">
<tr ><td class="TTable" colspan="2" >Datos Generales del Usuario</td></tr>

<%	if (session.getAttribute("AccesoId")==null || session.getAttribute("AccesoId").toString().compareToIgnoreCase("0")==0)
        {%>
                Debe iniciar sesion
            <script>
                top.location.href='index.html';
            </script>
        <%
            return;
        }
	if (SeguridadC.verificaRequest(request.getQueryString())==false){
            response.sendRedirect("ErrorPage.jsp");
            return;
	}

	String strclUsr ="0";
	if (session.getAttribute("clUsrApp")!=null){
            strclUsr = session.getAttribute("clUsrApp").toString();
	}else{%>
                Debe iniciar sesión
        <%
            return;
	}
        %><%--if (SeguridadC.verifica__HorarioC(Integer.parseInt(strclUsr))!=true)
	{
		%>Fuera de Horario<% return;
	} --%>

	<tr><td class='FTable' colspan='2'>Usuario:&nbsp<%=session.getAttribute("NombreUsuario").toString()%></td>
	<tr><td class='FTable' colspan='2'>Inicio:&nbsp<%=session.getAttribute("FechaInicio").toString()%></td></tr>

	<input type='hidden' id='clUsrApp' name='clUsrApp' value='<%=session.getAttribute("clUsrApp").toString()%>'>
        <tr><td align="left"><input class='cBtn' type='button' value=' Contraseña' onClick='fnCambiaPwd();'></input></td>
	<td align="right"><input class='cBtn' type='button' value='Salir Sistema' onClick='fnSalir();'></input></td></tr>
	<!--<tr><td colspan="2" align="right"><input class='cBtn' type='button' value='Correo' onClick='fnCorreo();'></input></td></tr>-->
</table>
</p>
	<div id="loading" class="loading-invisible" style="position:absolute; top:0px; width:250px;"/>

    <script>
	function fnSalir() {
            top.document.body.style.filter='gray';
            if (confirm('¿Estas seguro de finalizar tu sesión?')){
                top.location.href='FinSesion.jsp';
                return true;
            }else{
                top.document.body.style.filter='';
                return false;
            }
	}

         function fnCambiaPwd() {
            WSave=window.open('Seguridad/CambiaPwd.jsp?clUsrApp=' + document.all.clUsrApp.value,'WSave','resizable=yes,menubar=0,status=1,toolbar=0,height=270,width=370,screenX=1,screenY=1');
                if (WSave != null) {
                    if (WSave.opener == null)
                        WSave.opener = self;
                }
        }

         function fnCorreo(){
            WSave=window.open('IngresaCorreo.jsp?clUsrApp=' + document.all.clUsrApp.value,'WSave','resizable=yes,menubar=0,status=1,toolbar=0,height=270,width=370,screenX=1,screenY=1');
                if (WSave != null) {
                    if (WSave.opener == null)
                            WSave.opener = self;
                }
         }
    </script>
</body>
</html>

