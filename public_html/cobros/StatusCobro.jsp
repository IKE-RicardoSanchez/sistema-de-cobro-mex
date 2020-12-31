<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage=""
		import="Utilerias.UtileriasBDF,Seguridad.SeguridadC, Utilerias.SessionCobros"%>
<jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
        <link href="../StyleClasses/StyleC.css" rel="stylesheet" type="text/css">
        <link href="../StyleClasses/styleCB.css" rel="stylesheet" type="text/css">
        <title>JSP Page</title>
        <script src="../Utilerias/Util.js"></script>
    </head>
    <body class="cssBody">
    <script>    function refresh(){ setTimeout("location.href='StatusCobro.jsp';",3000); }  </script>
        <%--    if (session.getAttribute("AccesoId")==null){    %>Debe iniciar sesion<% return; }
                else{   if (session.getAttribute("AccesoId").toString().compareToIgnoreCase("0")==0){   %>Debe iniciar sesion<% return; }   }

		if (SeguridadC.verificaRequest(request.getQueryString())==false){   response.sendRedirect("ErrorPage.jsp"); return; }
--%>
    <%	System.out.println("Status Cobro...");

        String StrclUsrApp ="0";
        String strclUsr ="0";

        if (session.getAttribute("clUsrApp")!=null){
          strclUsr = session.getAttribute("clUsrApp").toString();
        }else{
    %>          Debe iniciar sesión
                    <script>    setTimeout("top.location.href='../index.html';", 3000); </script>
    <%          return;
	}
   %><%--if (SeguridadC.verifica__HorarioC(Integer.parseInt(strclUsr)) != true){
        %>      <center> Tiempo de espera de 10 segundos </center>
			<script>    setTimeout("refresh();", 7000)  </script>
          <% return;
        }else{--%>
   <%	String StrclpaginaWeb = "280";
        session.setAttribute("clpaginawebp", StrclpaginaWeb);
            session.setAttribute("proyecto", null);
            session.setAttribute("nombre", null);

        MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));

        StringBuffer StrSql = new StringBuffer();

                StrSql.append("sp_tmkgcA_ListaProyectoCobros_SCA '").append(session.getAttribute("PermisoConf").toString()+"'");
        StringBuffer StrSalida = new StringBuffer();

        UtileriasBDF.rsTableNP(StrSql.toString(), StrSalida);
          %>
            <center> <h1> Estatus de Cobro México </h1><br>
                <%=StrSalida%>
                <br><br>
                <table border="0">
                    <tr>
                        <td bgcolor="blue" width="20" height="10"/><td>Cobro al <font color="blue">Corriente</font></td><td width="20"/>
                        <td bgcolor="green" width="20" height="10"/><td>Hoy se <font color="green">Genera</font> el Cobro</td><td width="20"/>
                        <td bgcolor="yellow" width="20" height="10"/><td>Manda a Prevalidar con el Cliente</td>
                    </tr>
                </table>

                <table border="0">
                    <tr>
                        <td bgcolor="orange" width="20" height="10"/><td>En espera de <font color="orange">Respuesta</font></td><td width="20"/>
                        <td bgcolor="red" width="20" height="10"/><td> <font color="red"> ˇˇAlerta!! </font> Cobros anterior NO generado </td>
                    </tr>
                </table>
          </center>

        <div id="loading" class="loading-invisible">
            <table border="0" width="100%" align="center" cellpadding="0" cellspacing="0">
                    <tr><td height="500" valign="middle" class="textoPagPrecarga" align="center"><img src="../Imagenes/cargando.gif" alt="Cargando ..." vspace="5"/></td></tr>
            </table>
        </div>

        <script>
            function desactiva(id){
                    block();
                    if (confirm("Validar Cobro de "+id.name+"??"))
                    {       location.href='ValidacionCobro.jsp?cuenta='+id.id+'&nombre='+id.name+'';
                                            return true;
                    }else{
                                            unblock();
                                            top.document.body.style.filter='';
                                            return false;
                                      }
                        }

            unblock();
         </script>

          <%
                StrSql.delete(0, StrSql.length());
                StrSalida.delete(0, StrSalida.length());
           %>
    </body>
</html>
