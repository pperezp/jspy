package jspy.common;

import java.io.Serializable;

public class ClientImageByte implements Serializable{
    private byte[] bytes;
    private String clientName;

    public ClientImageByte(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    
}
