/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ResourceBundle;

/**
 *
 * @author jomorenoro
 */
public class ConexionSocket{

    private int puerto;
    private String ipHost;
    private Socket host;

    public ConexionSocket() {
        ResourceBundle rb = ResourceBundle.getBundle("recursos/archivosconfiguracion/conexionPropiedades");
        puerto = Integer.parseInt(rb.getString("puerto"));
        ipHost = rb.getString("ipServidor");
    }
    
    public void iniciarConexion(){
        try {
            host = new Socket(ipHost, puerto);
            //conexion ok
        } catch (IOException ex) {
            //conexion fallida
            System.out.println("Error al realizar la conexión:"+ ex.getMessage());
        }
    }

    public Socket getHost() {
        return host;
    }
    
    
    

}
