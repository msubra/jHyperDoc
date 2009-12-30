package jhd.parser;

import java.util.HashMap;

public class ParserApp {
  private Parser p;
  private HashMap map;
  private String templateName;

  private final Integer JAVA = new Integer(2),
      VB = new Integer(3),
      VB_NET = new Integer(4),
      CPP = new Integer(5),
      PYTHON = new Integer(6);

  public ParserApp(String templateName) {
    this.templateName = templateName;
    init();
  }

  private void init() {
    map = new HashMap();

    map.put("vb", VB_NET);
    map.put("txt", VB);
    map.put("cs", CPP);
    map.put("c", CPP);
    map.put("cpp", CPP);
    map.put("c++", CPP);
    map.put("h", CPP);
    map.put("h++", CPP);
    map.put("cxx", CPP);
    map.put("cx", CPP);
    map.put("java", JAVA);
    map.put("py", PYTHON);
  }

  private String getExtension(String path) {
    return path.substring(path.lastIndexOf('.') + 1).toLowerCase();
  }

  public void convert(String s) {

    Integer choice = (Integer) map.get(getExtension(s));

    if (choice == null) {
      System.out.println(s + " is Invalid");
      return;
    }

    System.out.println("[converting] : " + s);

    if (choice.compareTo(VB_NET) == 0) {
      vbParser(s);
    }
    else if (choice.compareTo(JAVA) == 0) {
      javaParser(s);
    }
    else if (choice.compareTo(CPP) == 0) {
      cppParser(s);
    }
    else if (choice.compareTo(PYTHON) == 0) {
      pythonParser(s);
    }
    else if (choice.compareTo(VB) == 0) {
      vbParser(s);
    }
    else {
      System.out.println("INVALID");
    }
  }

  /* Converts Java source File */
  private void javaParser(String fileName) {
    p = new JavaParser(fileName, templateName);
    p.convert();
  }

  /* Converts C source File */
  private void cppParser(String fileName) {
    p = new CCPPParser(fileName, templateName);
    p.convert();
  }

  /* Converts VB source File */
  private void vbParser(String fileName) {
    p = new VBParser(fileName, templateName);
    p.convert();
  }

  /* Converts Python source File */
  private void pythonParser(String fileName) {
    p = new PythonParser(fileName, templateName);
    p.convert();
  }
}