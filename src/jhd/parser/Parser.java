package jhd.parser;

import java.io.DataInputStream;

import jhd.util.ColorTemplate;
import jhd.util.HtmlWriter;
import antlr.CharScanner;

public abstract class Parser {

  private String fileName;
  private static String lang;
  protected HtmlWriter doc;
  private static java.util.Vector keywordList, datatypeList;
  private static final String keyFolder = "keywords";
  protected CharScanner toks;

  public Parser(String fileName, ColorTemplate template, String lang) {
    Parser.lang = lang;
    this.fileName = fileName;
    doc = new HtmlWriter(template, fileName);

    loadKeywords();
    loadDataTypes();
  }

  public String getFileName() {
    return fileName;
  }

  public void convert() {
    if (doc.getTemplate() == null) {
      System.out.println("Invalid Template");
      return;
    }

    doc.start();
    format();
    doc.end();
  }

  protected boolean isKeyword(String s) {
    return keywordList.contains(s);
  }

  protected boolean isDataType(String s) {
    return datatypeList.contains(s);
  }

  private java.net.URL getPath(String s){
    java.net.URL url = ClassLoader.getSystemResource(s);
    return url;
  }

  private void loadKeywords() {
    try {
      String path = keyFolder + "/" +  lang + ".key";
      java.net.URL url = getPath(path);
      DataInputStream read = new DataInputStream(url.openStream());

      String s;
      keywordList = new java.util.Vector();

      while ( (s = read.readLine()) != null) {
        keywordList.add(s);
      }
      s = null;
      read.close();
      url = null;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void loadDataTypes() {
    try {
      String path = keyFolder + "/" +  lang + ".dt";
      java.net.URL url = getPath(path);
      DataInputStream read = new DataInputStream(url.openStream());

      String s;
      datatypeList = new java.util.Vector();

      while ( (s = read.readLine()) != null) {
        datatypeList.add(s);
      }
      s = null;
      read.close();
      url = null;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected abstract void format();
}