<%-- 
    Document   : UploadAPI
    Created on : 12/05/2021, 11:42:42 AM
    Author     : rmartin
--%>

<%@page import="java.util.concurrent.Delayed"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java"
         import="java.sql.ResultSet,Utilerias.UtileriasBDF,Seguridad.SeguridadC,Utilerias.PorcentageAPI" errorPage="" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Carga Archivos de Respuesta</title>
        <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
        <link href="../StyleClasses/StyleC.css" rel="stylesheet" type="text/css">
        <link href="../StyleClasses/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="../StyleClasses/StyleD.css" rel="stylesheet" type="text/css">
        <script link='../plugin/jquery-1.9.1.js'></script>
    </head>

    <body class="cssBody">
        <jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
        <script src='../Utilerias/Util.js'></script>
        <% System.out.println("UploadAPI inicia");
            Thread.sleep(1700);
            String StrclUsrApp = "0";
            String StrclPaginaWeb = "0";

            if (session.getAttribute("clUsrApp") != null) {
                StrclUsrApp = session.getAttribute("clUsrApp").toString();
            } else {
        %>Debe iniciar sesión
        <script>
            setTimeout("top.location.href='../index.html';", 3000);
        </script> <%
                 return;
             }
             /*if (SeguridadC.verificaHorarioC(Integer.parseInt(StrclUsrApp)) != true) {
    % >Fuera de Horario<%
        return;
    }*/

             StrclPaginaWeb = "281";
             session.setAttribute("clPaginaWebP", StrclPaginaWeb);
             MyUtil.InicializaParametrosC(Integer.parseInt(StrclPaginaWeb), Integer.parseInt(StrclUsrApp));

             String complemento = "", tipo = "", datos = "";
             StringBuffer SQL = new StringBuffer();
             //SQL.append("sp_tmkgcA_getInfoResp").append(" '',4");
             //SQL.append("sp_tmkgcA_cargaRespuesta '").append(session.getAttribute("PermisoConf").toString()+"'");
             SQL.append("sp_tmkgcA_cargaRespuesta ").append(" '',0,2");
             ResultSet query = UtileriasBDF.rsSQLNP(SQL.toString());

             while (query.next()) {
                 complemento = query.getString("Descripcion").toString();
                 tipo = query.getString("tipoR").toString();
                 datos = query.getString("datos").toString();
             }
        %>
        <b>
            <center>
                <table>
                    <tr>
                        <td><img src="../Imagenes/cargabase3.png" height="40" width="70"></td>
                        <td><font color='#423A9E'><b>Carga de Respuesta API( <%=session.getAttribute("NombreUsuario").toString()%>) </b></font></td>
                    </tr>
                </table>
            </center>
        </b>
        <br>
        <br>
        <form ACTION="../servlet/Utilerias.PorcentageAPI" name="gestionafichero" id="gestionafichero" enctype="multipart/form-data" METHOD="post">
            <div class="card shadow mb-5 bg-white rounded" id="s" style="width: 33rem; margin-left: 50px">
                <div class="card-header"> Carga de Archivo de Respuesta </div>
                <div class="card-body">

                    <div style="left:170px; top:100px;">
                        <p class='FTable'>Proyecto </p>
                        <%=complemento%><br>
                    </div>
                    <div id='D5' Name='D4' class='VTable' style='position:absolute; z-index:3; left:34px; top:150px;'>
                        <input type="hidden" id="tipoResp" name="tipoResp" value=''/>
                    </div>  
                    <div  class='VTable' style=' z-index:4; left:380px; top:258px;'>
                        <input type="button" value="Reload" onclick='location.reload();'/>
                        <!--<input type="button" id="refresca" value="ReloadF" onclick='prueba();'/>-->
                    </div> 
                </div>
            </div> 

            <div class="card shadow mb-5 bg-white rounded" id="SomResBlock2" style="visibility:hidden; width: 33rem; margin-left: 50px;height: 270px">
                <div class="card-header"> Datos Adicionales </div>
                <div class="card-body">

                    <div><p><%=datos%></p></div>
                    <br><br>
                    <!--<div ><b>Avance</b></div> <br>
                    <div>
                        <progress id="progress-bar2" role="progressbar2" class="progress-bar progress-bar-striped progress-bar-animated" max="100" value="50"></progress>
                    </div> 
                    <br><br>-->
                    <div  class='VTable' style=' position:absolute;z-index:4; left:380px; top:220px;'>
                        <input type="button" value="Procesar Respuesta" onclick='fnProcesoFile();'  id="btnUpload" disabled>
                    </div> 
                    <br><br>
                </div>
            </div>            

        </form>      

        <%
            StrclUsrApp = null;
            StrclPaginaWeb = null;
        %>

        <div id="loading" class="loading-invisible">
            <table border="0" width="100%" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="500" valign="middle" class="textoPagPrecarga" align="center"><img src="../Imagenes/cargando.gif" alt="Cargando ..." vspace="5"/></td>
                </tr>
            </table>
        </div>
    </body>

    <script type="text/javascript">
        <%=tipo%>

        
        
         function prueba(){
           /* $(document).ready(function(){
                $("#refresca").click(function(e){
                    e.preventDefault();
                   document.getElementById("prueba").innerText="Qued"; 
                });
            });*/
            var dsproyectovar=document.all.dsproyecto.value;
           /* $.post('PorcentageAPI2',{
                dsproyecto=dsproyectovar;
            }, function(responseText){
                $('#prueba').html(responseText);
            }); */
       
           /*$.ajax({
               type:"POST",
               url:"../servlet/Utilerias.PorcentageAPI2",
               data:"dsproyecto="+ dsproyectovar,
               success: function(msg){
                   alert("Entro "+msg);
               },
               error:function(xml,msg){
                   alert("No Entro "+msg);
               }
            });*/
        
        
            alert("Entro "+dsproyectovar);
        }
        function TipoR1(cu) {

            document.getElementById("SomResBlock2").style.visibility = "hidden";
            
            if (document.getElementById("tipoResp").value == "0" && document.getElementById(cu.id).value != '')
            {
                document.getElementById("SomResBlock2").style.visibility = "visible";
                document.getElementById(document.getElementById(cu.id).value).style.visibility = "visible";
                if (document.getElementById("progress-bar").value == "100") {
                    document.getElementById("btnUpload").disabled = false;
                }
            } else {
                document.getElementById("SomResBlock2").style.visibility = "hidden";
            }
        }


        function fnProcesoFile()
        {
            
            block();

            var MSG = "Falta informar: ";

        if (document.all.dsproyecto.value == '')
                MSG = MSG + "Proyecto, ";

            if (MSG != 'Falta informar: ')
            {
                alert(MSG);
                unblock();
            } else
            {
                fnProcesoUpload(1);
                fnOpenWindow();
                document.all.gestionafichero.target = "WinSave";
                document.all.gestionafichero.submit();
            }
        }



        function fnProcesoUpload(Proceso)
        {
            if (Proceso == 1)
            {
                document.all.btnUpload.disabled = true;
                loading.className = 'loading-visible';
            } else
            {
                document.all.btnUpload.disabled = false;
                loading.className = 'loading-invisible';
            }
        }

        unblock();
    </script>
</html>
