package hologram;

import hologram.Queue.InMemoryQueueService;
import hologram.Queue.QueueObject;
import hologram.enums.Template;
import hologram.request.HologramCreationRequest;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.io.BufferedInputStream;
import java.io.File;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.*;

import java.net.URL;
import java.lang.String;

@RestController()
@RequestMapping(value = "hologram/v2/")
public class HologramControllerV2 {

  @Autowired
  private InMemoryQueueService<QueueObject> inMemoryQueueService;

  @CrossOrigin(origins = "*")
  @RequestMapping(value = "get/allProducts", method = RequestMethod.GET)
  @ApiOperation(value = "Get all products available in the repository")
  public ResponseEntity<List<String>> getAllProducts() {
    List<String> allProductsList = new ArrayList<>();
    allProductsList.add("coca-cola");
    allProductsList.add("diet-coke");
    allProductsList.add("fanta");
    allProductsList.add("mirinda");
    allProductsList.add("pepsi");
    allProductsList.add("sprite");

    allProductsList.add("hbeer");
    allProductsList.add("nike-shoe");
    allProductsList.add("puma-shoe");

    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new ResponseEntity<>(allProductsList, headers, HttpStatus.OK);
  }

  @CrossOrigin(origins = "*")
  @RequestMapping(value = "get/allTemplates", method = RequestMethod.GET)
  @ApiOperation(value = "Get all templates available")
  public ResponseEntity<List<String>> getAllTemplates() {
    List<String> allTemplates = new ArrayList<>();
    allTemplates.add(Template.ROUND_ROTATION.getValue());
    allTemplates.add(Template.ZOOM_EFFECT.getValue());
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new ResponseEntity<>(allTemplates, headers, HttpStatus.OK);
  }

  @CrossOrigin(origins = "*")
  @RequestMapping(value = "image/gif/generator", method = RequestMethod.POST)
  @ApiOperation(value = "Generate Hologram Gif from simple image")
  public ResponseEntity<String> hologramGifGenerator(@RequestBody HologramCreationRequest hologramCreationRequest) {
    File writeFile = null;
    try {
      String productName = hologramCreationRequest.getProductName();
      String offerText = hologramCreationRequest.getOfferText();
      Template template = hologramCreationRequest.getTemplate();
      File file = ResourceUtils.getFile("classpath:img/" + productName + "/" + productName + ".png");
      BufferedImage image = ImageIO.read(file);
      writeFile = new File(file.getParent() + "_" + template.getValue().toLowerCase() + ".gif");
      ImageOutputStream imageOutputStream = new FileImageOutputStream(writeFile);
      if (Template.ROUND_ROTATION.equals(template)) {
        GifCreator.createRoundRotationGif(image, imageOutputStream, offerText);
      } else if (Template.ZOOM_EFFECT.equals(template)) {
        GifCreator.createZoomGif(image, imageOutputStream, offerText);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (writeFile != null) {
      final HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
      return new ResponseEntity<>(writeFile.getPath(), headers, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @CrossOrigin(origins = "*")
  @RequestMapping(value = "image/video/generator", method = RequestMethod.POST)
  @ApiOperation(value = "Generate Hologram Gif from simple image")
  public ResponseEntity<String> hologramVideoGenerator(@RequestBody HologramCreationRequest hologramCreationRequest) {
    String outputFile = null;
    try {
      String productName = hologramCreationRequest.getProductName();
      String offerText = hologramCreationRequest.getOfferText();
      Template template = hologramCreationRequest.getTemplate();
      File file = ResourceUtils.getFile("classpath:img/" + productName + "/" + productName + ".png");
      BufferedImage image = ImageIO.read(file);
      BufferedImage[] bufferedImages = null;
      if (Template.ROUND_ROTATION.equals(template)) {
        bufferedImages = Util.getRoundRotatedImages(image, offerText);
      } else if (Template.ZOOM_EFFECT.equals(template)) {
        bufferedImages = Util.getZoomImages(image, offerText);
      }
      outputFile = file.getParent() + "_" + template.getValue().toLowerCase() + ".mp4";
      VideoCreator.getMp4VideoFromImages(bufferedImages, outputFile);

      URL url = file.toURI().toURL();
      BufferedInputStream imageInFile = new BufferedInputStream(url.openConnection().getInputStream());
      byte imageData[] = new byte[91044];
      /*List<Byte> imageData = new ArrayList<Byte>();*/
      imageInFile.read(imageData);

      String imageDataString = encodeImage(imageData);


      new Thread(() -> hologramGifGenerator(hologramCreationRequest)).start();
      QueueObject queueObject = new QueueObject();
      queueObject.setProductName(productName);
      queueObject.setImageLink("data:image/jpeg;base64," + imageDataString);
      queueObject.setVideoLink(outputFile);
      queueObject.setGifLink(file.getParent() + "_" + template.getValue().toLowerCase() + ".gif");
      queueObject.setPromotionName(hologramCreationRequest.getPromotionName());
      queueObject.setDisplayDevice("Hologram 1");
      queueObject.setOfferText(hologramCreationRequest.getOfferText());
      inMemoryQueueService.add(queueObject);
    } catch (IOException e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if (outputFile != null) {
      final HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
      return new ResponseEntity<>(outputFile, headers, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public static String encodeImage(byte[] imageByteArray) {
    return Base64.getEncoder().encodeToString(imageByteArray);
    /*return EncodingUtil.base64Encode(image)*/
  }

  @CrossOrigin(origins = "*")
  @RequestMapping(value = "get/playList", method = RequestMethod.GET)
  @ApiOperation(value = "Get the playlist of hologram in queue")
  public ResponseEntity<List<QueueObject>> getHologramPlayList() {

    List<QueueObject> queueObjects = new ArrayList<>();
    if (!inMemoryQueueService.isEmpty()) {
      int size = inMemoryQueueService.size();
      for(int i=0; i<size; i++) {
        queueObjects.add(inMemoryQueueService.poll(i));
      }
    }
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    return new ResponseEntity<>(queueObjects, headers, HttpStatus.OK);
  }


}
