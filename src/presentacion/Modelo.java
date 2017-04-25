/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import logica.Logica;
import recursos.enumeraciones.EnumMensajeErrores;

/**
 *
 * @author CristianPc
 */
public class Modelo {

    private VistaTablero vista;
    private Logica logica;
    private String nombreJugador;
    private DataInputStream datosEntrada;
    private DataOutputStream datosSalida;

    public void iniciar() {
        getVista().setVisible(true);
        getVista().setResizable(false);
        getVista().getBotonPlantar().setEnabled(false);
        getVista().getBotonPedir().setEnabled(false);
        getLogica().conectarSocket();
        if(getLogica().getHost()==null){
            getVista().getMensaje().setText(EnumMensajeErrores.MENSAJE_CONEXION.getMensaje());
        }
       
    }
    
    public void enviarMensaje(){
        try {
            datosSalida = new DataOutputStream(getLogica().getHost().getOutputStream());
            datosSalida.write(("").getBytes());
        } catch (IOException ex) {
           getVista().getMensaje().setText(EnumMensajeErrores.MENSAJE_ENVIO.getMensaje()+"--"+ex.getMessage());
        }
    }
    
    public void recibirMensaje(){
        try {
            datosEntrada = new DataInputStream(getLogica().getHost().getInputStream());
            String mensajeEntrante = datosEntrada.toString();
             getVista().getMensaje().setText(mensajeEntrante);
        } catch (IOException ex) {
            getVista().getMensaje().setText(EnumMensajeErrores.MENSAJE_RECIBIDO.getMensaje()+"--"+ex.getMessage());
        }
    }

    
    
    public VistaTablero getVista() {
        if(vista==null){
            vista = new VistaTablero();
        }
        return vista;
    }

    public Logica getLogica() {
        if(logica == null){
            logica = new Logica();
        }
        return logica;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }
    
    
    

}
