        setInterval ("window.status = ''",10);
/*
        var message="Funci�n deshabilitada";
        function click(e) {
            if (document.all) {
                if (event.button == 2) {
                    alert("Funci�n deshabilitada");
                    return false;
                }
            }

            if (document.layers) {
                if (e.which == 3) {
                    alert("Funci�n deshabilitada");
                    return false;
                }
            }
        }
        if (document.layers) {
            document.captureEvents(Event.MOUSEDOWN);
        }
        document.onmousedown=click;
*/
	function fnValidaResponse(CodeResponse, Url){
		if (CodeResponse!=-1){
 			WSave.close(); 
			location.href=Url;
		}
		else{
			blnAceptar=0;
			document.all.btnGuarda.disabled=false;
			document.all.btnCancela.disabled=false;
			WSave.resizeTo(640,480);
			WSave.focus();
		}
	}

	function fnRelocate(Url){
		location.href=Url;
	}

	function fnOpenWindow(){
		WSave=window.open('','WinSave','modal=yes,resizable=yes,menubar=0,status=0,toolbar=0,height=1,width=1,screenX=1,screenY=1');
   
		if (WSave != null) {
		    if (WSave.opener == null)
			WSave.opener = self;
		}		
		WSave.opener.focus();		
	}	

	function fnReplaceSelect(strName,strNew){	
    strSELECT = "";
    strSELECT = document.all[strName].outerHTML;
    strSELECT = strSELECT.substring(0,strSELECT.indexOf("<OPTION",1));
    strSELECT = strSELECT + "<OPTION VALUE=''>SELECCIONE OPCION</OPTION>" + strNew;
    strSELECT = strSELECT + "</SELECT>";
    document.all[strName].length=0;
    document.all[strName].outerHTML=strSELECT;
	}

	function fnOptionxAdd(strName,strIndex,strClave,strDescription){	
		document.all[strName].options[strIndex] = new Option(strClave,strDescription);
	}
	function fnOptionxDefault(strName,strCadena){	
		document.all[strName].length=0;
		document.all[strName].options[0] = new Option('SELECCIONE OPCION','');
		document.all[strName].value='';		
		window.open(strCadena,"","scrollbars=no,status=yes,width=1,height=1");	
	}


	iRow=0;

	function fnIncrementaLinks(){
		if (iRow>200){
			return;
		}
		else{
			iRow+=8;
			strRows="80,200,"+iRow;
			top.document.all.leftPO.rows=strRows;			
			setTimeout("fnIncrementaLinks()",1);
		}
	} 

	function fnIncrementa(){
		if (iRow>60){
			return;
		}
		else{
			iRow+=8;
			strRows=iRow+",*";				
			//top.document.all.rightPO.rows=strRows;			
			setTimeout("fnIncrementa()",1);
		}
	}
	
        function fnOpenFolders(iInicio){
		if (iInicio<60){
			iRow=iInicio;
			//top.document.all.rightPO.scrolling="no";
			setTimeout("fnIncrementa()",1);
			//top.document.all.rightPO.scrolling="yes";		
			top.document.all.DatosExpediente.src="Mail/folders.jsp";
		}else{
			strRows="80,*";				
			//top.document.all.rightPO.rows=strRows;			
		}
        }

        function fnOpenFilters(iInicio){
                alert("Quitar llamado a fnOpenFilters(), ya no se usa");
        }

        function fnOpenLinks(iInicio){

          if (iInicio<200){
            iRow=iInicio;
            top.document.all.leftPO.scrolling="no";
            setTimeout("fnIncrementaLinks()",1);
          }else{
            strRows="80,200,*";				
            top.document.all.leftPO.rows=strRows;			
          }
          top.document.all.leftPO.scrolling="yes";	
          top.document.all.InfoRelacionada.contentWindow.location.reload();
        }

	function fnDecrementaLinks(){
		if (iRowClose<=0){
			strRows=iRowClose+",*";				
			//top.document.all.rightPO.rows=strRows;			
			return;
		}
		else{
			iRowClose-=8;
			strRows=iRowClose+",*";				
			//top.document.all.rightPO.rows=strRows;			
			setTimeout("fnDecrementaLinks()",1);
		}
	}

	function fnDecrementa(){
		if (iRowClose<=0){
			strRows="80,85,"+iRowClose+",*";				
			top.document.all.leftPO.rows=strRows;			
			return;
		}
		else{
			iRowClose-=8;
			strRows="80,85,"+iRowClose+",*";				
			top.document.all.leftPO.rows=strRows;			
			setTimeout("fnDecrementa()",1);
		}
	}

        function fnCloseLinks(){
		strRows="80,*,0";				
		top.document.all.leftPO.rows=strRows;
                //top.document.all.rightPO.rows="0,*";
        }   
	
        function fnCloseFilters(){
                alert("Quitar llamado a fnCloseFilters(), ya no se usa");
        }   

        function fnCloseFolders(){
		strRows="80,85,0,*";				
		top.document.all.leftPO.rows=strRows;			
        }

        function EsNumerico(Campo){ 
           if (isNaN(Campo.value)==true)
           {
              alert(Campo.name + ' debe ser num�rico');
              Campo.value="";
           } 
        } 

        function fnRango(Campo,ValIni,ValFin){ 
           if (isNaN(Campo.value)==true)
           {
              alert(Campo.name + ' debe ser num�rico');
              Campo.value="";
           } 
           else {
             if (Campo.value < ValIni) {
                alert(Campo.name + ' debe ser entre ' + ValIni + ' y ' + ValFin);
                Campo.value="";
             }
             if (Campo.value > ValFin) { 
                alert(Campo.name + ' debe ser entre ' + ValIni + ' y ' + ValFin);
                Campo.value="";
             }
           } 
        }


        ///******************************************************************************
        //Funci�n:isValidDate (myDate,sep)
        //Prop�sito: Validar si la informaci�n capturada es una fecha
        // Parametros: myDate.- Cadena que se deber� validar
        //             sep.- Caracter que se ocupa como separador  
        ///******************************************************************************

        function isValidDate (myDate,sep) {
        // verifica si la fecha is valida en el formato dd/mm/yyyy hh:mm
                  switch (myDate.length){
                     case 10: //Valida la nada mas la fecha 
                            if (myDate.substring(7,8) == sep && myDate.substring(4,5) == sep) {
                               var date  = myDate.substring(8,10);
                               var month = myDate.substring(5,7);
                               var year  = myDate.substring(0,4);
                               var test = new Date(year,month-1,date);
                               if (year == y2k(test.getYear()) && (month-1 == test.getMonth()) && (date == test.getDate())) {
                                  return 1;
                                 }
                               else {
                                   alert('Formato v�lido pero Fecha inv�lida');
                                   return 0;
                                }
                              }
                            else {
                               alert('Separadores Inv�lidos');
                               return 0;
                            }
                               break;   
                     case 16: //Valida fecha con hora sencilla
                            if (myDate.substring(7,8) == sep && myDate.substring(4,5) == sep) {
                                 var date  = myDate.substring(8,10);
                                 var month = myDate.substring(5,7);
                                 var year  = myDate.substring(0,4);
                                 var hour =  myDate.substring(11,16);  
                                 var test = new Date(year,month-1,date);
                                 if (year == y2k(test.getYear()) && (month-1 == test.getMonth()) && (date == test.getDate())) {
                                     if (isValidHour(hour,':',false)==true){return 1;}
                                     else{
                                          alert('Fecha v�lida pero Hora inv�lida');
                                          return 0;
                                         }
                                  }
                                 else{
                                     alert('Formato v�lido pero Fecha inv�lida');
                                     return 0;
                                  } 
                              }
                             else{
                                 alert('Separadores Inv�lidos');
                                 return 0;
                             }
                             break;   
                     case 19://Valida fecha con hora completa
                            if (myDate.substring(7,8) == sep && myDate.substring(4,5) == sep) {
                                 var date  = myDate.substring(8,10);
                                 var month = myDate.substring(5,7);
                                 var year  = myDate.substring(0,4);
                                 var hour =  myDate.substring(11,19);  
                                 var test = new Date(year,month-1,date);
                                 if (year == y2k(test.getYear()) && (month-1 == test.getMonth()) && (date == test.getDate())) {
                                     if (isValidHour(hour,':',true)==true){return 1;}
                                     else{
                                          alert('Fecha v�lida pero Hora inv�lida');
                                          return 0;
                                         }
                                  }
                                 else{
                                     alert('Formato v�lido pero Fecha inv�lida');
                                     return 0;
                                  } 
                              }
                             else{
                                 alert('Separadores Inv�lidos');
                                 return 0;
                             }
                             break;
                     case 0: 
                             return 0;     
                             break;
                     default:
                             alert('Tama�o Inv�lido');
                             return 0;
                             break;
                } 
        }

        function y2k(number) { return (number < 1000) ? number + 1900 : number; }


        ///******************************************************************************
        //Funci�n:iisValidHour  (myDate,sep)
        //Prop�sito: Validar si la informaci�n capturada es una Hora 
        // Parametros: myHour.- Cadena que se deber� validar
        //             sep.- Caracter que se ocupa como separador  
        ///******************************************************************************

        function isValidHour (myHour,sep,conSegundos) {
        // verifica si la hora is valida en el formato hh:mm:ss 

           if (conSegundos==true){ 
               if (myHour.length==8) {
                  if (myHour.substring(2,3) == sep && myHour.substring(5,6) == sep) {
                        var Hour  = myHour.substring(0,2);
                        var Min = myHour.substring(3,5);
                        var Seg  = myHour.substring(6,8);

                       if (Hour>=0 && Hour<=24){
                          if (Min>=0 &&  Min<=60){
                             if (Seg>=0 &&  Seg<=60){
                                return 1;
                              }else{
                                alert('Segundos Invalidos');
                                return 0;
                              }
                           }else{
                           alert('Minutos Invalidos');
                           return 0;
                           }  
                        }//if hora
                         else{
                           alert('Hora Invalida');
                           return 0;
                        }
                    }// if separador
                    else {
                        alert('Separadores Invalidos');
                        return 0;
                    }
                }//if tama�o
                else {
                    alert('Tama�o Invalido');
                    return 0;
                }
           } //if conSegundos
           else{

               if (myHour.length==5) {
                  if (myHour.substring(2,3) == sep) {
                        var Hour  = myHour.substring(0,2);
                        var Min = myHour.substring(3,5);

                       if (Hour>=0 && Hour<=24){
                          if (Min>=0 &&  Min<=60){
                                return 1;
                           }
                           else{
                           alert('Minutos Invalidos');
                           return 0;
                           }  
                        }//if hora
                         else{
                           alert('Hora Invalida');
                            return 0;
                        }
                    }// if separador
                    else {
                        alert('Separadores Invalidos');
                        return 0;
                    }
                }//if tama�o
                else {
                    alert('Tama�o Invalido');
                    return 0;
                }

           }


        }

        function fnValidaCampo(obj,strTipoCampo){
           if (document.all.Action.value==1 ||document.all.Action.value==2){
                var strCampo=obj.value;
                var ok;     
                  switch (strTipoCampo){
                     case "HoraCompleta":
                                 ok=isValidHour(strCampo,':',true);
                                 break;   
                     case "HoraParcial":
                                 ok=isValidHour(strCampo,':',false);
                                 break;   
                     case "Fecha":   
                                 ok=isValidDate(strCampo,'/');
                                break;    
                     default: break;
                } 

              if (ok==0){
                    alert("La informaci�n de " + strTipoCampo + " no esta capturada correctamente");
                   obj.value="";
               }             
          }
        }


        function fnValidaModelo(pObj){
         if (isNaN(pObj.value)==false){
             var num= eval(pObj.value);
              if (num<1980){
                  alert("El modelo debe ser mayor o igual a 1980");
                  pObj.focus(); 
                  return 0;
              }
              var fecha = new Date(); 
              var anio = fecha.getYear() + 2 ; 
              if (num>anio){
                  alert("El modelo debe ser menor o igual a " + anio );
                  pObj.focus();  
                  return 0;
              }   
          }
          else{
               alert("El campo de modelo debe ser n�merico");
               pObj.focus();  
               return 0;
          }

        }

        function fnOrder(vObjTable, iColumn){
            // var vObjTable = document.getElementById('ObjTable');
            // el orden empieza en el rengl�n 1 para omitir los t�tulos
            strValAct="";
            strValCmp="";
            iRowAct=1;
            iRowCmp=1;

            for (iR=1; iR < vObjTable.rows.length; iR++) {
                if (iR>1){
                    iRowAct = iR;
                    iRowCmp = iR;
                    strValAct = vObjTable.rows(iRowAct).cells(iColumn).innerHTML;            
                    strValCmp = vObjTable.rows(iRowCmp-1).cells(iColumn).innerHTML
                    while((strValAct < strValCmp) && (iRowCmp>1)){
                        iRowCmp-=1;                
                        strValCmp = vObjTable.rows(iRowCmp-1).cells(iColumn).innerHTML
                    }
                    vObjTable.moveRow(iRowAct,iRowCmp);
                }
            }
        }

    function fnBuscaGral(strProc){
      window.open('FiltroBusquedaGral.jsp?strSQL=' + strProc,'WinGral','resizable=yes,menubar=0,status=0,toolbar=0,height=250,width=700,screenX=50,screenY=50');
    }

    function fnActualizaLlave(strObject, strValuecl, strValueTxt){
      document.all[strObject].value=strValuecl;
      document.all[strObject+"V"].value=strValueTxt;
    }


    function fnCancelaTMK(){
        var afil = document.all.clAfilTMK.value;
        window.open('../Utilerias/cancelarTMK.jsp?clafil='+ afil ,'WinGral','resizable=yes,menubar=0,status=0,toolbar=0,height=50,width=50,screenX=50,screenY=50');
    }

    function fnCargaTipoResumen(){              
        var strConsulta = "st_TipoResumen " + document.all.clCliente.value;
        var pstrCadena = "../servlet/Utilerias.LlenaCombos?strSQL=" + strConsulta;

        pstrCadena = pstrCadena + "&strName=clTipoResumenC";
        fnOptionxDefault('clTipoResumenC',pstrCadena);               
    }

    function fnReplaceScripting(value,id){
        if(value=="") {
            return;
        }

        var xmlHttpgetVl;

        try{
            xmlHttpgetVl=new XMLHttpRequest();// Firefox, Opera 8.0+, Safari
        } catch (e){
            try{
                xmlHttpgetVl=new ActiveXObject("Msxml2.XMLHTTP"); // Internet Explorer
            } catch (e){
                try{
                    xmlHttpgetVl=new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e){
                    alert("No AJAX!?");
                }
            }
        }

        xmlHttpgetVl.onreadystatechange = function() {
            if(xmlHttpgetVl.readyState == 4)
                if( xmlHttpgetVl.status == 200)
                    document.getElementById(id).value = ltrim(rtrim(unescape(xmlHttpgetVl.responseText)));
        }

        xmlHttpgetVl.open("POST","verifInst.jsp",true);
        xmlHttpgetVl.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
        xmlHttpgetVl.send("value="+escape(value));
    }

    function ltrim(cad){
        return cad.replace(/^\s+/,"");
    }

    function rtrim(cad){
        return cad.replace(/\s+$/,"");
    }

    function closeDialog(){
        window.close();
    }

    function block(){ 
        window.parent.Menu.document.getElementById("loading").className='loading-visible';
        window.parent.DatosUsuario.document.getElementById("loading").className='loading-visible';
        window.document.getElementById("loading").className='loading-visible';
    }

    function unblock(){
        window.parent.Menu.document.getElementById("loading").className='loading-invisible';
        window.parent.DatosUsuario.document.getElementById("loading").className='loading-invisible';
        window.document.getElementById("loading").className='loading-invisible';
    }

