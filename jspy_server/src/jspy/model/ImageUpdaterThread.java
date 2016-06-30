package jspy.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import jspy.common.JspyConstants;
import static jspy.common.JspyConstants.PAUSE;
import jspy.gui.JSpyServer;
import jspy.server.Server;

/**
 *
 * @author LAB-315
 */
public class ImageUpdaterThread implements Runnable {

    private final JSpyServer appGUI;

    /**
     *
     * @param appGUI
     */
    public ImageUpdaterThread(JSpyServer appGUI) {
        this.appGUI = appGUI;
    }

    @Override
    public void run() {
        int laps;
        int clientCount, labelsCount;
        ClientThread ct;

        while (true) {
            clientCount = Server.getInstance().getClientCount();
            
            try {
                labelsCount = appGUI.getLabelsSize();
                if (clientCount > labelsCount) { // si llego un nuevo cliente
                    laps = clientCount - labelsCount;

                    for (int i = (clientCount - laps); i < clientCount; i++) {
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
