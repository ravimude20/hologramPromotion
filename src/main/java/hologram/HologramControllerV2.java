package hologram;

import hologram.enums.Template;
import hologram.request.HologramCreationRequest;
import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;

@RestController()
@RequestMapping(value = "hologram/v2/")
public class HologramControllerV2 {

  @RequestMapping(value = "get/allProducts", method = RequestMethod.GET)
  @ApiOperation(value = "Get all products available in the repository")
  public ResponseEntity<List<String>> getAllProducts() {
     List<String> allProductsList = new ArrayList<>();
     allProductsList.add("coca-cola");
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new ResponseEntity<>(allProductsList, headers, HttpStatus.OK);
  }

  @RequestMapping(value = "get/allTemplates", method = RequestMethod.GET)
  @ApiOperation(value = "Get all templates available")
  public ResponseEntity<List<String>> getAllTemplates() {
    List<String> allTemplates = new ArrayList<>();
    allTemplates.add("roundRotation");
    allTemplates.add("zoomEffect");
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new ResponseEntity<>(allTemplates, headers, HttpStatus.OK);
  }

  @RequestMapping(value = "image/gif/generator", method = RequestMethod.POST)
  @ApiOperation(value = "Generate Hologram Gif from simple image")
  public ResponseEntity<byte[]> hologramGifGenerator(@RequestBody HologramCreationRequest hologramCreationRequest) {
    File writeFile;
    byte[] b = null;
    try {
      String productName = hologramCreationRequest.getProductName();
      String offerText = hologramCreationRequest.getOfferText();
      Template template = hologramCreationRequest.getTemplate();
      File file = ResourceUtils.getFile("classpath:img/"+productName+"/"+productName+".png");
      BufferedImage image = ImageIO.read(file);
      writeFile = new File(file.getParent()+"_"+template.getValue().toLowerCase()+".gif");
      ImageOutputStream imageOutputStream = new FileImageOutputStream(writeFile);
      if(Template.ROUND_ROTATION.equals(template)) {
        GifCreator.createRoundRotationGif(image, imageOutputStream, offerText);
      } else if(Template.ZOOM_EFFECT.equals(template)) {
        GifCreator.createZoomGif(image, imageOutputStream, offerText);
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

  @RequestMapping(value = "image/video/generator", method = RequestMethod.POST)
  @ApiOperation(value = "Generate Hologram Gif from simple image")
  public ResponseEntity hologramVideoGenerator(@RequestBody HologramCreationRequest hologramCreationRequest) {
    try {
      String productName = hologramCreationRequest.getProductName();
      String offerText = hologramCreationRequest.getOfferText();
      Template template = hologramCreationRequest.getTemplate();
      File file = ResourceUtils.getFile("classpath:img/"+productName+"/"+productName+".png");
      BufferedImage image = ImageIO.read(file);
      BufferedImage[] bufferedImages = null;
      if(Template.ROUND_ROTATION.equals(template)) {
        bufferedImages = Util.getRoundRotatedImages(image, offerText);
      } else if(Template.ZOOM_EFFECT.equals(template)) {
        bufferedImages = Util.getZoomImages(image, offerText);
      }
      String outputFile = file.getParent()+"_"+template.getValue().toLowerCase()+".mp4";
      VideoCreator.getMp4VideoFromImages(bufferedImages, outputFile);
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
