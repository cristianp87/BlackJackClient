/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.ArrayList;

/**
 *
 * @author jomorenoro
 */
public class Juego {
    
    private String nombreJugador;
    private String idJugador;
    private ArrayList<Carta> juego;
    private ArrayList<Carta> cartasEnemigo;
    private int sumaCartas;

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public ArrayList<Carta> getJuego() {
        return juego;
    }

    public void setJuego(ArrayList<Carta> juego) {
        this.juego = juego;
    }

    public ArrayList<Carta> getCartasEnemigo() {
        return cartasEnemigo;
    }

    public void setCartasEnemigo(ArrayList<Carta> cartasEnemigo) {
        this.cartasEnemigo = cartasEnemigo;
    }

    public int getSumaCartas() {
        return sumaCartas;
    }

    public void setSumaCartas(int sumaCartas) {
        this.sumaCartas = sumaCartas;
    }
    
    
    
    
    
}
