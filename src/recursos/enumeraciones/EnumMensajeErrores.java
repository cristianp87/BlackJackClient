/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursos.enumeraciones;

/**
 *
 * @author jomorenoro
 */
public enum EnumMensajeErrores {
    
    MENSAJE_CONEXION("ERROR AL CONECTAR CON EL SERVIDOR"),
    MENSAJE_ENVIO("ERROR AL ENVIAR DATOS "),
    MENSAJE_RECIBIDO("ERROR AL RECIBIR DATOS");



    private String mensaje;
    
    private EnumMensajeErrores(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    
  


    
    
    
}
