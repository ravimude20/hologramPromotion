import hologram.*;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HologramControllerV2Test {

  // Create round gif for hologram fan
  @Test
  public void gif_creation_rotaton() {
    try {
      File file = ResourceUtils.getFile("classpath:img/puma-shoe/puma-shoe.png");
      String offerText = "Buy 2 get 15% off";
      BufferedImage image;
      image = ImageIO.read(file);
      ImageOutputStream imageOutputStream = new FileImageOutputStream(new File(file.getParent()+".gif"));
      GifCreator.createRoundRotationGif(image, imageOutputStream, offerText);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Create round rotation Video from set of buffered Images
  @Test
  public void getMp4video_fromRoundRotationImages() {
    try {
      File file = ResourceUtils.getFile("classpath:img/coca-cola/coca-cola.png");
      String offerText = "Buy 2 get 15% off";
      String outputFile = file.getParent()+".mp4";
      BufferedImage image;
      image = ImageIO.read(file);
      BufferedImage [] bufferedImages = Util.getRoundRotatedImages(image, offerText);
      VideoCreator.getMp4VideoFromImages(bufferedImages, outputFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  // Create zoom gif for hologram fan
  @Test
  public void gif_creation_zoomed() {
    try {
      File file = ResourceUtils.getFile("classpath:img/diet-coke/diet-coke.png");
      String offerText = "Buy 2 get 1";
      BufferedImage image;
      image = ImageIO.read(file);
      ImageOutputStream imageOutputStream = new FileImageOutputStream(new File(file.getParent()+".gif"));
      GifCreator.createZoomGif(image, imageOutputStream, offerText);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Create zoom rotation Video from set of buffered Images
  @Test
  public void getMp4video_fromZoomedImages() {
    try {
      File file = ResourceUtils.getFile("classpath:img/crystalBall.png");
      String outputFile = file.getParent()+".mp4";
      String offerText = "";
      BufferedImage image;
      image = ImageIO.read(file);
      BufferedImage [] bufferedImages = Util.getZoomImages(image, offerText);
      VideoCreator.getMp4VideoFromImages(bufferedImages, outputFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  // Create zoom rotation Video from set of buffered Images
  @Test
  public void getMp4video_fromZoomedImages_test() {
    try {
      File file = ResourceUtils.getFile("classpath:img/crystalBall.png");
      String outputFile = file.getParent()+".mp4";
      String offerText = "";
      BufferedImage image;
      image = ImageIO.read(file);
      BufferedImage [] bufferedImages = Util.getZoomImages(image, offerText);
      VideoCreator.getMp4VideoFromImages(bufferedImages, outputFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void test() throws IOException {
    File file = ResourceUtils.getFile("classpath:img/coca-cola/coca-cola.png");
    BufferedImage image;
    image = ImageIO.read(file);
    List<BufferedImage> bufferedImageList = new ArrayList<>();
    double x = 0.04;
    do{
      BufferedImage image1 = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

      Graphics2D g2d = image1.createGraphics();
      g2d.shear(x, x);
      g2d.drawRenderedImage(image, null);
      g2d.dispose();
      bufferedImageList.add(image1);
      x = x + 0.04;
    }while(x <=0.20);

    do{
      BufferedImage image1 = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
      Graphics2D g2d = image1.createGraphics();
      g2d.shear(x, x);
      g2d.drawRenderedImage(image, null);
      g2d.dispose();
      bufferedImageList.add(image1);
      x = x - 0.04;
    }while(x >= 0.04);

    do{
      BufferedImage image1 = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

      Graphics2D g2d = image1.createGraphics();
      g2d.shear(-x, -x);
      g2d.drawRenderedImage(image, null);
      g2d.dispose();
      bufferedImageList.add(image1);
      x = x + 0.04;
    }while(x <=0.20);

    do{
      BufferedImage image1 = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
      Graphics2D g2d = image1.createGraphics();
      g2d.shear(-x, -x);
      g2d.drawRenderedImage(image, null);
      g2d.dispose();
      bufferedImageList.add(image1);
      x = x - 0.04;
    }while(x >= 0.04);
    System.out.println(x);

  }
}
