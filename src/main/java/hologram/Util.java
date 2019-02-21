package hologram;

import org.imgscalr.Scalr;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Util {

  private static int ROTATION_ANGLE = 4;
  private static int ZOOMING_ANGLE = 13;

  public static BufferedImage[] getZoomImages(BufferedImage masterImg) {
    BufferedImage [] bufferedImages = new BufferedImage[120];
    int width = masterImg.getWidth();
    int height = masterImg.getHeight();
    bufferedImages[0] = masterImg;
    for(int i=1; i<60; i++) {
      width = width - ZOOMING_ANGLE;
      height = height - ZOOMING_ANGLE;
//      BufferedImage image = new BufferedImage(width, height, masterImg.getType());
      BufferedImage image = Scalr.resize(masterImg, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, width, height, Scalr.OP_ANTIALIAS);
      Graphics g = image.getGraphics();
      g.setColor(Color.BLACK);
//      AffineTransform at = new AffineTransform();
//
//      int x = width / 2;
//      int y = height / 2;
//
//      at.scale(x, y);
      bufferedImages[i] = image;
    }

    for(int i=60; i<120; i++){
      width = width + ZOOMING_ANGLE;
      height = height + ZOOMING_ANGLE;
      BufferedImage image = Scalr.resize(masterImg, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, width, height, Scalr.OP_ANTIALIAS);
      bufferedImages[i] = image;
    }

    return bufferedImages;
  }

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

  static BufferedImage rotateImageByDegrees(BufferedImage masterImg, double angle) {

    double rads = Math.toRadians(angle);
    int w = masterImg.getWidth();
    int h = masterImg.getHeight();
    BufferedImage rotated = new BufferedImage(w, h, masterImg.getType());
    Graphics2D g2d = rotated.createGraphics();
    AffineTransform at = new AffineTransform();

    int x = w / 2;
    int y = h / 2;

    at.rotate(rads, x, y);
    g2d.drawRenderedImage(masterImg, at);
    g2d.dispose();

    return rotated;
  }
}
