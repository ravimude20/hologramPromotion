package hologram;

import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class VideoCreator {

  public static void getMp4VideoFromImages(BufferedImage [] bufferedImages, String writableChannel) {
    SeekableByteChannel out = null;
    try {
      out = NIOUtils.writableFileChannel(writableChannel);
      AWTSequenceEncoder encoder = new AWTSequenceEncoder(out, Rational.R(25, 2));
      for (BufferedImage image : bufferedImages) {
        encoder.encodeImage(image);
      }
      encoder.finish();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      NIOUtils.closeQuietly(out);
    }
  }
}
