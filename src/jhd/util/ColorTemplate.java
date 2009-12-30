package jhd.util;

import java.util.HashMap;
import java.util.Iterator;

public class ColorTemplate {

  private String templateName;
  private HashMap table;

  public ColorTemplate(String templateName) {
    this.templateName = templateName;
    table = new HashMap();
    init();
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public String getTemplateName() {
    return templateName;
  }

  public void add(String s) {
    if (!table.containsKey(s)) {
      table.put(s, new ColorObject());
    }
  }

  public void add(String s, ColorObject obj) {
    table.put(s, obj);
  }

  public ColorObject get(String name) {
    return (ColorObject) table.get(name);
  }

  private void init() {
    ColorObject[] clr = new ColorObject[13];

    clr[0] = new ColorObject(".comment", "lucida console", "6c5958",
                             ColorObject.ITALIC, 10);
    clr[1] = new ColorObject(".keyword", "courier new", "00008B",
                             ColorObject.BOLD, 10);
    clr[2] = new ColorObject(".bracket", "lucida console", "800080",
                             ColorObject.NORMAL, 10);
    clr[3] = new ColorObject(".chars", "lucida console", "0002D0",
                             ColorObject.NORMAL, 10);
    clr[4] = new ColorObject(".string", "lucida console", "0002D0",
                             ColorObject.NORMAL, 10);
    clr[5] = new ColorObject(".number", "lucida console", "007501",
                             ColorObject.NORMAL, 10);
    clr[6] = new ColorObject(".operator", "lucida console", "742998",
                             ColorObject.NORMAL, 10);

    String other = "background-color : fff0f5;";
    clr[7] = new ColorObject(".sourceTitle", "tahoma", "red", ColorObject.BOLD,
                             10, other);

    other = "background-color : fff5ee;";
    clr[8] = new ColorObject(".sourceCode", "lucida console", "black",
                              ColorObject.NORMAL, 10, other);
    other = "background-color : ffcc00;";
    clr[9] = new ColorObject(".sourceTitle", "tahoma", "black",
                             ColorObject.BOLD,
                             10, other);

    other = "background-color : fff5ee;";
    clr[10] = new ColorObject(".sourceCode", "courier", "black",
                              ColorObject.NORMAL, 10, other);

    clr[11] = new ColorObject(".docComment", "lucida console", "0002E8",
                             ColorObject.NORMAL, 8);

   clr[12] = new ColorObject(".datatype", "courier new", "00008B",
                            ColorObject.BOLD, 10);

    for (int i = 0; i < clr.length; i++) {
      add(clr[i].getStyleName(), clr[i]);
    }
  }

  public String getCSSCode() {
    StringBuffer sb = new StringBuffer();
    Iterator iter = table.values().iterator();

    while (iter.hasNext()) {
      ColorObject o = (ColorObject) iter.next();
      sb.append(o.apply());
    }

    return sb.toString();
  }
}