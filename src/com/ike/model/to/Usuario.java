/*
 * Usuario.java
 *
 * Created on 22 de marzo de 2006, 06:16 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package com.ike.model.to;

/**
 *
 * @author cabrerar
 */
public class Usuario implements java.io.Serializable{

    private String activo;
    private String mess;
    private String clUsrApp;
    private String password;
    private String nombre;
    private String fechaAlta;
    private String fechaInicio;
    private String correo;
    private String CambioPwd;
    private String AccesoId;
    private String TipoMenu;
    private String PermisoConf;

    /** Creates a new instance of Usuario */
    public Usuario() {
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getClUsrApp() {
        return clUsrApp;
    }

    public void setClUsrApp(String clUsrApp) {
        this.clUsrApp = clUsrApp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }    

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
 
    public String getCambioPwd() {
        return CambioPwd;
    }

    public void setCambioPwd(String CambioPwd) {
        this.CambioPwd = CambioPwd;
    }


    public String getAccesoId() {
        return AccesoId;
    }

    public void setAccesoId(String AccesoId) {
        this.AccesoId = AccesoId;
    }

    public String getTipoMenu() {
        return TipoMenu;
    }

    public void setTipoMenu(String tMenu) {
        this.TipoMenu = tMenu;
    }

     public String getPermisoConf() {
        return PermisoConf;
    }

    public void setPermisoConf(String tMenu) {
        this.PermisoConf = tMenu;
    }
}
