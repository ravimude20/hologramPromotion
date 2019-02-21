import hologram.*;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HologramControllerV2Test {

  // Create round gif for hologram fan
  @Test
  public void gif_creation() {
    try {
      File file = ResourceUtils.getFile("classpath:img/cocacola/coca-cola.png");
      BufferedImage image;
      image = ImageIO.read(file);
      ImageOutputStream imageOutputStream = new FileImageOutputStream(new File(file.getParent()+".gif"));
      GifCreator.createRoundRotationGif(image, imageOutputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Create Video from set of buffered Images
  @Test
  public void getMp4video_fromRoundRotationImages() {
    try {
      File file = ResourceUtils.getFile("classpath:img/cocacola/coca-cola.png");
      String outputFile = file.getParent()+".mp4";
      BufferedImage image;
      image = ImageIO.read(file);
      BufferedImage [] bufferedImages = Util.getRoundRotatedImages(image);
      VideoCreator.getMp4VideoFromImages(bufferedImages, outputFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  // This is in testing phase
  public void getMp4video_fromZoomedImages() {
    try {
      File file = ResourceUtils.getFile("classpath:img/cocacola/coca-cola.png");
      String outputFile = file.getParent()+"coca-cola.mp4";
      BufferedImage image;
      image = ImageIO.read(file);
      BufferedImage [] bufferedImages = Util.getZoomImages(image);
      VideoCreator.getMp4VideoFromImages(bufferedImages, outputFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}