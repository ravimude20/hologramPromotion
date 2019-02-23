package hologram;

import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GifCreator {

  public static void createRoundRotationGif(BufferedImage masterImg, ImageOutputStream imageOutputStream, String offerText) {

    try{
      BufferedImage [] bufferedImages = Util.getRoundRotatedImages(masterImg, offerText);
      int milliSeconds = 1000/bufferedImages.length;
      GifSequenceWriter writer =
              new GifSequenceWriter(imageOutputStream, masterImg.getType(), milliSeconds, false);
      for(int i=0; i<bufferedImages.length-1; i++) {
        writer.writeToSequence(bufferedImages[i]);
      }
      writer.close();
      imageOutputStream.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public static void createZoomGif(BufferedImage masterImg, ImageOutputStream imageOutputStream, String offerText) {
    try{
      BufferedImage [] bufferedImages = Util.getZoomImages(masterImg, offerText);
      int milliSeconds = 5000/bufferedImages.length;
      GifSequenceWriter writer =
              new GifSequenceWriter(imageOutputStream, masterImg.getType(), milliSeconds, false);
      for(int i=0; i<bufferedImages.length-1; i++) {
        writer.writeToSequence(bufferedImages[i]);
      }
      writer.close();
      imageOutputStream.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
}
