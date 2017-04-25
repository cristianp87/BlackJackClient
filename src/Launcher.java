
import java.io.IOException;
import javax.swing.text.BadLocationException;
import presentacion.Modelo;
import presentacion.Vista;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CristianPc
 */
public class Launcher {

    private Modelo visorCliente;
    
    public Launcher(){
        Vista v = new  Vista();
        v.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Launcher();
    } 
    
}
