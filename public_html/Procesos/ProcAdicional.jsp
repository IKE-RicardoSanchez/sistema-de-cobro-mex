<%-- 
    Document   : ProAdicional
    Created on : 13/08/2013, 04:36:02 PM
    Author     : rarodriguez
--%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" import="Utilerias.UtileriasBDF,Seguridad.SeguridadC, java.sql.ResultSet"%>
<jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
        <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
        <link href="../StyleClasses/styleCB.css" rel="stylesheet" type="text/css">
        <link href="../StyleClasses/StyleC.css" rel="stylesheet" type="text/css">

        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lobster" />
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script>
     <title>JSP Page</title>
    </head>
    <body class="cssBody">
    <%
     String StrclUsrApp ="0";
        String strclUsr ="0";
        if (session.getAttribute("clUsrApp")!=null){
          strclUsr = session.getAttribute("clUsrApp").toString();
        }else{
            %>Debe iniciar sesión
            <script>
                setTimeout("top.location.href='../index.html';", 3000);
             </script> <%
            return;
        }

        if (SeguridadC.verificaHorarioC(Integer.parseInt(strclUsr)) != true)
         {
       %>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <div align="center"><em><font color="#000066" size="3" face="Verdana, Arial, Helvetica, sans-serif"><strong>
          Fuera de Horario </strong></font></em></div>
          <% return;
         }

        String StrclpaginaWeb = "280";
        session.setAttribute("clpaginawebp", StrclpaginaWeb);

        MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));

         String err="", text="", result="";

        StringBuffer query =new StringBuffer();
        query.append("sp_tmkgcA_ProcesosAdicionales ");

            ResultSet rs= UtileriasBDF.rsSQLNP(query.toString());

                while(rs.next())
                {
                    err= rs.getString("err").toString();
                    text= rs.getString("msg").toString();
                }

            if(request.getParameter("cuenta")!=null)
                {   String procesos="", proyecto="";
                
                    proyecto= "'"+ request.getParameter("cuenta").toString() +"'";

                    if(request.getParameter("proc")!=null)
                        {
                            procesos= ",'"+ request.getParameter("proc").toString();
                        }

                     query.delete(0, query.length());
                     //query=null;
                        query.append("sp_tmkgcA_ProcesosAdicionales ").append(proyecto);

                        if (rs!=null) {rs.close();rs=null;}

                        rs= UtileriasBDF.rsSQLNP(query.toString());

                            while(rs.next())
                            {
                                err= rs.getString("err").toString();
                                result= rs.getString("msg").toString();
                            }
                    }
        %>

        <div style="position:absolute; left:50px; top:0px;">
                Selecciona una cuenta:  <%=text%>
        </div>
        
        <div style="position:absolute; left:50px; top:40px;">
                <%=result%>
        </div>

        <script>
            function selected(cu){
                document.location.href="ProcAdicional.jsp?cuenta="+cu.value;
            }

            function procesa(cu){
                document.location.href="Upload_PA.jsp?cuenta="+cu.name+"&pss="+cu.id;
            }

            function genera(cu){
                if(confirm("seguro"))
                    {
                        document.location.href="DescargaCobro.jsp?cuenta="+cu.name+"&lote="+cu.id+"&arch=PA&tipo=pre"+cu.id;
                    }
            }

        </script>

         <div id="loading" class="loading-invisible">
            <table border="0" width="100%" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="500" valign="middle" class="textoPagPrecarga" align="center"><img src="../Imagenes/cargando.gif" alt="Cargando ..." vspace="5"/></td>
                </tr>
            </table>
        </div>
    </body>
</html>
