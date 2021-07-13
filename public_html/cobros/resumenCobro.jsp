<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" import="Utilerias.UtileriasBDF,Seguridad.SeguridadC, java.sql.ResultSet"%>
<jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
        <link href="../StyleClasses/styleCB.css" rel="stylesheet" type="text/css">
        <title>JSP Page</title>
    </head>
    <body class="cssBody">
        <script src='../Utilerias/Util.js'></script>
         <% System.out.println("Resumen Cobro...");
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

        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <div align="center"><em><font color="#000066" size="3" face="Verdana, Arial, Helvetica, sans-serif"><strong>
          Fuera de Horario Rs </strong></font></em></div>
          <% return;
         }
         String fecha="", total="", monto="", portal="", envioAPI="";

         if(session.getAttribute("proyecto")!=null && session.getAttribute("nombre")!=null)
             {
                System.out.println("Cuenta: "+session.getAttribute("nombre"));
                System.out.println("Proyecto: "+session.getAttribute("proyecto").toString());

        String StrclpaginaWeb = "280";
        session.setAttribute("clpaginawebp", StrclpaginaWeb);

        MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));

        StringBuffer StrSql = new StringBuffer();
            System.out.println(StrSql.toString());
        StrSql.append("sp_tmkgcA_ValidaCobroMx '").append(session.getAttribute("proyecto")).append("', 6");
            System.out.println(StrSql.toString());
        //StringBuffer StrSalida = new StringBuffer();
        ResultSet rs= UtileriasBDF.rsSQLNP(StrSql.toString());

            while(rs.next())
                {
                    session.setAttribute("archivo", rs.getString("archivo").toString().replace("|", "-"));
                    fecha= rs.getString("fecha").toString();
                    total= rs.getString("total").toString();
                    monto= rs.getString("monto").toString();
                    session.setAttribute("lote", rs.getString("id").toString());
                    //session.setAttribute("concat", rs.getString("tipoC").toString());
                    portal= rs.getString("portal").toString();
                 }

            StringBuffer StrSql2 = new StringBuffer();
            StrSql2.append("sp_tmkgcA_ValidaEnvioAPI '").append(session.getAttribute("proyecto")).append("'");
                System.out.println(StrSql2.toString());
            ResultSet rs2= UtileriasBDF.rsSQLNP(StrSql2.toString());

            while(rs2.next())
                {
                    envioAPI= rs2.getString("EnvioAPI").toString();
                 }
            System.out.println("EnvioAPI: ."+envioAPI+".");

            if (envioAPI.contentEquals("1")){
                System.out.println("ENTRO");
            }else System.out.println("No entro");
          %>
          <br><br> <center> <h1><b> Resumen del cobro <%=session.getAttribute("nombre")%> </b></h1>
              <br><br>
              <table border="0">
                  <%if (envioAPI.contentEquals("0")){%>
                  <tr><td width="200">Nombre del archivo:</td><td width="250" align="left"><%=session.getAttribute("archivo")%></td>
                  <%}%>
                  <tr><td width="200">Secuencial:</td><td width="250" align="left"><%=session.getAttribute("lote")%></td>
                  <tr><td width="200">Fecha de generacion:</td><td align="left"> <%=fecha%></td>
                  <tr><td width="200">Total de Operaciones:</td><td align="left"><%=total%></td>
                  <tr><td width="200">Importe Total:</td><td align="left"><%=monto%></td>
              </table>
          </center>

              <script>
                  function download(){
                      location.href='../servlet/Utilerias.SessionCobros?C=<%=session.getAttribute("proyecto").toString()%>';
                  }
              </script>
                  <br>
                      <center>  <%if (envioAPI.contentEquals("0")){%>
                          <input type="button" value="Descargar Lote:<%=session.getAttribute("lote")%>" onclick="download()" class="cssLinkOu"/> <br><br>
                          <%--<a href="<%=portal%>" target="_blank" onclick="document.getElementById('back').style.visibility='visible';">
                          <font color="red">Portal de Envio:<br>El archivo de cobro se debe enviar por el sitio seguro de <%=nombre%> (SFTP)</font>
                          -</a>--%>
                          <%=portal%>
                          <%}else{%>
                           Proyecto Enviado via API
                          <%}%>
                          <br><br><br>
                          <input id="back" type="button" class="cssPlasmaVerde" value="Regresar" onclick="location.href='StatusCobro.jsp';"/>
                      </center>

          <%
          rs.close();
            fecha=null; total=null; monto=null;

              StrSql.delete(0, StrSql.length());
                StrSql=null;
          } else {
         %>
         <h1 class="btm"> <font class="cssRojo">Error de Lectura </font></h1>
         <%  }  %>

     <script>
         unblock();
     </script>
    </body>
</html>
