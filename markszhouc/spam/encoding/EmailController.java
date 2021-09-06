package markszhouc.spam.encoding;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import markszhouc.spam.gui.GUIHandler;
import markszhouc.spam.gui.GUIScreen;
import markszhouc.spam.gui.sprite.SpriteButtonMethods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;

public class EmailController {

    public static String RECEIVING_EMAIL = "", SENDING_EMAIL = "", SENDING_PASSWORD = "";
    public static String LOGO_PATH = null, OUTPUT_PATH = null;
    public static int TIME_BETWEEN = 60, NUM_TO_SEND = 0;
    public static boolean COLOR_DATA_INPUT_SUCCESSFUL = true;

    private static int[] byteData;
    private static TreeMap<Integer, String> fileQueue = new TreeMap<>();
    private static int counter = 0;
    private static boolean counting = false;

    public static void initByteData() {
        byteData = new int[] {
                rgb(80,0,1),
                rgb(80,1,1),
                rgb(80,0,0),
                rgb(80,1,0),
                rgb(81,0,1),
                rgb(81,1,1),
                rgb(81,0,0),
                rgb(81,1,0),
                rgb(80,0,2),
                rgb(81,1,2),
                rgb(81,0,2),
                rgb(80,1,2),
                rgb(80,2,1),
                rgb( 81,2,0),
                rgb( 80,2,0),
                rgb(81,2,1),
                rgb(82,0,1)
        };
    }

    public static boolean canStartDecoding() {
        return LOGO_PATH != null &&
                OUTPUT_PATH != null &&
                COLOR_DATA_INPUT_SUCCESSFUL;
    }

    public static boolean canStartEncoding() {
        return RECEIVING_EMAIL.length() > 0 &&
                LOGO_PATH != null &&
                SENDING_PASSWORD.length() > 0 &&
                SENDING_EMAIL.length() > 0 &&
                COLOR_DATA_INPUT_SUCCESSFUL;
    }

    public static void pause() {
        if (counting)
            counting = false;
    }

    public static boolean beginEncoding() {
        if (NUM_TO_SEND == 0)
            return false;
        if (counting) {
            sendNextEmail();
            counter = 0;
            return true;
        }
        counting = true;
        return true;
    }

    public static boolean beginDecoding() {
        try {
            if (ByteDecoder.decode(LOGO_PATH, OUTPUT_PATH, byteData))
                GUIHandler.createInfo("File decoded: found information!");
            else GUIHandler.createInfo("No information in this file.");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getQueueAsString() {
        String output = "";
        for (int id : fileQueue.keySet())
            output = id + ": " + SpriteButtonMethods.formatFilePath(fileQueue.get(id)) + "\n" + output;
        output += "Emails left to send: " + NUM_TO_SEND;
        return output;
    }

    public static void update(int timeElapsed) {
        if (! counting)
            return;
        counter += timeElapsed;
        GUIScreen.getButtons()[8].setSuppl("Next email will send in " + (TIME_BETWEEN - counter / 1000) + " seconds");
        if (counter >= TIME_BETWEEN * 1000)
            sendNextEmail();
    }

    private static void sendNextEmail() {
        // send the email
        System.out.println(fileQueue.get(1));
        Image encodedImage = fileQueue.get(1) == null ? ByteEncoder.encodeFakeFile(LOGO_PATH, byteData) :
                ByteEncoder.encodeFile(LOGO_PATH, fileQueue.get(1), byteData);
        try {
            EmailSender.send(encodedImage);
        } catch (Exception e) {
            System.out.println("ERROR sending email!");
            e.printStackTrace();
        }
        // decrement queue line
        TreeSet<Integer> ids = new TreeSet<>();
        ids.addAll(fileQueue.keySet());
        for (int id : ids) {
            if (id > 1)
                fileQueue.put(id - 1, fileQueue.get(id));
            fileQueue.put(id, null);
        }
        // decrement counter and numToSend
        counter = 0;
        if (--NUM_TO_SEND == 0)
            counting = false;
    }

    public static void clearQueue() {
        fileQueue.clear();
    }

    public static boolean addFile(String path, int order) {
        if (fileQueue.get(order) != null)
            return false;
        fileQueue.put(order, path);
        return true;
    }

    public static boolean setNumToSend(int newNum) {
        if (newNum < 0)
            return false;
        for (int time : fileQueue.keySet())
            if (time > newNum)
                return false;
        NUM_TO_SEND = newNum;
        return true;
    }

    public static boolean readInColorData(String path) {
        COLOR_DATA_INPUT_SUCCESSFUL = false;
        if (! Files.exists(Paths.get(path)))
            return false;
        try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
            String next = br.readLine();
            int size = Integer.parseInt(next.trim());
            byteData = new int[size];
            int count = 0;
            while ((next = br.readLine()) != null) {
                next = next.trim();
                if (next.startsWith("#") || next.startsWith("//"))
                    continue;
                String[] split = next.split("[ ]+");
                System.out.println(Arrays.toString(split));
                byteData[count] = Integer.parseInt(split[0]) << 16 +
                        Integer.parseInt(split[1]) << 8 + Integer.parseInt(split[2]);
                count++;
            }
            if (count < size)
                return false;
            COLOR_DATA_INPUT_SUCCESSFUL = true;
            return true;
        } catch (Exception e) {
            System.out.println("Error reading colorData file.");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean doesFileExist(String path) {
        return Files.exists(Paths.get(path));
    }

    private static int rgb(int r, int g, int b) {
        return (r << 16) + (g << 8) + b;
    }

}
