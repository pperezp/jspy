package jspy.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import jspy.common.K;
import static jspy.common.K.PAUSE;
import jspy.gui.JSpyServer;
import jspy.server.Server;

public class ImageUpdaterThread implements Runnable {

    private final JSpyServer appGUI;

    public ImageUpdaterThread(JSpyServer appGUI) {
        this.appGUI = appGUI;
    }

    @Override
    public void run() {
        int vueltas;
        int clientCount, labelsCount;
        ClientThread ct;

        while (true) {
            clientCount = Server.getInstance().getClientCount();
            
            try {
                labelsCount = appGUI.getLabelsSize();
                if (clientCount > labelsCount) { // si llego un nuevo cliente
                    vueltas = clientCount - labelsCount;

                    for (int i = (clientCount - vueltas); i < clientCount; i++) {
                        try {
                            ct = Server.getInstance().getClient(i);
                            appGUI.setImageToLabel(ct);

                            i++;
                            appGUI.setClientThreadListModel();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }

                appGUI.labelImagesUpdate();

                
            } catch (NullPointerException ex){
                System.out.println("GUI esta como null");
            }
            try {
                Thread.sleep(PAUSE);
            } catch (InterruptedException ex) {
                Logger.getLogger(ImageUpdaterThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
