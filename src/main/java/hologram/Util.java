package hologram;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;

public class Util {

  private static int ROTATION_ANGLE = 4;
  private static double SCALING_ANGLE = 0.04;
  private static double DEFAULT_SCALE = 1;

  public static BufferedImage[] getZoomImages(BufferedImage masterImg, String offerText) {
    List<BufferedImage> bufferedImageList = new ArrayList<>();
    BufferedImage image = new BufferedImage(masterImg.getWidth(), masterImg.getHeight(), masterImg.getType());
    Graphics2D g2d = image.createGraphics();
    g2d.scale(DEFAULT_SCALE, DEFAULT_SCALE);
    g2d.drawRenderedImage(masterImg, null);
    g2d.dispose();
    bufferedImageList.add(image);
    double scale = DEFAULT_SCALE;
    do{
      scale = scale - SCALING_ANGLE;
      updateScaledImage(masterImg, offerText, bufferedImageList, scale);
    }while(scale >= SCALING_ANGLE);

    do{
      scale = scale + SCALING_ANGLE;
      updateScaledImage(masterImg, offerText, bufferedImageList, scale);
    }while(scale < DEFAULT_SCALE);

    BufferedImage [] bufferedImages = new BufferedImage[bufferedImageList.size()];
    bufferedImages = bufferedImageList.toArray(bufferedImages);
    return bufferedImages;
  }

  private static void updateScaledImage(BufferedImage masterImg, String offerText, List<BufferedImage> bufferedImageList, double scale) {
    BufferedImage image;
    Graphics2D g2d;
    image = new BufferedImage(masterImg.getWidth(), masterImg.getHeight(), masterImg.getType());
    g2d = image.createGraphics();
    g2d.scale(scale, scale);
    g2d.drawRenderedImage(masterImg, null);
    g2d.dispose();
    if (offerText != null)
      getTextOnImageZoom(offerText, image);
    bufferedImageList.add(image);
  }

  public static BufferedImage[] getRoundRotatedImages(BufferedImage masterImg, String offerText) {

    int angle = 0;
    int numberOfImages = 360/ROTATION_ANGLE;
    BufferedImage [] bufferedImages = new BufferedImage[numberOfImages+1];
    for(int i=0; i<=numberOfImages; i++) {
      BufferedImage tempImage = Util.rotateImageByDegrees(masterImg, angle);
      if(offerText != null) {
        getTextOnImage(offerText, numberOfImages, i, tempImage);
      }
      bufferedImages[i] = tempImage;
      angle += ROTATION_ANGLE;
    }

    return bufferedImages;
  }

  private static void getTextOnImageZoom(String offerText, BufferedImage tempImage) {
    Graphics g = tempImage.getGraphics();
    g.setFont(g.getFont().deriveFont(tempImage.getWidth() >= 600 ? 60F : 50F));
    g.setColor(Color.WHITE);
    int x = offerText.length() > 14 ? (tempImage.getWidth()/8) : (tempImage.getWidth()/4);
    int y = tempImage.getHeight()/4;
    g.drawString(offerText, x, y);
    Font font = new Font("Tahoma", Font.BOLD, 80);
    g.setFont(font);
    g.dispose();
  }

  private static void getTextOnImage(String offerText, int numberOfImages, int i, BufferedImage tempImage) {
    Graphics g = tempImage.getGraphics();
    g.setFont(g.getFont().deriveFont(80F));
    g.setColor(Color.WHITE);
    if (i <= numberOfImages/4 || i >= 3*(numberOfImages/4)) {
      g.drawString(offerText, tempImage.getWidth()/8, tempImage.getHeight()/4);
    } else {
      g.drawString(offerText, tempImage.getWidth()/8, tempImage.getHeight()-tempImage.getWidth()/8);
    }
    Font font = new Font("Tahoma", Font.BOLD, 100);
    g.setFont(font);
    g.dispose();
  }

  static BufferedImage rotateImageByDegrees(BufferedImage masterImg, double angle) {

    double rads = Math.toRadians(angle);
    int w = masterImg.getWidth();
    int h = masterImg.getHeight();
    BufferedImage rotated = new BufferedImage(w, h, masterImg.getType());
    Graphics2D g2d = rotated.createGraphics();
    AffineTransform at = new AffineTransform();

    int x = w/2;
    int y = h/2;

    at.rotate(rads, x, y);
    g2d.drawRenderedImage(masterImg, at);
    g2d.dispose();
    return rotated;
  }
}
