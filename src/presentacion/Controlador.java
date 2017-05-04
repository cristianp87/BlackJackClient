/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import entidades.Juego;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author CristianPc
 */
public class Controlador implements ActionListener {

    private VistaTablero vista;
    private Socket socket;
    private Juego juego;
    private DataOutputStream salidaDatos;

    public Controlador(VistaTablero vista) {
        this.vista = vista;
        this.socket = vista.getModelo().getSocket();
        this.juego = vista.getModelo().getJuego();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boton = (JButton) e.getSource();
        if ("PEDIR".equalsIgnoreCase(boton.getText())) {
            juego.setComando("PED");
            try {
                salidaDatos = new DataOutputStream(this.socket.getOutputStream());
                salidaDatos.writeUTF(this.vista.getModelo().getLogica().convierteObjetoJuego(juego));
            } catch (IOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
