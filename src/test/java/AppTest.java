import hologram.HologramController;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AppTest {

  HologramController hologramController = new HologramController();

  @Test
  public void hologramImageGenerator_whenValidImage_generateHologramImage() throws IOException {
    File file = new File("/Users/1023556/Desktop/Beer_new.jpg");
    BufferedImage image = ImageIO.read(file);
    HologramController hologramController = new HologramController();
    hologramController.hologramImageGenerator(image);
  }

  @Test
  public void generateHologramFromText_whenValidText_generateHologramImage() {
    String text = "30% off on coca-cola";
    hologramController.generateHologramFromText(text);
  }

  @Test
  public void generateHologramImageWithTextOnImage() throws IOException {
    String text = "Buy 2 get 15% off";
    File file = new File("/Users/1023556/Desktop/coca-cola.png");
    BufferedImage image = ImageIO.read(file);
    HologramController hologramController = new HologramController();
    hologramController.generateHologramImageWithTextOnImage(text, image);
  }
}
