/*
 * UtileriasObj.java
 *
 * Created on 13 de octubre de 2004, 06:43 PM
 */

package Utilerias;

import Seguridad.SeguridadC;
import java.sql.ResultSet;
import java.util.List;
import Utilerias.UtileriasBDF;
import Utilerias.Idioma;
import javax.servlet.http.HttpSession;

/**
 *
 * @author  cabrerar
 */
public class UtileriasObj {
    
    public boolean blnAccess[] = new boolean[6];    
    private static int blnAlta = 0;
    private static int blnBaja = 1;
    private static int blnCambio = 2;
    private static int blnConsulta = 3;
    private static int blnEdicion = 4;
    private static int blnCancela = 5;
    private static List comboList = null;
    
    private StringBuffer strScriptHA; // Variable de Script para habilitar campos en Alta
    private StringBuffer strScriptHC; // Variable de Script para habilitar campos en cambio
    private StringBuffer strScriptVA; // Variable de Script para validar los campos requeridos en alta
    private StringBuffer strScriptVC; // Variable de Script para validar los campos requeridos en cambio
    
    // Manejo de Bitácora: RCS 26 Ago 2005
    private StringBuffer strScriptBITAyB; // Variable de Script para guardar bitácora
    private StringBuffer strScriptBITC; // Variable de Script para guardar bitácora
    
    private int DivXT;
    private int DivYT;
    private int DivXD;
    private int DivYD;
    private int zIndexBlock;
    private int zIndex;
    
    // Manejo de Bitácora: RCS 26 Ago 2005
    private String strBitacora;
        
    /** Creates a new instance of UtileriasObj */

    private byte bytLanguaje = 1; // Portugués
    
    public void InicializaParametrosC(int pclPage, int pclUsrApp){
    
        blnAccess=SeguridadC.VerificaC(pclPage, pclUsrApp);        
        strScriptHA = new StringBuffer();
        strScriptHC = new StringBuffer();        
        strScriptVA = new StringBuffer();        
        strScriptVC = new StringBuffer();        
        
        // Manejo de Bitácora: RCS 26 Ago 2005
        strScriptBITAyB = new StringBuffer();        
        strScriptBITC = new StringBuffer();        
        
        DivXT=5000;
        DivYT=10000;
        DivXD=0;
        DivYD=0;
        zIndexBlock=1;
        zIndex=2;
        
        // Manejo de Bitácora: RCS 26 Ago 2005
        strBitacora = "";
        //strBitacora=SeguridadC.VerificaBitacoraC(pclPage);
        
    }

    public void InicializaParametrosC(int pclPage, int pclUsrApp, byte pbytLanguaje){
    
        blnAccess=SeguridadC.VerificaC(pclPage, pclUsrApp);        
        strScriptHA = new StringBuffer();
        strScriptHC = new StringBuffer();        
        strScriptVA = new StringBuffer();        
        strScriptVC = new StringBuffer();        
        
        // Manejo de Bitácora: RCS 26 Ago 2005
        strScriptBITAyB = new StringBuffer();        
        strScriptBITC = new StringBuffer();        
        
        DivXT=5000;
        DivYT=10000;
        DivXD=0;
        DivYD=0;
        zIndexBlock=1;
        zIndex=2;
        
        // Manejo de Bitácora: RCS 26 Ago 2005
        strBitacora=SeguridadC.VerificaBitacoraC(pclPage);
        bytLanguaje = pbytLanguaje;
    }

  // Cierra el canal cuando este objeto es reciclado 
/*  protected void finalize() throws Throwable {
    try {
      System.out.println("Entre a Finalize Obj");
       close();        // close open files
    } finally {
        super.finalize();
    }
   }
  */
    
   private void close()
   {
     strBitacora=null;
     strScriptHA.delete(0,strScriptHA.length());
     strScriptHC.delete(0,strScriptHC.length());
     strScriptVA.delete(0,strScriptVA.length());
     strScriptVC.delete(0,strScriptVC.length());
     strScriptBITAyB.delete(0,strScriptBITAyB.length());
     strScriptBITC.delete(0,strScriptBITC.length());
}
    public String DoBlock(String pTitle){
        int iWi;
        iWi=DivXT - 10;
        int iHi;
        iHi=DivYT - 20;
        int iW;
        iW=DivXD - DivXT + 200;
        int iH;
        iH=DivYD - DivYT + 70;

        int iWiShadow;
        iWiShadow=iWi + 10;
        int iHiShadow;
        iHiShadow = iHi + 10;
        
        pTitle = Idioma.getLabel(pTitle,bytLanguaje);
        
        StringBuffer StrReturn  = new StringBuffer();
        
        StrReturn.append("<div class='cssBGDetSw' style='background-color:#052145; position:absolute; z-index:");
        StrReturn.append(zIndexBlock).append("; left:").append(iWiShadow ).append("px; top:").append(iHiShadow);
        StrReturn.append("px; width:").append(iW).append("px; height:").append(iH).append("px;'><p class='cssTitDet'></p></div>");
        
        zIndexBlock++;
        StrReturn.append("<div class='cssBGDet' style='position:absolute; z-index:").append(zIndexBlock);
        StrReturn.append("; left:").append(iWi).append("px; top:").append(iHi).append("px; width:");
        StrReturn.append(iW).append("px; height:").append(iH).append("px;'><p class='cssTitDet'>");
        StrReturn.append(pTitle).append("</p></div>");
        
        DivXT=5000;
        DivYT=10000;
        DivXD=0;
        DivYD=0;
        
        zIndexBlock = zIndex+1;        
        zIndex=zIndex+2;
        
        return StrReturn.toString();
        
    }

    public String DoBlock(String pTitle, int xAjust, int yAjust){
        int iWi;
        iWi=DivXT - 10;
        int iHi;
        iHi=DivYT - 20;
        int iW;
        iW=DivXD - DivXT + 200 + xAjust;
        int iH;
        iH=DivYD - DivYT + 70 + yAjust;

        int iWiShadow;
        iWiShadow=iWi + 10;
        int iHiShadow;
        iHiShadow = iHi + 10;
        
        pTitle = Idioma.getLabel(pTitle,bytLanguaje);
        
        StringBuffer StrReturn = new StringBuffer();
        
        StrReturn.append("<div class='cssBGDetSw' style='background-color:#052145; position:absolute; z-index:");
        StrReturn.append(zIndexBlock).append("; left:").append(iWiShadow).append("px; top:");
        StrReturn.append(iHiShadow).append("px; width:").append(iW).append("px; height:").append(iH);
        StrReturn.append("px;'><p class='cssTitDet'></p></div>");
        
        zIndexBlock++;
        StrReturn.append("<div class='cssBGDet' style='position:absolute; z-index:");
        StrReturn.append(zIndexBlock).append("; left:").append(iWi).append("px; top:").append(iHi);
        StrReturn.append("px; width:").append(iW).append("px; height:").append(iH).append("px;'><p class='cssTitDet'>");
        StrReturn.append(pTitle).append("</p></div>");
        
        DivXT=5000;
        DivYT=10000;
        DivXD=0;
        DivYD=0;
        zIndexBlock = zIndex+1;        
        zIndex=zIndex+2;
        
        return StrReturn.toString();
        
    }
    
    public UtileriasObj() {

        //Se hace un llamado para obtener los permisos a los que tiene derecho el 
        //usuario que ingresa
    }
    
    public String GeneraScripts(){
        StringBuffer strReturn = new StringBuffer();
        strReturn.append("<script> function fnHabilitaA(){").append(strScriptHA).append("}");
        strReturn.append("function fnHabilitaC(){").append(strScriptHC).append("}");
        strReturn.append("function fnValida(){");
        strReturn.append("                    if (document.all.Action.value==1){");
        strReturn.append("                         fnValidaA(); ");
        strReturn.append("                    }else{fnValidaC();}");
        strReturn.append("                    if(msgVal!=\"\"){document.all.btnGuarda.disabled=false;document.all.btnCancela.disabled=false;}");
        strReturn.append("        }");
        
        strReturn.append("function fnValidaA(){msgVal=\"\";").append(strScriptVA);
                        strReturn.append(" if(msgVal!=\"\"){");
                        strReturn.append("     return(msgVal);");
                        strReturn.append(" } ");
                        strReturn.append("}");
        strReturn.append("function fnValidaC(){msgVal=\"\";").append(strScriptVC);
                        strReturn.append(" if(msgVal!=\"\"){");
                        strReturn.append("     return(msgVal);");
                        strReturn.append(" } ");
                        strReturn.append("}");
                        
        // Manejo de Bitácora: RCS 26 Ago 2005
        if (strBitacora.compareToIgnoreCase("")!=0){
            strReturn.append("function fnBitacora(){strBitacora=\"\";");
                        strReturn.append("if (document.all.Action.value==2){");
                            strReturn.append(strScriptBITC);
                        strReturn.append("}else{");
                            strReturn.append(strScriptBITAyB);
                        strReturn.append("}");
                        strReturn.append("document.all.Bitacora.value = strBitacora; ");
                        strReturn.append("}");
        }
        strReturn.append("</script>");
        
        // Manejo de Bitácora: RCS 26 Ago 2005
        if (strBitacora.compareToIgnoreCase("")!=0){
            strReturn.append("<input type='hidden' id='Bitacora' name='Bitacora'></input>");
        }
        strScriptHA.delete(0,strScriptHA.length());
        strScriptHC.delete(0,strScriptHC.length());
        strScriptVA.delete(0,strScriptVA.length());
        strScriptVC.delete(0,strScriptVC.length());
    
        strScriptBITAyB.delete(0,strScriptBITAyB.length()); 
        strScriptBITC.delete(0,strScriptBITC.length()); 

        return strReturn.toString();
    }
    
