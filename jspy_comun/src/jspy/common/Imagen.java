package jspy.common;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Imagen {
    private BufferedImage bufferedImage;
    private Image image;
    private ImageIcon imageIcon;
    private ImagenBytes imBytes;
    
    public Imagen(ImagenBytes imgBytes) throws IOException{
        this.imBytes = imgBytes;
        bufferedImage = ImageIO.read(new ByteArrayInputStream(imgBytes.getImgBytes()));
        image = bufferedImage.getScaledInstance(
                bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
    }
    
    public Imagen(ImagenBytes imgBytes, int ancho, int alto) throws IOException{
        this.imBytes = imgBytes;
        bufferedImage = ImageIO.read(new ByteArrayInputStream(imgBytes.getImgBytes()));
        image = bufferedImage.getScaledInstance(
                ancho,
                alto,
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    public String getNombreCliente(){
        return imBytes.nombre;
    }
    
}
