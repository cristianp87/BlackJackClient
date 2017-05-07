/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import entidades.Carta;
import entidades.Juego;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Logica;
import recursos.enumeraciones.EnumComando;
import recursos.enumeraciones.EnumMensajeErrores;

/**
 *
 * @author CristianPc
 */
public class Modelo implements Runnable {

    private VistaTablero vista;
    private Logica logica;
    private String nombreJugador;
    private DataInputStream datosEntrada;
    private DataOutputStream datosSalida;
    private int idUsuario;
    private String mensajeRecibido;
    private String mensajeEnviado;
    private Thread hiloDibujo;
    private boolean pintaLienzo;
    private Canvas lienzo;
    private BufferedImage dobleBuffer;
    private Juego juego;
    private Socket socket;

    public Modelo() {
        hiloDibujo = new Thread(this);
    }

    //inicia el tablero y se conecta al socket que esta
    //en la lógica
    public void iniciar(String nombreJugador) throws IOException {
        this.nombreJugador = nombreJugador;
        pintaLienzo = true;
        getVista().setVisible(true);
        getVista().setResizable(false);
        getVista().getBotonPlantar().setEnabled(false);
        getVista().getBotonPedir().setEnabled(false);
        getVista().getBotonNuevo().setEnabled(false);
        setSocket(getLogica().getHost());
        if (this.socket == null) {
            getVista().getMensaje().setText(EnumMensajeErrores.MENSAJE_CONEXION.getMensaje());
        } else {
            idUsuario = getLogica().generarIdUsuario();
            this.logica.setIdUsuario("" + idUsuario);
            this.logica.setNombreUsuario(nombreJugador);
            mensajeEnviado = getLogica().armaMensajeEnvio(EnumComando.REG.name());
            datosSalida = new DataOutputStream(socket.getOutputStream());
            datosSalida.writeUTF(mensajeEnviado);
            datosEntrada = new DataInputStream(socket.getInputStream());
            mensajeRecibido = datosEntrada.readUTF();
            System.out.println("mensaje recibido" + mensajeRecibido);
            this.juego = getLogica().convierteMensaje(mensajeRecibido);
            muestraMensajesVista();
            hiloDibujo.start();
            getVista().establecerEventos();
            verificaMensajeEntrante();

        }

    }

    public void muestraMensajesVista() {
        getVista().getPuntuacion().setText("NOMBRE: " + this.juego.getNombreJugador() + " Puntuacion:" + this.juego.getSumaCartas());
        getVista().getMensajeEnemigo().setText("NOMBRE:" + this.juego.getNombreJugadorEnemigo() + " Puntuación:" + this.juego.getSumaCartasEnemigo());

    }

    public void verificaMensajeEntrante() {
        getVista().getBotonNuevo().setEnabled(false);
        if (juego.getComando().equalsIgnoreCase(EnumComando.JUG.name())) {
            getVista().getBotonPedir().setEnabled(true);
            getVista().getBotonPlantar().setEnabled(true);
            getVista().getMensaje().setText(EnumMensajeErrores.MENSAJE_JUG.getMensaje());

        }
        if (juego.getComando().equalsIgnoreCase(EnumComando.REG.name())) {
            getVista().getMensaje().setText(EnumMensajeErrores.MENSAJE_REG.getMensaje());
            recibirMensajesServidor();
        }
        if (juego.getComando().equalsIgnoreCase(EnumComando.PER.name())) {
            juego.setCartasEnemigo(getLogica().destapaCartas(juego.getCartasEnemigo()));
            getVista().getMensaje().setText(EnumMensajeErrores.MENSAJE_PER.getMensaje());
            getVista().getBotonPedir().setEnabled(false);
            getVista().getBotonPlantar().setEnabled(false);
            getVista().getBotonNuevo().setEnabled(true);
            getVista().getMensajeEnemigo().setText(EnumMensajeErrores.MENSAJE_GAN.getMensaje());
        }
        if (juego.getComando().equalsIgnoreCase(EnumComando.GAN.name())) {
            juego.setCartasEnemigo(getLogica().destapaCartas(juego.getCartasEnemigo()));
            getVista().getMensaje().setText(EnumMensajeErrores.MENSAJE_GAN.getMensaje());
            getVista().getBotonPedir().setEnabled(false);
            getVista().getBotonPlantar().setEnabled(false);
            getVista().getBotonNuevo().setEnabled(true);
            getVista().getMensajeEnemigo().setText(EnumMensajeErrores.MENSAJE_PER.getMensaje());
        }
        if (juego.getComando().equalsIgnoreCase(EnumComando.EMP.name())) {
            juego.setCartasEnemigo(getLogica().destapaCartas(juego.getCartasEnemigo()));
            getVista().getMensaje().setText(EnumMensajeErrores.MENSAJE_EMP.getMensaje());
            getVista().getBotonPedir().setEnabled(false);
            getVista().getBotonPlantar().setEnabled(false);
            getVista().getBotonNuevo().setEnabled(true);
            getVista().getMensajeEnemigo().setText(EnumMensajeErrores.MENSAJE_EMP.getMensaje());
        }

    }

    public void recibirMensajesServidor() {
        DataInputStream datosE = null;
        try {
            datosE = new DataInputStream(socket.getInputStream());
            mensajeRecibido = datosE.readUTF();
            this.juego = getLogica().convierteMensaje(mensajeRecibido);
            muestraMensajesVista();
            verificaMensajeEntrante();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (pintaLienzo) {
            try {
                repintarLienzo();
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void repintarLienzo() {
        lienzo = getVista().getLienzo();
        dobleBuffer = new BufferedImage(lienzo.getWidth(), lienzo.getHeight(), BufferedImage.TYPE_INT_ARGB);
        dibujaCartas();
    }

    public void nuevoJuego() {
        lienzo.repaint();
        getVista().getMensajeEnemigo().setText("");
        getVista().getMensaje().setText("");
    }

    private void dibujaCartas() {
        if (juego != null && !juego.getJuego().isEmpty()) {
            //900 * 490
            int inicioDibujo = lienzo.getWidth() / 2;
            int numeroCartas = juego.getJuego().size();
            inicioDibujo = inicioDibujo + (numeroCartas * 40);
            for (Carta item : juego.getJuego()) {
                inicioDibujo += -80;
                dobleBuffer.getGraphics().drawImage(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/cartas/" + item.getNombreCarta() + ".png")).getImage(), inicioDibujo, 350, null);
            }
            numeroCartas = juego.getCartasEnemigo().size();
            inicioDibujo = lienzo.getWidth() / 2;
            inicioDibujo = inicioDibujo + (numeroCartas * 40);
            for (Carta item : juego.getCartasEnemigo()) {
                inicioDibujo += -80;
                if (!item.getEstadoCarta().equalsIgnoreCase("T")) {
                    dobleBuffer.getGraphics().drawImage(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/cartas/" + item.getNombreCarta() + ".png")).getImage(), inicioDibujo, 0, null);
                } else {
                    dobleBuffer.getGraphics().drawImage(new javax.swing.ImageIcon(getClass().getResource("/recursos/imagenes/fondos/dorso.png")).getImage(), inicioDibujo, 0, null);
                }
            }

            Graphics g = lienzo.getGraphics();
            g.drawImage(dobleBuffer, 0, 0, lienzo);
        }
    }

    public VistaTablero getVista() {
        if (vista == null) {
            vista = new VistaTablero(this);
        }
        return vista;
    }

    public Logica getLogica() {
        if (logica == null) {
            logica = new Logica();
        }
        return logica;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

}
