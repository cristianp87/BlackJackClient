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
import java.util.ArrayList;
import java.util.StringTokenizer;
import logica.Logica;
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
    private boolean inicioJuego;
    private int idUsuario;
    private String mensajeRecibido;
    private String mensajeEnviado;
    private Thread hiloDibujo;
    private boolean pintaLienzo;
    private Canvas lienzo;
    private BufferedImage dobleBuffer;
    private Juego juego;

    public Modelo() {
        hiloDibujo = new Thread(this);
    }

    //inicia el tablero y se conecta al socket que esta
    //en la lógica
    public void iniciar(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        pintaLienzo = true;
        getVista().setVisible(true);
        getVista().setResizable(false);
        getVista().getBotonPlantar().setEnabled(false);
        getVista().getBotonPedir().setEnabled(false);
        getLogica().conectarSocket();
        if (getLogica().getHost() == null) {
            getVista().getMensaje().setText(EnumMensajeErrores.MENSAJE_CONEXION.getMensaje());
            prueba();
            hiloDibujo.start();
        } else {
            idUsuario = getLogica().generarIdUsuario();
            mensajeEnviado = idUsuario + "|" + "REG";
            enviarMensaje();
            iniciaMensajeria();
        }

    }

    //si hay una conexion con el servidor inicia el envio y recepción de 
    //mensajes
    public void iniciaMensajeria() {
        inicioJuego = true;
        while (inicioJuego) {
            recibirMensaje();
            enviarMensaje();

        }
    }

    //metodo que envia mensaje al servidor
    public void enviarMensaje() {
        try {
            datosSalida = new DataOutputStream(getLogica().getHost().getOutputStream());
            datosSalida.write((mensajeEnviado).getBytes());
        } catch (IOException ex) {
            getVista().getMensaje().setText(EnumMensajeErrores.MENSAJE_ENVIO.getMensaje() + "--" + ex.getMessage());
        }
    }
    //metodo que recibe mensaje del servidor

    public void recibirMensaje() {
        try {
            datosEntrada = new DataInputStream(getLogica().getHost().getInputStream());
            mensajeRecibido = datosEntrada.toString();
            getVista().getMensaje().setText(mensajeRecibido);
        } catch (IOException ex) {
            getVista().getMensaje().setText(EnumMensajeErrores.MENSAJE_RECIBIDO.getMensaje() + "--" + ex.getMessage());
        }
    }

    @Override
    public void run() {
        if (pintaLienzo) {
            repintarLienzo();
        }
    }

    private void repintarLienzo() {
        lienzo = getVista().getLienzo();
        dobleBuffer = new BufferedImage(lienzo.getWidth(), lienzo.getHeight(), BufferedImage.TYPE_INT_ARGB);
        dibujaCartas();
    }

    private void dibujaCartas() {
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

    private void prueba() {
//        juego = new Juego();
//        juego.setNombreJugadorEnemigo("Cristian");
//        ArrayList<Carta> prueba = new ArrayList();
//        for (int i = 2; i < 7; i++) {
//            Carta c = new Carta();
//            c.setNombreCarta("corazones/" + i);
//            c.setValorCarta("" + i);
//            prueba.add(c);
//        }
//        ArrayList<Carta> pruebaEnenmigo = new ArrayList();
//        for (int i = 2; i < 5; i++) {
//            Carta c = new Carta();
//            c.setNombreCarta("picas/" + i);
//            c.setValorCarta("" + i);
//            if (i == 2) {
//                c.setEstadoCarta("T");
//            } else {
//                c.setEstadoCarta("D");
//            }
//            pruebaEnenmigo.add(c);
//        }
//
//        juego.setJuego(prueba);
//        juego.setCartasEnemigo(pruebaEnenmigo);
        pruebaCadena();

        getVista().getMensajeEnemigo().setText(juego.getNombreJugadorEnemigo() + ":" + "Puntuación:" + juego.getSumaCartasEnemigo());
        getVista().getPuntuacion().setText(nombreJugador + " Puntuación:" + juego.getSumaCartas());

    }

    public VistaTablero getVista() {
        if (vista == null) {
            vista = new VistaTablero();
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

    public void pruebaCadena() {
        juego = new Juego();
        String texto = "INJ|1234|John|corazones/A-1-T,picas/2-2-D|A|5678|Cristian|picas//3%3%T,diamantes//5%5%D";
        StringTokenizer st = new StringTokenizer(texto, "|");
        while (st.hasMoreElements()) {
            juego.setComando(st.nextToken());
            juego.setIdUsuario(st.nextToken());
            juego.setNombreJugador(st.nextToken());
            String listaCartas = st.nextToken();
            StringTokenizer st2 = new StringTokenizer(listaCartas, ",");
            while (st2.hasMoreElements()) {
                Carta c = new Carta();
                String carta = st2.nextToken();
                StringTokenizer st21 = new StringTokenizer(carta, "-");
                while (st21.hasMoreElements()) {
                    c.setNombreCarta(st2.nextToken());
                    c.setValorCarta(st2.nextToken());
                    c.setEstadoCarta(st2.nextToken());
                }

                juego.getJuego().add(c);
            }
            juego.setEstado(st.nextToken());
            juego.setIdUsuarioEnemigo(st.nextToken());
            juego.setNombreJugadorEnemigo(st.nextToken());
            String listaCartasEnemigo = st.nextToken();
            StringTokenizer st3 = new StringTokenizer(listaCartasEnemigo, ",");
            while (st3.hasMoreTokens()) {

                String carta = st2.nextToken();
                StringTokenizer st22 = new StringTokenizer(carta, "%");
                Carta c = new Carta();
                while (st22.hasMoreTokens()) {
                    c.setNombreCarta(st3.nextToken());
                    c.setValorCarta(st3.nextToken());
                    c.setEstadoCarta(st3.nextToken());
                }
                juego.getCartasEnemigo().add(c);
            }
        }
    }

}
