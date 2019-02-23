package hologram.enums;

public enum Template{

  ZOOM_EFFECT("ZOOM_EFFECT"),
  ROUND_ROTATION("ROUND_ROTATION");

  private final String value;


  Template(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
