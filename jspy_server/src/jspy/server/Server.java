/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jspy.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jspy.gui.JSpyServer;
import jspy.model.ClientThread;
import jspy.model.ImageUpdaterThread;
import jspy.model.DisconnectedClientListener;

/**
 *
 * @author LAB-315
 */
public class Server implements Runnable, DisconnectedClientListener {

    private static Server _server;
    private List<ClientThread> clients;
    private ServerSocket serverSocket;
    private JSpyServer appGUI;
    private ImageUpdaterThread imageUpdateThread;
    public static long idClientDeleted = -1;

    private Server() {
        try {
            clients = new ArrayList<>();
//        labels = new ArrayList<>();
            serverSocket = new ServerSocket(7777);
            System.out.println("Escuchando en el puerto 7777...");

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Server getInstance() {
        if (_server == null) {
            _server = new Server();
        }
        return _server;
    }

    public void init(JSpyServer appGUI) {
        this.appGUI = appGUI;
        new Thread(this).start();
        imageUpdateThread = new ImageUpdaterThread(appGUI);
        new Thread(imageUpdateThread).start();
    }

    // <editor-fold defaultstate="collapsed" desc="Hilo del servidor">
    @Override
    public void run() {
        Socket socketCliente;
        ClientThread hc;
        while (true) {
            try {
                System.out.println("Esperando a clientes...");
                socketCliente = serverSocket.accept();

                hc = new ClientThread(
                        socketCliente,
                        this
                );
                hc.start();
                clients.add(hc);

            } catch (IOException ex) {
                Logger.getLogger(JSpyServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //</editor-fold>

    @Override
    public void whenClientDisconnected(long id) {
        deleteClient(id);
    }
    
    public void deleteClient(long id){
        clients.remove(getClient(id));
        Server.idClientDeleted = id;
    }

    public List<ClientThread> getClients() {
        return clients;
    }

    public int getClientCount() {
        return clients.size();
    }

    public ClientThread getClient(long id) {
        for (ClientThread hc : clients) {
            if (hc.getId() == id) {
                return hc;
            }
        }

        return null;
    }

    public ClientThread getClient(int index) {
        return clients.get(index);
    }

}
