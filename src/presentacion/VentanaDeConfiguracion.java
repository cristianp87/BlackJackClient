/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author CristianPc
 */
public class VentanaDeConfiguracion extends JDialog {
    private JTextField tfHost;
    private JTextField tfPuerto;
    private JList jlistContacto;
    private JList jlistUsuarios;
    
    String[] contactos = {"Oscar - 301", "Maria - 302", "Juan - 303", "Pedro - 304", "Luis - 305"};
    String[] contactosId = {"301", "302", "303", "304", "305" };
    
    /**
     * Constructor de la ventana de configuracion inicial
     * 
     * @param padre Ventana padre
     */
    public VentanaDeConfiguracion(JFrame padre) {
        super(padre, "Configuracion inicial", true);
        
        JLabel lbUsuario = new JLabel("Usuario que se conecta:");
        JLabel lbContacto = new JLabel("Contacto:"); 
        JLabel lbHost = new JLabel("Host:");
        JLabel lbPuerto = new JLabel("Puerto:");        
        
        tfHost = new JTextField("localhost");
        tfPuerto = new JTextField("3333");
        jlistUsuarios = new JList(contactos);
        jlistUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane1 = new JScrollPane(getJlistUsuarios());
        
        jlistContacto = new JList(contactos);
        jlistContacto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane2 = new JScrollPane(getJlistContacto());
        
        JButton btAceptar = new JButton("Aceptar");
        btAceptar.addActionListener(new ActionListener() {            
            @Override
            public void actionPerformed(ActionEvent e) {
                        
                if (jlistUsuarios.getSelectedIndex()>-1 && jlistContacto.getSelectedIndex()>-1)
                {
                    if (jlistUsuarios.getSelectedIndex() != jlistContacto.getSelectedIndex())
                      setVisible(false);
                    else
                      lanzarMensaje(1);
                }
                else
                    lanzarMensaje(0);
            }            
        });
        
        Container c = this.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(20, 20, 0, 20);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        c.add(lbHost, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        c.add(lbPuerto, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        c.add(lbUsuario, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        c.add(lbContacto, gbc);
        
        gbc.ipadx = 100;
        gbc.fill = GridBagConstraints.HORIZONTAL;        

        gbc.gridx = 1;
        gbc.gridy = 0;
        c.add(tfHost, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        c.add(tfPuerto, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        c.add(scrollPane1, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        c.add(scrollPane2, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        //gbc.insets = new Insets(20, 20, 20, 20);
        c.add(btAceptar, gbc);
        
        this.pack(); // Le da a la ventana el minimo tamaño posible
        this.setLocation(450, 200); // Posicion de la ventana
        this.setResizable(false); // Evita que se pueda estirar la ventana
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Deshabilita el boton de cierre de la ventana 
        this.setVisible(true);
    }
    
     public static void lanzarMensaje(int tipo){ 
         if(tipo==0)
            JOptionPane.showMessageDialog(null, "Debe seleccionar el usuario con el que se desea conectar, y el contacto con el cual hablara");
         else
           if(tipo==1)
               JOptionPane.showMessageDialog(null, "El usuario y el contacto no deben ser iguales");
     }
 

    public String getUsuario(){
        return contactosId[getJlistUsuarios().getSelectedIndex()];
    }    
    public String getHost(){
        return this.tfHost.getText();
    }    
    public int getPuerto(){
        return Integer.parseInt(this.tfPuerto.getText());
    }    
    public String getContacto(){
        return contactosId[getJlistContacto().getSelectedIndex()];
    }
    public String getContactoNombre(){
        return contactos[getJlistContacto().getSelectedIndex()];
    }    
    public JList getJlistContacto() {
        return jlistContacto;
    }
    public void setJlistContacto(JList jlistContacto) {
        this.jlistContacto = jlistContacto;
    }
    public JList getJlistUsuarios() {
        return jlistUsuarios;
    }
    public void setJlistUsuarios(JList jlistUsuarios) {
        this.jlistUsuarios = jlistUsuarios;
    }
}
