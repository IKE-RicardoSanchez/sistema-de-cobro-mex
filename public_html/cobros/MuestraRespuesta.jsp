<%-- 
    Document   : MuestraRespuesta
    Created on : 20/05/2013, 10:45:20 AM
    Author     : rarodriguez
--%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage=""
         import="Utilerias.UtileriasBDF,Seguridad.SeguridadC, java.sql.ResultSet"%>
<jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
        <link href="../StyleClasses/styleCB.css" rel="stylesheet" type="text/css">
        <link href="../StyleClasses/StyleC.css" rel="stylesheet" type="text/css">
     <title>JSP Page</title>
    </head>
    <body class="cssBody"
          style=" background-image:url('../Imagenes/angel2.png');
                  background-repeat:no-repeat;
                  background-position:center top;"
                  >
        <%
     String StrclUsrApp ="0";
        String strclUsr ="0";
        if (session.getAttribute("clUsrApp")!=null){
          strclUsr = session.getAttribute("clUsrApp").toString();
        }else{
            %>Debe iniciar sesión
            <script>
                setTimeout("top.location.href='../index.html';", 3000);
             </script>
            <%
            return;
        }

        if (SeguridadC.verificaHorarioC(Integer.parseInt(strclUsr)) != true)
         {
       %>

        <p>&nbsp;</p>        <p>&nbsp;</p>
        <div align="center"><em><font color="#000066" size="3" face="Verdana, Arial, Helvetica, sans-serif"><strong>Fuera de Horario </strong></font></em></div>

          <% return;
         }
                String StrclpaginaWeb = "280";
                session.setAttribute("clpaginawebp", StrclpaginaWeb);

                MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));

                String proyecto="", nombre="", lote="", link=".";

                if(request.getParameter("cuenta")!=null)
                    {
                        proyecto= request.getParameter("cuenta").toString(); }

                if(request.getParameter("nombre")!=null)
                    {
                        nombre= request.getParameter("nombre").toString(); }

                if(request.getParameter("lote")!=null)
                    {
                        lote= request.getParameter("lote").toString(); }
                
                if(request.getParameter("link")!=null)
                    {
                        link= request.getParameter("link").toString(); }

                if(link.equals("No Selecionado"))
                     {%>
                     <script>
                         //setTimeout(back(), 900);

                                function back(){    location.href="SubirRespuesta.jsp?message=1";   }
                        back();
                      </script>
                     <%}
                 else if(link.equals("Ok"))
                 {
                     StringBuffer SQL = new StringBuffer();
                            link="sp_tmkgcA_ProcesaRespuestaBMXD "+lote+",'1'";
                        UtileriasBDF.rsTableNP(link, SQL);
            %>
            <center>
                <h1>Respuesta Procesada<br><%=nombre%></h1><br><br>
                    <%=SQL%>
            </center>

         <%}
            else
                {%>
                
                <center><br>
                    <h3><font color="red">Error en Respuesta</font>
                        <br><br><%=nombre%></h3><br><br>
                    <%=link%>

                  <br><br>
                  <input id="back" type="button" onclick="redir()" class="cssRojo" value="Regresar" />

                  <script>
                      function redir() { location.href='SubirRespuesta.jsp'; }
                  </script>
                </center>
                
                   <% }
                %>
    </body>
</html>
