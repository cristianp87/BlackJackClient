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
    MENSAJE_RECIBIDO("ERROR AL RECIBIR DATOS"),
    MENSAJE_JUG("ESCOJA SU ACCION, PEDIR O PLANTAR"),
    MENSAJE_REG("ESPERE MIENTRAS SU RIVAL JUEGA"),
    MENSAJE_PER("LO SENTIMOS, HA PERDIDO"),
    MENSAJE_GAN("FELICITACIONES, ERES EL GANADOR"),
    MENSAJE_EMP("EMPATE");



    private String mensaje;
    
    private EnumMensajeErrores(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    
  


    
    
    
}
