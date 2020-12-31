<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage=""
         import="Utilerias.UtileriasBDF,Seguridad.SeguridadC, java.sql.ResultSet"%>
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
        <script src='../Utilerias/Util.js'></script>
       <%   System.out.println("Validacion Cobro...");

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

         if(request.getParameter("cuenta")!=null && request.getParameter("nombre")!=null)// && session.getAttribute("proyecto")==null)
             {  session.setAttribute("proyecto", request.getParameter("cuenta"));
                session.setAttribute("nombre", request.getParameter("nombre"));
               System.out.println("Proy: "+session.getAttribute("nombre"));
               System.out.println("DsProyecto "+session.getAttribute("proyecto"));
               

        String StrclpaginaWeb = "280";
        session.setAttribute("clpaginawebp", StrclpaginaWeb);

        MyUtil.InicializaParametrosC(Integer.parseInt(StrclpaginaWeb),Integer.parseInt(StrclUsrApp));
        
        
        /*---Validacion para Cobros 20141111 cqv*/
        /*------------------------------------------------*/
        StringBuffer StrSql1 = new StringBuffer();

        StrSql1.append("st_TMKGCA_GeneraValidacionMX '").append(session.getAttribute("proyecto")).append("'");
         
        StringBuffer StrSalida1 = new StringBuffer();
        UtileriasBDF.rsTableNP(StrSql1.toString(), StrSalida1);
        /*------------------------------------------------*/

        StringBuffer StrSql = new StringBuffer();
            System.out.println(StrSql.toString());
        StrSql.append("sp_tmkgcA_ValidaCobroMx '").append(session.getAttribute("proyecto")).append("', 1");
        StringBuffer StrSalida = new StringBuffer();
        UtileriasBDF.rsTableNP(StrSql.toString(), StrSalida);
          %>

          <script>
              function ActivaCobro(intro){
                  if (document.getElementById(intro.id).checked){
                      if(document.getElementById("optimo").value!='0'){
                                document.getElementById("Gen").style.visibility="visible";
                      }else{ document.getElementById(intro.id).checked=0;
                                alert("No se puede generar el cobro en CEROS");
                                location.href='StatusCobro.jsp';}
                  }
                  else
                  { document.getElementById("Gen").style.visibility="hidden";
                          }
              }

              function GenerarCobro(intro){
                  block();
                  //loading.className='loading-visible';
                    if (confirm('Confirmar la Generación del Cobro <%=session.getAttribute("nombre")%>')){
                        document.getElementById(intro.id).disabled="true";
                            location.href="GeneraCobro.jsp";
                        return true;
                    }else{
                            unblock();
                            //loading.className='loading-invisible';
                            document.getElementById("CheckGen").checked=false;
                            document.getElementById(intro.id).style.visibility="hidden";
                        return false;
                    }
              }


            </script>

                            <center> <h2><b>Validación de Cobro <%=session.getAttribute("nombre")%></b></h2></center><br><br>
          <center>
              <table border="0" class="btm">
                  <tr>
                      <td><center><h4 class="VerdeValidacion">Validacion Portafolio</h4></center></td>
                  </tr>
                  <tr>
                      <td><%=StrSalida1%></td>
                  </tr>
              </table>
                      
              <table border="0" class="btm">
                 <tr>
                     <td><center><h4 class="VerdeValidacion">Detalle del Cobro Anterior.</h4></center></td>
                 </tr>
                 <tr>
                        <td><%=StrSalida%></td>
                 </tr>
              </table>
                 <br><br>
              <table border="0" >
                 <tr>
                     <td width="300"><center><h4 class="VerdeValidacion">Detalle del Cobro a Enviar.</h4></center></td><td width="50"/>
                     <td ><center><h4 class="VerdeValidacion">Detalle del Monto a Cobrar.</h4></center></td>
                 </tr>
           <%
           StrSql.delete(0, StrSql.length());
           StrSalida.delete(0, StrSalida.length());

           System.out.println(StrSql.toString());
           String valida="", anexo="";
        StrSql.append("sp_tmkgcA_ValidaCobroMx '").append(session.getAttribute("proyecto")).append("', 2");
        ResultSet rs= UtileriasBDF.rsSQLNP(StrSql.toString());

            if(rs.next())
            {
                anexo= rs.getString("anexo").toString();
                valida= rs.getString("valida").toString();

                if (valida.contains("Proceso no "))
                {
                     rs.close();
                       StrSql.delete(0, StrSql.length());
                       StrSalida.delete(0, StrSalida.length());
                   StrSql.delete(0, StrSql.length());
                   StrSalida.delete(0, StrSalida.length());
                    %>
                 <script>
                        alert("<%=anexo%>");
                        location.href='StatusCobro.jsp';
                 </script>
                    <%
                    return;
                }

            %>
                 <tr>
            <td>
                <input type="hidden" id="optimo" value="<%=valida%>"/>
                <table border="0" class="TablePlasma">
                     <tr class="cssAzul">
                        <th style="text-align:left; padding: 3px 3px 3px 3px;">Ventas Nuevas</th>
                        <th style="text-align:left; padding: 3px 3px 3px 3px;">Recurrentes</th>
                    </tr>
                    <tr class="R1Table">
                         <td style="text-align:center; padding: 3px 3px 3px 3px;"><%=rs.getString("VentasNuevas").toString()%></td>
                         <td style="text-align:center; padding: 3px 3px 3px 3px;"><%=rs.getString("RecurrentesEnviar").toString()%></td>
                    </tr>
                </table>
                <br>
            </td><td width="50"/>
                <td>
                <%}
                    rs.close();
                       StrSql.delete(0, StrSql.length());
                       StrSalida.delete(0, StrSalida.length());
                   StrSql.delete(0, StrSql.length());
                   StrSalida.delete(0, StrSalida.length());

                StrSql.append("sp_tmkgcA_ValidaCobroMx '").append(session.getAttribute("proyecto")).append("', 5");
                UtileriasBDF.rsTableNP(StrSql.toString(), StrSalida);
              %>
                <%=anexo%>
                 <%=StrSalida%>
                </td>
             </tr>
             <tr>
           <%
            StrSql.delete(0, StrSql.length());
            StrSalida.delete(0, StrSalida.length());

            System.out.println(StrSql.toString());
        StrSql.append("sp_tmkgcA_ValidaCobroMx '").append(session.getAttribute("proyecto")).append("', 3");
        UtileriasBDF.rsTableNP(StrSql.toString(), StrSalida);
          %>
              <td><br><center><h4 class="VerdeValidacion">Detalle de Ventas Nuevas a Enviar.</h4></center></td><td width="50"/>
              <td><br><center><h4 class="VerdeValidacion">Detalle de las Cancelaciones del Periodo.</h4></center></td>
          </tr>
          <tr>
              <td><%=StrSalida%></td><td width="50"/>

               <%
           StrSql.delete(0, StrSql.length());
           StrSalida.delete(0, StrSalida.length());

           System.out.println(StrSql.toString());
        StrSql.append("sp_tmkgcA_ValidaCobroMx '").append(session.getAttribute("proyecto")).append("', 4");
        UtileriasBDF.rsTableNP(StrSql.toString(), StrSalida);
          %>
              <td><%=StrSalida%></td>
          </tr>
          <tr>
              <td width="180"/>
              <td width="50"/>
              <td><br><center><h4 class="VerdeValidacion">Detalle de no envío a cobro.</h4></center></td>
          </tr>
          <%
           StrSql.delete(0, StrSql.length());
           StrSalida.delete(0, StrSalida.length());

           System.out.println(StrSql.toString());
        StrSql.append("sp_tmkgcA_ValidaCobroMx '").append(session.getAttribute("proyecto")).append("', 8");
        UtileriasBDF.rsTableNP(StrSql.toString(), StrSalida);
          %>
          <tr>
              <td width="180"/>
              <td width="50"/>
              <td><%=StrSalida%></td>
          </tr>

         </table>
          </center>

          <br><center>
              <input id="CheckGen" type="checkbox" align="right" name="checkGenera" value="genera"
                     onclick="ActivaCobro(this)"/> <font class="cssAzul">Confirmar Validación Correcta</font><br><br>
              <input id="Gen" type="button" value="Generar Cobro" name="btnGenera"
                     class="cssPlasmaVerde" onclick="GenerarCobro(this)" style="visibility:hidden" />
          </center>

           <%
           StrSql.delete(0, StrSql.length());
           StrSalida.delete(0, StrSalida.length());
           }
         else {

             %>
                 <script>
                    setTimeout("self.location.href='StatusCobro.jsp';", 1);
                 </script>
             <%  }  %>

        <div id="loading" class="loading-invisible">
            <table border="0" width="100%" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="500" valign="middle" class="textoPagPrecarga" align="center"><img src="../Imagenes/cargando.gif" alt="Cargando ..." vspace="5"/></td>
                </tr>
            </table>
        </div>

     <script>
         unblock();
     </script>
    </body>
</html>
