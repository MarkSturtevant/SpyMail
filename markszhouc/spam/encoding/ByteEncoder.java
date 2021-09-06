package markszhouc.spam.encoding;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

public class ByteEncoder {

    public static final int NUM_BITS_PER_PIXEL = 4;
    public static final int NUM_BITS_SQUARED = NUM_BITS_PER_PIXEL * NUM_BITS_PER_PIXEL;

    /** Encodes the given file into a specified company's logo.
     *
     * @param imagePath the path to the logo image to encode the file into.
     * @param filePath the path to the file to encode
     * @param byteData map of integer index (0-15) to ARGB color.  Should depend on
     *                 the selected logo and should focus around the most prevalent
     *                 solid color on the logo.
     * @return the newly constructed logo modified to contain the specified file.
     */
    public static Image encodeFile(String imagePath, String filePath, int[] byteData) {
        // find logo and file to encode in system
        Image logo = null;
        byte[] data = null;
        try {
            logo = new Image(new FileInputStream(imagePath));
            data = Files.readAllBytes(Paths.get(filePath));
        } catch(IOException e) {
            System.out.println("ERROR: Reading in logo and file to encode.");
            e.printStackTrace();
        }

        // prepare file to be encoded; turn from bytes into 4 bit numbers
        //     and add to intstream
        IntStream.Builder isb = IntStream.builder();
        // add file name extension at beginning
        for (int i = filePath.lastIndexOf('.') + 1; i < filePath.length(); ++i) {
            char c = filePath.charAt(i);
            if (c >= 256) {
                System.out.println("ERROR: file extension contains special characters.");
            }

            addByte(c, isb);
        }
        // add "." as separator.
        addByte('.', isb);
        // add contents of file
        int length = data.length;
        for (int i = 0; i < length; ++i)
            addByte(data[i], isb);

        //System.out.println
        // now intstream has been fully built
        // encode stream into file itself

        WritableImage newLogo = new WritableImage((int) logo.getWidth(), (int) logo.getHeight());
        encodeImage(logo, newLogo, byteData, isb.build());

        return newLogo;
    }

    /** creates a copy of logo with random, meaningless data.
     *
     * @param imagePath the path to the logo to copy.
     * @param byteData the index/byte->color data
     * @return the new logo image
     */
    public static Image encodeFakeFile(String imagePath, int[] byteData) {
        // find logo and file to encode in system
        Image logo = null;
        try {
            logo = new Image(new FileInputStream(imagePath));
        } catch(IOException e) {
            System.out.println("ERROR: Reading in logo and file to encode.");
            e.printStackTrace();
        }

        //
        WritableImage newLogo = new WritableImage((int) logo.getWidth(), (int) logo.getHeight());
        encodeImage(logo, newLogo, byteData, IntStream.builder().build());

        return newLogo;
    }

    /** given an intstream of data (consisting of 4 bits of data from each int), writes
     *  the data into the newLogo image, a near clone of the logo image.  The end to the data
     *  is signified by a random pixel of color outside the bound of NUM_BYTES_SQUARED in
     *  the byteData array.  After this, random pixels are put after in appropriate spots.
     *
     * @param logo the logo to build the image off of
     * @param newLogo the new image file, actively being constructed
     * @param byteData the array of index/byte->color data
     * @param is the intstream to encode into newLogo
     */
    private static void encodeImage(Image logo, WritableImage newLogo, int[] byteData, IntStream is) {
        PixelReader pr = logo.getPixelReader();
        PixelWriter pw = newLogo.getPixelWriter();
        Set<Integer> replaceableColors = ByteDecoder.dataArrayToMap(byteData).keySet();
        PrimitiveIterator.OfInt iterator = is.iterator();
        Random rand = new Random();
        final int NUM_NULL_COLORS = byteData.length - NUM_BITS_SQUARED;
        boolean finishedReading = false;

        for (int y = 0; y < logo.getHeight(); y++)
            for (int x = 0; x < logo.getWidth(); x++) {
                int rgb = getRGB(pr.getColor(x, y));
                if (replaceableColors.contains(rgb)) {
                    if (finishedReading)
                        pw.setColor(x, y, getColor(byteData[rand.nextInt(byteData.length)]));
                    else if (iterator.hasNext())
                        pw.setColor(x, y, getColor(byteData[iterator.next()]));
                    else {
                        pw.setColor(x, y, getColor(byteData[rand.nextInt(NUM_NULL_COLORS) + NUM_BITS_SQUARED]));
                        finishedReading = true;
                    }
                } else
                    pw.setColor(x, y, getColor(rgb));
            }
    }

    /** Adds a character to the IntStream
     *
     * @param c the character to add
     * @param isb the intstream
     */
    private static void addByte(int c, IntStream.Builder isb) {
        if (c < 0)
            c += 256;
        isb.accept(c / NUM_BITS_SQUARED);
        isb.accept(c % (int) Math.pow(2, NUM_BITS_PER_PIXEL));
    }

    public static int getRGB(Color c) {
        int r = (int) (c.getRed() * 255.99999999);
        int g = (int) (c.getGreen() * 255.99999999);
        int b = (int) (c.getBlue() * 255.99999999);
        return (r << 16) + (g << 8) + b;
    }

    public static Color getColor(int rgb) {
        int b = rgb % 256;
        rgb >>= 8;
        int g = rgb % 256;
        rgb >>= 8;
        int r = rgb;
        return Color.rgb(r, g, b);
    }



}