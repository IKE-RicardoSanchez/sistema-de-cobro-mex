<%-- 
    Document   : SubirRespuesta
    Created on : 15/05/2013, 09:55:47 AM
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
                  background-position:top center;"
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

                String proyecto="''", complemento="''", message="";

                if(request.getParameter("message")!=null)
                    {   message= "<font color=\"red\"> Se debe seleccionar un archivo</font>"; }

                StringBuffer SQL = new StringBuffer();
                    SQL.append("sp_tmkgcA_cargaRespuesta ").append(proyecto).append(",").append(complemento);

                ResultSet query= UtileriasBDF.rsSQLNP(SQL.toString());

                    while(query.next())
                        {
                            complemento= query.getString("Descripcion").toString();
          %>

          <script>
              ret();
              function ret(){ location.href="../CargaBD/Upload.jsp"; }
          </script>
          <div style="position:absolute; z-index:3; left:30px; top:50px;">
              <!--<img src="../Imagenes/cargabase2.png" alt="cargabase" height="140" width="120"/>-->
          </div>
          <br><br><center> <h3>Carga Respuesta - Cobranza</h3><br> [Solamente Respuestas faltantes] <br>
                <%=message%><br>   <%=complemento%>

               <input type="button" id="AgregaLote" disabled="true" style="text-align:center;" onclick="CargaFile()" value="Lote: "/>
               <br><br>
               <img id="carga" alt="carga" src="../Imagenes/cargando.gif" style="visibility:hidden">
                 
          </center>
          <%            }
                query=null;    %>

                <script>
                    function CargaFile(){
                        var x= document.getElementById("menuCB").selectedIndex;
                        var y= document.getElementById("menuCB").options;
                        //alert(x+' kk '+ y[x].value);
                            document.getElementById("AgregaLote").value="Lote: "+ y[x].value;
                            document.getElementById("AgregaLote").disabled="false";

                            document.getElementById("menuCB").disabled="true";
                            document.getElementById("carga").style.visibility="visible";
                            location.href="CargaRespuesta.jsp?cuenta="+ y[x].id +"&nombre="+ y[x].label +"&lote="+ y[x].value +"";
                    }
                </script>
    </body>
</html>
