import hologram.HologramControllerV1;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HologramControllerV1Test {
  private HologramControllerV1 hologramControllerV1 = new HologramControllerV1();

  // This is for prism. Not going to work on hologram fan
  public void hologramImageGenerator_whenValidImage_generateHologramImage() throws IOException {
    File file = new File("/Users/1023556/Desktop/Beer_new.jpg");
    BufferedImage image = ImageIO.read(file);
    HologramControllerV1 hologramControllerV1 = new HologramControllerV1();
    hologramControllerV1.hologramImageGenerator(image);
  }

  // This is for prism. Not going to work on hologram fan
  public void generateHologramFromText_whenValidText_generateHologramImage() {
    String text = "30% off on cocacola";
    hologramControllerV1.generateHologramFromText(text);
  }

  // This is for prism. Not going to work on hologram fan
  public void generateHologramImageWithTextOnImage() throws IOException {
    String text = "Buy 2 get 15% off";
    File file = new File("/Users/1023556/Desktop/cocacola.png");
    BufferedImage image = ImageIO.read(file);
    HologramControllerV1 hologramControllerV1 = new HologramControllerV1();
    File outputFile = new File("/Users/1023556/Desktop/writeImage.png");
    hologramControllerV1.generateHologramImageWithTextOnImage(text, image, outputFile);
  }

  // Richa Code Test
  public void test() {
    hologramControllerV1.test();
  }
}
