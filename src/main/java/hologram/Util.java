package hologram;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Util {

  private static int ROTATION_ANGLE = 4;

  public static BufferedImage[] getRoundRotatedImages(BufferedImage masterImg) {

    int angle = 0;
    int numberOfImages = 360/ROTATION_ANGLE;
    BufferedImage [] bufferedImages = new BufferedImage[numberOfImages+1];
    for(int i=0; i<=numberOfImages; i++) {
      BufferedImage tempImage = Util.rotateImageByDegrees(masterImg, angle);
      bufferedImages[i] = tempImage;
      angle += ROTATION_ANGLE;
    }

    return bufferedImages;
  }

  public static BufferedImage rotateImageByDegrees(BufferedImage masterImg, double angle) {

    double rads = Math.toRadians(angle);
//    double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
    int w = masterImg.getWidth();
    int h = masterImg.getHeight();
//    int newWidth = (int) Math.floor(w * cos + h * sin);
//    int newHeight = (int) Math.floor(h * cos + w * sin);

    BufferedImage rotated = new BufferedImage(w, h, masterImg.getType());
    Graphics2D g2d = rotated.createGraphics();
    AffineTransform at = new AffineTransform();
//    at.translate((newWidth - w) / 2, (newHeight - h) / 2);

    int x = w / 2;
    int y = h / 2;

    at.rotate(rads, x, y);
    g2d.drawRenderedImage(masterImg, at);
    g2d.dispose();

    return rotated;
  }
}
