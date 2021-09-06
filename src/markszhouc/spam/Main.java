package markszhouc.spam;

import markszhouc.spam.encoding.EmailController;
import markszhouc.spam.gui.GUIWindow;

public class Main {

    public static void main(String[] args) {
        EmailController.initByteData();
        GUIWindow.launchGUI("Spymail");
    }

    private static int rgb(int r, int g, int b) {
        return (r << 16) + (g << 8) + b;
    }

}
