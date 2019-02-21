package hologram;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController()
@RequestMapping(value = "hologram/v2/")
public class HologramControllerV2 {

  @RequestMapping(value = "image/gif/generator", method = RequestMethod.GET)
  @ApiOperation(value = "Generate Hologram Gif from simple image")
  public ResponseEntity<byte[]> hologramGifGenerator(@RequestParam("productName") String productName,
                                                     @RequestParam("imageName") String imageName,
                                                     @RequestParam("offerText") String offerText,
                                                     @RequestParam("template") String template) {
    File writeFile;
    byte[] b = null;
    try {
      File file = ResourceUtils.getFile("classpath:img/"+productName+"/"+imageName+".png");
      BufferedImage image = ImageIO.read(file);
      writeFile = new File(file.getParent()+".gif");
      ImageOutputStream imageOutputStream = new FileImageOutputStream(writeFile);
      if(template.equals("roundRotation")) {
        GifCreator.createRoundRotationGif(image, imageOutputStream);
      }
      b = Files.readAllBytes(writeFile.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (b != null) {
      final HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.IMAGE_GIF);
      return new ResponseEntity<>(b, headers, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @RequestMapping(value = "image/video/generator", method = RequestMethod.GET)
  @ApiOperation(value = "Generate Hologram Gif from simple image")
  public ResponseEntity hologramVideoGenerator(@RequestParam("productName") String productName,
                                               @RequestParam("imageName") String imageName,
                                               @RequestParam("offerText") String offerText,
                                               @RequestParam("template") String template) {
    try {
      File file = ResourceUtils.getFile("classpath:img/"+productName+"/"+imageName+".png");
      BufferedImage image = ImageIO.read(file);
      BufferedImage[] bufferedImages = null;
      if(template.equals("roundRotation")) {
        bufferedImages = Util.getRoundRotatedImages(image);
      }
      String outputFile = file.getParent()+".mp4";
      VideoCreator.getMp4VideoFromImages(bufferedImages, outputFile);
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
