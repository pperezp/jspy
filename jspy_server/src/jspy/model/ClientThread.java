package jspy.model;

import jspy.common.Imagen;
import jspy.common.ImagenBytes;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import jspy.common.K;
import static jspy.common.K.ALTO;
import static jspy.common.K.ANCHO;

/**
 *
 * @author pperezp
 */
public class ClientThread extends Thread{
    private Socket socket;
    private ImageIcon imgen;
    private InputStream inputStream;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Object o;
    private ImagenBytes imBytes;
    private Imagen im;
    private boolean vivo;
    private String nombre;
    private int ancho;
    private int alto;
    private DisconnectedClientListener listener;

    public ClientThread(Socket socket,DisconnectedClientListener listener) {
        this.socket = socket;
        System.out.println("["+new Date()+"] Nuevo Cliente --> "+socket.getInetAddress());
        vivo = true;
        redimensionarImagen(ANCHO, ALTO);
        this.listener = listener;
    }
    
    @Override
    public void run(){
        boolean primeraVez = true;
        while(vivo){
            try {
                inputStream = socket.getInputStream();
                System.out.println(new Date()+" Esperando un objeto desde el cliente...");
                ois = new ObjectInputStream(inputStream);
                System.out.println(new Date()+" Llegó objeto!");
                o = ois.readObject();
                
                
                if(o instanceof ImagenBytes){
                    imBytes = (ImagenBytes)o;
                    System.out.println(new Date()+" Llegó imagen!");
                    
                    im = new Imagen(imBytes, ancho, alto);
                    
                    if(primeraVez){
                        primeraVez = false;
                        nombre = imBytes.nombre;
                    }
                }
                
//                Thread.sleep(PAUSE);
            } catch(java.net.SocketException ex){
                vivo = false;
            } catch(EOFException ex){
                vivo = false;
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
        System.out.println("Hilo cliente terminado! --> "+getId());
        listener.whenClientDisconnected(this.getId());
    }

    public Imagen getImagen() {
        return im;
    }
    
    public void redimensionarImagen(int ancho, int alto){
        this.ancho = ancho;
        this.alto = alto;
    }
    
    public boolean isImageZoomed(){
        return this.ancho == K.ANCHO_ZOOM;
    }
    
    public void enviarObjeto(Object o){
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(o);
            System.out.println("Objeto enviado! --> "+o);
//            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return this.getId() + ".- "+this.getNombre();
    }
    
    
}