    public String ObjInput(String pTitulo, String pName, String pValue, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, boolean pReqAlta, boolean pReqCambio, int pSize){
        
        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
        
        StringBuffer StrHtml = new StringBuffer();
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex);
        StrHtml.append("' class='VTable' style='position:absolute; z-index:").append(zIndex);
        StrHtml.append("; left:").append(xPosition).append("px; top:").append(yPosition).append("px;'>");
        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>");
        
        // Manejo de Bitácora: RCS 26 Ago 2005: Se agrega valor al atributo label para guardar referencia del dato en bitácora
        // Para un inputtext el label queda directamente en el campo
        StrHtml.append("<INPUT class='VTable' label='").append(pTitulo).append("' ");
        StrHtml.append(" readOnly size=").append(pSize);
        StrHtml.append(" onBlur='fnReplaceScripting(this.value,this.id);' ");  // -- Agregar Esta Linea para la evitar palabras reservadas --        
        StrHtml.append(" id='").append(pName).append("' name='").append(pName).append("' value='").append(pValue).append("'></INPUT>");
        
        if (blnAccess[blnEdicion] == true){            
            if (pEditAlta ==true){
                strScriptHA.append("document.all.").append(pName).append(".readOnly=false;");
            }

            strScriptHA.append(" document.all.").append(pName).append(".value='").append(pDefValue).append("';");
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && (blnAccess[blnAlta] ==true || blnAccess[blnBaja] ==true)){
                strScriptBITAyB.append("strBitacora = strBitacora + document.all.");
                strScriptBITAyB.append(pName).append(".label + \": \" + document.all.").append(pName).append(".value + \" \";");
            }
            
