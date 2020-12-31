<%-- 
    Document   : AdminDetalle
    Created on : 18/07/2013, 06:20:34 PM
    Author     : rarodriguez
--%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java"
         import="java.sql.ResultSet,Utilerias.UtileriasBDF,Seguridad.SeguridadC" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
        <link href="../StyleClasses/StyleC.css" rel="stylesheet" type="text/css">
        <title>Administ</title>
    </head>
    <body class="cssBody">
        <jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
        <script>
            window.parent.adminmenu.location.reload();
        </script>
        <%

        String clUsrApp = "0";
        String StrclPaginaWeb = "0";

        if (session.getAttribute("clUsrApp") != null) {
            clUsrApp = session.getAttribute("clUsrApp").toString();
        }else{
            %>Debe iniciar sesión
            <script>
                setTimeout("top.location.href='../index.html';", 3000);
             </script> <%
            return;
        }

        StrclPaginaWeb = "281";
        session.setAttribute("clPaginaWebP", StrclPaginaWeb);
        MyUtil.InicializaParametrosC(Integer.parseInt(StrclPaginaWeb), Integer.parseInt(clUsrApp));

        String complemento="6", detalle="", resp="", block="";
        StringBuffer SQL = new StringBuffer();

        %>      <h4 align="left">Detalle de Usuarios.</h4>      <%
            if(request.getParameter("usr")!=null)
            {   complemento= request.getParameter("usr").toString();
                    //System.out.println(complemento);
            }

            if(request.getParameter("det")!=null)
                {detalle= ","+request.getParameter("det").toString();

            if(request.getParameter("block")!=null)
            {  block= ","+ request.getParameter("block").toString(); }


                    SQL.append("sp_tmkgcA_Administ_SCA ").append(complemento).append(detalle).append(block);
                    System.out.println(SQL.toString());

                ResultSet rs= UtileriasBDF.rsSQLNP(SQL.toString());

                if(rs.next())
                {
                    resp= rs.getString("detalle").toString();
                    }


        %>

            <%=resp%>

         <%

            if(rs!=null)
                rs.close();

              rs=null;
            }%>
        

    </body>
</html>
