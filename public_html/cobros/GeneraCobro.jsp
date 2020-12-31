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
    <%	System.out.println("Genera Cobro...");

        String StrclUsrApp ="0";
        String strclUsr ="0";
            if (session.getAttribute("clUsrApp")!=null){
                    strclUsr = session.getAttribute("clUsrApp").toString();
            }else{
    %>
                    Debe iniciar sesión
                <script>    setTimeout("top.location.href='../index.html';", 3000); </script>
    <%          return;
            }
    %>      <%--<% if (SeguridadC.verificaHorarioC(Integer.parseInt(strclUsr)) != true)
		{%>
		<p>&nbsp;</p>		<p>&nbsp;</p>
		<div align="center"><em><font color="#000066" size="3" face="Verdana, Arial, Helvetica, sans-serif"><strong>
			Fuera de Horario </strong></font></em></div>
			<% return;}--%>
    <%	System.out.println(" "+session.getAttribute("nombre"));

        if(session.getAttribute("proyecto")!=null){
                String StrclpaginaWeb = "280";
                session.setAttribute("clpaginawebp", StrclpaginaWeb);

        MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));

            StringBuffer cobro = new StringBuffer();
            StringBuffer sql = new StringBuffer();

               sql.append("sp_tmkgcA_CobroAutomaticoMX '").append(strclUsr).append("','").append(session.getAttribute("proyecto")).append("'");

                UtileriasBDF.rsTableNP(sql.toString(), cobro);

                if(!cobro.toString().contains("Proceso Exitoso"))
                {    System.out.println(cobro.toString());
    %>
                <br><br>
                <center> <%=cobro%><br><br>Tiempo de espera de 10 segundos. </center>
                    <script>
                        alert("Error");
                        function Error(){
                                location.href='StatusCobro.jsp';
                        }

                        setTimeout("Error();", 10000);
                    </script>
    <%          }else{%>
                        <%--    <center> <br> Cobro Exitoso<br> <%=cobro%> --%>
                </center>
                    <script>
                        function resumen() {
                            location.href='resumenCobro.jsp';
                        }

                        resumen();
                    </script>
    <%          }
            cobro.delete(0, cobro.length());
            sql.delete(0, sql.length());
        }else{
    %>
                <br><br>
                <center>    Error de Sesión<br><br>Tiempo de espera de 10 segundos. </center>
                    <script>
                        alert("Error");
                        function Error(){
                            location.href='StatusCobro.jsp';
                        }

                        setTimeout("Error();", 10000);
                    </script>
    <%}%>
</body>
</html>