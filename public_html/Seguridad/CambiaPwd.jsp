<%@ page contentType="text/html; charset=iso-8859-1" language="java" errorPage="" import="Seguridad.SeguridadC"%>
<html>
<head>
<title>Cambio de Contraseña</title>
</head>
<body class="cssBody">
<link href="../StyleClasses/Global.css" rel="stylesheet" type="text/css">
<jsp:useBean id="MyUtil" scope="session" class="Utilerias.UtileriasObj"/>
<script type="text/javascript" src='../Utilerias/Util.js'></script>

<%

    if (SeguridadC.verificaRequest(request.getQueryString())==false || session.getAttribute("clUsrApp") == null){
        %> <script> window.opener.top.location.href='index.html'; </script> <%
           return;
    }else{
    
/*
MyUtil.InicializaParametrosC(6,1); 
out.println(MyUtil.ObjInput("clUsrApp","Usuario","",false,false,50,100,"",false,false,10));
out.println(MyUtil.DoBlock("Usuario"));

out.println(MyUtil.ObjInput("Password","Password","",true,true,50,200,"",false,false,10));
out.println(MyUtil.ObjInput("Confirmacion","Confirma","",true,true,150,200,"",false,false,10));
out.println(MyUtil.DoBlock("Cambio de Contraeña"));

out.println(MyUtil.ObjInput("Contador","Counter","",true,true,200,10,"",false,false,10)); 
out.println(MyUtil.DoBlock("Tiempo de Espera"));
out.println(MyUtil.doMenuAct("servlet/SIAIKE/EjecutaAccion",""));  
out.println(MyUtil.GeneraScripts());  
*/

    String StrExpiro = "0";
    String StrUsuario= "";
    
    if (request.getParameter("Usuario")!=null){
        StrUsuario = request.getParameter("Usuario").toString();
    } 
    else {
        StrUsuario = session.getAttribute("Usr").toString();
    }
        
    
     if (request.getParameter("Expiro")!= null)
      	{
             StrExpiro = request.getParameter("Expiro").toString().trim();
        }
%> 
      <%  
    if (StrExpiro.equals("1")){%>
        <div class='FTable' style='position:absolute; z-index:1; left:110px; top:65px;'>Su contraseña expiro</div>
   <%}  %>

<%  
    out.println("<form action=\"../servlet/Seguridad.CambiaPwd?msgValor\" method=\"get\" target=\"WinSave\" id=\"forma\" autocomplete=\"off\" name=\"forma\">");
    out.println("<input type=\"hidden\" id=\"Action\" name=\"Action\"></input>");
    
    out.println("<input type=\"button\" id=\"btnGuarda\" value=\"Guardar\" onClick=\"fnValida();fnAction();if(msgVal==''){fnOpenWindow();document.all.forma.submit();}else{alert('Falta informar: ' + msgVal)}\" ></input>");
    //out.println("<input type=\"button\" id=\"prueba\" value=\"prueba\" onClick=\"fnReload();\"></input>");
    out.println("<input type=\"button\" id=\"btnCancela\" value=\"Cancelar\" onClick=\"window.close();\"></input>");


    out.println("<INPUT id='URLBACK' name='URLBACK' type='hidden' value='" + request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/") + 1) + "Main.jsp?'>");                      
//    out.println("<INPUT id='URLBACK' name='URLBACK' type='hidden' value='http://localhost:8080/SIAIKE/Main.jsp'>");
    
    out.println("<div class='VTable' style='position:absolute; z-index:3; left:50px; top:100px;'><p class='FTable'>Usuario<br>");
    out.println("<INPUT  disabled size=10 id='UsrApp' name='UsrApp' value='"+ StrUsuario +"'></INPUT>");//session.getAttribute("Usr")
    out.println("<INPUT  type='hidden' id='clUsrApp' name='clUsrApp' value='"+ request.getParameter("clUsrApp") +"'></INPUT>");
    out.println("</p></div>");
    out.println("<div class='cssBGDetSw' style='background-color:#052145; position:absolute; z-index:1; left:50px; top:100px; width:200px; height:70px;'><p class='cssTitDet'></p></div>");
    out.println("<div class='cssBGDet' style='position:absolute; z-index:2; left:40px; top:80px; width:200px; height:70px;'><p class='cssTitDet'>Usuario</p></div>");
    out.println("<div class='VTable' style='position:absolute; z-index:6; left:50px; top:200px;'><p class='FTable'>Password<br>");
    out.println("<INPUT  type='password' size=10 id='Password' name='Password' onBlur='fnChekPass(this.value);' value=''></INPUT>");
    out.println("</p></div>");
    out.println("<div class='VTable' style='position:absolute; z-index:7; left:150px; top:200px;'><p class='FTable'>Confirmacion<br>");
    out.println("<INPUT  type='password' size=10 id='Confirma' name='Confirma' value=''></INPUT>");
    out.println("</p></div>");
    out.println("<div class='cssBGDetSw' style='background-color:#052145; position:absolute; z-index:3; left:50px; top:190px; width:300px; height:70px;'><p class='cssTitDet'></p></div>");
    out.println("<div class='cssBGDet' style='position:absolute; z-index:4; left:40px; top:180px; width:300px; height:70px;'><p class='cssTitDet'>Cambio de Contraseña</p></div>");
    out.println("<div class='VTable' style='position:absolute; z-index:10; left:200px; top:10px;'><p class='FTable'>Tiempo de Espera<br><INPUT  disabled size=10 id='Counter' name='Counter' value=''></INPUT></p></div>");
    //out.println("<div class='VTable' style='position:absolute; z-index:10; left:80px; top:300px;'><p class='FTable'>Contador<br><INPUT  disabled size=10 id='Counter' name='Counter' value=''></INPUT></p></div>");
    //out.println("<div class='cssBGDetSw' style='background-color:#052145; position:absolute; z-index:7; left:80px; top:290px; width:200px; height:70px;'><p class='cssTitDet'></p></div><div class='cssBGDet' style='position:absolute; z-index:8; left:70px; top:280px; width:200px; height:70px;'><p class='cssTitDet'>Tiempo de Espera</p></div>");

%>

<script>
iX=(screen.width)/2 - 135;
iY=(screen.height)/2 - 120;	
window.moveTo(iX,iY);
window.focus();

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
              //si el codigo ASCII es el de las vocales minúsculas
              if(cadena.charCodeAt(i) ==97|| cadena.charCodeAt(i) ==101 || cadena.charCodeAt(i)==105 || cadena.charCodeAt(i) ==111 || cadena.charCodeAt(i) ==117 ){
                 vocal=false;
                 }
             //si el codigo ASCII es el de las vocales mayúsculas
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
                alert("La password elegida no es segura. Introduzca al menos una mayúscula, una minúscula y un número, No puede ingresar vocales.") ;
                document.all.Password.value='';
                document.all.Password.focus();
                //return false ;
            }
  }
  else
  {
  alert("La Longitud de la  contraseña Debe tener  entre 8 y 10  Caracteres");
  document.all.Password.value='';
  document.all.Password.focus();
  }
  }

function fnValidaResponse(CodeResponse, Url,Msg){
    if (CodeResponse!=0){
        WSave.close();
        alert(Msg);
//        window.opener.close();
        fnReload();
        window.close();
        
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
            alert("La contraseña y su confirmación deben ser iguales");
            document.all.Password.value='';
            document.all.Password.focus();
            //return;
        }
        blnAceptar=1;
    }
}
</script>

<script>
var gWindowCloseWait = 30;

function SetupWindowClose(){
    window.setTimeout("window.CloseW()",1000);
}
function CloseW(){
    document.all.Counter.value=gWindowCloseWait;

    if (gWindowCloseWait>0){
        gWindowCloseWait--;
        window.setTimeout("window.CloseW()",1000);
    }
    else{
        window.close();
    }
}

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
SafeAddOnload(SetupWindowClose);
</script>

<script> 
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
            msgVal=msgVal + 'Password'
        }
        if (document.all.Confirma.value==''){
            msgVal=msgVal + 'Confirmacion'
        }
        if(msgVal!=""){
            return(msgVal);
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

    function fnReload(){
        window.opener.top.location.href="../FinSesion.jsp";
    }

</script>

    <%}%>
</body>
</html>
