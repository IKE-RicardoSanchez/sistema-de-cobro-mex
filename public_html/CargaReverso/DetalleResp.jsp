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

        if(session.getAttribute("proyecto")!=null) {
                System.out.println("Cuenta: "+session.getAttribute("proyecto"));

            String StrclpaginaWeb = "280";
            session.setAttribute("clpaginawebp", StrclpaginaWeb);

            MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));

            StringBuffer StrSql = new StringBuffer();
                System.out.println(StrSql.toString());
            StrSql.append("sp_TMKGCA_ValidaReverso '").append(session.getAttribute("proyecto")).append("'");
            System.out.println(StrSql.toString());
      
            StringBuffer StrSalida = new StringBuffer();

            UtileriasBDF.rsTableNP(StrSql.toString(), StrSalida);

          %>
          <br><br> 
            <center> <h1><b>Detalle de Reverso</b></h1>
            <br><br><br>
            <% 
                if(session.getAttribute("proyecto").equals("BMXD") ){ 
                %>
                     <h1><b>Banamex Domiciliacion</b></h1>
            <%    }
                if(session.getAttribute("proyecto").equals("HDEBI") ){ 
                %>
                     <h1><b>HSBC Debito</b></h1>
            <%    }
            %>
            <br><br><br>
            <%=StrSalida%>
            <br><br>
            <input type="button" value="Regresar" onclick="location.href='../cobros/StatusCobro.jsp';" class="cBtn">
            </center>
              <script>
                    unblock();
              </script>
         <%  }  %>
    </body>
</html>
