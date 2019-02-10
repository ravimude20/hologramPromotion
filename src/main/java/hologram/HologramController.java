package hologram;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.imgscalr.Scalr;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RestController()
@Api(value = "Hologram", description = "Hologram API")
public class HologramController {

  private JLabel myDisplay;

  @GetMapping
  public String helloGradle() {
    return "Hello Gradle!";
  }

  @RequestMapping(value = "image/generator", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Generate Hologram Image from simple image")
  public Boolean hologramImageGenerator(@RequestParam("imageSrc") String imageSrc) {
    Boolean result = false;
    File file = new File(imageSrc);
    BufferedImage image;
    try {
      image = ImageIO.read(file);
      HologramController hologramController = new HologramController();
      hologramController.hologramImageGenerator(image);
      result = true;
    } catch (IOException e) {
      e.printStackTrace();
    }
   return result;
  }

  @RequestMapping(value = "text/image/generator", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Generate Hologram Image from text")
  public Boolean hologramTextImage(@RequestParam("text") String text) {
    generateHologramFromText(text);
    return true;
  }

  @RequestMapping(value = "imageWithText/generator", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Generate Hologram Image from text")
  public Boolean hologramImageWithTextOnImage(@RequestParam("imageSrc") String imageSrc, @RequestParam("text") String text) {
    Boolean result = false;
    File file = new File(imageSrc);
    BufferedImage image;
    try {
      image = ImageIO.read(file);
      HologramController hologramController = new HologramController();
      File newFile = new File("/Users/1023556/Desktop/Text.jpeg");
      hologramController.generateHologramImageWithTextOnImage(text, image, newFile);
      result = true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  private BufferedImage generateImageFromText(String text) {
    BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d = img.createGraphics();
    Font font = new Font("Arial", Font.PLAIN, 48);
    g2d.setFont(font);
    FontMetrics fm = g2d.getFontMetrics();
    int width = fm.stringWidth(text);
    int height = fm.getHeight();
    g2d.dispose();

    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    g2d = img.createGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g2d.setFont(font);
    fm = g2d.getFontMetrics();
    g2d.setColor(Color.WHITE);
    g2d.drawString(text, 0, fm.getAscent());
    g2d.dispose();
    try {
      ImageIO.write(img, "jpeg", new File("/Users/1023556/Desktop/Text.jpeg"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return img;
  }

  public void generateHologramFromText(String text) {
    BufferedImage image = generateImageFromText(text);
    hologramImageGenerator(image);
  }

  public void generateHologramImageWithTextOnImage(String text, BufferedImage image, File file) {
    Graphics g = image.getGraphics();
    g.setFont(g.getFont().deriveFont(80F));
    g.setColor(Color.BLACK);
    g.drawString(text, image.getWidth()/8, image.getHeight()/4);
    Font font = new Font("Tahoma", Font.BOLD, 100);
    g.setFont(font);
    g.dispose();
    hologramImageGenerator(image);
    try {
      ImageIO.write(image, "png", file);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void hologramImageGenerator(BufferedImage image) {
    int imagesCount = 4;
    BufferedImage images[] = new BufferedImage[imagesCount];

    images[0] = image;
    images[1] = rotateImage(images[0], 90);
    images[2] = rotateImage(images[0], 180);
    images[3] = rotateImage(images[0], 270);

    int dimension = images[0].getWidth()*2 + images[0].getHeight()*2;
    int space = images[0].getWidth()/4;
    BufferedImage concatImage = new BufferedImage(dimension-(space*2),
              dimension, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2d_new = concatImage.createGraphics();
    g2d_new.drawImage(images[0], images[0].getHeight()+space , space, null);
    g2d_new.drawImage(images[1], images[0].getWidth()+ images[0].getHeight()+space, images[0].getHeight()+(space*2), null);
    g2d_new.drawImage(images[2], images[0].getHeight()+space, images[0].getWidth()+ images[0].getHeight()+(space*3), null);
    g2d_new.drawImage(images[3], space, images[0].getHeight()+(space*2), null);

    g2d_new.dispose();

    try {
      ImageIO.write(concatImage, "png", new File("/Users/1023556/Desktop/hologramImage.png")); // export concat image
    } catch (IOException e) {
      e.printStackTrace();
    }
    }

  private static BufferedImage rotateImage(BufferedImage src, int rotationAngle) {
    double theta = (Math.PI * 2) / 360 * rotationAngle;
    int width = src.getWidth();
    int height = src.getHeight();
    BufferedImage dest;
    if (rotationAngle == 90 || rotationAngle == 270) {
      dest = new BufferedImage(src.getHeight(), src.getWidth(), src.getType());
    } else {
      dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
    }

    Graphics2D graphics2D = dest.createGraphics();

    if (rotationAngle == 90) {
      graphics2D.translate((height - width) / 2, (height - width) / 2);
      graphics2D.rotate(theta, height / 2, width / 2);
    } else if (rotationAngle == 270) {
      graphics2D.translate((width - height) / 2, (width - height) / 2);
      graphics2D.rotate(theta, height / 2, width / 2);
    } else {
      graphics2D.translate(0, 0);
      graphics2D.rotate(theta, width / 2, height / 2);
    }
    graphics2D.drawRenderedImage(src, null);
    return dest;
  }

  public void test() {
    String inputFileName = "/Users/1023556/Desktop/Beer_new.jpg";
    File f = new File(inputFileName);
    BufferedImage img;
    try {
      img = ImageIO.read(f);
      BufferedImage thumbImg = Scalr.resize(img, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, 50, 30, Scalr.OP_ANTIALIAS);
      File outputFile = new File("/Users/1023556/Desktop/zoomed.jpg");
      ImageIO.write(thumbImg, "jpg", outputFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}