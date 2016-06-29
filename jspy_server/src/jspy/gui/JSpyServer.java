package jspy.gui;

import com.sun.glass.events.KeyEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import jspy.model.HiloCliente;
import static jspy.common.K.ALTO;
import static jspy.common.K.ALTO_ZOOM;
import static jspy.common.K.ANCHO;
import static jspy.common.K.ANCHO_ZOOM;
import static jspy.common.K.PAUSE;
import jspy.common.Mensaje;
import jspy.interprete.Interprete;
import jspy.interprete.excepciones.SyntaxParametroException;

public class JSpyServer extends javax.swing.JFrame {

    private ServerSocket server;
    private List<HiloCliente> clientes;
    private List<JLabel> labels;
    private boolean mostrarTodos;
    private final List<String> listaNombres = new ArrayList<>();

    public JSpyServer() {
        initComponents();

        try {
            clientes = new ArrayList<>();
            labels = new ArrayList<>();
            server = new ServerSocket(7777);
            System.out.println("Escuchando en el puerto 777...");

            /*---------------Hilo del server----------------*/
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Socket socketCliente;
                    HiloCliente hc;

                    while (true) {
                        try {
                            System.out.println("Esperando a clientes...");
                            socketCliente = server.accept();

                            hc = new HiloCliente(socketCliente, String.valueOf(clientes.size()));
                            hc.start();
                            clientes.add(hc);

                        } catch (IOException ex) {
                            Logger.getLogger(JSpyServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }).start();
            /*---------------Hilo del server----------------*/

            new Thread(new Runnable() {

                @Override
                public void run() {
                    JLabel lbl;
                    HiloCliente hc;
                    int vueltas;
                    int cantClientes, cantLabels;
                    

                    while (true) {
                        cantClientes = clientes.size();
                        cantLabels = labels.size();
                        try {
                            if (cantClientes > cantLabels) { // si llego un nuevo cliente

                                vueltas = cantClientes - cantLabels;

                                for (int i = (cantClientes - vueltas); i < cantClientes; i++) {
                                    try {
                                        lbl = new JLabel();
                                        hc = clientes.get(i);

                                        lbl.setIcon(hc.getImagen().getImageIcon());
                                        lbl.setHorizontalTextPosition(SwingConstants.CENTER);
                                        lbl.setVerticalTextPosition(SwingConstants.BOTTOM);
                                        lbl.setFont(new java.awt.Font("Ubuntu", 1, 14));
                                        lbl.setText(i + ".- " + hc.getImagen().getNombreCliente());
                                        labels.add(lbl);

                                        jPanel1.add(lbl);
                                        listaNombres.add(i + ".- " + hc.getImagen().getNombreCliente());

                                        i++;
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage());
                                    }

                                    listaGr.setModel(new javax.swing.AbstractListModel() {

                                        @Override
                                        public int getSize() {
                                            return listaNombres.size();
                                        }

                                        @Override
                                        public Object getElementAt(int i) {
                                            return listaNombres.get(i);
                                        }
                                    });
                                }
                            }

                            /*Actualizando las imagenes de los labels*/
                            for (int i = 0; i < labels.size(); i++) {
                                hc = clientes.get(i);

                                labels.get(i).setIcon(hc.getImagen().getImageIcon());
//                                System.out.println(hc.getImagen().getImage().getWidth(null)+" - "+hc.getImagen().getImage().getHeight(null));
                            }

                            jPanel1.updateUI();
                            /*Actualizando las imagenes de los labels*/

                            Thread.sleep(PAUSE);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(JSpyServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }).start();
        } catch (IOException ex) {
            Logger.getLogger(JSpyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtComando = new javax.swing.JTextField();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaGr = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtComando.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComandoActionPerformed(evt);
            }
        });
        txtComando.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtComandoKeyReleased(evt);
            }
        });

        jPanel1.setLayout(new java.awt.GridLayout(0, 3));
        jScrollPane1.setViewportView(jPanel1);

        jSplitPane1.setRightComponent(jScrollPane1);

        listaGr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                listaGrMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(listaGr);

        jButton1.setText("Mostrar Todos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtComando, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComando, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listaGrMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaGrMouseReleased
        if (evt.getClickCount() == 2) {
            String idStr = listaGr.getSelectedValue().toString().split("\\.")[0];
            int id = Integer.parseInt(idStr);
            
            HiloCliente hc = clientes.get(id);
            hc.redimensionarImagen(ANCHO_ZOOM, ALTO_ZOOM);
        }
    }//GEN-LAST:event_listaGrMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        HiloCliente hc;
        for(int i=0; i<labels.size(); i++){
            hc = clientes.get(i);
            
            hc.redimensionarImagen(ANCHO, ALTO);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtComandoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComandoKeyReleased
        /*
        Comandos:
            /msg [mensaje] -t
            /msg [mensaje] -a [idAlumno]
        */
        
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            String comando = txtComando.getText();
            
            if(comando.charAt(0) == '/'){
                String[] palabras = comando.split(" ");
                
                switch(palabras[0]){
                    case "/msg":{
                        System.out.println("comando msg...");
                        String mensaje;
                        String parametro;
                        String idAlumno;
                        
                        if(comando.contains("\"")){
                            mensaje = comando.split("\"")[1];
                            parametro = comando.split("\"")[2].trim().substring(0, 2);
                        }else{
                            mensaje = palabras[1];
                            parametro = palabras[2];
                        }
                        
                        Mensaje men = new Mensaje(mensaje);
                        System.out.println("Mensaje --> "+mensaje);
                        
                        System.out.println("Parametro --> "+parametro);
                        switch(parametro){
                            case "-t":{
                                // mensaje hacia todos!
                                enviarObjetoATodos(men);
                                break;
                            }
                            
                            case "-a":{
                                // mensaje para alguien en especifico
                                idAlumno = comando.split("\"")[2].trim().split(" ")[1];
                                System.out.println("ID Alumno --> "+idAlumno);
                                enviarObjeto(men, idAlumno);
                                break;
                            }
                        }
                        
                        break;
                    }
                }
            }
            
            txtComando.setText("");
            txtComando.requestFocus();
        }
        
        /*Filtro de busqueda de usuarios*/
        
        /*final List<String> filtro = new ArrayList<>();
        for(String nombre : listaNombres){
            if(nombre.toLowerCase().contains(jTextField1.getText().toLowerCase())){
                filtro.add(nombre);
            }
        }
        
        listaGr.setModel(new javax.swing.AbstractListModel() {

            @Override
            public int getSize() {
                return filtro.size();
            }

            @Override
            public Object getElementAt(int i) {
                return filtro.get(i);
            }
        });*/
    }//GEN-LAST:event_txtComandoKeyReleased

    private void txtComandoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComandoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComandoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            Interprete i = new Interprete();
            
//        System.out.println(i.getIdComando(txtComando.getText()));
            
            for(String p:i.getParametros(txtComando.getText())){
                System.out.println(p);
            }
        } catch (SyntaxParametroException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JSpyServer().setVisible(true);
            }
        });
    }

    public void enviarObjetoATodos(Object o){
        System.out.println("Enviando un objeto a todos..."+o);
        for(HiloCliente h : clientes){
            h.enviarObjeto(o);
        }
    }
    
    public void enviarObjeto(Object o, String id){
        System.out.println("Enviando un objeto a ["+id+"]..."+o);
        for(HiloCliente h : clientes){
            if(h.getID().equals(id)){
                h.enviarObjeto(o);
                break;
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList listaGr;
    private javax.swing.JTextField txtComando;
    // End of variables declaration//GEN-END:variables
}
