package hologram;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RestController()
@Api(value = "Hologram/V2/", description = "Hologram API")
public class HologramControllerV2 {

  @RequestMapping(value = "image/gif/generator", method = RequestMethod.GET)
  @ApiOperation(value = "Generate Hologram Gif from simple image")
  public ResponseEntity<byte[]> hologramGifGenerator(String productName, String imageName, String offerText, String template) {
    File writeFile = null;
    try {
      File file = ResourceUtils.getFile("img/"+productName+"/"+imageName+".png");
      BufferedImage image = ImageIO.read(file);
      writeFile = new File(file.getParent()+imageName+"_gif.png");
      ImageOutputStream imageOutputStream = new FileImageOutputStream(writeFile);
      if(template.equals("roundRotation")) {
        GifCreator.createRoundRotationGif(image, imageOutputStream);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (null != writeFile) {
      byte[] b = new byte[(int)writeFile.length()];
      final HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.IMAGE_PNG);
      return new ResponseEntity<>(b, headers, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @RequestMapping(value = "image/gif/generator", method = RequestMethod.GET)
  @ApiOperation(value = "Generate Hologram Gif from simple image")
  public ResponseEntity hologramVideoGenerator(String productName, String imageName, String offerText, String template) {
    File writeFile = null;
    try {
      File file = ResourceUtils.getFile("img/"+productName+"/"+imageName+".png");
      BufferedImage image = ImageIO.read(file);
      BufferedImage[] bufferedImages = null;
      if(template.equals("roundRotation")) {
        bufferedImages = Util.getRoundRotatedImages(image);
      }
      String outputFile = file.getParent()+imageName+".mp4";
      VideoCreator.getMp4VideoFromImages(bufferedImages, outputFile);
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
