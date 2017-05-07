/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import entidades.Carta;
import entidades.Juego;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import recursos.enumeraciones.EnumComando;


/**
 *
 * @author jomorenoro
 */
public class Logica {

    private Socket host;
    private String idUsuario;
    private String nombreUsuario;
    private int puerto;
    private String ipHost;

    public void conectarSocket() throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("recursos/archivosconfiguracion/conexionPropiedades");
        puerto = Integer.parseInt(rb.getString("puerto"));
        ipHost = rb.getString("ipServidor");
        host = new Socket(ipHost, puerto);
    }

    public int generarIdUsuario() {
        return 1000 + (int) (Math.random() * 8999);
    }

    public String armaMensajeEnvio(String tipo) {
        Juego juego = new Juego();
        if (EnumComando.REG.name().equalsIgnoreCase(tipo)) {
            juego.setComando(EnumComando.REG.name());
            juego.setIdUsuario(this.idUsuario);
            juego.setNombreJugador(nombreUsuario);
            juego.setEstado("A");
        }
        return convierteObjetoJuego(juego);
    }

    public Juego convierteMensaje(String mensaje) {
        Juego juego = new Juego();
        StringTokenizer st = new StringTokenizer(mensaje, "|");
        while (st.hasMoreElements()) {
            juego.setComando(st.nextToken());
            juego.setIdUsuario(st.nextToken());
            if (idUsuario.equalsIgnoreCase(juego.getIdUsuario())) {
                juego.setNombreJugador(st.nextToken());
                String listaCartas = st.nextToken();
                juego.setJuego(convierteListaCartas(listaCartas));
                juego.setEstado(st.nextToken());
                juego.setIdUsuarioEnemigo(st.nextToken());
                juego.setNombreJugadorEnemigo(st.nextToken());
                String listaCartasEnemigo = st.nextToken();
                juego.setCartasEnemigo(convierteListaCartas(listaCartasEnemigo));
            } else {
                juego.setNombreJugadorEnemigo(st.nextToken());
                String listaCartas = st.nextToken();
                juego.setCartasEnemigo(convierteListaCartas(listaCartas));
                juego.setEstado(st.nextToken());
                juego.setIdUsuarioEnemigo(st.nextToken());
                juego.setNombreJugadorEnemigo(st.nextToken());
                String listaCartasEnemigo = st.nextToken();
                juego.setJuego(convierteListaCartas(listaCartasEnemigo));
            }
        }
        return juego;
    }

    public String convierteObjetoJuego(Juego objeto) {
        String mensaje = "";
        mensaje += objeto.getComando().concat("|").concat(objeto.getIdUsuario().concat("|"))
                .concat(objeto.getNombreJugador().concat("|")).concat(recorreArray(objeto.getJuego())).concat("|")
                .concat(objeto.getEstado().concat("|")).concat(objeto.getIdUsuarioEnemigo()).concat("|").concat(objeto.getNombreJugadorEnemigo())
                .concat("|").concat(recorreArray(objeto.getCartasEnemigo()));
        return mensaje;
    }

    public ArrayList<Carta> convierteListaCartas(String mensajeCartas) {
        ArrayList<Carta> listaCartas = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(mensajeCartas, ",");
        while (st.hasMoreElements()) {
            String mensajeCarta = st.nextToken();
            listaCartas.add(convierteMensajeCarta(mensajeCarta));
        }
        return listaCartas;
    }

    public Carta convierteMensajeCarta(String mensajeCarta) {
        Carta c = new Carta();
        StringTokenizer st = new StringTokenizer(mensajeCarta, "%");
        while (st.hasMoreElements()) {
            c.setNombreCarta(st.nextToken());
            c.setValorCarta(st.nextToken());
            c.setEstadoCarta(st.nextToken());
        }
        return c;
    }

    public String recorreArray(ArrayList<Carta> listaCartas) {
        String mensaje = "";
        for (Carta item : listaCartas) {
            mensaje = mensaje.concat(item.getNombreCarta().concat("%")).concat(item.getValorCarta().concat("%")).concat(item.getEstadoCarta().concat(","));
        }
        if ("".equalsIgnoreCase(mensaje)) {
            mensaje = "null";
        }
        return mensaje;
    }
    
    public ArrayList<Carta> destapaCartas(ArrayList<Carta> cartasRival){
        for(Carta item:cartasRival){
            item.setEstadoCarta("D");
        }
            
        return cartasRival;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }



    public Socket getHost() {
        if(host ==null){
            try {
                conectarSocket();
            } catch (IOException ex) {
                Logger.getLogger(Logica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return host;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

}
