<%@ page contentType="text/html; charset=iso-8859-1" language="java"  errorPage="" import="Seguridad.SeguridadC"%>

<%
    if (SeguridadC.verificaRequest(request.getQueryString())==false){ 
        response.sendRedirect("ErrorPage.jsp");
        return;
    }    
    
%>    
<html>
    <head>
        <title>Cambio de Contrase�a</title>
        <link href="../StyleClasses/Global<%=session.getAttribute("Prefijo") !=null ? session.getAttribute("Prefijo"):""%>.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="../StyleClasses/form-field-tooltip.css" media="screen" type="text/css">
    </head>

    <body class="cssBody">
        <script type="text/javascript" src="../Utilerias/rounded-corners.js"></script>
        <script type="text/javascript" src="../Utilerias/form-field-tooltip<%=session.getAttribute("Prefijo") !=null ? session.getAttribute("Prefijo"):""%>.js"></script>
        <jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
        <script type="text/javascript" src='../Utilerias/Util.js'></script>

<%
    String StrExpiro = "0";
    String StrclUsrCambiaPwd = "0";
    String StrUsrNomCambiaPwd= "";
    
    if (session.getAttribute("clUsrCambiaPwd")!=null){
        StrclUsrCambiaPwd = session.getAttribute("clUsrCambiaPwd").toString();
    } else {
        %>Solicitud de Cambio registrado fuera de sesi�n <%
        return;
    }
    
    if (session.getAttribute("UsrNomCambiaPwd")!=null){
        StrUsrNomCambiaPwd = session.getAttribute("UsrNomCambiaPwd").toString();
    } else {
        %>Solicitud de Cambio registrado fuera de sesi�n <%
        return;
    }
    
    if (request.getParameter("Expiro")!= null)	{
        StrExpiro = request.getParameter("Expiro").toString().trim();
    }
    
    if (StrExpiro.equals("1")){%>
        <div class='FTable' style='position:absolute; z-index:1; left:30px; top:65px;'>Su contrase�a Expir�</div>
<%  }  
    out.println("<form action=\"../servlet/Seguridad.CambiaPwd?msgValor\" method=\"get\" target=\"WinSave\" id=\"forma\" autocomplete=\"off\"  name=\"forma\">");
    out.println("<input type=\"hidden\" id=\"Action\" name=\"Action\"></input>");
    
    out.println("<input type=\"button\" id=\"btnGuarda\" value=\"Guardar\" onClick=\"fnValida();fnAction();if(msgVal==''){fnOpenWindow();document.all.forma.submit();}else{alert('Falta informar: ' + msgVal)}\" ></input>");
    out.println("<input type=\"button\" id=\"btnCancela\" value=\"Cancelar\" onClick=\"RegresaPag();\"></input>");
    out.println("<INPUT id='URLBACK' name='URLBACK' type='hidden' value='" + request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/") + 1) + "Main.jsp?'>");                      
    out.println("<div class='VTable' style='position:absolute; z-index:3; left:50px; top:120px;'><p class='FTable'>Usuario<br>");
    out.println("<INPUT  disabled size=10 id='UsrApp' name='UsrApp' value='" + StrUsrNomCambiaPwd + "'></INPUT>");
    out.println("<INPUT  type='hidden' id='clUsrApp' name='clUsrApp' value='" + StrclUsrCambiaPwd + "'></INPUT>");
    out.println("</p></div>");
    out.println("<div class='cssBGDetSw' style='background-color:#052145; position:absolute; z-index:1; left:35px; top:95px; width:100px; height:160px;'><p class='cssTitDet'></p></div>");
    out.println("<div class='cssBGDet' style='position:absolute; z-index:2; left:40px; top:90px; width:110px; height:160px;'><p class='cssTitDet'>CAMBIO DE PASSWORD </p></div>");
    out.println("<div class='VTable' style='position:absolute; z-index:6; left:50px; top:160px;'><p class='FTable'>Password<br>");
    out.println("<INPUT  type='password' size=10 id='Password' name='Password' onBlur='fnChekPass(this.value);' value=''></INPUT>");
    out.println("</p></div>");
    out.println("<div class='VTable' style='position:absolute; z-index:7; left:50px; top:200px;'><p class='FTable'>Confirmacion<br>");
    out.println("<INPUT type='password' size=10 id='Confirma' name='Confirma' value='' tooltipText='Confirma tu nueva Contrase�a (8 Caracteres)<br> <font size=1>Debe coincidir con la nueva contrase�a.</font>' ></INPUT>");
    out.println("</p></div> </form>");
    
    StrUsrNomCambiaPwd = null;
    StrExpiro = null;
    StrclUsrCambiaPwd = null;
%>

<script type="text/javascript">

window.focus();

document.forma.Password.maxLength=8
document.forma.Confirma.maxLength=8

function fnChekPass(cadena) {

    var minuscula = false ;
    var mayuscula = false ;
    var numero = false  ;
    var caracter = false ;
    var vocal = true ;
    var serie = false;

    if(cadena.length >= 8 && cadena.length <=10)
    {
           //recorre cada caracter de la cadena
            for(i=0;i<cadena.length;i++) {


              // si el codigo ASCII es el de las minusculas
                if(cadena.charCodeAt(i)>=97 && cadena.charCodeAt(i)<=122) {
                    minuscula=true ;
               //si el codigo ASCII es el de las mayusculas
                } else if(cadena.charCodeAt(i)>=65 && cadena.charCodeAt(i)<=90) {
                    mayuscula=true   ;
                //si el codigo ASCII es el de los numeros
                } else if(cadena.charCodeAt(i)>=48 && cadena.charCodeAt(i)<=57) {
                    numero=true  ;
               //si no es ninguno de los anteriores
                } else
                    caracter=true;
              //si el codigo ASCII es el de las vocales min�sculas
              if(cadena.charCodeAt(i) ==97|| cadena.charCodeAt(i) ==101 || cadena.charCodeAt(i)==105 || cadena.charCodeAt(i) ==111 || cadena.charCodeAt(i) ==117 ){
                 vocal=false;
                 }
             //si el codigo ASCII es el de las vocales may�sculas
              if(cadena.charCodeAt(i) == 65|| cadena.charCodeAt(i) ==69 || cadena.charCodeAt(i)==73 || cadena.charCodeAt(i) ==79 || cadena.charCodeAt(i) ==85 ){
                   vocal=false;
                 }


               if(i>=3){
                    //si el usuario ingresa mas de 3 caracteres iguales
                    if(cadena.charCodeAt(i-2)== cadena.charCodeAt(i) && cadena.charCodeAt(i-1)== cadena.charCodeAt(i)) {
                        alert("No puede ingresar mas de 3 de caracteres iguales.  Ejemplo(aaaa,111)") ;
                        document.all.Password.value='';
                        document.all.Password.focus();
                        //return false ;  //cambiar false por true para hacer el submit
                        }

                      //si el usuario ingresa serie de numeros o letras
                      if (cadena.charCodeAt(i-2)+1 == cadena.charCodeAt(i-1) && cadena.charCodeAt(i-1)+1 == cadena.charCodeAt(i)){
                        alert("No puede ingresar serie de caracteres .  Ejemplo(abcd..,1234..)") ;
                        document.all.Password.value='';
                        document.all.Password.focus();
                        //return false ;  //cambiar false por true para hacer el submit
                      }


                  }





            }

            if(vocal==true && numero==true && minuscula==true && mayuscula==true) {
              //  alert("La password elegida contiene todos los caracteres requeridos.") ;
                return false ;  //cambiar false por true para hacer el submit
            } else {
                alert("La password elegida no es segura. Introduzca al menos una may�scula, una min�scula y un n�mero, No puede ingresar vocales.") ;
                document.all.Password.value='';
                document.all.Password.focus();
                //return false ;
            }
  }
  else
  {
  alert("La Longitud de la  contrase�a Debe tener  entre 8 y 10  Caracteres");
  document.all.Password.value='';
  document.all.Password.focus();
  }
  }
 
function fnValidaResponse(CodeResponse, Url,Msg){
    if (CodeResponse!=0){
        WSave.close();
        alert(Msg);
        RegresaPag();
      
    }else{
        WSave.focus();
        alert(Msg);
        document.forma.Password.value='';
        document.forma.Confirma.value='';
        document.forma.Password.focus();
        WSave.close();
    }
}

function fnOpenWindow(){
    WSave=window.open('','WinSave','resizable=yes,menubar=0,status=0,toolbar=0,height=1,width=1,screenX=1,screenY=1');
    if (WSave != null) {
        if (WSave.opener == null)
            WSave.opener = self;
    }		
    WSave.opener.focus();		
}

function fnAceptar(){
    if (fnValFields() ==''){   
        if (document.all.Password.value.toUpperCase() != document.all.Confirma.value.toUpperCase()){
            alert("La contrase�a y su confirmaci�n deben ser iguales");
            return;
        }
        blnAceptar=1;
    }
}

function RegresaPag(){
   window.location="<%=request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/") + 1)%>" + "../index.html";
}
</script>

