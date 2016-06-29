package jspy.common;

import java.io.Serializable;

public class ImagenBytes implements Serializable{
    private byte[] imgBytes;
    public String nombre;

    public ImagenBytes(byte[] imgBytes) {
        this.imgBytes = imgBytes;
    }

    public byte[] getImgBytes() {
        return imgBytes;
    }

    public void setImgBytes(byte[] imgBytes) {
        this.imgBytes = imgBytes;
    }
    
    
}
