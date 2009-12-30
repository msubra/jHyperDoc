package jhd.util;

public class ColorTemplates {
  static java.util.HashMap map;
  static ColorObject clr;
  static ColorTemplate template;

  static {
    map = new java.util.HashMap();
    javaT1();
    javaT2();
    vbT();
    KawaT();
    EclipseT();
    MonochromeT();
    microsoftT();
    pythonT();
  }

  public static ColorTemplate get(String s) {
    return (ColorTemplate) map.get(s);
  }

  public static ColorTemplate get(int i) {
    java.util.Iterator iter = map.values().iterator();
    int count = 0;

    while (iter.hasNext()) {
      count++;
      if (i == count) {
        return (ColorTemplate) iter.next();
      }
      iter.next();
    }
    return null;
  }

  private static void javaT1() {
    template = new ColorTemplate("java");
    map.put(template.getTemplateName(), template);
  }

  private static void add(ColorObject obj) {
    template.add(obj.getStyleName(), obj);
  }

  private static void init() {
    String other;

    other = "BORDER-RIGHT: #6699cc 1px solid; BORDER-TOP:#6699cc 1px solid;";
    other += "BORDER-LEFT:#6699cc 1px solid; BORDER-BOTTOM: #6699cc 1px solid;";
    clr = new ColorObject(".border", "lucida console", "black",
                          ColorObject.NORMAL, 10, other);
    add(clr);

    other = "background-color : ffcc00;";
    clr = new ColorObject(".sourceTitle", "tahoma", "black",
                          ColorObject.BOLD,
                          10, other);
    add(clr);

    other = "background-color : fff5ee;";
    clr = new ColorObject(".sourceCode", "courier", "black",
                          ColorObject.NORMAL, 10, other);
    add(clr);

    clr = new ColorObject(".docComment", "lucida console", "0002E8",
                          ColorObject.NORMAL, 8);
    add(clr);
  }

  private static void javaT2() {
    template = new ColorTemplate("java2");
    String other;

    /* initate compulsory things */
    init();

    clr = new ColorObject(".comment", "lucida console", "green",
                          ColorObject.ITALIC, 12);
    add(clr);
    clr = new ColorObject(".keyword", "courier new", "00008B",
                          ColorObject.BOLD, 10);
    add(clr);
    clr = new ColorObject(".bracket", "lucida console", "red",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".chars", "lucida console", "0002D0",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".string", "lucida console", "0002D0",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".number", "lucida console", "Fuchsia",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".operator", "lucida console", "gray",
                          ColorObject.NORMAL, 10);
    add(clr);

    map.put(template.getTemplateName(), template);
  }

  private static void KawaT() {
    template = new ColorTemplate("kawa");
    String other;

    /* initate compulsory things */
    init();

    clr = new ColorObject(".comment", "lucida console", "008000",
                          ColorObject.ITALIC, 10);
    add(clr);
    clr = new ColorObject(".keyword", "courier new", "0000c0",
                          ColorObject.BOLD, 10);
    add(clr);

    clr = new ColorObject(".datatype", "courier new", "c00000",
                          ColorObject.BOLD, 10);
    add(clr);

    clr = new ColorObject(".bracket", "courier new", "black",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".chars", "courier new", "990000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".string", "Courier New", "990000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".number", "Courier New", "990000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".operator", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    map.put(template.getTemplateName(), template);
  }

  private static void EclipseT() {
    template = new ColorTemplate("eclipse");
    String other;

    /* initate compulsory things */
    init();

    clr = new ColorObject(".comment", "Courier New", "3f5fbf",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".keyword", "Courier New", "7f0055",
                          ColorObject.BOLD, 10);
    add(clr);

    clr = new ColorObject(".datatype", "courier new", "7f0055",
                          ColorObject.BOLD, 10);
    add(clr);

    clr = new ColorObject(".bracket", "Courier New", "black",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".chars", "Courier New", "990000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".string", "Courier New", "2a00ff",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".number", "Courier New", "990000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".operator", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    map.put(template.getTemplateName(), template);

  }

  private static void MonochromeT() {
    template = new ColorTemplate("monochrome");
    String other;

    /* initate compulsory things */
    init();

    clr = new ColorObject(".comment", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".keyword", "Courier New", "000000",
                          ColorObject.BOLD, 10);
    add(clr);

    clr = new ColorObject(".datatype", "courier new", "000000",
                          ColorObject.BOLD, 10);
    add(clr);

    clr = new ColorObject(".bracket", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".chars", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".string", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".number", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".operator", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    map.put(template.getTemplateName(), template);

  }

  private static void vbT() {
    template = new ColorTemplate("vb");
    String other;

    /* initate compulsory things */
    init();

    clr = new ColorObject(".comment", "courier new", "007f00",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".keyword", "courier new", "darkblue",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".bracket", "lucida console", "red",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".chars", "lucida console", "0002D0",
                          ColorObject.BOLD, 10);
    add(clr);
    clr = new ColorObject(".string", "courier new", "0002D0",
                          ColorObject.BOLD, 10);
    add(clr);
    clr = new ColorObject(".number", "courier new", "Fuchsia",
                          ColorObject.NORMAL, 10);
    add(clr);

    clr = new ColorObject(".operator", "courier new", "gray",
                          ColorObject.NORMAL, 10);
    add(clr);

    clr = new ColorObject("table", "lucida console", "black",
                          ColorObject.NORMAL, 10);
    add(clr);
    map.put(template.getTemplateName(), template);

  }

  private static void microsoftT() {
    template = new ColorTemplate("microsoft");
    String other;

    /* initate compulsory things */
    init();

    clr = new ColorObject(".comment", "Courier New", "007f00",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".keyword", "Courier New", "blue",
                          ColorObject.NORMAL, 10);
    add(clr);

    clr = new ColorObject(".datatype", "Courier new", "blue",
                          ColorObject.NORMAL, 10);
    add(clr);

    clr = new ColorObject(".bracket", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".chars", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".string", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".number", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".operator", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    map.put(template.getTemplateName(), template);
  }

  private static void pythonT() {
    template = new ColorTemplate("python");
    String other;

    /* initate compulsory things */
    init();

    clr = new ColorObject(".comment", "lucida console", "008000",
                          ColorObject.ITALIC, 10);
    add(clr);
    clr = new ColorObject(".keyword", "courier new", "0000c0",
                          ColorObject.BOLD, 10);
    add(clr);

    clr = new ColorObject(".datatype", "courier new", "c00000",
                          ColorObject.BOLD, 10);
    add(clr);

    clr = new ColorObject(".bracket", "courier new", "red",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".chars", "courier new", "990000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".string", "Courier New", "990000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".number", "Courier New", "990000",
                          ColorObject.NORMAL, 10);
    add(clr);
    clr = new ColorObject(".operator", "Courier New", "000000",
                          ColorObject.NORMAL, 10);
    add(clr);
    map.put(template.getTemplateName(), template);
  }
}