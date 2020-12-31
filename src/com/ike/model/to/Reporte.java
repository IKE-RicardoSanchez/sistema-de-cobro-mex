/*
 * CorreoGM.java
 *
 * Created on 4 de mayo de 2006, 12:13 PM
 */

package com.ike.model.to;
/*
 * @author perezern
 * @version
 */
public class Reporte implements java.io.Serializable{
    private String dsconcesionario;
    private String propietario;
    private String dsConcesionarioGM;
    private String Orden;
    private String Telefono1;
    private String Telefono2;
    private String Telefono3;
    private String Modelo;
    private String Marca;
    private String Submarca;
    private String Intentos;
    private String VentaMO;
    private String porcentaje;
    private String mejorardes;

    /** Creates a new instance of Usuario */
    public Reporte() {
    }

    public String getDsconcesionario() {
        return dsconcesionario;
    }

    public void setDsconcesionario(String dsconcesionario) {
        this.dsconcesionario = dsconcesionario;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getDsConcesionarioGM() {
        return dsConcesionarioGM;
    }

    public void setDsConcesionarioGM(String dsConcesionarioGM) {
        this.dsConcesionarioGM = dsConcesionarioGM;
    }

    public String getOrden() {
        return Orden;
    }

    public void setOrden(String Orden) {
        this.Orden = Orden;
    }

    public String getTelefono1() {
        return Telefono1;
    }

    public void setTelefono1(String Telefono1) {
        this.Telefono1 = Telefono1;
    }

    public String getTelefono2() {
        return Telefono2;
    }

    public void setTelefono2(String Telefono2) {
        this.Telefono2 = Telefono2;
    }

    public String getTelefono3() {
        return Telefono3;
    }

    public void setTelefono3(String Telefono3) {
        this.Telefono3 = Telefono3;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String Modelo) {
        this.Modelo = Modelo;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public String getSubmarca() {
        return Submarca;
    }

    public void setSubmarca(String Submarca) {
        this.Submarca = Submarca;
    }

    public String getIntentos() {
        return Intentos;
    }

    public void setIntentos(String Intentos) {
        this.Intentos = Intentos;
    }

    public String getVentaMO() {
        return VentaMO;
    }

    public void setVentaMO(String VentaMO) {
        this.VentaMO = VentaMO;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getMejorardes() {
        return mejorardes;
    }

    public void setMejorardes(String mejorardes) {
        this.mejorardes = mejorardes;
    }

  
  
}
