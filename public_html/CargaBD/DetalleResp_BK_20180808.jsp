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
         <% System.out.println("Detalle Resp...");
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
          Fuera de Horario DetResp </strong></font></em></div>
          <% return;
         }

//********         session.setAttribute("proyecto", "HDEBI");

        if(session.getAttribute("proyecto")!=null)
         {
                System.out.println("Cuenta: "+session.getAttribute("proyecto"));

            String StrclpaginaWeb = "280";
            session.setAttribute("clpaginawebp", StrclpaginaWeb);

            MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));

            StringBuffer StrSql = new StringBuffer();
                System.out.println(StrSql.toString());
            StrSql.append("sp_tmkgcA_ValidaCobroMx '").append(session.getAttribute("proyecto")).append("', 7");
            //StringBuffer StrSalida = new StringBuffer();
            ResultSet rs= UtileriasBDF.rsSQLNP(StrSql.toString());

            String total, acep, rech;
                if(rs.next()){

                    total= rs.getString("Total").toString();
                    acep= rs.getString("Aceptado").toString();
                    rech= rs.getString("Rechazado").toString();
          %>
          <br><br> <center> <h1><b>Detalle de la Respuesta de cobro <%= rs.getString("nombre").toString()%> </b></h1>
              <br><br><br>
              <table border="0">
                  <tr><th width="200">Secuencial</th><td width="250" align="left"><%=rs.getString("id").toString()%></td>
                  <tr><th width="200">Total de Operaciones:</th><td align="left"><%=total%></td>
              </table>
          <br>
          <table border="1">
                  <tr><th width="200">Aceptadas:</th><td align="left"><%=acep%></td>
                      <th width="200">Rechazadas</th><td align="left"><%=rech%></td>
                  <tr><th width="200">Monto Aceptadas:</th><td align="left">$<%=rs.getString("MontoAcep").toString()%></td>
                      <th width="200">Monto Rechazadas</th><td align="left">$<%=rs.getString("MontoRecha").toString()%></td>
              </table>         
                      <br><br><%=rs.getString("Observaciones").toString()%>
            <br><br>
            <input type="button" value="Regresar" onclick="location.href='../cobros/StatusCobro.jsp';" class="cBtn">
          </center>
              <script>
                    unblock();
              </script>
          

          <%        }
              rs.close();
              StrSql.delete(0, StrSql.length());
                StrSql= null;
          } else {
         %>
         <h1 class="btm"> <font class="cssRojo">Error de Lectura -- Proyecto invalido</font></h1>
         <%  }  %>
    </body>
</html>
