package jhd.util;

public class ColorObject {
  private String fontName, fontColor, styleName, other;
  private int fontStyle, fontSize;

  /* fontStyle = BOLD, ITALIC, NORMAL */
  public final static int
      NORMAL = 1,
      BOLD = 2,
      ITALIC = 3;

  public ColorObject() {
    this(null, null, null, -1, -1, null);
  }

  public ColorObject(String styleName, String fontName, String fontColor,
                     int fontStyle,
                     int fontSize) {
    this(styleName, fontName, fontColor, fontStyle, fontSize, null);
  }

  public ColorObject(String styleName, String fontName, String fontColor,
                     int fontStyle,
                     int fontSize, String other) {
    this.fontName = fontName;
    this.fontColor = fontColor;
    this.fontStyle = fontStyle;
    this.fontSize = fontSize;
    this.styleName = styleName;
    this.other = other;
  }

  public void setFontName(String fontName) {
    this.fontName = fontName;
  }

  public void setStyleName(String styleName) {
    this.styleName = styleName;
  }

  public void setFontColor(String fontColor) {
    this.fontColor = fontColor;
  }

  public void setFontStyle(int fontStyle) {
    this.fontStyle = fontStyle;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
  }

  public String setOtherStyle(String s) {
    return s;
  }

  public String getFontName() {
    return fontName;
  }

  public String getFontColor() {
    try {
      Long.decode("0x" + fontColor);
      return "#" + fontColor;
    }
    catch (NumberFormatException e) {
      return fontColor;
    }
  }

  public int getFontSize() {
    return fontSize;
  }

  public int getFontStyle() {
    return fontStyle;
  }

  public String getStyleName() {
    return styleName;
  }

  public String getOtherStyle() {
    return other;
  }

  public String apply() {
    StringBuffer sb = new StringBuffer();

    sb.append(getStyleName());
    sb.append('{');
    sb.append('\n');

	//sb.append(';');
	//sb.append('\n');

	sb.append("color:");
	sb.append(getFontColor());
	sb.append(';');
	sb.append('\n');

	sb.append("font:");

	sb.append(" ");
	switch (getFontStyle()) {
	  case BOLD:
		sb.append("bold");
		break;
	  case ITALIC:
		sb.append("italic");
		break;
	  case NORMAL:
		sb.append("normal");
		break;
	}

	sb.append(" " + getFontSize() + "pt");

	sb.append( " \"" + getFontName() + "\""  );

	sb.append(';');
	sb.append('\n');    
//
//    sb.append("font-family:");
//    sb.append(getFontName());
//    sb.append(';');
//    sb.append('\n');
//
//    sb.append("color:");
//    sb.append(getFontColor());
//    sb.append(';');
//    sb.append('\n');
//
//    sb.append("font:");
//    switch (getFontStyle()) {
//      case BOLD:
//        sb.append("bold");
//        break;
//      case ITALIC:
//        sb.append("italic");
//        break;
//      case NORMAL:
//        sb.append("normal");
//        break;
//    }
//    sb.append(';');
//    sb.append('\n');
//
//    sb.append("font-size: " + getFontSize() + "pt");
//    sb.append(';');
//    sb.append('\n');

    if (other != null) {
      sb.append(other);
      sb.append('\n');
    }

    sb.append('}');
    sb.append('\n');

    return sb.toString();
  }

  public static String formatWS(String s) {
    return "";
  }
}