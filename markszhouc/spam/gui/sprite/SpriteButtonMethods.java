package markszhouc.spam.gui.sprite;

import javafx.scene.control.Alert;
import markszhouc.spam.encoding.EmailController;
import markszhouc.spam.gui.GUIHandler;
import markszhouc.spam.gui.GUIScreen;

import java.nio.file.Files;
import java.nio.file.Paths;

public class SpriteButtonMethods {

    public static void setAddress() {
        String userIn = GUIHandler.createTextInput("Enter a valid email " +
                "to recieve the \"spam\" emails.");
        if (userIn == null || userIn.equals(""))
            return;
        EmailController.RECEIVING_EMAIL = userIn;
        if (userIn.length() > 20)
            userIn = userIn.substring(0, 17) + "...";
        GUIScreen.getButtons()[0].setSuppl(userIn);
    }

    public static void setSender() {
        String userIn = GUIHandler.createTextInput("Enter a valid email " +
                "to send the \"spam\" emails.");
        if (userIn == null || userIn.equals(""))
            return;
        EmailController.SENDING_EMAIL = userIn;
        if (userIn.length() > 20)
            userIn = userIn.substring(0, 17) + "...";
        GUIScreen.getButtons()[1].setSuppl(userIn);
    }

    public static void setSenderPassword() {
        String userIn = GUIHandler.createTextInput("Enter the " +
                "password to the sender of the \"spam\" emails.");
        if (userIn == null || userIn.equals(""))
            return;
        EmailController.SENDING_PASSWORD = userIn;
        for (int i = Math.min(userIn.length() / 3 + 1, 3); i < userIn.length(); ++i)
            userIn = userIn.substring(0, i) + "*" + userIn.substring(i + 1);
        if (userIn.length() > 20)
            userIn = userIn.substring(0, 17) + "...";
        GUIScreen.getButtons()[2].setSuppl(userIn);
    }

    public static void setNumToSend() {
        String userIn = GUIHandler.createTextInput("Enter the " +
                "number of emails to send in total.  This number must be\n" +
                "greater than or equal to the assigned position of any file " +
                "in the queue.");
        if (userIn == null || userIn.trim().equals(""))
            return;
        try {
            int i = Integer.parseInt(userIn.trim());
            if (! EmailController.setNumToSend(i)) {
                GUIHandler.createAlert("Number not accepted.");
                return;
            }
        } catch (Exception e) {
            GUIHandler.createAlert("Error reading input.  Did you input a number?");
            return;
        }
        GUIScreen.getButtons()[3].setSuppl(userIn);
    }

    public static void addFile() {
        String userIn = GUIHandler.createTextInput("Enter the file " +
                "to send in the spam email.");
        if (userIn == null || userIn.equals(""))
            return;
        if (! Files.exists(Paths.get(userIn))) {
            GUIHandler.createAlert("Not a valid file.");
            return;
        }
        String userIn2 = GUIHandler.createTextInput("Enter the position in queue " +
                "where this file should be sent.  No two files can share the " +
                "same position in queue.");
        try {
            int i = Integer.parseInt(userIn2);
            if (! EmailController.addFile(userIn, i)) {
                GUIHandler.createAlert("Number not accepted.");
                return;
            }
        } catch (Exception e) {
            GUIHandler.createAlert("Error reading input.  Did you input a number?");
            return;
        }
    }

    public static void setLogo() {
        String userIn = GUIHandler.createTextInput("Enter the path to the logo " +
                "to encrypt the files into.");
        if (userIn == null || userIn.equals(""))
            return;
        if (! Files.exists(Paths.get(userIn))) {
            GUIHandler.createAlert("Not a valid file.");
            return;
        }
        EmailController.LOGO_PATH = userIn;
        GUIScreen.getButtons()[5].setSuppl(formatFilePath(userIn));
        GUIScreen.getButtons()[12].setSuppl(formatFilePath(userIn));
    }

    public static void setOutputPath() {
        String userIn = GUIHandler.createTextInput("Enter the path to the output file. " +
                "Include the name of the output file but not the extension.");
        if (userIn == null || userIn.equals(""))
            return;
        EmailController.OUTPUT_PATH = userIn;
        GUIScreen.getButtons()[13].setSuppl(formatFilePath(userIn));
    }

    public static void setColorReadingData() {
        String userIn = GUIHandler.createTextInput("Enter the path to the color data" +
                " reader.  Ensure that it follows proper format.");
        if (userIn == null || userIn.equals(""))
            return;
        if (! Files.exists(Paths.get(userIn))) {
            GUIHandler.createAlert("Not a valid file.");
            return;
        }
        if (! EmailController.readInColorData(userIn)) {
            GUIHandler.createAlert("Failed to read the color data.");
            GUIScreen.getButtons()[6].setSuppl("");
            return;
        }
        GUIScreen.getButtons()[6].setSuppl(formatFilePath(userIn));
        GUIScreen.getButtons()[14].setSuppl(formatFilePath(userIn));
    }

    public static void setTimeBetween() {
        String userIn = GUIHandler.createTextInput("Enter the time to wait " +
                "between emails (in seconds).");
        if (userIn == null || userIn.equals(""))
            return;
        try {
            int i = Integer.parseInt(userIn);
            EmailController.TIME_BETWEEN = i;
        } catch (Exception e) {
            GUIHandler.createAlert("Error reading input.  Did you input a number?");
            return;
        }
        GUIScreen.getButtons()[7].setSuppl(userIn);
    }

    public static void begin() {
        if (! EmailController.canStartEncoding()) {
            GUIHandler.createAlert("Not enough information given to start encoding!");
            return;
        }
        else if (! EmailController.beginEncoding()) {
            GUIHandler.createAlert("No emails to send!");
            return;
        }
    }

    public static void pause() {
        EmailController.pause();
    }

    public static void clear() {
        EmailController.clearQueue();
    }

    public static void viewQueue() {
        GUIHandler.createInfo("File Queue: \n" + EmailController.getQueueAsString());
    }

    public static String formatFilePath(String userIn) {
        if (userIn.contains("\\"))
            userIn = userIn.substring(userIn.lastIndexOf("\\"));
        else if (userIn.contains("/"))
            userIn = userIn.substring(userIn.lastIndexOf("/"));
        if (userIn.length() > 20)
            userIn = userIn.substring(0, 17) + "...";
        return userIn;
    }

    public static void decode() {
        if (! EmailController.canStartDecoding()) {
            GUIHandler.createAlert("Not enough information given to start decoding!");
            return;
        }
        else if (! EmailController.beginDecoding()) {
            GUIHandler.createAlert("Failed to decode the file!");
            return;
        }
    }

}
