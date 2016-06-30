package jspy.model;

import jspy.common.EnhancedImage;
import jspy.common.ClientImageByte;
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
import jspy.common.JspyConstants;
import static jspy.common.JspyConstants.WIDTH;
import static jspy.common.JspyConstants.HEIGHT;

/**
 *
 * @author pperezp
 */
public class ClientThread extends Thread{
    private final Socket socket;
    private ImageIcon imgen;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Object object;
    private ClientImageByte clientByteImage;
    private EnhancedImage enhancedImage;
    private boolean alive;
    private String name;
    private int width;
    private int height;
    private final DisconnectedClientListener disconnectedClientListener;

    /**
     *
     * @param socket
     * @param listener
     */
    public ClientThread(Socket socket,DisconnectedClientListener listener) {
        this.socket = socket;
        System.out.println("["+new Date()+"] Nuevo Cliente --> "+socket.getInetAddress());
        alive = true;
        redimensionarImagen(WIDTH, HEIGHT);
        this.disconnectedClientListener = listener;
    }
    
    @Override
    public void run(){
        boolean primeraVez = true;
        while(alive){
            try {
                inputStream = socket.getInputStream();
                System.out.println(new Date()+" Esperando un objeto desde el cliente...");
                objectInputStream = new ObjectInputStream(inputStream);
                System.out.println(new Date()+" Llegó objeto!");
                object = objectInputStream.readObject();
                
                
                if(object instanceof ClientImageByte){
                    clientByteImage = (ClientImageByte)object;
                    System.out.println(new Date()+" Llegó imagen!");
                    
                    enhancedImage = new EnhancedImage(clientByteImage, width, height);
                    
                    if(primeraVez){
                        primeraVez = false;
                        name = clientByteImage.getClientName();
                    }
                }
                
//                Thread.sleep(PAUSE);
            } catch(java.net.SocketException ex){
                alive = false;
            } catch(EOFException ex){
                alive = false;
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
        System.out.println("Hilo cliente terminado! --> "+getId());
        disconnectedClientListener.whenClientDisconnected(this.getId());
    }

    /**
     *
     * @return
     */
    public EnhancedImage getImagen() {
        return enhancedImage;
    }
    
    /**
     *
     * @param ancho
     * @param alto
     */
    public void redimensionarImagen(int ancho, int alto){
        this.width = ancho;
        this.height = alto;
    }
    
    /**
     *
     * @return
     */
    public boolean isImageZoomed(){
        return this.width == JspyConstants.ZOOM_WIDTH;
    }
    
    /**
     *
     * @param o
     */
    public void enviarObjeto(Object o){
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(o);
            System.out.println("Objeto enviado! --> "+o);
//            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return name;
    }

    @Override
    public String toString() {
        return this.getId() + ".- "+this.getNombre();
    }
    
    
}
