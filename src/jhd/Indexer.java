package jhd;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Vector;

import jhd.util.FileList;

public class Indexer {
  private String path;
  private Vector v;
  private FileList f;


  public Indexer(String path){
    this.path = path;
    v = new Vector();
    f = new FileList();
  }

  private void write(String data, OutputStream os) {
    try {
      for (int i = 0; i < data.length(); i++) {
        os.write(data.charAt(i));
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

  private StringBuffer makeLink(String data) {
    StringBuffer sb = new StringBuffer();

    sb.append("<A href=\"" + data + "\" ");
    sb.append("target=\"main\">" + data + "</A>");

    return sb;
  }

  private void createIndexFile() {
    StringBuffer buf = new StringBuffer();
    buf.append(writeStart());

    buf.append("<FRAMESET  COLS=\"25%,*\">");
    buf.append(
        "<FRAME NAME=\"left\" SRC=\"left.htm\" SCROLLING=\"auto\" FRAMEBORDER=\"yes\">");
    buf.append(
        "<FRAME NAME=\"main\" SRC=\"main.htm\" >");
    buf.append("</FRAMESET>");
    buf.append("<noframes>");

    buf.append("<body></body></noframes></html>");

    try {
      FileOutputStream fos = new FileOutputStream(path + "index.htm");
      write(buf.toString(), fos);
    }
    catch (Exception e) {
      System.out.println(e);
    }

  }

  private StringBuffer writeStart() {
    StringBuffer buf = new StringBuffer();
    buf.append("<html>");
    buf.append("<head>");
    buf.append("<title>");
    buf.append("</title>");
    buf.append("</head>");

    return buf;

  }

  private StringBuffer writeEnd() {
    StringBuffer buf = new StringBuffer();

    buf.append("</body>");
    buf.append("</html>");

    return buf;

  }

  private void createLeftFile() {
    StringBuffer buf = new StringBuffer();

    buf.append(writeStart());
    buf.append("<body>");

    Iterator iter = v.iterator();

    while (iter.hasNext()) {
      String s = (String) iter.next();
      File file = new File(s);
      buf.append(makeLink(file.getName()));
      buf.append("<BR>");
    }

    buf.append(writeEnd());
    try {
      FileOutputStream fos = new FileOutputStream(path + "left.htm");
      write(buf.toString(), fos);
    }
    catch (Exception e) {
      System.out.println(e);
    }

  }

  private void createRightFile() {
    StringBuffer buf = new StringBuffer();
    buf.append(writeStart());
    buf.append("<body>");
    buf.append(
        "<P align = center><H1>Click the links to view the files</H1></p>");
    buf.append(writeEnd());
    try {
      FileOutputStream fos = new FileOutputStream(path + "main.htm");
      write(buf.toString(), fos);
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

  public void createIndex(){
    v = f.getFileList(path + "*.html");
    v.addAll(f.getFileList(path + "*.htm"));

    if( v.size() == 0 )
      return;

    System.out.println("Indexing " + v.size() + " files ");

    createLeftFile();
    createRightFile();
    createIndexFile();
  }

  public static void main(String[] args) {
    String path = ".\\";

    if( args != null && args.length == 1 )
      path = args[0];

    Indexer in = new Indexer(path);
    in.createIndex();
  }
}