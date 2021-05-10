package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test imageWriter
 *
 * @author Chani & Sara Lea
 */
class ImageWriterTest {
    /**
     * test the function WriteToImage- create an image
     */
    @Test
    void testWriteToImage() {
        //create an image 800x500 pixels with 16x10 squares.
        ImageWriter imageWriter = new ImageWriter("testColor1",800,500);
        //pass every pixel of the image
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                //800 / 16 = 50 - color every 50 pixels in the length of the image in different color to create squares
                if (i % 50 == 0) {
                    imageWriter.writePixel(i, j, new Color(0, 0, 255));
                }
                //500 / 10 = 50 - color every 50 pixels in the width of the image in different color to create squares
                else if (j % 50 == 0) {
                    imageWriter.writePixel(i, j, new Color(0, 255, 0));
                } else { //the rest of the image in different color
                    imageWriter.writePixel(i, j, new Color(255, 0, 0));
                }
            }
        }
        imageWriter.writeToImage();
    }
}