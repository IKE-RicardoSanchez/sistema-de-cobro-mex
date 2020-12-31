<%-- 
    Document   : ActualizaConf
    Created on : 14/05/2013, 12:28:39 PM
    Author     : rarodriguez
--%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" import="Utilerias.UtileriasBDF,Seguridad.SeguridadC,java.sql.ResultSet,java.util.StringTokenizer"%>
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
          Fuera de Horario </strong></font></em></div>
          <% return;
         }
                String StrclpaginaWeb = "280";
                session.setAttribute("clpaginawebp", StrclpaginaWeb);

                MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));

                    String proyecto="", cambios="";
                    StringBuffer actualiza = new StringBuffer();

                if(request.getParameter("user")!=null)
                    cambios= request.getParameter("user").toString();

                if(request.getParameter("cuenta")!=null)
                     {   proyecto= request.getParameter("cuenta").toString(); }

                    if(cambios.equals("0"))
                        {   String activo="", preval="", mesG="";

                                activo= request.getParameter("activo").toString();
                                    if(activo.equals("true"))
                                        {  activo="1"; } else { activo="0"; }

                                preval= request.getParameter("prevalidacion").toString();
                                    if(preval.equals("true"))
                                        {preval="1"; } else { preval="0"; }

                                mesG= request.getParameter("mesg").toString();

                                

                               cambios= ",0,0, '- Activo: "+activo+" - Preval: "+preval+" - MGratis: "+mesG+"'";
                            
                                actualiza.append("sp_tmkgcA_Actualiza_Configuracion 0,").append(strclUsr).append(",'").append(proyecto)
                                        .append("',").append(activo).append(",").append(preval).append(",").append(mesG).append(cambios);
                                System.out.println(actualiza);

                               ResultSet ca= UtileriasBDF.rsSQLNP(actualiza.toString());

                                    if(ca.next())
                                       {     proyecto= ca.getString("salida").toString(); }

                                if(ca!=null)
                                    ca.close();

                                ca=null;
                                cambios="0";
                                
                        } // IF CAMBIOS 0
                    else if(cambios.equals("1"))
                        {       String monto="", ike="", banco="";
                                ResultSet co=null;

                                ike= request.getParameter("ike").toString();
                                    if(ike.equals("N/A"))
                                        { ike="0"; }

                                banco= request.getParameter("banco").toString();
                                    if(banco.equals("N/A"))
                                        { banco="0"; }


                            if(request.getParameter("monto")!=null)
                                { monto= request.getParameter("monto").toString(); }

                                         StringTokenizer token= new StringTokenizer(monto.replace(";", " "));

                                while(token.hasMoreTokens())
                                {   monto= token.nextToken();

                                    StringTokenizer SQLToken = new StringTokenizer(monto.replace(":", " "));

                                        while(SQLToken.hasMoreTokens())
                                        {
                                            actualiza.append("sp_tmkgcA_Actualiza_Configuracion 1,").append(strclUsr+",'").append(proyecto+"','")
                                                    .append(SQLToken.nextToken()+"','").append(SQLToken.nextToken()+"'");
                                            //System.out.println(actualiza);

                                             co= UtileriasBDF.rsSQLNP(actualiza.toString());

                                                    if(co.next())
                                                    {     proyecto= co.getString("salida").toString(); 
                                                          System.out.println(proyecto);
                                                    }

                                                      if(proyecto.equals("Error")==true || proyecto.equals("Proyecto")==true)
                                                        {   break;  }

                                            actualiza.delete(0, actualiza.length());
                                        }
                                    if(proyecto.equals("Error")==true || proyecto.equals("Proyecto")==true)
                                                        {   break;  }
                                }//*/


                                 if(proyecto.equals("Error")==false || proyecto.equals("Proyecto")==false)
                                 {
                                           co=null; actualiza.delete(0, actualiza.length());

                                           cambios="- Ike: "+ike+" - Banco: "+banco+"'";
                                            actualiza.append("sp_tmkgcA_Actualiza_Configuracion 1,").append(strclUsr+",'").append(proyecto)
                                                    .append("','','','',").append(ike+",").append(banco+",'").append(cambios);
                                                        System.out.println(actualiza);
                                            co= UtileriasBDF.rsSQLNP(actualiza.toString());

                                                            if(co.next())
                                                            {     proyecto= co.getString("salida").toString();
                                                                  System.out.println(proyecto);
                                                            }
                                                    actualiza.delete(0, actualiza.length());
                                    }

                                if(co!=null)
                                    co.close();

                                co=null;
                                cambios="1";
                         } //ELSE IF CAMBIOS 1

                actualiza.delete(0, actualiza.length());
                actualiza=null;
               %>            <script>           <%
                if(!proyecto.equals("Error"))
                    {
                %>
                    function direc()
                        {   location.href='Configuracion.jsp?cuenta=<%=proyecto%>&status=0&user=<%=cambios%>&menu=1'; }

                     direc();
                <%}
                else if(proyecto.equals("Error"))
                    { proyecto= session.getAttribute("PermisoConf").toString(); %>
                        alert('Erro al grabar la información. Contacte a su administrador');

               <%}
                else if(proyecto.equals("Proyecto"))
                  { proyecto= session.getAttribute("PermisoConf").toString();%> 
                       alert('Erro Proyecto inexitente. Contacte a su administrador');
 
                <%}%>
                    location.href='Configuracion.jsp?cuenta=<%=proyecto%>&status=0&user=<%=cambios%>&menu=1';
               </script>
    </body>
</html>
