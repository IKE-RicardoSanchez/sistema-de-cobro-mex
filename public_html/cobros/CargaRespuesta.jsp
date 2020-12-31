<%-- 
    Document   : CargarRespuesta
    Created on : 16/05/2013, 01:51:32 PM
    Author     : rarodriguez
--%>

<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage=""
         import="Utilerias.UtileriasBDF,Seguridad.SeguridadC, java.sql.SQLException, Utilerias.Respuesta, java.util.regex.*"%>
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

        <p>&nbsp;</p>        <p>&nbsp;</p>
        <div align="center"><em><font color="#000066" size="3" face="Verdana, Arial, Helvetica, sans-serif"><strong>Fuera de Horario </strong></font></em></div>

          <% return;
         }
                String StrclpaginaWeb = "280";
                session.setAttribute("clpaginawebp", StrclpaginaWeb);

                MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));

                String proyecto="", nombre="", lote="", link=".";
                Respuesta file =new Respuesta();
                //file=null;
                
                if(request.getParameter("cuenta")!=null)
                    {
                        proyecto= request.getParameter("cuenta").toString(); }

                 if(request.getParameter("nombre")!=null)
                    {
                        nombre= request.getParameter("nombre").toString(); }

                if(request.getParameter("lote")!=null)
                    {
                        lote= request.getParameter("lote").toString(); 
                        Pattern valido=null;
                        Matcher m=null;
                        boolean match=false;
                           try{
                                   link= file.actionFile(proyecto, Integer.parseInt(lote), 0);
                                        //System.out.println("\taF: "+link);

                                valido = Pattern.compile("Archivo No valido|Error");
                                   m = valido.matcher(link);

                                match=m.find();

                                if(!match)
                                {
                                             try{
                                                    link= file.CargaTabla(link, "BMXD", 1736, 0);
                                            }catch(SQLException i){ System.out.println("Carga "+i); }
                                             
                                                     if(link.equals("Ok"))
                                                    {
                                                        link="Procesando";
                                                        link= file.ProcesaTabla(proyecto, Integer.parseInt(lote));
                                                    }
                                }
                              }
                            catch(SQLException e) { link="Error"; System.out.println("ProcesaTablaJSP "+e); System.out.println("link: "+link); }
                          finally {
                                        try {
                                                if(file!=null)
                                                    {   file=null;
                                                        System.out.println("Nullea fileJSP");
                                                    }
                                                valido=null;
                                                    if(match){ m.end(); }
                                                m=null;
                                               System.out.println("Nullea Regex");
                                         }catch(Exception e){ System.out.println("Nullea file: "+e); }
                                    }
                    }
               
                 %>
 
            <script>
                setTimeout(redir(), 1000);
                    function redir(){
                       // alert("time");
                       location.href="MuestraRespuesta.jsp?cuenta=<%=proyecto%>&nombre=<%=nombre%>&lote=<%=lote%>&link=<%=link%>";
                        }

                      redir();
                </script>
    </body>
</html>
