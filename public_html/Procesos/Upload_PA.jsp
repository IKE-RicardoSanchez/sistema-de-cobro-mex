<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.ResultSet,Utilerias.UtileriasBDF,Seguridad.SeguridadC,Utilerias.Captcha,Utilerias.Upload" errorPage="" %>
<html>
    <head>
        <title>Carga Archivos de Respuesta</title>
        <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
        <link href="../StyleClasses/StyleC.css" rel="stylesheet" type="text/css">
    </head>
    <body class="cssBody">
        <jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
        <script src='../Utilerias/Util.js'></script>
        <%

        String StrclUsrApp = "0",  StrclPaginaWeb = "0";

        ResultSet rs = null;

        if (session.getAttribute("clUsrApp") != null) {
            StrclUsrApp = session.getAttribute("clUsrApp").toString();
        }else{
            %>Debe iniciar sesión
            <script>
                setTimeout("top.location.href='../index.html';", 3000);
             </script> <%
            return;
        }

         StrclPaginaWeb = "281";
        session.setAttribute("clPaginaWebP", StrclPaginaWeb);
        MyUtil.InicializaParametrosC(Integer.parseInt(StrclPaginaWeb), Integer.parseInt(StrclUsrApp));

        String proyecto="", proceso="";

        if(request.getParameter("cuenta")!=null)
        {
            proyecto= request.getParameter("cuenta").toString();

            if(request.getParameter("pss")!=null)
           {
                proceso= ","+ request.getParameter("pss").toString();

                String descripcion="", err="";
                StringBuffer SQL = new StringBuffer();
                    SQL.append("sp_tmkgcA_ProcesosAdicionales ").append(proyecto).append(proceso);

                ResultSet query= UtileriasBDF.rsSQLNP(SQL.toString());

                    while(query.next())
                        {
                            descripcion= query.getString("msg").toString();
                            err= query.getString("err").toString();
                            }
        %>

        <b>
            <center>
                <table>
                    <tr>
                        <td><img src="../Imagenes/cargabase3.png" height="40" width="70"></td>
                        <td><font color='#423A9E'><b>Carga de Respuesta( <%=session.getAttribute("NombreUsuario").toString()%>) </b></font></td>
                    </tr>
                </table>
            </center>
        </b>

           <form ACTION="../servlet/Utilerias." name="gestionafichero" id="gestionafichero" enctype="multipart/form-data" METHOD="post">
            <br>
            <br>
            <div id='D2' Name='D2' class='VTable' style='position:absolute; z-index:3; left:25px; top:100px;'>
                <p class='FTable'>Codigo de Acceso<br>
                </p>
            </div>

            <div id='D3' Name='D3' class='VTable' style='position:absolute; z-index:3; left:34px; top:165px;'>
                <INPUT TYPE="Codigo" class='VTable' label='Codigo' size=13  id='Codigo' name='Codigo' value='' onkeydown="fnVerificaFile(this);" >
            </div>

            <div style="position:absolute; z-index:3; left:170px; top:100px;">
                <p class='FTable'>Proceso: </p>
                <font size="3"> <%=descripcion%></font><br>
            </div>
            <div id='D5' Name='D4' class='VTable' style='position:absolute; z-index:3; left:34px; top:150px;'>
                <input type="hidden" id="tipoResp" name="tipoResp" value=''/>
            </div>

        <div id='D4' Name='D4' class='VTable' style='position:absolute; z-index:3; left:34px; top:160px;'>
                <INPUT TYPE="hidden" id='Archivo' name='Archivo' value='' >
            </div>  

            <div class='VTable' style='position:absolute; z-index:3; left:35px; top:210px;'>
                <p class='FTable'>Selecciona el Archivo<br>
                    <input id="fichero" type="file" name="fichero" class="VTable" size="60" onblur="fnVerificaFile();" >
            </div>

            <div  class='VTable' style='position:absolute; z-index:4; left:400px; top:120px;'>
                <input type="button" value="Subir Archivo" class="cBtn" onclick='fnProcesoFile();'  id="btnUpload">
            </div>

            <div class='cssBGDetSw' style='background-color:#7F778A; position:absolute; z-index:1; left:20px; top:80px; width:500px; height:195px;'><p class='cssTitDet'></p></div>
            <div id="ResBlock1" name="ResBlock1" class='cssBGDet' style='position:absolute; z-index:1; left:10px; top:70px; width:500px; height:190px;'>
                <p class='cssTitDet'> Carga de Archivo de Respuesta</p>

            </div>
            <%
                    StrclUsrApp = null;
                    StrclPaginaWeb = null;
            %>
        </form>

        <div id="Upload" class='VTable' style='position:absolute; z-index:3; left:35px; top:130px;'>
            <img src='../servlet/Utilerias.Captcha'/>
        </div>

        <div id="loading" class="loading-invisible">
            <table border="0" width="100%" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="500" valign="middle" class="textoPagPrecarga" align="center"><img src="../Imagenes/cargando.gif" alt="Cargando ..." vspace="5"/></td>
                </tr>
            </table>
        </div>
        </body>

    <script>
       function fnProcesoFile()
        {
            block();
            var MSG = "Falta informar: ";

            if ( document.all.fichero.value == '' )
                MSG = MSG + "Archivo, ";

            if ( document.all.Codigo.value == '' )
                MSG = MSG + "Codigo de Acceso, ";

            if (MSG!='Falta informar: ')
                alert(MSG);
            else
            {
                fnProcesoUpload( 1 );
                fnOpenWindow();
                document.all.gestionafichero.target = "WinSave";
                if(document.getElementById("fichero").value!='')
                    { document.all.gestionafichero.submit(); }
            }
        }

        function fnVerificaFile()
        {
            if( document.getElementById("fichero").value != "" )
            {
               //  fail =  file.value.substr(file.value.lastIndexOf(".",file.value));
               // if( (fail == ".txt") || (fail == '.xls') )   
                    document.getElementById("Archivo").value=document.getElementById("fichero").value;
                      return true ;
                  }
                else
                {
                    alert( "Verificar extension de archivo " + fail);
                    MSG= MSG + "Archivo"
                    document.getElementById("fichero").focus()
                    return false
                }
        }

      function fnProcesoUpload( Proceso )
        {
            if ( Proceso == 1 )
            {
                document.all.ResBlock1.style.filter = 'gray';
                document.all.btnUpload.disabled = true;
                //  document.all.DivUpload.style.visibility = 'visible';
                loading.className = 'loading-visible';
            }
            else
            {
                document.all.ResBlock1.style.filter = '';
                document.all.btnUpload.disabled = false;
                loading.className = 'loading-invisible';
                //  document.all.DivUpload.style.visibility = 'hidden';
            }
        }
    </script>
        <%
           }
         }
        %>
</html>
