
import java.io.IOException;
import javax.swing.text.BadLocationException;
import presentacion.Modelo;

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
    
    public Launcher() throws BadLocationException, IOException{
        visorCliente = new Modelo();
        visorCliente.iniciar();        
    }
    
    public static void main(String[] args) throws BadLocationException, IOException {
        new Launcher();
    } 
    
}
