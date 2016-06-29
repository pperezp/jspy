package jspy.gui;

import java.awt.AWTException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import jspy.common.ImagenBytes;
import static jspy.common.K.PAUSE;
import org.jcap.JCap;

public class JSpyClient extends javax.swing.JFrame {

    public JSpyClient() {
        initComponents();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnComenzar = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnComenzar.setText("Comenzar");
        btnComenzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComenzarActionPerformed(evt);
            }
        });

        jTextField1.setText("localhost");

        txtNombre.setText("Nombre Apellido");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(btnComenzar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnComenzar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnComenzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComenzarActionPerformed

        
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                boolean primeraVez = true;
                try {
                    System.out.println("Intentando conectarse...");
                    Socket s = new Socket(jTextField1.getText(), 7777);
                    
                    System.out.println("Conectado");
                    ObjectOutputStream oos;
                    
                    JCap cap = new JCap();
                    byte[] capBytes;
                    ImagenBytes im;
                    
                    while (true) {
                        try {
                            capBytes = cap.getCapPantallaCompletaInBytes();
                            im = new ImagenBytes(capBytes);
                            
//                            if(primeraVez){
//                                primeraVez = false;
//                                im.nombre = txtNombre.getText();
//                            }
                            
                            im.nombre = txtNombre.getText();
                            
                            oos = new ObjectOutputStream(s.getOutputStream());
                            
                            oos.writeObject(im);
                            
                            Thread.sleep(PAUSE);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(JSpyClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(JSpyClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (AWTException ex) {
                    Logger.getLogger(JSpyClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

    }//GEN-LAST:event_btnComenzarActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JSpyClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnComenzar;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
