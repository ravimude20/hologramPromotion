package hologram.Queue;

public class QueueObject {

  private String productName;

  private String offerText;

  private String promotionName;

  private String videoLink;

  private String imageLink;

  private String gifLink;

  private String displayDevice;

  private int numberOfFrames;

  public QueueObject() {
  }

  public String getProductName() {
    return productName;
  }

  public String getOfferText() {
    return offerText;
  }

  public void setOfferText(String offerText) {
    this.offerText = offerText;
  }

  public String getPromotionName() {
    return promotionName;
  }

  public void setPromotionName(String promotionName) {
    this.promotionName = promotionName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getVideoLink() {
    return videoLink;
  }

  public void setVideoLink(String videoLink) {
    this.videoLink = videoLink;
  }

  public String getImageLink() {
    return imageLink;
  }

  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }

  public int getNumberOfFrames() {
    return numberOfFrames;
  }

  public void setNumberOfFrames(int numberOfFrames) {
    this.numberOfFrames = numberOfFrames;
  }

  public String getGifLink() {
    return gifLink;
  }

  public void setGifLink(String gifLink) {
    this.gifLink = gifLink;
  }

  public String getDisplayDevice() {
    return displayDevice;
  }

  public void setDisplayDevice(String displayDevice) {
    this.displayDevice = displayDevice;
  }
}
