<%-- 
    Document   : statusCobro
    Created on : 21/03/2013, 04:26:23 PM
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
    <%      System.out.println("Configuracion ...");
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
         String proyecto="", info="", parametro="", usr="0";
         int status=0, menu=0;


         if(request.getParameter("user")!=null)
             {  usr= request.getParameter("user").toString();       }

         if(request.getParameter("cuenta")!=null)
             {  proyecto=request.getParameter("cuenta").toString();}
         else
             {  proyecto= session.getAttribute("PermisoConf").toString();   }

         if(request.getParameter("status")!=null)
             {  status= Integer.parseInt(request.getParameter("status").toString()); }

         if(request.getParameter("menu")!=null)
             {  menu= Integer.parseInt(request.getParameter("menu").toString());    }
                
         System.out.println(" "+proyecto);

        String StrclpaginaWeb = "280";
        session.setAttribute("clpaginawebp", StrclpaginaWeb);

        MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));

        StringBuffer SQL= new StringBuffer();
        StringBuffer Config= new StringBuffer();

        if(!proyecto.equals("vacio"))
            {
        SQL.append("sp_tmkgcA_Configuracion '").append(proyecto).append("',").append(status).append(",").append(usr).append(",").append(menu);
        System.out.println(SQL.toString());
            if(menu==0)
                {
                    UtileriasBDF.rsTableNP(SQL.toString(), Config);
    
     %>                 <center> <h3>Configuración de Cuentas</h3><br> <%=Config%> </center>                        <%} //IF proyecto=''
        else if(menu==1)
            {   ResultSet conf= UtileriasBDF.rsSQLNP(SQL.toString());

                    while(conf.next())
                    {    info= conf.getString("informacion").toString();
                         parametro= conf.getString("parametro").toString();

                            if(info==null ||info.equals("ERR"))
                                { %>            <h1> Error de Nulos</h1>                    <%}
                             else{%>            <%=info%>                   <%}

                             if(parametro.equals("ERR"))
                                 {  parametro=""; }
                     }
            }
                SQL.delete(0, SQL.length());
                Config.delete(0, Config.length());
                Config=null;
                SQL=null;
                info="";
              }
              else { %>  <h4> Error de Proyecto</h4>     <%}
            %>
    <script>
        function cuenta(cu)
        {   location.href="Configuracion.jsp?cuenta="+cu.id+"&status="+cu.name+"&user=<%=usr%>&menu=1";          }

        function recolecta()
            {   
                //window.showModalDialog("../LoginConf.jsp", "form", "dialogWidth:800px; dialogHeight:900px; center:yes");
                if(confirm('Realizar los cambios de Configuración?'))
                 {
                     location.href="ActualizaConf.jsp?cuenta=<%=proyecto%><%=parametro%>&user=<%=usr%>";
                 }
            }
    </script>
</body>
</html>
