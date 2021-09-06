package markszhouc.spam.encoding;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

import java.io.*;
import java.util.HashMap;
import java.util.PrimitiveIterator;
import java.util.stream.IntStream;

import static markszhouc.spam.encoding.ByteEncoder.*;

public class ByteDecoder {

    /** Decodes the passed in image file, creating a new file.
     *
     * @param imagePath the path to the logo to decode.
     * @param outputPath the path of where the new file should be created.  Includes the
     *                   name of the file but not the extension,
     * @param byteData map of integer index (0-15) to ARGB color.  Should depend on
     *                       the selected logo and should focus around the most prevalent
     *                       solid color on the logo.
     * @return true if data was found, false if not.
     */
    public static boolean decode(String imagePath, String outputPath, int[] byteData) {
        HashMap<Integer, Integer> dataMap = dataArrayToMap(byteData);

        // find logo to decode in system
        Image logo;
        try {
            logo = new Image(new FileInputStream(imagePath));
        } catch(IOException e) {
            System.out.println("Error creating files.");
            return false;
        }

        // read in all data from the logo and put it into an intstream
        int streamSize = 0;
        IntStream.Builder isb = IntStream.builder();
        PixelReader pr = logo.getPixelReader();

        outerloop: for (int y = 0; y < logo.getHeight(); y++)
            for (int x = 0; x < logo.getWidth(); x++) {
                int rgb = getRGB(pr.getColor(x, y));
                if (dataMap.keySet().contains(rgb)) {
                    if (dataMap.get(rgb) >= NUM_BITS_SQUARED)
                        break outerloop;
                    else {
                        isb.accept(dataMap.get(rgb));
                        streamSize++;
                    }
                }
            }

        // generate file now.
        if (streamSize == 0) {
            System.out.println("No data in this logo!");
            return false;
        }

        IntStream stream = isb.build();
        PrimitiveIterator.OfInt iterator = stream.iterator();

        // read file extension
        String fileExtension = "";
        while (iterator.hasNext()) {
            char next = (char) getNextItem(iterator);
            streamSize -= 2;
            if (next != '.')
                fileExtension += next;
            else break;
        }

        // read file data into byte array.
        byte[] byteArray = new byte[streamSize];
        int i = 0;
        while (iterator.hasNext()) {
            int next = (iterator.next() << NUM_BITS_PER_PIXEL) + iterator.next();
            if (next >= 128)
                next = next - 256;
            byteArray[i] = (byte) next;
            i++;
        }

        // creating and writing bytes into output file.
        try {
            System.out.println("Sending message to " + outputPath + "." + fileExtension);
            FileOutputStream fos = new FileOutputStream(new File(outputPath + "." + fileExtension));
            fos.write(byteArray);
            fos.close();
        } catch (Exception e) {
            System.out.println("ERROR: Making file in ByteDecoder");
            e.printStackTrace();
        }

        return true;
    }

    private static int getNextItem(PrimitiveIterator.OfInt iterator) {
        int next = (iterator.next() << NUM_BITS_PER_PIXEL) + iterator.next();
        if (next >= 128)
            next = next - 256;
        return next;
    }

    /** Turns the byte data array into a hashmap following the pattern
     *
     *  (argb color) --> (integer index)
     *
     * @param byteData the original array to convert to hashmap.
     * @return the map.
     */
    public static HashMap<Integer, Integer> dataArrayToMap(int[] byteData) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < byteData.length; ++i)
            map.put(byteData[i], i);
        return map;
    }

}
