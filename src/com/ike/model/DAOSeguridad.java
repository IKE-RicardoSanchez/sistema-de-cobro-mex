/*
 * DAOSeguridad.java
 *
 * Created on 22 de marzo de 2006, 06:11 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.ike.model;

import com.ike.model.to.Usuario;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author cabrerar
 */

public class DAOSeguridad extends DAOBASE{
    
    /** Creates a new instance of DAOSeguridad */

    
    public Usuario getUsuario( String user, String password, String host) throws DAOException{
        StringBuffer sb = new StringBuffer();
        Collection col = null;
        
        sb.append("sp_tmkgcA_sys_EncriptDesEncriptPassword '")
            .append( user) 
           //.append( user.toUpperCase()) 
           .append("',1,'")
           .append(host)
           .append("','")
            .append(password)
           // .append(password.toUpperCase() )
           .append("'");
        
        col = this.rsSQLNP(sb.toString(), new UsuarioFiller());

        Iterator it = col.iterator();
        return it.hasNext() ? (Usuario) it.next() : null;

    }
    
    
    public class UsuarioFiller implements LlenaDatos{
        public Object llena(java.sql.ResultSet rs) throws java.sql.SQLException{
            Usuario usuario = new Usuario();
            usuario.setActivo( rs.getString("Activo"));
            usuario.setMess( rs.getString("strMess"));
            usuario.setClUsrApp( rs.getString("clUsrApp"));
            usuario.setPassword( rs.getString("password"));
            usuario.setNombre( rs.getString("Nombre"));
            usuario.setFechaAlta( rs.getString("FechaAlta"));
            usuario.setFechaInicio( rs.getString("FechaInicio"));
            usuario.setCorreo( rs.getString("Correo")); 
            usuario.setCambioPwd( rs.getString("CambioPwd"));             
            usuario.setAccesoId(rs.getString("AccesoId"));
            usuario.setTipoMenu(rs.getString("tipoMenu"));
            usuario.setPermisoConf(rs.getString("PermisoConf"));
            
            return usuario;
        }
    }
    
    
}
