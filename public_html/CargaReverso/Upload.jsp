<%@ page contentType="text/html; charset=iso-8859-1" language="java"
         import="java.sql.ResultSet,Utilerias.UtileriasBDF,Seguridad.SeguridadC,Utilerias.Captcha,Utilerias.UploadReverso" errorPage="" %>
<html>
    <head>
        <title>Carga Archivos de Reverso</title>
        <link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
        <link href="../StyleClasses/StyleC.css" rel="stylesheet" type="text/css">
    </head>
    <body class="cssBody">
        <jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
        <script src='../Utilerias/Util.js'></script>
        <% System.out.println("Upload");
        String StrclUsrApp = "0";
        String StrclPaginaWeb = "0";
        
        
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
        /*if (SeguridadC.verificaHorarioC(Integer.parseInt(StrclUsrApp)) != true) {
        % >Fuera de Horario<%
            return;
        }*/

        StrclPaginaWeb = "281";
        session.setAttribute("clPaginaWebP", StrclPaginaWeb);
        MyUtil.InicializaParametrosC(Integer.parseInt(StrclPaginaWeb), Integer.parseInt(StrclUsrApp));

        String complemento="";
        StringBuffer SQL = new StringBuffer();
                    SQL.append("st_TMKGCA_CargaReverso");
                System.out.append("inicio");
                ResultSet query= UtileriasBDF.rsSQLNP(SQL.toString());

                    while(query.next())
                        {
                            complemento= query.getString("ComboReverso").toString();
                            //tipo= query.getString("tipoR").toString();
                           // datos= query.getString("datos").toString();
                            }

              
        %>
        <b>
            <center>
                <table>
                    <tr>
                        <td><img src="../Imagenes/cargabase3.png" height="40" width="70"></td>
                        <td><font color='#423A9E'><b>Carga de Reverso (<%=session.getAttribute("NombreUsuario").toString()%>) </b></font></td>
                    </tr>
                </table>
            </center>
        </b>

           <form ACTION="../servlet/Utilerias.UploadReverso" name="gestionafichero" id="gestionafichero" enctype="multipart/form-data" METHOD="post">
            <br>
            <br>
            <div id='D2' Name='D2' class='VTable' style='position:absolute; z-index:3; left:25px; top:100px;'>
                <p class='FTable'>Codigo de Acceso<br>
                </p>
            </div>

            <div id='D3' Name='D3' class='VTable' style='position:absolute; z-index:3; left:34px; top:165px;'>
                <INPUT TYPE="Codigo" class='VTable' label='Codigo' size=13  id='Codigo' name='Codigo' value='' autocomplete="off">
            </div>

            <div style="position:absolute; z-index:3; left:170px; top:100px;">
                <p class='FTable'>Proyecto </p>
                <%=complemento%><br>
            </div>
            <div id='D5' Name='D4' class='VTable' style='position:absolute; z-index:3; left:34px; top:150px;'>
                <input type="hidden" id="tipoResp" name="tipoResp" value=''/>
            </div>

        <div id='D4' Name='D4' class='VTable' style='position:absolute; z-index:3; left:34px; top:160px;'>
                <INPUT TYPE="hidden" id='Archivo' name='Archivo' value='' >
            </div>

            <div class='VTable' style='position:absolute; z-index:3; left:35px; top:210px;'>
                <p class='FTable'>Selecciona el Archivo<br>
                    <input id="fichero" type="file" name="fichero" class="VTable" size="60">
            </div>

            <div  class='VTable' style='position:absolute; z-index:4; left:380px; top:258px;'>
                <input type="button" value="Subir Archivo" class="cBtn" onclick='fnProcesoFile();' id="btnUpload">
            </div>

            <div class='cssBGDetSw' style='background-color:#7F778A; position:absolute; z-index:1; left:20px; top:80px; width:500px; height:225px;'><p class='cssTitDet'></p></div>
            <div id="ResBlock1" name="ResBlock1" class='cssBGDet' style='position:absolute; z-index:1; left:10px; top:70px; width:500px; height:220px;'>
                <p class='cssTitDet'>Carga de Archivo de Reverso</p>

            </div>

            <div  id="SomResBlock2" style="visibility:hidden">
                <div class='cssBGDetSw' style='background-color:#7F778A; position:absolute; z-index:1; left:620px; top:80px; width:280px; height:150px;'><p class='cssTitDet'></p></div>
                <div id="ResBlock2" name="ResBlock2" class='cssBGDet' style='position:absolute; z-index:1; left:610px; top:70px; width:280px; height:150px;'>
                    <p class='cssTitDet'>Datos Adicionales</p>
                    <br><br>
                       
                </div>
            </div>

            <%
                    StrclUsrApp = null;
                    StrclPaginaWeb = null;
            %>
        </form>

        <div id="Upload" class='VTable' style='position:absolute; z-index:3; left:35px; top:130px;'>
            <img src='../servlet/Utilerias.Captcha'/>
        </div>

        <div id="tipoetiqueta" style="position:absolute; z-index:3; left:160px;">
            <a id="tipoRetiquetaPreval" class="cssAzul" style="visibility:hidden;">Reverso Banamex Domiciliacion<br>(ej: R_BMX_L[NumLote]_dbr[Fecha Archivo+No.Afiliacion]) </a><br>
            <a id="tipoRetiquetaResp" class="cssAzul" style="visibility:hidden;">Reverso HSBC Debito<br>(ej: R_HSBC_L[NumLote]_MXIKEDOM[Fecha Archvio])</a>
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
      function Datos(dsproy){
          dsproy = document.getElementById("dsproyecto").value;
          
          if(dsproy == "BMXD"){
              document.getElementById("tipoetiqueta").style.top="160px";
              document.getElementById("tipoRetiquetaResp").style.visibility="hidden";
              document.getElementById("tipoRetiquetaPreval").style.visibility="visible";
          } else{
                 document.getElementById("tipoetiqueta").style.top="140px";
                 document.getElementById("tipoRetiquetaPreval").style.visibility="hidden";
                 document.getElementById("tipoRetiquetaResp").style.visibility="visible";
            }
      }
        
        
        function TipoR1(cu){
            //alert(document.getElementById(cu.id).value);
            document.getElementById("SomResBlock2").style.visibility="hidden";
            if(document.getElementById("tipoResp").value=="1" && document.getElementById(cu.id).value!='')
                {
                    document.getElementById("tipoetiqueta").style.top="160px";
                    document.getElementById("tipoRetiquetaResp").style.visibility="hidden";
                    document.getElementById("tipoRetiquetaPreval").style.visibility="visible";
                    alert('Recuerda que la información de la pre validación se debe encontrar en la primer hoja del libro de Excel.');
                }
            else if(document.getElementById("tipoResp").value=="0" && document.getElementById(cu.id).value!='')
                {
                    document.getElementById("tipoetiqueta").style.top="140px";
                    document.getElementById("tipoRetiquetaPreval").style.visibility="hidden";
                    document.getElementById("tipoRetiquetaResp").style.visibility="visible";
                    document.getElementById("SomResBlock2").style.visibility="visible";
                    document.getElementById(document.getElementById(cu.id).value).style.visibility="visible";
                }
            else{   document.getElementById("tipoRetiquetaPreval").style.visibility="hidden";
                    document.getElementById("tipoRetiquetaResp").style.visibility="hidden";
                    document.getElementById("SomResBlock2").style.visibility="hidden";
                }
        }


       function fnProcesoFile()
        {
          
            fnVerificaFile();
            block();

            var MSG = "Falta informar: ";

            if ( document.all.fichero.value == '' )
                MSG = MSG + "Archivo, ";

            if ( document.all.Codigo.value == '' )
                MSG = MSG + "Codigo de Acceso, ";

           if ( document.all.dsproyecto.value == '' )
                MSG = MSG + "Proyecto, ";

            if (MSG!='Falta informar: ')
               { alert(MSG);
                   unblock();
               }

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
        {   //alert(document.getElementById("dsproyecto").value);
            if( document.getElementById("fichero").value!="")
            {
               //  fail =  file.value.substr(file.value.lastIndexOf(".",file.value));
               // if( (fail == ".txt") || (fail == '.xls') )
                    document.getElementById("Archivo").value=document.getElementById("fichero").value;
                      return true ;
                  }
             else if(document.getElementById("dsproyecto").value!="" && document.getElementById("Codigo").value!="")
                {
                    alert("Verificar archivo ");
                    MSG= MSG + "Archivo"
                    document.getElementById("fichero").focus()
                    return false;
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

        unblock();
    </script>
</html>
