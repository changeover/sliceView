package ch.fhnw.ima.sliceview.persist;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileImageLoader {

    /**
     * Loads an image in any of the formats supported by Java and convert it into an two-dimensional
     * array of integer values.
     * Only the information from the red color channel is used. Blue and green intensities are discarded.
     * This is rather simplistic and could be improved.
     * @param fileName the name of the file to load
     * @return the array of values
     */
    public static int[][] loadImageFromFile(String fileName) {
        File imageFile = new File(fileName);
        BufferedImage image;
        try {
            image = ImageIO.read(imageFile);
        } catch (IOException e) {
            return null;
        }

        if (image != null) {
            int[][] data = new int[image.getHeight()][image.getWidth()];
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = image.getRGB(x, y);
                    int alpha = (pixel >> 24) & 0xff;
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;
                    data[y][x] = red;
                }
            }

            return data;
        } else {
            return null;
        }
    }

    /**
     * Read a grid of integer values from a text file.
     * Values must be separated by tab characters.
     * @param fileName the name of the file to load
     * @return the array of values
     */
    public static int[][] loadDataFromFile(String fileName) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            return null;
        }

        String[] values;
        try {
            values = reader.readLine().split("\t");
            int width = Integer.parseInt(values[0]);
            int height = Integer.parseInt(values[1]);
            int[][] data = new int[height][width];
            for (int y = 0; y < height; y++) {
                values = reader.readLine().split("\t");
                for (int x = 0; x < width; x++) {
                    data[y][x] = Integer.parseInt(values[x]);
                }
            }
            return data;
        } catch (Exception e) {
            return null;
        }
    }
}
