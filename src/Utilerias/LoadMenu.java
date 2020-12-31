package Utilerias;

/*
 * LoadMenus.java
 *
 * Created on 13 de febrero de 2006, 01:36 PM
 */

/**
 *
 * @author  cabrerar
 */

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.sql.ResultSet;
import Utilerias.UtileriasBDF;
import java.util.Iterator;
import Utilerias.MenuItem;
import java.util.Map;
import java.util.Set;
import java.sql.Connection;
import java.util.Hashtable;

/**
 *
 * @author  cabrerar
 */

public class LoadMenu {
    private static HashMap comboHashParents = null;
    private static HashMap comboHashMenu = null;
    private static boolean isConfigured = false;

    /** Creates a new instance of ComboSingleton */
    private LoadMenu()
    {
    }
    
    private synchronized static boolean loadComboList()
    {
         if(isConfigured == false)
         {
           Connection con = null;
           con = UtileriasBDF.getConnection();
           ResultSet rsP = null;
           ResultSet rsM = null;
           comboHashParents = new HashMap();
           try{
               rsP = UtileriasBDF.rsSQLNP( "sp_tmkgcA_sys_GetMenues 1");
               while(rsP.next())
               {
                   MenuItem MenuItemI = new MenuItem();
                   
                   MenuItemI.setIntclUsrApp(rsP.getInt("clUsrApp"));
                   MenuItemI.setIntclMenu(rsP.getInt("clMenu"));
                   MenuItemI.setStrdsMenu(rsP.getString("dsMenu"));
                   MenuItemI.setStrdsPagina("");
                   comboHashParents.put(Integer.toString(MenuItemI.getIntclUsrApp()),MenuItemI);
               }

               rsM = UtileriasBDF.rsSQLNP( "sp_tmkgcA_sys_GetMenues 2");
               while(rsM.next())
               {
                   MenuItem MenuItemI = new MenuItem();
                   
                   MenuItemI.setIntclUsrApp(rsM.getInt("clUsrApp"));
                   MenuItemI.setIntclMenu(rsM.getInt("clMenuParent"));
                   MenuItemI.setStrdsMenu(rsM.getString("dsMenu"));
                   MenuItemI.setIntclMenu(rsM.getInt("clMenu"));
                   MenuItemI.setStrdsPagina(rsM.getString("NombrePaginaWeb"));
                   comboHashMenu.put(Integer.toString(MenuItemI.getIntclUsrApp())+'-'+ Integer.toString(MenuItemI.getIntclMenuParent()),MenuItemI);
               }
               isConfigured = true;
           }
           catch(Exception e)
           {
               System.out.print(e.getMessage());
               isConfigured = false;
               return false;
           }
           finally
           {
              try
              {
                if (rsP!=null)
                {
                  rsP.close();
                  rsP=null;
                }
                if (rsM!=null)
                {
                  rsM.close();
                  rsM=null;
                }
                if (con!=null)
                {
                  con.close();
                  con=null;
                  
                }

              }catch(Exception ee)
              {
                ee.printStackTrace();
              }
               System.out.println("Entre a Load de Menues");
           }
         }
         return true;
    }
    
    public static HashMap getComboHashParents()
    {
        loadComboList();
        return comboHashParents;
    }

    
/*    public static void GetMenu(String pLlave){
        MenuItem MenuItemI =null;
        HashMap hshMapP=LoadMenu.getComboHashParents();
        if (hshMapP!=null){
          MenuItemI = (MenuItem)hshMapP.get(pLlave);
          if (MenuItemLst !=null){
            int x=0;
            int xR = 1;
            for(x=0; x<MenuItemLst.size(); x++, xR++)
            {
                MenuItem MenuItemI = (MenuItem)MenuItemLst.get(x);
                System.out.print(MenuItemI.getStrdsMenu());
            }
          }
        }
        MenuItemLst =null;
        //return "";
    }*/
    

    public static void main(String args[])
    {
    }
    
    public static void reLoad(){
        isConfigured=false;
    }
}
