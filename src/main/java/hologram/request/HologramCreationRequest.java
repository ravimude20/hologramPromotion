package hologram.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import hologram.enums.Template;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HologramCreationRequest {

  @NotNull
  private String productName;
  @NotNull
  private String offerText;
  @NotNull
  private Template template;

  public HologramCreationRequest() {
  }

  public HologramCreationRequest(String productName, String offerText, Template template) {
    this.productName = productName;
    this.offerText = offerText;
    this.template = template;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getOfferText() {
    return offerText;
  }

  public void setOfferText(String offerText) {
    this.offerText = offerText;
  }

  public Template getTemplate() {
    return template;
  }

  public void setTemplate(Template template) {
    this.template = template;
  }
}
