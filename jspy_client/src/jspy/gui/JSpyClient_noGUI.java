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
import jspy.common.ClientImageByte;
import static jspy.common.JspyConstants.PAUSE;
import jspy.common.Message;
import org.jcap.JCap;

/**
 *
 * @author pperezp
 */
public class JSpyClient_noGUI {

    private static Socket socketClient;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        final String ip, name;

        Scanner scan = new Scanner(System.in);

        System.out.print("IP: ");
        ip = scan.nextLine();
        scan = new Scanner(System.in);

        System.out.print("Nombre: ");
        name = scan.nextLine();

        /*Hilo para enviar los pantallazos*/
        new Thread(new Runnable() {
            boolean connected = false;

            @Override
            public void run() {
                try {

                    ObjectOutputStream objectOutputStream;

                    JCap cap = new JCap();
                    byte[] capBytes;
                    ClientImageByte clientImageByte;

                    tryConnect();

                    while (true) {
                        try {
                            capBytes = cap.getCapPantallaCompletaInBytes();
                            clientImageByte = new ClientImageByte(capBytes);
                            clientImageByte.setClientName(name);
                            objectOutputStream = new ObjectOutputStream(socketClient.getOutputStream());
                            objectOutputStream.writeObject(clientImageByte);
                            Thread.sleep(PAUSE);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(JSpyClient.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SocketException ex) {
                            System.out.println("Conexión perdida con el servidor");
                            tryConnect();
                        } catch (IOException ex) {
                            Logger.getLogger(JSpyClient_noGUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                } catch (AWTException ex) {
                    Logger.getLogger(JSpyClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void tryConnect() {
                connected = false;
                while (!connected) {
                    try {
                        System.out.println("Intentando conectarse...");
                        socketClient = new Socket(ip, 7777);
                        connected = true;
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
                ObjectInputStream objectInputStream;
                Object o;
                boolean disconnected = true;

                try {
                    while (true) {
                        while (socketClient == null) {
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
                            objectInputStream = new ObjectInputStream(socketClient.getInputStream());
                            System.out.println("Esperando Objeto 2...");
                            o = objectInputStream.readObject();
                            System.out.println("Objeto recibido!");

                            disconnected = false;

                            if (o instanceof Message) {
                                System.out.println("Es un mensaje!");
                                Message message = (Message) o;

                                JOptionPane.showMessageDialog(null, message.getContent());
                            }
                        } catch (SocketException | EOFException se) {
                            disconnected = true;
                            try {
                                System.out.println("Conexión perdida...5 seg...");
                                Thread.sleep(5000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(JSpyClient_noGUI.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }

                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(JSpyClient_noGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Hilo recibir mensaje TERMINADO!.");
            }
        }).start();

        /*Hilo para recibir objetos*/
    }

}
