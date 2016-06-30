package jspy.common;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class EnhancedImage {
    private final BufferedImage bufferedImage;
    private final Image image;
    private final ImageIcon imageIcon;
    private final ClientImageByte imBytes;
    
    public EnhancedImage(ClientImageByte imgBytes) throws IOException{
        this.imBytes = imgBytes;
        bufferedImage = ImageIO.read(new ByteArrayInputStream(imgBytes.getBytes()));
        image = bufferedImage.getScaledInstance(
                bufferedImage.getWidth(),
                bufferedImage.getHeight(),
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
    }
    
    public EnhancedImage(ClientImageByte imgBytes, int ancho, int alto) throws IOException{
        this.imBytes = imgBytes;
        bufferedImage = ImageIO.read(new ByteArrayInputStream(imgBytes.getBytes()));
        image = bufferedImage.getScaledInstance(
                ancho,
                alto,
                Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public Image getImage() {
        return image;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public String getClientName(){
        return imBytes.getClientName();
    }
    
}
