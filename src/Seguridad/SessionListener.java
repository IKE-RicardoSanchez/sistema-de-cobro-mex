/*
 * SessionListener.java
 *
 * Created on 18 de marzo de 2009, 01:33 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Seguridad;
//import Utilerias.SessionCobros;

/**
 *
 * @author gvazquez
 */

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import Utilerias.UtileriasBDF;
//import Asterisk.IkeAstDeslogueo;

public class SessionListener implements HttpSessionAttributeListener {

    public void attributeAdded(HttpSessionBindingEvent se) {
    // TODO Auto-generated method stub
     //System.out.println("Inicia Session Atribute" );
    }

    public void attributeRemoved(HttpSessionBindingEvent se) {
    // TODO Auto-generated method stub
    //clusrApp
        //System.out.println("attributeRemoved clusrapp:");
      if (se.getName().toString().equals("clUsrApp")){
              String strclUsrApp="";
              if (se.getValue()==null){
                 strclUsrApp="";
              }else{
                 System.out.println("Fin de Sesion DB");
                 strclUsrApp=se.getValue().toString();
                 UtileriasBDF.ejecutaSQLNP("sp_tmkgcA_sys_ADM_Salir " + strclUsrApp);

                 //SessionCobros.ValidaPath(se.getSession());
              }
              strclUsrApp =null;
      }
    }

    public void attributeReplaced(HttpSessionBindingEvent se) {
    // TODO Auto-generated method stub
     //System.out.println("Remplaza Session Atribute" );
    }
}
