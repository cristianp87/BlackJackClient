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
    private String nombreJugadorEnemigo;
    private String idJugador;
    private ArrayList<Carta> juego;
    private ArrayList<Carta> cartasEnemigo;
    private int sumaCartas;
    private int sumaCartasEnemigo;
    private String estado;
    private String estadoJugadorEnemigo;

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
        if(!this.getJuego().isEmpty()){
            this.getJuego().forEach((item) -> {
                sumaCartas+=Integer.parseInt(item.getValorCarta());
            });
        }
        return sumaCartas;
    }

    public void setSumaCartas(int sumaCartas) {
        this.sumaCartas = sumaCartas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getSumaCartasEnemigo() {
        if(!this.getCartasEnemigo().isEmpty()){
            this.getCartasEnemigo().forEach((item)-> {
                if("D".equalsIgnoreCase(item.getEstadoCarta())){
                    sumaCartasEnemigo+=Integer.parseInt(item.getValorCarta());
                }           
        });
        }
        return sumaCartasEnemigo;
    }

    public void setSumaCartasEnemigo(int sumaCartasEnemigo) {
        this.sumaCartasEnemigo = sumaCartasEnemigo;
    }

    public String getNombreJugadorEnemigo() {
        return nombreJugadorEnemigo;
    }

    public void setNombreJugadorEnemigo(String nombreJugadorEnemigo) {
        this.nombreJugadorEnemigo = nombreJugadorEnemigo;
    }

    public String getEstadoJugadorEnemigo() {
        return estadoJugadorEnemigo;
    }

    public void setEstadoJugadorEnemigo(String estadoJugadorEnemigo) {
        this.estadoJugadorEnemigo = estadoJugadorEnemigo;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