            if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append(".readOnly=false;");
            }

            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){
                strScriptBITC.append("if(\"").append(pValue).append("\"!=document.all.");
                strScriptBITC.append(pName).append(".value){strBitacora = strBitacora + document.all.");
                strScriptBITC.append(pName).append(".label + \": ant:");
                strScriptBITC.append(pValue).append(" nvo:\" + document.all.").append(pName).append(".value + \" \";}");
            }

            if (pReqAlta ==true){
                strScriptHA.append(" document.all.").append(pName).append(".className='FReq';");
                strScriptVA.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
            
            if (pReqCambio ==true){
                strScriptVC.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
        }
        StrHtml.append("</p></div>");
        return StrHtml.toString();

    }

    public String ObjInput(String pTitulo, String pName, String pValue, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, boolean pReqAlta, boolean pReqCambio, int pSize, String pOnBlur){
        
        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
        
        StringBuffer StrHtml = new StringBuffer();
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex);
        StrHtml.append("' class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:");
        StrHtml.append(xPosition).append("px; top:").append(yPosition).append("px;'>");        
        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>");

        // Manejo de Bitácora: RCS 26 Ago 2005: Se agrega valor al atributo label para guardar referencia del dato en bitácora
        // Para un inputtext el label queda directamente en el campo
        StrHtml.append("<INPUT class='VTable' label='").append(pTitulo).append("' ");
        StrHtml.append(" onBlur='fnReplaceScripting(this.value,this.id);").append(pOnBlur).append("' "); // -- Agregar Esta Linea para la evitar palabras reservadas --
        StrHtml.append(" readOnly size=").append(pSize);
        StrHtml.append(" id='").append(pName).append("' name='").append(pName).append("' value='").append(pValue).append("'></INPUT>");

        if (blnAccess[blnEdicion] == true){
            if (pEditAlta ==true){
                strScriptHA.append("document.all.").append(pName).append(".readOnly=false;");
            }
            strScriptHA.append(" document.all.").append(pName).append(".value='").append(pDefValue).append("';");
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && (blnAccess[blnAlta] ==true || blnAccess[blnBaja] ==true)){
                strScriptBITAyB.append("strBitacora = strBitacora + document.all.").append(pName);
                strScriptBITAyB.append(".label + \": \" + document.all.").append(pName).append(".value + \" \";");
            }

            if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append(".readOnly=false;");    
            }

            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){
                strScriptBITC.append("if(\"").append(pValue).append("\"!=document.all.").append(pName);
                strScriptBITC.append(".value){strBitacora = strBitacora + document.all.").append(pName);
                strScriptBITC.append(".label + \": ant:").append(pValue).append(" nvo:\" + document.all.").append(pName).append(".value + \" \";}");
            }

            if (pReqAlta ==true){
                strScriptHA.append(" document.all.").append(pName).append(".className='FReq';");
                strScriptVA.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
            
            if (pReqCambio ==true){
                strScriptVC.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
        }
        StrHtml.append("</p></div>");
        return StrHtml.toString();
    }
    public String ObjInput(String pTitulo, String pName, String pValue, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, boolean pReqAlta, boolean pReqCambio, int pSize, String pOnBlur,String pOnClick){
        
        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
        
        StringBuffer StrHtml = new StringBuffer();
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex);
        StrHtml.append("' class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:");
        StrHtml.append(xPosition).append("px; top:").append(yPosition).append("px;'>");        
        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>");

        // Manejo de Bitácora: RCS 26 Ago 2005: Se agrega valor al atributo label para guardar referencia del dato en bitácora
        // Para un inputtext el label queda directamente en el campo
        StrHtml.append("<INPUT class='VTable' label='").append(pTitulo).append("' ");
        StrHtml.append(" onBlur='fnReplaceScripting(this.value,this.id);").append(pOnBlur).append("' "); // -- Agregar Esta Linea para la evitar palabras reservadas --
        StrHtml.append(" onClick='").append(pOnClick).append("' ");        
        StrHtml.append(" readOnly size=").append(pSize);
        StrHtml.append(" id='").append(pName).append("' name='").append(pName).append("' value='").append(pValue).append("'></INPUT>");

        if (blnAccess[blnEdicion] == true){
            if (pEditAlta ==true){
                strScriptHA.append("document.all.").append(pName).append(".readOnly=false;");
            }
            strScriptHA.append(" document.all.").append(pName).append(".value='").append(pDefValue).append("';");
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && (blnAccess[blnAlta] ==true || blnAccess[blnBaja] ==true)){
                strScriptBITAyB.append("strBitacora = strBitacora + document.all.").append(pName);
                strScriptBITAyB.append(".label + \": \" + document.all.").append(pName).append(".value + \" \";");
            }

            if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append(".readOnly=false;");    
            }

            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){
                strScriptBITC.append("if(\"").append(pValue).append("\"!=document.all.").append(pName);
                strScriptBITC.append(".value){strBitacora = strBitacora + document.all.").append(pName);
                strScriptBITC.append(".label + \": ant:").append(pValue).append(" nvo:\" + document.all.").append(pName).append(".value + \" \";}");
            }

            if (pReqAlta ==true){
                strScriptHA.append(" document.all.").append(pName).append(".className='FReq';");
                strScriptVA.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
            
            if (pReqCambio ==true){
                strScriptVC.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
        }
        StrHtml.append("</p></div>");
        return StrHtml.toString();
    }
    public String ObjInput(String pTitulo, String pName, String pValue, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, boolean pReqAlta, boolean pReqCambio, int pSize, boolean pPass){
        
        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
        
        StringBuffer StrHtml = new StringBuffer();
        
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex);
        StrHtml.append("' class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:");
        StrHtml.append(xPosition).append("px; top:").append(yPosition).append("px;'>");
        
        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>");

        // Manejo de Bitácora: RCS 26 Ago 2005: Se agrega valor al atributo label para guardar referencia del dato en bitácora
        // Para un inputtext el label queda directamente en el campo
        StrHtml.append("<INPUT class='VTable' label='").append(pTitulo).append("' ");
        
        if (pPass) {
            StrHtml.append(" type='password' ");
        }
        StrHtml.append(" readOnly size=").append(pSize);
        StrHtml.append(" onBlur='fnReplaceScripting(this.value,this.id);' ");  // -- Agregar Esta Linea para la evitar palabras reservadas --        
        StrHtml.append(" id='").append(pName).append("' name='").append(pName).append("' value='").append(pValue).append("'></INPUT>");

        if (blnAccess[blnEdicion] == true){
            if (pEditAlta ==true){
                strScriptHA.append("document.all.").append(pName).append(".readOnly=false;");    
            }
            strScriptHA.append(" document.all.").append(pName).append(".value='").append(pDefValue).append("';");
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && (blnAccess[blnAlta] ==true || blnAccess[blnBaja] ==true)){
                strScriptBITAyB.append("strBitacora = strBitacora + document.all.").append(pName).append(".label + \": \" + document.all.").append(pName).append(".value + \" \";");
            }

            if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append(".readOnly=false;");
            }

            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){
                strScriptBITC.append("if(\"").append(pValue).append("\"!=document.all.").append(pName);
                strScriptBITC.append(".value){strBitacora = strBitacora + document.all.").append(pName).append(".label + \": ant:").append(pValue).append(" nvo:\" + document.all.").append(pName).append(".value + \" \";}");
            }

            if (pReqAlta ==true){
                strScriptHA.append(" document.all.").append(pName).append(".className='FReq';");                
                strScriptVA.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
            
            if (pReqCambio ==true){
                strScriptVC.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
        }
        StrHtml.append("</p></div>");
        return StrHtml.toString();
    }
    
    public String ObjTextArea(String pTitulo, String pName, String pValue, String pCols, String pRows, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, boolean pReqAlta, boolean pReqCambio){
        
        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
        
        StringBuffer StrHtml = new StringBuffer();
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex);
        StrHtml.append("' class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:").append(xPosition).append("px; top:").append(yPosition).append("px;'>");        
        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>");

        // Manejo de Bitácora: RCS 26 Ago 2005: Se agrega valor al atributo label para guardar referencia del dato en bitácora
        // Para un textarea el label queda directamente en el campo
        StrHtml.append("<TEXTAREA class='VTable' label='").append(pTitulo).append("' ");
        
        StrHtml.append(" readOnly ");
        StrHtml.append(" rows=").append(pRows).append(" cols=").append(pCols).append(" ");
        StrHtml.append(" onblur='fnReplaceScripting(this.value,this.id);' ");// -- Agregar Esta Linea para la evitar palabras reservadas --        
        StrHtml.append(" id='").append(pName).append("' name='").append(pName).append("'>").append(pValue).append("</TEXTAREA>");

        if (blnAccess[blnEdicion] == true){            
            if (pEditAlta ==true){
                strScriptHA.append("document.all.").append(pName).append(".readOnly=false;");    
            }
            strScriptHA.append(" document.all.").append(pName).append(".value='").append(pDefValue).append("';");
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && (blnAccess[blnAlta] ==true || blnAccess[blnBaja] ==true)){
                strScriptBITAyB.append("strBitacora = strBitacora + document.all.").append(pName).append(".label + \": \" + document.all.").append(pName).append(".value + \" \";");
            }

            if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append(".readOnly=false;");    
            }

            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){
                strScriptBITC.append("if(\"").append(pValue).append("\"!=document.all.").append(pName);
                strScriptBITC.append(".value){strBitacora = strBitacora + document.all.").append(pName);
                strScriptBITC.append(".label + \": ant:").append(pValue).append(" nvo:\" + document.all.").append(pName).append(".value + \" \";}");
            }

            if (pReqAlta ==true){
                strScriptHA.append(" document.all.").append(pName).append(".className='FReq';");                
                strScriptVA.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ");
                strScriptVA.append(pTitulo).append(". '}");    
            }
            
            if (pReqCambio ==true){
                strScriptVC.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
        }
        StrHtml.append("</p></div>");
        return StrHtml.toString();
    }
    
    public String ObjChkBox(String pTitulo, String pName, String pValue, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, String lblChecked, String lblUnChecked, String pfnClick){

        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
        
        int iMaxLen = 0;
        iMaxLen = lblChecked.length();
        if (iMaxLen < lblUnChecked.length()){iMaxLen = lblUnChecked.length();}
        
        StringBuffer StrHtml = new StringBuffer();
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex);
        StrHtml.append("' class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:").append(xPosition).append("px; top:").append(yPosition).append("px;'>");        
        
        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>");        
        StrHtml.append("<INPUT type='hidden' value=").append(pValue).append(" id='").append(pName).append("' name='").append(pName).append("' >");
        StrHtml.append("<INPUT class='VTable' type='checkbox' disabled id='").append(pName).append("C' name='").append(pName).append("C'");
        if (pValue.compareToIgnoreCase("1")==0){
            StrHtml.append(" checked ");
        }

        StrHtml.append(" onClick=\"if (this.checked){").append(pName).append(".value=1;").append(pName).append("Label.value='").append(lblChecked).append("'}");
        StrHtml.append(" else{").append(pName).append(".value=0;").append(pName).append("Label.value='").append(lblUnChecked).append("';}").append(pfnClick).append("\"");
        StrHtml.append("></INPUT>");

        // Manejo de Bitácora: RCS 26 Ago 2005: Se agrega valor al atributo label para guardar referencia del dato en bitácora
        // Para un checkbox el label queda en el campo de referencia pName->Label.
        
        StrHtml.append("<input size=").append(iMaxLen).append(" class='cssInputTextE' border='0' readonly name=").append(pName).append("Label value='");
        if (pValue.compareToIgnoreCase("1")==0){
            StrHtml.append(lblChecked).append("' label='").append(pTitulo).append("'></input>");
        }else{
            StrHtml.append(lblUnChecked).append("' label='").append(pTitulo).append("'></input>");
        }
            
        if (blnAccess[blnEdicion] == true){            
            if (pEditAlta ==true){
                strScriptHA.append("document.all.").append(pName).append("C.disabled=false;");    
            }
            
            if (pDefValue.compareToIgnoreCase("1")==0){
                strScriptHA.append("document.all.").append(pName).append("C.checked = true;");
                strScriptHA.append("document.all.").append(pName).append("Label.value = '").append(lblChecked).append("';");
                strScriptHA.append("document.all.").append(pName).append(".value =1;");
            }else{
                strScriptHA.append("document.all.").append(pName).append("C.checked = false;");
                strScriptHA.append("document.all.").append(pName).append("Label.value = '").append(lblUnChecked).append("';");
                strScriptHA.append("document.all.").append(pName).append(".value =0;");
            }
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){
                strScriptBITC.append("if(\"").append(pValue).append("\"!=document.all.").append(pName).append(".value){strBitacora = strBitacora + document.all.").append(pName).append("Label.label + \": ant:");
                        if (pValue.compareToIgnoreCase("1")==0){
                            strScriptBITC.append(lblChecked);
                        }else{
                            strScriptBITC.append(lblUnChecked);
                        }
                        strScriptBITC.append(" nvo:\" + document.all.").append(pName).append("Label.value + \" \";}");
            }

            if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append("C.disabled=false;");    
            }
        }
        StrHtml.append("</P></div>");            
        return StrHtml.toString();
    }

    public String ObjChkBox(String pTitulo, String pName, String pValue, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, String pfnClick){

        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
        
        StringBuffer StrHtml = new StringBuffer();
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex);
        StrHtml.append("' class='VTable' style='position:absolute; z-index:").append(zIndex);
        StrHtml.append("; left:").append(xPosition).append("px; top:").append(yPosition).append("px;'>");        
        //String strChk="";

        StrHtml.append("<p class='FTable'>");
        if (blnAccess[blnEdicion] == true){
            StrHtml.append("<INPUT type='hidden' value=").append(pValue).append(" id='").append(pName).append("' name='").append(pName).append("' >");            
            StrHtml.append("<INPUT class='VTable' type='checkbox' disabled id='").append(pName).append("C' name='").append(pName).append("C'");
            if (pValue.compareToIgnoreCase("1")==0){
                StrHtml.append(" checked ");
            }
            StrHtml.append(" onClick=\"if (this.checked){").append(pName).append(".value=1;}");
	    StrHtml.append(" else{").append(pName).append(".value=0;}").append(pfnClick).append("\"");
        
            StrHtml.append("></INPUT>");
        
            if (pEditAlta==true){
                strScriptHA.append("document.all.").append(pName).append("C.disabled=false;");    
            }

            if (pDefValue.compareToIgnoreCase("1")==0){
                strScriptHA.append("document.all.").append(pName).append("C.checked = true;");
                strScriptHA.append("document.all.").append(pName).append(".value =1;");                                                
            }else{
                strScriptHA.append("document.all.").append(pName).append("C.checked = false;");
                strScriptHA.append("document.all.").append(pName).append(".value =0;");                                                
            }
            
            if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append("C.disabled=false;");
            }
        }
        else{
            StrHtml.append("<INPUT class='VTable' type='checkbox' disabled id='").append(pName).append("' name='").append(pName).append("'");
            if (pValue.compareToIgnoreCase("1")==0){
                StrHtml.append(" checked ");
            }
            StrHtml.append("></INPUT>");
        }
        StrHtml.append(pTitulo);                
        StrHtml.append("</P></div>");            
        return StrHtml.toString();
    }

    public String ObjComboC(String pTitulo, String pName, String pValue, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, String pSQL, String pfnChange, String pfnClick, int pSize, boolean pReqAlta, boolean pReqCambio){

        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
        
        StringBuffer StrHtml = new StringBuffer();
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex).append("' class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:").append(xPosition).append("px; top:").append(yPosition).append("px;'>");        
        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>"); 
        String strValue = "";
        
        if (blnAccess[blnEdicion] == true){
            StrHtml.append("<select class='VTable' disabled id='").append(pName).append("C' name='").append(pName).append("C' ");
            StrHtml.append(" onChange=\"document.all.").append(pName).append(".value = this.value;"); 
            if (pfnChange!=""){
                StrHtml.append(pfnChange).append("\"");			
            } 
            else{StrHtml.append("\"");			
            }

            if (pfnClick!=""){
                StrHtml.append(" onClick=\"").append(pfnClick).append("\"");			
            } 

            // Manejo de Bitácora: RCS 26 Ago 2005: Se agrega valor al atributo label para guardar referencia del dato en bitácora
            // Para un combo el label queda en el campo de options, selectedIndex relacionado a pName->C.
            StrHtml.append(" label='").append(pTitulo).append("' ><option value=''>SELECCIONE OPCIÓN</option>");

            ResultSet rs = UtileriasBDF.rsSQLNP(pSQL);
            int iLen=0;
            try{
                if (rs.next()){
                    rs.first();
                    do {  
                        StrHtml.append("<option label='").append(rs.getString(2)).append("' value='").append(rs.getString(1)).append("'");
                        iLen = rs.getString(2).length();
                        if (iLen>pSize){iLen=pSize ;}
                        if (pValue!=null){
                            if (pValue.equalsIgnoreCase(rs.getString(2))==true){
                                strValue=rs.getString(1);
                                StrHtml.append(" selected ");
                            }
                        }
                        StrHtml.append(" >").append(rs.getString(2).substring(0,iLen)).append("</option>");
                    } while(rs.next());
                }
                rs.close();
                rs=null;
            }
            catch (Exception e) {
                //Fallo carga driver JDBC/ODBC.;
                e.printStackTrace();
            }
            StrHtml.append("</select>");
            StrHtml.append("<input type='hidden' id='").append(pName).append("' name='").append(pName).append("' value='").append(strValue).append("'>");            
            if (pReqAlta ==true){
                strScriptVA.append("if (document.all.").append(pName).append("C.value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");
            }
            
            if (pReqCambio ==true){
                strScriptVC.append("if (document.all.").append(pName).append("C.value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
            
            if (pEditAlta ==true){
                strScriptHA.append("document.all.").append(pName).append("C.disabled=false;");    
            }
            strScriptHA.append(" document.all.").append(pName).append(".value='").append(pDefValue).append("';");            
            strScriptHA.append(" document.all.").append(pName).append("C.value='").append(pDefValue).append("';");
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && (blnAccess[blnAlta] ==true || blnAccess[blnBaja] ==true)){
                strScriptBITAyB.append("strBitacora = strBitacora + document.all.").append(pName).append("C.label + \": \" + document.all.").append(pName).append("C.options[document.all.").append(pName).append("C.selectedIndex].label + \" \";");
            }

           if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append("C.disabled=false;");
           }
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){
                strScriptBITC.append("if(\"").append(strValue).append("\"!=document.all.").append(pName);
                strScriptBITC.append("C.value){strBitacora = strBitacora + document.all.").append(pName).append("C.label + \": ant:").append(pValue).append(" nvo:\" + document.all.").append(pName).append("C.options[document.all.").append(pName).append("C.selectedIndex].label + \" \";}");
            }

        }
        else{
            StrHtml.append("<INPUT class='VTable' ");
            StrHtml.append(" type='text' ");
            if (pSize>20){
                pSize = pSize-5;
            }
            StrHtml.append(" readOnly size=").append(pSize);
            StrHtml.append(" id='").append(pName).append("' name='").append(pName).append("' value='").append(pValue).append("'></INPUT>");
            
            /*StrHtml = StrHtml + pValue;
             **/
        }
        StrHtml.append("</P></div>");            
        return StrHtml.toString();
    }

