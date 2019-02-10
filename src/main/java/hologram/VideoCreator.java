package hologram;

import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

public class VideoCreator {

  SeekableByteChannel out = null;

  public void getMp4VideoFromImages(BufferedImage [] bufferedImages) {
    try {
      out = NIOUtils.writableFileChannel("//Users/1023556/Desktop/output.mp4");
      AWTSequenceEncoder encoder = new AWTSequenceEncoder(out, Rational.R(25, 2));
      for (BufferedImage image : bufferedImages) {
        encoder.encodeImage(image);
      }
      encoder.finish();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      NIOUtils.closeQuietly(out);
    }
  }

  public void getMp4VideoFromGif(BufferedImage image) {

  }
}
