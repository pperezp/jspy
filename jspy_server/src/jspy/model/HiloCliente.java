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
import javax.swing.JLabel;
import static jspy.common.K.ALTO;
import static jspy.common.K.ANCHO;
import static jspy.common.K.PAUSE;

/**
 *
 * @author pperezp
 */
public class HiloCliente extends Thread{
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
    private String id;
    private int ancho;
    private int alto;

    public HiloCliente(Socket socket, String id) {
        this.socket = socket;
        System.out.println("["+new Date()+"]Nuevo Cliente --> "+socket.getInetAddress());
        vivo = true;
        redimensionarImagen(ANCHO, ALTO);
        this.id = id;
    }
    
    @Override
    public void run(){
        boolean primeraVez = true;
        while(vivo){
            try {
                inputStream = socket.getInputStream();
                ois = new ObjectInputStream(inputStream);
//                System.out.println("Llego objeto!");
                o = ois.readObject();
                
                
                if(o instanceof ImagenBytes){
                    imBytes = (ImagenBytes)o;
//                    System.out.println("Llego imagen!");
                    
                    im = new Imagen(imBytes, ancho, alto);
                    
                    if(primeraVez){
                        primeraVez = false;
                        nombre = imBytes.nombre;
                    }
                }
                
//                Thread.sleep(PAUSE);
            } catch(EOFException ex){
                vivo = false;
            } catch (IOException ex) {
                Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
        System.out.println("Hilo cliente terminado! --> "+nombre);
    }

    public Imagen getImagen() {
        return im;
    }
    
    public void redimensionarImagen(int ancho, int alto){
        this.ancho = ancho;
        this.alto = alto;
    }
    
    public void enviarObjeto(Object o){
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(o);
            System.out.println("Objeto enviado! --> "+o);
//            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getID() {
        return id;
    }
    
    
    
    
}