/*    public String ObjCombo(String pTitulo, String pName, String pValue, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, String pSQL, String pfnChange, String pfnClick, int pSize, boolean pReqAlta, boolean pReqCambio){

        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
        
        StringBuffer StrHtml = new StringBuffer();
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex).append("' class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:").append(xPosition).append("px; top:").append(yPosition).append("px;'>");        
        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>"); 
        String strValue = "";
        
        if (blnAccess[blnEdicion] == true){
            StrHtml.append("<select class='VTable' disabled id='").append(pName).append("C' name='").append(pName).append("C' ");
            StrHtml.append(" onChange=\"document.all.").append(pName).append(".value = this.value;"); 
            if (pfnChange!=""){
                StrHtml.append(pfnChange).append("\"");			
            } 
            else{StrHtml.append("\"");			
            }

            if (pfnClick!=""){
                StrHtml.append(" onClick=\"").append(pfnClick).append("\"");			
            } 

            // Manejo de Bitácora: RCS 26 Ago 2005: Se agrega valor al atributo label para guardar referencia del dato en bitácora
            // Para un combo el label queda en el campo de options, selectedIndex relacionado a pName->C.
            StrHtml.append(" label='").append(pTitulo).append("' ><option value=''>SELECCIONE OPCIÓN</option>");

            ResultSet rs = UtileriasBDF.rsSQL(pSQL);
            int iLen=0;
            try{
                if (rs.next()){
                    rs.first();
                    do {  
                        StrHtml.append("<option label='").append(rs.getString(2)).append("' value='").append(rs.getString(1)).append("'");
                        iLen = rs.getString(2).length();
                        if (iLen>pSize){iLen=pSize ;}
                        if (pValue!=null){
                            if (pValue.equalsIgnoreCase(rs.getString(2))==true){
                                strValue=rs.getString(1);
                                StrHtml.append(" selected ");
                            }
                        }
                        StrHtml.append(" >").append(rs.getString(2).substring(0,iLen)).append("</option>");
                    } while(rs.next());
                }
                rs.close();
                rs=null;
            }
            catch (Exception e) {
                //Fallo carga driver JDBC/ODBC.;
                e.printStackTrace();
            }
            StrHtml.append("</select>");
            StrHtml.append("<input type='hidden' id='").append(pName).append("' name='").append(pName).append("' value='").append(strValue).append("'>");            
            if (pReqAlta ==true){
                strScriptVA.append("if (document.all.").append(pName).append("C.value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");
            }
            
            if (pReqCambio ==true){
                strScriptVC.append("if (document.all.").append(pName).append("C.value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
            
            if (pEditAlta ==true){
                strScriptHA.append("document.all.").append(pName).append("C.disabled=false;");    
            }
            strScriptHA.append(" document.all.").append(pName).append(".value='").append(pDefValue).append("';");            
            strScriptHA.append(" document.all.").append(pName).append("C.value='").append(pDefValue).append("';");
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && (blnAccess[blnAlta] ==true || blnAccess[blnBaja] ==true)){
                strScriptBITAyB.append("strBitacora = strBitacora + document.all.").append(pName).append("C.label + \": \" + document.all.").append(pName).append("C.options[document.all.").append(pName).append("C.selectedIndex].label + \" \";");
            }

           if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append("C.disabled=false;");
           }
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){
                strScriptBITC.append("if(\"").append(strValue).append("\"!=document.all.").append(pName);
                strScriptBITC.append("C.value){strBitacora = strBitacora + document.all.").append(pName).append("C.label + \": ant:").append(pValue).append(" nvo:\" + document.all.").append(pName).append("C.options[document.all.").append(pName).append("C.selectedIndex].label + \" \";}");
            }

        }
        else{
            StrHtml.append("<INPUT class='VTable' ");
            StrHtml.append(" type='text' ");
            if (pSize>20){
                pSize = pSize-5;
            }
            StrHtml.append(" readOnly size=").append(pSize);
            StrHtml.append(" id='").append(pName).append("' name='").append(pName).append("' value='").append(pValue).append("'></INPUT>");
            
            //StrHtml = StrHtml + pValue;
             
        }
        StrHtml.append("</P></div>");            
        return StrHtml.toString();
    }
*/
    
    public String ObjComboHabC(String pTitulo, String pName, String pValue, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, String pSQL, String pfnChange, String pfnClick, int pSize, boolean pReqAlta, boolean pReqCambio){

        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
        
        StringBuffer StrHtml = new StringBuffer();
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex);
        StrHtml.append("' class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:");
        StrHtml.append(xPosition).append("px; top:").append(yPosition).append("px;'>");        
        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>"); 
        String strValue = "";
        
        if (blnAccess[blnEdicion] == true){
            StrHtml.append("<select class='VTable'  id='").append(pName).append("C' name='").append(pName).append("C' ");
            StrHtml.append(" onChange=\"document.all.").append(pName).append(".value = this.value;"); 
            if (pfnChange!=""){
                StrHtml.append(pfnChange).append("\"");			
            } 
            else{StrHtml.append("\"");			
            }

            if (pfnClick!=""){
                StrHtml.append(" onClick=\"").append(pfnClick).append("\"");
            } 
            
            // Manejo de Bitacora: RCS 26 Ago 2005: Se agrega valor al atributo label para guardar referencia del dato en bitácora
            // Para un combo el label queda en el campo relacionado pName->C.
            StrHtml.append(" label='").append(pTitulo).append("' ><option value=''>SELECCIONE OPCIÓN</option>");

            ResultSet rs = UtileriasBDF.rsSQLNP(pSQL);
            int iLen=0;
            try{
                if (rs.next()){
                    rs.first();
                    do {  
                        StrHtml.append("<option label='").append(rs.getString(2)).append("' value='").append(rs.getString(1)).append("'");
                        iLen = rs.getString(2).length();
                        if (iLen>pSize){iLen=pSize ;}
                        if (pValue!=null){
                            if (pValue.equalsIgnoreCase(rs.getString(2))==true){
                                strValue=rs.getString(1);
                                StrHtml.append(" selected ");
                            }
                        }
                        StrHtml.append(" >").append(rs.getString(2).substring(0,iLen)).append("</option>");
                    } while(rs.next());
                }
                rs.close();
                rs=null;
            }
            catch (Exception e) {
                //Fallo carga driver JDBC/ODBC.;
                e.printStackTrace();
            }
            StrHtml.append("</select>");
            StrHtml.append("<input type='hidden' id='").append(pName).append("' name='").append(pName).append("' value='").append(strValue).append("'>");
            if (pReqAlta ==true){
                strScriptVA.append("if (document.all.").append(pName).append("C.value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
            
            if (pReqCambio ==true){
                strScriptVC.append("if (document.all.").append(pName).append("C.value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
            
            if (pEditAlta ==true){
                strScriptHA.append("document.all.").append(pName).append("C.disabled=false;");    
            }
            strScriptHA.append(" document.all.").append(pName).append(".value='").append(pDefValue).append("';");            
            strScriptHA.append(" document.all.").append(pName).append("C.value='").append(pDefValue).append("';");

            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && (blnAccess[blnAlta] ==true || blnAccess[blnBaja] ==true)){
                strScriptBITAyB.append("strBitacora = strBitacora + document.all.").append(pName).append("C.label + \": \" + document.all.").append(pName).append("C.options[document.all.").append(pName).append("C.selectedIndex].label + \" \";");
            }

           if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append("C.disabled=false;");    
           }
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){
                strScriptBITC.append("if(\"").append(strValue).append("\"!=document.all.").append(pName).append("C.value){strBitacora = strBitacora + document.all.").append(pName).append("C.label + \": ant:").append(pValue).append(" nvo:\" + document.all.").append(pName).append("C.options[document.all.").append(pName).append("C.selectedIndex].label + \" \";}");
            }

        }
        else{
            StrHtml.append("<INPUT class='VTable' ");
            StrHtml.append(" type='text' ");
            if (pSize>20){
                pSize = pSize-5;
            }
            StrHtml.append(" readOnly size=").append(pSize);
            StrHtml.append(" id='").append(pName).append("' name='").append(pName).append("' value='").append(pValue).append("'></INPUT>");
            
            /*StrHtml = StrHtml + pValue;
             **/
        }
        StrHtml.append("</P></div>");            
        return StrHtml.toString();
    }

