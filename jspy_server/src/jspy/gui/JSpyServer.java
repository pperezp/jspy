package jspy.gui;

import com.sun.glass.events.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import jspy.model.ClientThread;
import static jspy.common.K.ALTO;
import static jspy.common.K.ALTO_ZOOM;
import static jspy.common.K.ANCHO;
import static jspy.common.K.ANCHO_ZOOM;
import jspy.common.Mensaje;
import jspy.interprete.Interprete;
import jspy.interprete.excepciones.SyntaxParametroException;
import jspy.server.Server;

public class JSpyServer extends javax.swing.JFrame {

    private List<JLabel> labels;
    private boolean mostrarTodos;
    private JSpyServer _appGUI;
//    private final List<String> listaNombres = new ArrayList<>();

    public JSpyServer() {
        initComponents();
        labels = new ArrayList();
        Server.getInstance().init(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtComando = new javax.swing.JTextField();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        labelsPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaGr = new javax.swing.JList();
        btnShowAll = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

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

        labelsPanel.setLayout(new java.awt.GridLayout(0, 3));
        jScrollPane1.setViewportView(labelsPanel);

        jSplitPane1.setRightComponent(jScrollPane1);

        listaGr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                listaGrMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(listaGr);

        btnShowAll.setText("Mostrar Todos");
        btnShowAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addComponent(btnShowAll, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnShowAll))
        );

        jSplitPane1.setLeftComponent(jPanel2);

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtComando, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listaGrMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaGrMouseReleased
        if (evt.getClickCount() == 2) {
            Object ob = listaGr.getSelectedValue();

            if (ob instanceof ClientThread) {
                ClientThread ct = (ClientThread) ob;
                ct.redimensionarImagen(ANCHO_ZOOM, ALTO_ZOOM);
            }
        }
    }//GEN-LAST:event_listaGrMouseReleased

    private void btnShowAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowAllActionPerformed
        ClientThread ct;
        for (int i = 0; i < labels.size(); i++) {
            ct = Server.getInstance().getClient(i);

            ct.redimensionarImagen(ANCHO, ALTO);
        }
    }//GEN-LAST:event_btnShowAllActionPerformed

    private void txtComandoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComandoKeyReleased
        /*
        Comandos:
            /msg [mensaje] -t
            /msg [mensaje] -a [idAlumno]
         */

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String comando = txtComando.getText();

            if (comando.charAt(0) == '/') {
                String[] palabras = comando.split(" ");

                switch (palabras[0]) {
                    case "/msg": {
                        System.out.println("comando msg...");
                        String mensaje;
                        String parametro;
                        String idAlumno;

                        if (comando.contains("\"")) {
                            mensaje = comando.split("\"")[1];
                            parametro = comando.split("\"")[2].trim().substring(0, 2);
                        } else {
                            mensaje = palabras[1];
                            parametro = palabras[2];
                        }

                        Mensaje men = new Mensaje(mensaje);
                        System.out.println("Mensaje --> " + mensaje);

                        System.out.println("Parametro --> " + parametro);
                        switch (parametro) {
                            case "-t": {
                                // mensaje hacia todos!
                                enviarObjetoATodos(men);
                                break;
                            }

                            case "-a": {
                                // mensaje para alguien en especifico
                                idAlumno = comando.split("\"")[2].trim().split(" ")[1];
                                System.out.println("ID Alumno --> " + idAlumno);
                                enviarObjeto(men, Long.parseLong(idAlumno));
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
            for (String p : i.getParametros(txtComando.getText())) {
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

    public void enviarObjetoATodos(Object o) {
        System.out.println("Enviando un objeto a todos..." + o);
        for (ClientThread h : Server.getInstance().getClients()) {
            h.enviarObjeto(o);
        }
    }

    public void enviarObjeto(Object o, long id) {
        System.out.println("Enviando un objeto a [" + id + "]..." + o);
        for (ClientThread h : Server.getInstance().getClients()) {
            if (h.getId() == id) {
                h.enviarObjeto(o);
                break;
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnShowAll;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel labelsPanel;
    private javax.swing.JList listaGr;
    private javax.swing.JTextField txtComando;
    // End of variables declaration//GEN-END:variables

    /*Este método se llama cuando llega un cliente nuevo*/
    public void setImageToLabel(ClientThread ct) {
        JLabel lbl = new JLabel();

        lbl.setIcon(ct.getImagen().getImageIcon());
        lbl.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        lbl.setName(String.valueOf(ct.getId()));

        lbl.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JLabel lbl = (JLabel) e.getSource();
                    long clientID = Long.parseLong(lbl.getName());

                    ClientThread ct = Server.getInstance().getClient(clientID);
                    if (ct.isImageZoomed()) {
                        ct.redimensionarImagen(ANCHO, ALTO);
                    } else {
                        ct.redimensionarImagen(ANCHO_ZOOM, ALTO_ZOOM);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
//                                        lbl.setFont(new java.awt.Font("Ubuntu", 1, 14));
        lbl.setText(ct.getId() + ".- " + ct.getImagen().getNombreCliente());
        labels.add(lbl);
        labelsPanel.add(lbl);
//        listaNombres.add(ct.getId() + ".- " + ct.getImagen().getNombreCliente());
    }

    public void setClientThreadListModel() {

        listaGr.setModel(new javax.swing.AbstractListModel() {
            List<ClientThread> clientes = Server.getInstance().getClients();

            @Override
            public int getSize() {
                return clientes.size();
            }

            @Override
            public Object getElementAt(int i) {
                return clientes.get(i);
            }
        });
    }

    public void labelImagesUpdate() {
        /*Actualizando las imagenes de los labels*/
        if (Server.idClientDeleted != -1) {
            // se ha borrado al idClienteDeleted
            for (int i = 0; i < labels.size(); i++) {
                if(labels.get(i).getName().equals(String.valueOf(Server.idClientDeleted))){
                    labelsPanel.remove(labels.get(i));
                    labels.remove(i);
                    System.out.println("Removido!!!");
                    break;
                }
            }
            
            // actualizo la el modelo de la lista
            setClientThreadListModel();
            
            Server.idClientDeleted = -1;
        }

        ClientThread ct;
        JLabel lbl;
        for (int i = 0; i < labels.size(); i++) {
            ct = Server.getInstance().getClient(i);

            lbl = labels.get(i);
            lbl.setIcon(ct.getImagen().getImageIcon());
            //              System.out.println(hc.getImagen().getImage().getWidth(null)+" - "+hc.getImagen().getImage().getHeight(null));
        }

        labelsPanel.updateUI();
        /*Actualizando las imagenes de los labels*/
    }

    public List<JLabel> getLabels() {
        return labels;
    }

    public int getLabelsSize() {
        return labels.size();
    }

}
