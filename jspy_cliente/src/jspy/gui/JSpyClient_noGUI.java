/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jspy.gui;

import java.awt.AWTException;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jspy.common.ImagenBytes;
import static jspy.common.K.PAUSE;
import jspy.common.Mensaje;
import org.jcap.JCap;

/**
 *
 * @author pperezp
 */
public class JSpyClient_noGUI {

    private static Socket sock;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final String ip, nombre;

        Scanner leer = new Scanner(System.in);

        System.out.print("IP: ");
        ip = leer.nextLine();
        leer = new Scanner(System.in);

        System.out.print("Nombre: ");
        nombre = leer.nextLine();

        /*Hilo para enviar los pantallazos*/
        new Thread(new Runnable() {
            boolean conectado = false;

            @Override
            public void run() {
                try {

                    ObjectOutputStream oos;

                    JCap cap = new JCap();
                    byte[] capBytes;
                    ImagenBytes im;

                    intentarConectarse();

                    while (true) {
                        try {
                            capBytes = cap.getCapPantallaCompletaInBytes();
                            im = new ImagenBytes(capBytes);

                            im.nombre = nombre;

                            oos = new ObjectOutputStream(sock.getOutputStream());

                            oos.writeObject(im);

                            Thread.sleep(PAUSE);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(JSpyClient.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SocketException ex) {
                            System.out.println("Conexión perdida con el servidor");
                            intentarConectarse();
                        } catch (IOException ex) {
                            Logger.getLogger(JSpyClient_noGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                } catch (AWTException ex) {
                    Logger.getLogger(JSpyClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void intentarConectarse() {
                conectado = false;
                while (!conectado) {
                    try {
                        System.out.println("Intentando conectarse...");
                        sock = new Socket(ip, 7777);
                        conectado = true;
                        System.out.println("Conectado");
                    } catch (IOException ex) {
                        System.out.println("Error --> " + ex.getMessage());
                        System.out.println("Intentando conectarse en 5 segundos...");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex1) {
                            Logger.getLogger(JSpyClient_noGUI.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                }
            }
        }).start();

        /*Hilo para enviar los pantallazos*/
        /*Hilo para recibir objetos*/
        new Thread(new Runnable() {

            @Override
            public void run() {
                ObjectInputStream ois;
                Object o;
                boolean desconectado = true;

                try {
                    while (true) {
                        while (sock == null) {
                            System.out.println("Esperando a conexión...5 seg...");
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(JSpyClient_noGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        System.out.println("Conectado! [Hilo recibir objetos]");

                        try {
                            System.out.println("Esperando Objeto...");
                            ois = new ObjectInputStream(sock.getInputStream());
                            System.out.println("Esperando Objeto 2...");
                            o = ois.readObject();
                            System.out.println("Objeto recibido!");

                            desconectado = false;

                            if (o instanceof Mensaje) {
                                System.out.println("Es un mensaje!");
                                Mensaje men = (Mensaje) o;

                                JOptionPane.showMessageDialog(null, men.getContenido());
                            }
                        } catch (EOFException e) {
                            desconectado = true;
                            try {
                                System.out.println("Conexión perdida...5 seg...");
                                Thread.sleep(5000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(JSpyClient_noGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(JSpyClient_noGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(JSpyClient_noGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Hilo recibir mensaje TERMINADO!.");
            }
        }).start();

        /*Hilo para recibir objetos*/
    }

}
