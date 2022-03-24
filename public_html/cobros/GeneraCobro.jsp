<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" import="Utilerias.UtileriasBDF,Seguridad.SeguridadC, java.sql.ResultSet,Utilerias.GeneraCobroAPI"%>
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
        String strclUsr ="0", envioAPI="", URLAPI="", URLToken="", UserTokenValue="", PassTokenValue="";
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

//******* Valida Ultimo Lote Creado
        StringBuffer StrSql = new StringBuffer();
            System.out.println(StrSql.toString());
        StrSql.append("sp_tmkgcA_ValidaCobroMx '").append(session.getAttribute("proyecto")).append("', 6");
            System.out.println(StrSql.toString());
        //StringBuffer StrSalida = new StringBuffer();
        ResultSet rs= UtileriasBDF.rsSQLNP(StrSql.toString());

            while(rs.next())
                {
                    session.setAttribute("lote", rs.getString("id").toString());
                 }
//*******  Fin Valida si se tiene que enviar datos a API

//******* Valida si se tiene que enviar datos a API
            StringBuffer StrSql2 = new StringBuffer();
            StrSql2.append("sp_tmkgcA_ValidaEnvioAPI '").append(session.getAttribute("proyecto")).append("'");
                System.out.println(StrSql2.toString());
            ResultSet rs2= UtileriasBDF.rsSQLNP(StrSql2.toString());

            while(rs2.next())
                {
                    envioAPI= rs2.getString("EnvioAPI").toString();
                    
                    URLAPI= rs2.getString("URLAPI").toString();
                    URLToken= rs2.getString("URLToken").toString();
                    UserTokenValue= rs2.getString("UserTokenValue").toString();
                    PassTokenValue= rs2.getString("PassTokenValue").toString();
                 }
            System.out.println("EnvioAPI: ."+envioAPI+"."+"\n Ruta API: "+URLAPI);
//*******  Fin Valida si se tiene que enviar datos a API

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
    <%          if (envioAPI.contentEquals("1")){
                    System.out.println("Se van a enviar datos al API: "+session.getAttribute("lote").toString()+" , "+session.getAttribute("proyecto").toString());
                    new GeneraCobroAPI(Integer.parseInt(session.getAttribute("lote").toString()),session.getAttribute("proyecto").toString(),URLAPI,URLToken,UserTokenValue,PassTokenValue);
                     //new GeneraCobroAPI(1,"BJPC","http://172.21.16.76:9220/api/banbajio/cobro/loteCobro","http://172.21.16.76:9220/authenticate","usrBBCobros","a9ˇ8an!1oXm");
                    System.out.println("Se enviaron datos al API");
                }
    %>
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