/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.net.Socket;

/**
 *
 * @author jomorenoro
 */
public class Logica {

    private ConexionSocket conexion;
    private Socket host;

    public void conectarSocket() {
        getConexion().iniciarConexion();
        host = getConexion().getHost();
    }

    public ConexionSocket getConexion() {
        if (conexion == null) {
            conexion = new ConexionSocket();
        }
        return conexion;
    }

    public Socket getHost() {
        return host;
    }
    
    

}