/*    public String ObjComboHab(String pTitulo, String pName, String pValue, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, String pSQL, String pfnChange, String pfnClick, int pSize, boolean pReqAlta, boolean pReqCambio){

        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
        
        StringBuffer StrHtml = new StringBuffer();
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex);
        StrHtml.append("' class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:");
        StrHtml.append(xPosition).append("px; top:").append(yPosition).append("px;'>");        
        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>"); 
        String strValue = "";
        
        if (blnAccess[blnEdicion] == true){
            StrHtml.append("<select class='VTable'  id='").append(pName).append("C' name='").append(pName).append("C' ");
            StrHtml.append(" onChange=\"document.all.").append(pName).append(".value = this.value;"); 
            if (pfnChange!=""){
                StrHtml.append(pfnChange).append("\"");			
            } 
            else{StrHtml.append("\"");			
            }

            if (pfnClick!=""){
                StrHtml.append(" onClick=\"").append(pfnClick).append("\"");
            } 
            
            // Manejo de Bitácora: RCS 26 Ago 2005: Se agrega valor al atributo label para guardar referencia del dato en bitácora
            // Para un combo el label queda en el campo relacionado pName->C.
            StrHtml.append(" label='").append(pTitulo).append("' ><option value=''>SELECCIONE OPCIÓN</option>");

            ResultSet rs = UtileriasBDF.rsSQL(pSQL);
            int iLen=0;
            try{
                if (rs.next()){
                    rs.first();
                    do {  
                        StrHtml.append("<option label='").append(rs.getString(2)).append("' value='").append(rs.getString(1)).append("'");
                        iLen = rs.getString(2).length();
                        if (iLen>pSize){iLen=pSize ;}
                        if (pValue!=null){
                            if (pValue.equalsIgnoreCase(rs.getString(2))==true){
                                strValue=rs.getString(1);
                                StrHtml.append(" selected ");
                            }
                        }
                        StrHtml.append(" >").append(rs.getString(2).substring(0,iLen)).append("</option>");
                    } while(rs.next());
                }
                rs.close();
                rs=null;
            }
            catch (Exception e) {
                //Fallo carga driver JDBC/ODBC.;
                e.printStackTrace();
            }
            StrHtml.append("</select>");
            StrHtml.append("<input type='hidden' id='").append(pName).append("' name='").append(pName).append("' value='").append(strValue).append("'>");
            if (pReqAlta ==true){
                strScriptVA.append("if (document.all.").append(pName).append("C.value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
            
            if (pReqCambio ==true){
                strScriptVC.append("if (document.all.").append(pName).append("C.value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
            
            if (pEditAlta ==true){
                strScriptHA.append("document.all.").append(pName).append("C.disabled=false;");    
            }
            strScriptHA.append(" document.all.").append(pName).append(".value='").append(pDefValue).append("';");            
            strScriptHA.append(" document.all.").append(pName).append("C.value='").append(pDefValue).append("';");

            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && (blnAccess[blnAlta] ==true || blnAccess[blnBaja] ==true)){
                strScriptBITAyB.append("strBitacora = strBitacora + document.all.").append(pName).append("C.label + \": \" + document.all.").append(pName).append("C.options[document.all.").append(pName).append("C.selectedIndex].label + \" \";");
            }

           if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append("C.disabled=false;");    
           }
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){
                strScriptBITC.append("if(\"").append(strValue).append("\"!=document.all.").append(pName).append("C.value){strBitacora = strBitacora + document.all.").append(pName).append("C.label + \": ant:").append(pValue).append(" nvo:\" + document.all.").append(pName).append("C.options[document.all.").append(pName).append("C.selectedIndex].label + \" \";}");
            }

        }
        else{
            StrHtml.append("<INPUT class='VTable' ");
            StrHtml.append(" type='text' ");
            if (pSize>20){
                pSize = pSize-5;
            }
            StrHtml.append(" readOnly size=").append(pSize);
            StrHtml.append(" id='").append(pName).append("' name='").append(pName).append("' value='").append(pValue).append("'></INPUT>");
            
            //StrHtml = StrHtml + pValue;
        }
        StrHtml.append("</P></div>");            
        return StrHtml.toString();
    }
*/
    
    public String ObjComboMem(String pTitulo, String pName, String pValue, String pNumValue,String strOptions, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, String pfnChange, String pfnClick, int pSize, boolean pReqAlta, boolean pReqCambio){

        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
        
        StringBuffer StrHtml = new StringBuffer();
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex).append("' class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:").append(xPosition).append("px; top:").append(yPosition).append("px;'>");        
        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>"); 
        
        if (blnAccess[blnEdicion] == true){
            StrHtml.append("<select class='VTable' disabled id='").append(pName).append("C' name='").append(pName).append("C' ");
            StrHtml.append(" onChange=\"document.all.").append(pName).append(".value = this.value;"); 
            if (pfnChange!=""){
                StrHtml.append(pfnChange).append("\"");			
            } 
            else{StrHtml.append("\"");			
            }

            if (pfnClick!=""){
                StrHtml.append(" onClick=\"").append(pfnClick).append("\"");			
            } 

            // Manejo de Bitácora: RCS 26 Ago 2005: Se agrega valor al atributo label para guardar referencia del dato en bitácora
            // Para un combo el label queda en el campo de options, selectedIndex relacionado a pName->C.
            StrHtml.append(" label='").append(pTitulo).append("' ><option value=''>SELECCIONE OPCIÓN</option>");
            
            StrHtml.append(strOptions);
            
            StrHtml.append("</select>");
            StrHtml.append("<input type='hidden' id='").append(pName).append("' name='").append(pName).append("' value='").append(pNumValue).append("'>");            
            if (pReqAlta ==true){
                strScriptVA.append("if (document.all.").append(pName).append("C.value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");
            }
            
            if (pReqCambio ==true){
                strScriptVC.append("if (document.all.").append(pName).append("C.value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    
            }
            
            if (pEditAlta ==true){
                strScriptHA.append("document.all.").append(pName).append("C.disabled=false;");    
            }
            strScriptHA.append(" document.all.").append(pName).append(".value='").append(pDefValue).append("';");            
            strScriptHA.append(" document.all.").append(pName).append("C.value='").append(pDefValue).append("';");
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && (blnAccess[blnAlta] ==true || blnAccess[blnBaja] ==true)){
                strScriptBITAyB.append("strBitacora = strBitacora + document.all.").append(pName).append("C.label + \": \" + document.all.").append(pName).append("C.options[document.all.").append(pName).append("C.selectedIndex].label + \" \";");
            }

           if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append("C.disabled=false;");
           }
            
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){
                strScriptBITC.append("if(\"").append(pNumValue).append("\"!=document.all.").append(pName);
                strScriptBITC.append("C.value){strBitacora = strBitacora + document.all.").append(pName).append("C.label + \": ant:").append(pValue).append(" nvo:\" + document.all.").append(pName).append("C.options[document.all.").append(pName).append("C.selectedIndex].label + \" \";}");
            }

        }
        else{
            StrHtml.append("<INPUT class='VTable' ");
            StrHtml.append(" type='text' ");
            if (pSize>20){
                pSize = pSize-5;
            }
            StrHtml.append(" readOnly size=").append(pSize);
            StrHtml.append(" id='").append(pName).append("' name='").append(pName).append("' value='").append(pValue).append("'></INPUT>");
            
            /*StrHtml = StrHtml + pValue;
             **/
        }
        StrHtml.append("</P></div>");            
        return StrHtml.toString();
    }
    
    public String doMenuAct(String pAction, String pfnPrevAdd){
        
        StringBuffer strMenu = new StringBuffer();
        
        if (blnAccess[blnEdicion] == true){
            strMenu.append("<form action=\"").append(pAction).append("\" method=\"get\" target=\"WinSave\" id=\"forma\" name=\"forma\">").append("<input type=\"hidden\" id=\"Action\" name=\"Action\"></input>").append(" <input ");

            if (blnAccess[blnAlta] == false){
                   strMenu.append(" disabled=true ");
            }


            strMenu.append(" type=\"button\" id=\"btnAlta\" value=\"Alta\" onClick=\"this.disabled=true;document.all.Action.value=1;document.all.btnGuarda.disabled=false;document.all.btnCancela.disabled=false;document.all.btnCambio.disabled=true;document.all.btnElimina.disabled=true;fnHabilitaA();");
            if (pfnPrevAdd!=""){
		strMenu.append(pfnPrevAdd);
            }			
            
            strMenu.append("\" ></input><input");
            
            if (blnAccess[blnCambio]==false){
                strMenu.append(" disabled=true ");			
            }

            strMenu.append(" type=\"button\" id=\"btnCambio\" value=\"Cambio\" onClick=\"this.disabled=true;document.all.Action.value=2;document.all.btnGuarda.disabled=false;document.all.btnCancela.disabled=false;document.all.btnAlta.disabled=true;document.all.btnElimina.disabled=true;fnHabilitaC();\" ></input>").append(" <input");

            if (blnAccess[blnBaja]==false){
                strMenu.append(" disabled=true ");
            }

            strMenu.append(" type=\"button\" id=\"btnElimina\" value=\"Eliminar\" onClick=\"this.disabled=true;document.all.Action.value=3;document.all.btnGuarda.disabled=false;document.all.btnCancela.disabled=false;document.all.btnAlta.disabled=true;document.all.btnCambio.disabled=true;\" ></input>");
            strMenu.append("  <input disabled=true type=\"button\" id=\"btnGuarda\" value=\"Guardar\" onClick=\"this.disabled=true;document.all.btnCancela.disabled=true;document.all.btnAlta.disabled=true;document.all.btnCambio.disabled=true;document.all.btnElimina.disabled=true;");
            
            strMenu.append(" fnValida();if(msgVal==\'\'){"); 

            // Manejo de Bitácora: RCS 26 Ago 2005 (Se agrega script al guardar)
            if(strBitacora.compareToIgnoreCase("")!=0){
                strMenu.append(" fnBitacora();");
            }
            
            strMenu.append(" fnOpenWindow();document.all.forma.submit();}else{alert(\'Falta informar: \' + msgVal)}\" ></input>");
            strMenu.append("  <input disabled=true type=\"button\" id=\"btnCancela\" value=\"Cancelar\" onClick=\"this.disabled=true;document.all.btnGuarda.disabled=true;");
	    
            if (blnAccess[blnAlta] == true){
                strMenu.append(" document.all.btnAlta.disabled=false;");
            }
            if (blnAccess[blnCambio] == true){
                strMenu.append(" document.all.btnCambio.disabled=false;");
            }
            if (blnAccess[blnBaja] == true){
                strMenu.append(" document.all.btnElimina.disabled=false;");
            }

            strMenu.append(" location.reload();\"></input>");
        }else{
            strMenu.append("<input id='btnAlta' type='hidden' name='btnAlta'></input>");
            strMenu.append("<input id='btnCambio' type='hidden' name='btnCambio'></input>");
            strMenu.append("<input id='btnElimina' type='hidden' name='btnElimina'></input>");
        }
        return strMenu.toString();
    }

    public String doMenuAct(String pAction, String pfnPrevAdd, String pfnGuarda){
        
        StringBuffer strMenu = new StringBuffer();
        
        if (blnAccess[blnEdicion] == true){
            strMenu.append("<form action=\"").append(pAction).append("\" method=\"get\" target=\"WinSave\" id=\"forma\" name=\"forma\">" + "<input type=\"hidden\" id=\"Action\" name=\"Action\"></input>").append(" <input ");

            if (blnAccess[blnAlta] == false){
                   strMenu.append(" disabled=true ");
            }


            strMenu.append(" type=\"button\" id=\"btnAlta\" value=\"Alta\" onClick=\"this.disabled=true;document.all.Action.value=1;document.all.btnGuarda.disabled=false;document.all.btnCancela.disabled=false;document.all.btnCambio.disabled=true;document.all.btnElimina.disabled=true;fnHabilitaA();");
            if (pfnPrevAdd!=""){
		strMenu.append(pfnPrevAdd);
            }			
            
            strMenu.append("\" ></input><input");
            
            if (blnAccess[blnCambio]==false){
                strMenu.append(" disabled=true ");			
            }

            strMenu.append(" type=\"button\" id=\"btnCambio\" value=\"Cambio\" onClick=\"this.disabled=true;document.all.Action.value=2;document.all.btnGuarda.disabled=false;document.all.btnCancela.disabled=false;document.all.btnAlta.disabled=true;document.all.btnElimina.disabled=true;fnHabilitaC();\" ></input>").append(" <input");

            if (blnAccess[blnBaja]==false){
                strMenu.append(" disabled=true ");
            }

            strMenu.append(" type=\"button\" id=\"btnElimina\" value=\"Eliminar\" onClick=\"this.disabled=true;document.all.Action.value=3;document.all.btnGuarda.disabled=false;document.all.btnCancela.disabled=false;document.all.btnAlta.disabled=true;document.all.btnCambio.disabled=true;\" ></input>");
            strMenu.append("  <input disabled=true type=\"button\" id=\"btnGuarda\" value=\"Guardar\" onClick=\"");
            strMenu.append("this.disabled=true;document.all.btnCancela.disabled=true;document.all.btnAlta.disabled=true;document.all.btnCambio.disabled=true;document.all.btnElimina.disabled=true;fnValida();");
            if (pfnGuarda!=""){
		strMenu.append(pfnGuarda);
            }			
            strMenu.append("if(msgVal==\'\'){");
            
            // Manejo de Bitácora: RCS 26 Ago 2005 (Se agrega script al guardar)
            if(strBitacora.compareToIgnoreCase("")!=0){
                strMenu.append(" fnBitacora();");
            }

            strMenu.append("fnOpenWindow();document.all.forma.submit();}else{alert(\'Falta informar: \' + msgVal)}\" ></input>");
            strMenu.append("  <input disabled=true type=\"button\" id=\"btnCancela\" value=\"Cancelar\" onClick=\"this.disabled=true;document.all.btnGuarda.disabled=true;");
	    
            if (blnAccess[blnAlta] == true){
                strMenu.append(" document.all.btnAlta.disabled=false;");
            }
            if (blnAccess[blnCambio] == true){
                strMenu.append(" document.all.btnCambio.disabled=false;");
            }
            if (blnAccess[blnBaja] == true){
                strMenu.append(" document.all.btnElimina.disabled=false;");
            }

            strMenu.append(" location.reload();\"></input>");
        }
        return strMenu.toString();
    }
    
    
    
    
    public String doMenuAct(String pAction, String pfnPrevAdd, String pfnGuarda, String pfnCancela){
        
        StringBuffer strMenu = new StringBuffer();
        
        if (blnAccess[blnEdicion] == true){
            strMenu.append("<form action=\"").append(pAction).append("\" method=\"get\" target=\"WinSave\" id=\"forma\" name=\"forma\">" + "<input type=\"hidden\" id=\"Action\" name=\"Action\"></input>").append(" <input ");

            if (blnAccess[blnAlta] == false){
                   strMenu.append(" disabled=true ");
            }


            strMenu.append(" type=\"button\" id=\"btnAlta\" value=\"Alta\" onClick=\"this.disabled=true;document.all.Action.value=1;document.all.btnGuarda.disabled=false;document.all.btnCancela.disabled=false;document.all.btnCambio.disabled=true;document.all.btnElimina.disabled=true;fnHabilitaA();");
            if (pfnPrevAdd!=""){
		strMenu.append(pfnPrevAdd);
            }			
            
            strMenu.append("\" ></input><input");
            
            if (blnAccess[blnCambio]==false){
                strMenu.append(" disabled=true ");			
            }

            strMenu.append(" type=\"button\" id=\"btnCambio\" value=\"Cambio\" onClick=\"this.disabled=true;document.all.Action.value=2;document.all.btnGuarda.disabled=false;document.all.btnCancela.disabled=false;document.all.btnAlta.disabled=true;document.all.btnElimina.disabled=true;fnHabilitaC();\" ></input>").append(" <input");

            if (blnAccess[blnBaja]==false){
                strMenu.append(" disabled=true ");
            }

            strMenu.append(" type=\"button\" id=\"btnElimina\" value=\"Eliminar\" onClick=\"this.disabled=true;document.all.Action.value=3;document.all.btnGuarda.disabled=false;document.all.btnCancela.disabled=false;document.all.btnAlta.disabled=true;document.all.btnCambio.disabled=true;\" ></input>");
            strMenu.append("  <input disabled=true type=\"button\" id=\"btnGuarda\" value=\"Guardar\" onClick=\"");
            strMenu.append("this.disabled=true;document.all.btnCancela.disabled=true;document.all.btnAlta.disabled=true;document.all.btnCambio.disabled=true;document.all.btnElimina.disabled=true;fnValida();");
            if (pfnGuarda!=""){
		strMenu.append(pfnGuarda);
            }			
            strMenu.append("if(msgVal==\'\'){");

            // Manejo de Bitácora: RCS 26 Ago 2005 (Se agrega script al guardar)
            if(strBitacora.compareToIgnoreCase("")!=0){
                strMenu.append(" fnBitacora();");
            }

            strMenu.append("fnOpenWindow();document.all.forma.submit();}else{alert(\'Falta informar: \' + msgVal)}\" ></input>");
            strMenu.append("  <input disabled=true type=\"button\" id=\"btnCancela\" value=\"Cancelar\" onClick=\"this.disabled=true;document.all.btnGuarda.disabled=true;");
	    if (pfnCancela!=""){
		strMenu.append(pfnCancela);
            }
            if (blnAccess[blnAlta] == true){
                strMenu.append(" document.all.btnAlta.disabled=false;");
            }
            if (blnAccess[blnCambio] == true){
                strMenu.append(" document.all.btnCambio.disabled=false;");
            }
            if (blnAccess[blnBaja] == true){
                strMenu.append(" document.all.btnElimina.disabled=false;");
            }

            strMenu.append(" location.reload();\"></input>");
        }
        return strMenu.toString();
    }
    
    
    

    public String doMenuCorreo(String pAction, int TipoMenu){
        
        StringBuffer strMenu = new StringBuffer();
        
        if (blnAccess[blnEdicion] == true){
            
            strMenu.append("<form action=\"").append(pAction).append("\" method= \"post\" ENCTYPE=\"application/x-www-form-urlencoded\" id=\"forma\" name=\"forma\">").append(" <input ");
            
            if (TipoMenu==0){
                strMenu.append(" type=\"submit\" id=\"btnAcceso\" value=\"Acceso\"");
                strMenu.append("\" ></input>").append("  <input type=\"reset\" id=\"btnCancela\" value=\"Cancelar\"></input>");
            } else {
                strMenu.append("type= \"hidden\"></input>");
                /* " type=\"button\" id=\"btnNuevo\" value=\"Nuevo\" onClick=\"fnSubmit();></input>" +
                    " <input type=\"button\" id=\"btnRespon\" value=\"Responder\" onClick=\"fnHabilitaC();\" ></input>" +
                    " <input type=\"button\" id=\"btnResponAll\" value=\"Responder a Todos\" onClick=\"fn();\" ></input>" + 
                    " <input disabled=true type=\"button\" id=\"btnReenvia\" value=\"Reenviar\" onClick=\"fn();\" ></input>";*/
                //strMenu = strMenu + " location.reload();\"> </input>";
            }
        }
        return strMenu.toString();
    }
    public String ObjInputF(String pTitulo, String pName, String pValue, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, boolean pReqAlta, boolean pReqCambio, int pSize, int pNiveles, String pOnBlur){

        

        if (xPosition<DivXT){DivXT=xPosition;}

        if (yPosition<DivYT){DivYT=yPosition;}

        if (xPosition>DivXD){DivXD=xPosition;}

        if (yPosition>DivYD){DivYD=yPosition;}

        zIndex = zIndex+1;

        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);

        

        String StrNiveles="../";

        

        for (int i=1;i<pNiveles;i++) { 

        StrNiveles = StrNiveles + "../";

        }

        

        StringBuffer StrHtml = new StringBuffer();

        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex);

        StrHtml.append("' class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:");

        StrHtml.append(xPosition).append("px; top:").append(yPosition).append("px;'>");        

        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>");

 

        // Manejo de Bitácora: RCS 26 Ago 2005: Se agrega valor al atributo label para guardar referencia del dato en bitácora

        // Para un inputtext el label queda directamente en el campo

        StrHtml.append("<INPUT class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:0px; top:13px;'");

        StrHtml.append("label='").append(pTitulo).append("' OnClick='hideCalendarControl();' ");

        StrHtml.append(" readOnly size=").append(pSize);

        StrHtml.append(" onBlur='").append(pOnBlur).append("' ");

        StrHtml.append(" id='").append(pName).append("' name='").append(pName).append("' value='").append(pValue).append("'  OnBlur='hideCalendarControl();'></INPUT>");

        StrHtml.append("<Img src='").append(StrNiveles).append("Imagenes/calendario.GIF'  onClick='showCalendarControl(document.all.").append(pName).append(")'");

        StrHtml.append(" OndblClick='hideCalendarControl();' style='position:absolute; z-index:").append(zIndex).append("; left:115px; top:10px;'>");

        

        if (blnAccess[blnEdicion] == true){

            if (pEditAlta ==true){

                strScriptHA.append("document.all.").append(pName).append(".readOnly=false;");

            }

            strScriptHA.append(" document.all.").append(pName).append(".value='").append(pDefValue).append("';");

            

            // Manejo de Bitácora: RCS 26 Ago 2005

            if (strBitacora.compareToIgnoreCase("")!=0 && (blnAccess[blnAlta] ==true || blnAccess[blnBaja] ==true)){

                strScriptBITAyB.append("strBitacora = strBitacora + document.all.").append(pName);

                strScriptBITAyB.append(".label + \": \" + document.all.").append(pName).append(".value + \" \";");

            }

 

            if (pEditCambio ==true){

                strScriptHC.append("document.all.").append(pName).append(".readOnly=false;");    

            }

 

            // Manejo de Bitácora: RCS 26 Ago 2005

            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){

                strScriptBITC.append("if(\"").append(pValue).append("\"!=document.all.").append(pName);

                strScriptBITC.append(".value){strBitacora = strBitacora + document.all.").append(pName);

                strScriptBITC.append(".label + \": ant:").append(pValue).append(" nvo:\" + document.all.").append(pName).append(".value + \" \";}");

            }

 

            if (pReqAlta ==true){

                strScriptHA.append(" document.all.").append(pName).append(".className='FReq';");

                strScriptVA.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    

            }

            

            if (pReqCambio ==true){

                strScriptVC.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");    

            }

        }

        StrHtml.append("</p></div>");

        return StrHtml.toString();

    }
    
     public String ObjInputFA(String pTitulo, String pName, String pValue, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, boolean pReqAlta, boolean pReqCambio, int pSize, int pNiveles, String pOnBlur){

        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
       
        String StrNiveles="../";
       
        for (int i=1;i<pNiveles;i++) {
        StrNiveles = StrNiveles + "../";
        }
       
        StringBuffer StrHtml = new StringBuffer();
        
        StrHtml.append(" <DIV id=popCal ");
        StrHtml.append(" style='BORDER-RIGHT: 2px ridge; BORDER-TOP: 2px ridge; Z-INDEX: 100; VISIBILITY: hidden; BORDER-LEFT: 2px ridge; WIDTH: 10px; BORDER-BOTTOM: 2px ridge; POSITION: absolute'"); 
        StrHtml.append(" onclick=event.cancelBubble=true ><IFRAME name=popFrame src='").append(StrNiveles).append("Utilerias/popcjs.htm' frameBorder=0 width=160 scrolling=no height=151></IFRAME> </DIV>");
        
        StrHtml.append(" <SCRIPT event=onclick() for=document> popCal.style.visibility = \"hidden\"; </SCRIPT> ");
        
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex);
        StrHtml.append("' class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:");
        StrHtml.append(xPosition).append("px; top:").append(yPosition).append("px;'>");       
        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>");
 
        StrHtml.append(" <INPUT class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:0px; top:13px;'");
        StrHtml.append(" label='").append(pTitulo).append("' ");
        StrHtml.append(" readOnly size=").append(pSize);
        StrHtml.append(" onBlur='").append(pOnBlur).append("' ");
        StrHtml.append(" id='").append(pName).append("' name='").append(pName).append("' value='").append(pValue).append("'  OnBlur='hideCalendarControl();'></INPUT>");
        StrHtml.append(" <Img src='").append(StrNiveles).append("Imagenes/calendario.GIF'  onClick='popFrame.fPopCalendar(").append(pName).append(",").append(pName).append(",popCal);return false')");
        StrHtml.append(" OndblClick='popCal.style.visibility = \"hidden\";' style='position:absolute; z-index:").append(zIndex).append("; left:115px; top:10px;'>");
       
        if (blnAccess[blnEdicion] == true){
            if (pEditAlta ==true){
                strScriptHA.append("document.all.").append(pName).append(".readOnly=false;");
            }
            strScriptHA.append(" document.all.").append(pName).append(".value='").append(pDefValue).append("';");
           
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && (blnAccess[blnAlta] ==true || blnAccess[blnBaja] ==true)){
                strScriptBITAyB.append("strBitacora = strBitacora + document.all.").append(pName);
                strScriptBITAyB.append(".label + \": \" + document.all.").append(pName).append(".value + \" \";");
            }
 
            if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append(".readOnly=false;");   
            }
 
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){
                strScriptBITC.append("if(\"").append(pValue).append("\"!=document.all.").append(pName);
                strScriptBITC.append(".value){strBitacora = strBitacora + document.all.").append(pName);
                strScriptBITC.append(".label + \": ant:").append(pValue).append(" nvo:\" + document.all.").append(pName).append(".value + \" \";}");
            }
 
            if (pReqAlta ==true){
                strScriptHA.append(" document.all.").append(pName).append(".className='FReq';");
                strScriptVA.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");   
            }
           
            if (pReqCambio ==true){
                strScriptVC.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");   
            }
        }
        StrHtml.append("</p></div>");
        return StrHtml.toString();
    }    

  public String ObjInputFH(String pTitulo, String pName, String pValue, boolean pEditAlta, boolean pEditCambio, int xPosition, int yPosition, String pDefValue, boolean pReqAlta, boolean pReqCambio, int pSize, int pNiveles, String pOnBlur){

        if (xPosition<DivXT){DivXT=xPosition;}
        if (yPosition<DivYT){DivYT=yPosition;}
        if (xPosition>DivXD){DivXD=xPosition;}
        if (yPosition>DivYD){DivYD=yPosition;}
        zIndex = zIndex+1;
        pTitulo = Idioma.getLabel(pTitulo,bytLanguaje);
       
        String StrNiveles="../";
       
        for (int i=1;i<pNiveles;i++) {
        StrNiveles = StrNiveles + "../";
        }
       
        StringBuffer StrHtml = new StringBuffer();
        
        StrHtml.append(" <DIV id=popCal ");
        StrHtml.append(" style='BORDER-RIGHT: 2px ridge; BORDER-TOP: 2px ridge; Z-INDEX: 100; VISIBILITY: hidden; BORDER-LEFT: 2px ridge; WIDTH: 10px; BORDER-BOTTOM: 2px ridge; POSITION: absolute'"); 
        StrHtml.append(" onclick=event.cancelBubble=true ><IFRAME name=popFrame src='").append(StrNiveles).append("Utilerias/popcjsh.htm' frameBorder=0 width=160 scrolling=no height=151></IFRAME> </DIV>");
        
        StrHtml.append(" <SCRIPT event=onclick() for=document> popCal.style.visibility = \"hidden\"; </SCRIPT> ");
        
        StrHtml.append("<div id='D").append(zIndex).append("' Name='D").append(zIndex);
        StrHtml.append("' class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:");
        StrHtml.append(xPosition).append("px; top:").append(yPosition).append("px;'>");       
        StrHtml.append("<p class='FTable'>").append(pTitulo).append("<br>");
 
        StrHtml.append(" <INPUT class='VTable' style='position:absolute; z-index:").append(zIndex).append("; left:0px; top:13px;'");
        StrHtml.append(" label='").append(pTitulo).append("' ");
        StrHtml.append(" readOnly size=").append(pSize);
        StrHtml.append(" onBlur='").append(pOnBlur).append("' ");
        StrHtml.append(" id='").append(pName).append("' name='").append(pName).append("' value='").append(pValue).append("'  OnBlur='hideCalendarControl();'></INPUT>");
        StrHtml.append(" <Img src='").append(StrNiveles).append("Imagenes/calendario.GIF'  onClick='popFrame.fPopCalendar(").append(pName).append(",").append(pName).append(",popCal);return false')");
        StrHtml.append(" OndblClick='popCal.style.visibility = \"hidden\";' style='position:absolute; z-index:").append(zIndex).append("; left:115px; top:10px;'>");
       
        if (blnAccess[blnEdicion] == true){
            if (pEditAlta ==true){
                strScriptHA.append("document.all.").append(pName).append(".readOnly=false;");
            }
            strScriptHA.append(" document.all.").append(pName).append(".value='").append(pDefValue).append("';");
           
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && (blnAccess[blnAlta] ==true || blnAccess[blnBaja] ==true)){
                strScriptBITAyB.append("strBitacora = strBitacora + document.all.").append(pName);
                strScriptBITAyB.append(".label + \": \" + document.all.").append(pName).append(".value + \" \";");
            }
 
            if (pEditCambio ==true){
                strScriptHC.append("document.all.").append(pName).append(".readOnly=false;");   
            }
 
            // Manejo de Bitácora: RCS 26 Ago 2005
            if (strBitacora.compareToIgnoreCase("")!=0 && blnAccess[blnCambio]==true){
                strScriptBITC.append("if(\"").append(pValue).append("\"!=document.all.").append(pName);
                strScriptBITC.append(".value){strBitacora = strBitacora + document.all.").append(pName);
                strScriptBITC.append(".label + \": ant:").append(pValue).append(" nvo:\" + document.all.").append(pName).append(".value + \" \";}");
            }
 
            if (pReqAlta ==true){
                strScriptHA.append(" document.all.").append(pName).append(".className='FReq';");
                strScriptVA.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");   
            }
           
            if (pReqCambio ==true){
                strScriptVC.append("if (document.all.").append(pName).append(".value==''){msgVal=msgVal + ' ").append(pTitulo).append(". '}");   
            }
        }
        StrHtml.append("</p></div>");
        return StrHtml.toString();
    }    

}  



