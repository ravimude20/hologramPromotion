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
    String text = "30% off on coca-cola1";
    hologramControllerV1.generateHologramFromText(text);
  }
}
