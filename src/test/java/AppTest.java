import hologram.*;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AppTest {

  private HologramController hologramController = new HologramController();
  private GifCreator gifCreator = new GifCreator();
  private VideoCreator videoCreator = new VideoCreator();

  // This is for prism. Not going to work on hologram fan
  public void hologramImageGenerator_whenValidImage_generateHologramImage() throws IOException {
    File file = new File("/Users/1023556/Desktop/Beer_new.jpg");
    BufferedImage image = ImageIO.read(file);
    HologramController hologramController = new HologramController();
    hologramController.hologramImageGenerator(image);
  }

  // This is for prism. Not going to work on hologram fan
  public void generateHologramFromText_whenValidText_generateHologramImage() {
    String text = "30% off on coca-cola";
    hologramController.generateHologramFromText(text);
  }

  // This is for prism. Not going to work on hologram fan
  public void generateHologramImageWithTextOnImage() throws IOException {
    String text = "Buy 2 get 15% off";
    File file = new File("/Users/1023556/Desktop/coca-cola.png");
    BufferedImage image = ImageIO.read(file);
    HologramController hologramController = new HologramController();
    File outputFile = new File("/Users/1023556/Desktop/writeImage.png");
    hologramController.generateHologramImageWithTextOnImage(text, image, outputFile);
  }

  // Richa Code Test
  public void test() {
    hologramController.test();
  }

  // Create round gif for hologram fan
  @Test
  public void gif_creation() {
    File file = new File("/Users/1023556/Desktop/coca-cola.png");
    BufferedImage image;
    try {
      image = ImageIO.read(file);
      ImageOutputStream imageOutputStream = new FileImageOutputStream(new File("/Users/1023556/Desktop/new_gif.gif"));
      gifCreator.createRoundRotationGif(image, imageOutputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Create Video from set of buffered Images
  @Test
  public void get_mp4_video() {
    File file = new File("/Users/1023556/Desktop/coca-cola.png");
    BufferedImage image;
    try {
        image = ImageIO.read(file);
      BufferedImage [] bufferedImages = Util.getRoundRotatedImages(image);
      videoCreator.getMp4VideoFromImages(bufferedImages);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
}