<script type="text/javascript">
var gSafeOnload = new Array();
function SafeAddOnload(f)
{
	isMac = (navigator.appVersion.indexOf("Mac")!=-1) ? true : false;
	IEmac = ((document.all)&&(isMac)) ? true : false;
	IE4 = ((document.all)&&(navigator.appVersion.indexOf("MSIE 4.")!=-1)) ? true : false;
	if (IEmac && IE4)  // IE 4.5 blows out on testing window.onload
	{
		window.onload = SafeOnload;
		gSafeOnload[gSafeOnload.length] = f;
	}
	else if  (window.onload)
	{
		if (window.onload != SafeOnload)
		{
			gSafeOnload[0] = window.onload;
			window.onload = SafeOnload;
		}		
		gSafeOnload[gSafeOnload.length] = f;
	}
	else
		window.onload = f;

}
function SafeOnload()
{
	for (var i=0;i<gSafeOnload.length;i++)
		gSafeOnload[i]();
}

// Call the following with your function as the argument
//SafeAddOnload(SetupWindowClose);
</script>

<script type="text/javascript">
    function fnValida(){
        if (document.all.Action.value==1){
            fnValidaA();
        }else{ 
            fnValidaC();             
        }
    }

    function fnValidaA(){
        msgVal="";
        if(msgVal!=""){
            return(msgVal);
        }
    }

    function fnValidaC(){
        msgVal="";
        if (document.all.Password.value==''){
            msgVal=msgVal + 'Campo \"Password\". '
        } else {
            LP = document.forma.Password.value.length;
            if (LP<8) {  msgVal=msgVal + " El campo \"Password\" tiene MENOS caracteres de los permitidos."} 
        }
        if (document.all.Confirma.value==''){
            msgVal=msgVal + ' Campo \"Confirmacion\".'
        }
        else{
            LC = document.forma.Confirma.value.length;
            if (LC<8) { msgVal=msgVal + " El campo \"Confirmaci�n\" tiene MENOS caracteres de los permitidos."}
        }
        if(msgVal!=""){
            return(msgVal);
        }
    }

   function fnValidaPass()
    {
        var s = document.all.Password.value;
        var filter = /(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{8,})$/;

        if(!filter.test(s)) {
            alert('El Password debe contener: una longitud de 8,almenos un digito y un caracter.');
            document.all.Password.value='';
            document.all.Password.focus();
        }
    }
    
    function fnAction() {
        msgValor='clUsrApp=' + document.all.clUsrApp.value;
        
        if (document.all.Password.value != '') {
            msgValor = msgValor + '&Password=' + document.all.Password.value;
        }

        if (document.all.Confirma.value != '') {
            msgValor = msgValor + '&Confirma=' + document.all.Confirma.value;
        }

        if(msgValor!=""){
            return(msgValor);
        }
    }
    
 
</script>
<script type="text/javascript">
var tooltipObj = new DHTMLgoodies_formTooltip();
tooltipObj.setTooltipPosition('right');
tooltipObj.setPageBgColor('#ecf2f9');
tooltipObj.setTooltipCornerSize(15);
tooltipObj.initFormFieldTooltip();
</script>
</body>
</html>