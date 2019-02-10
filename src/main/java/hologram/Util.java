package hologram;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Util {

  private static int ROTATION_ANGLE = 5;

  public static BufferedImage[] getRoundRotatedImages(BufferedImage masterImg) {

    int angle = 0;
    int numberOfImages = 360/ROTATION_ANGLE;
    BufferedImage [] bufferedImages = new BufferedImage[numberOfImages];
    bufferedImages[0] = masterImg;
    for(int i=1; i<numberOfImages; i++) {
      angle += ROTATION_ANGLE;
      bufferedImages[i] = Util.rotateImageByDegrees(masterImg, angle);
    }

    return bufferedImages;
  }

  public static BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {

    double rads = Math.toRadians(angle);
    double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
    int w = img.getWidth();
    int h = img.getHeight();
    int newWidth = (int) Math.floor(w * cos + h * sin);
    int newHeight = (int) Math.floor(h * cos + w * sin);

    BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = rotated.createGraphics();
    AffineTransform at = new AffineTransform();
    at.translate((newWidth - w) / 2, (newHeight - h) / 2);

    int x = w / 2;
    int y = h / 2;

    at.rotate(rads, x, y);
    g2d.setTransform(at);
    g2d.drawImage(img, 0, 0, null);
    g2d.dispose();

    return rotated;
  }
}
