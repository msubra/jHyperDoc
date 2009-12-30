package jhd.util;

import java.io.File;
import java.io.FileOutputStream;

public class HtmlWriter {

  private ColorTemplate template;
  private String fileName, dirName;
  private java.io.FileOutputStream out;
  private StringBuffer sb, data;

  public HtmlWriter(ColorTemplate template, String fileName) {
    this.template = template;
    this.fileName = fileName;
    this.dirName = new File(fileName).getParent();

    sb = new StringBuffer();
    data = new StringBuffer();
  }

  private void openFile() {
    try {
      out = new java.io.FileOutputStream(fileName + ".html");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void closeFile() {
    try {
      out.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void write(String s) {
    if (s == null) {
      return;
    }

    try {
      for (int i = 0; i < s.length(); i++) {
        out.write(s.charAt(i));
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String formatWS(String s) {
    StringBuffer b = new StringBuffer();
    char c = 0;

    if (s == null) {
      return "";
    }

    for (int i = 0; i < s.length(); i++) {
      c = s.charAt(i);
      switch (c) {
        case '\r':
          i++;
          c = s.charAt(i);
          if (c == '\n') {
            b.append("<br>");
          }
          else {
            i--;
          }
          break;
        case ' ':
          b.append("&nbsp;");
          break;
        case '\n':
          b.append("<br>");
          break;
        case '\t':
          for (int j = 0; j < 3; j++) {
            b.append("&nbsp;");
          }
          break;
        default:
          b.append(c);
          break;
      }
    }
    return b.toString();
  }

  private String formatSymbols(String s) {
    StringBuffer b = new StringBuffer();
    char c = 0;

    if (s == null) {
      return "";
    }

    for (int i = 0; i < s.length(); i++) {
      c = s.charAt(i);

      switch (c) {
        case '<':
          b.append("&lt;");
          break;
        case '>':
          b.append("&gt;");
          break;
        case '&':
          b.append("&amp;");
          break;
        case '\"':
          b.append("&quot;");
          break;
        case ' ':
          b.append("&nbsp;");
          break;
        case '\n':
          b.append("<br>");
          break;
        default:
          b.append(c);
          break;
      }
    }
    return b.toString();
  }

  public ColorTemplate getTemplate() {
    return template;
  }

  public void writeBegin() {
    writeAsTag("<html>");
    writeAsTag("<head>");
  }

  public void writeTitle() {
    writeAsTag("<title>");
    writeAsTag(new java.io.File(fileName).getName());
    writeAsTag("</title>");
  }

  public void writeStyle() {
    writeAsTag("<link type=text/css rel=stylesheet href=");
    writeAsTag('\"' + getTemplate().getTemplateName() + ".css \" >");
  }

  public void writeBody() {
    writeAsTag("<body>");
  }

  public void writeTable() {
/* main table */
    writeAsTag("<table cellpadding=\"0\" cellspacing=\"0\" style=\"font-size: 10pt; border-collapse: collapse\" width = 100% >");
    writeAsTag("<tr>");
    writeAsTag("<td width = 100% align=\"left\" valign=\"top\">");

    writeSourceCodeTitle();

    writeAsTag("<tr>");
    writeAsTag(
        "<td class = sourceCode width = 100% align=\"left\" valign=\"top\">");
  }

  public void writeEndTable() {
    writeAsTag("</td>");
    writeAsTag("</tr>");
    writeAsTag("</table>");
  }

  public void writeEnd() {
    writeAsTag("</body>");
    writeAsTag("</html>");
  }

  public String writeFont(String className, String data) {
    String s = new String();

    data = formatSymbols(data);
    data = formatWS(data);

    s = "<font class = \"" + className + "\" >";
    s += data;
    s += "</font>";

    return s;
  }

  public void writeComment(String s) {
    sb.append(writeFont("comment", s));
  }

  public void writeWhiteSpace(String s) {
    sb.append(formatWS(s));
  }

  public void writeData(String s) {
    s = formatSymbols(s);
    sb.append(formatWS(s));
  }

  public StringBuffer makeData(String s) {
    StringBuffer buf = new StringBuffer();
    s = formatSymbols(s);
    s = formatWS(s);
    buf.append(s);

    return buf;
  }

  public void writeKeyword(String s) {
    sb.append(writeFont("keyword", s));
  }

  public void writeDataType(String s) {
    sb.append(writeFont("datatype", s));
  }

  public void writeBracket(String s) {
    sb.append(writeFont("bracket", s));
  }

  public void writeOperator(String s) {
    sb.append(writeFont("operator", s));
  }

  public void writeNumber(String s) {
    sb.append(writeFont("number", s));
  }

  public void writeChar(String s) {
    sb.append(writeFont("chars", s));
  }

  public void writeString(String s) {
    sb.append(writeFont("string", s));
  }

  public void start() {
    openFile();
    toCSSFile();
    writeBegin();
    writeTitle();
    writeStyle();
    writeBody();
    writeTable();
  }

  public void end() {
    writeSourceCode();
    writeProductComment();
    writeEndTable();
    writeEnd();
    write(data.toString());
    closeFile();
  }

  public void writeLink(String addr) {
    sb.append("<A href=");
    sb.append("\"" + addr + "\">");
    sb.append(addr);
    sb.append("</A>");
  }

  public StringBuffer makeLink(String addr) {
    StringBuffer buf = new StringBuffer();
    buf.append("<A href=");
    buf.append("\"" + addr + "\">");
    buf.append(addr);
    buf.append("</A>");

    return buf;
  }

  public void writeAsTag(String s) {
    data.append(s);
  }

  public void toCSSFile() {
    String cssName = getTemplate().getTemplateName() + ".css";
    String path = dirName + File.separatorChar + cssName;
    File f = new File(path);

    if( f.exists() )
      return;

    try {
      FileOutputStream fout = new FileOutputStream(f);
      String s = getTemplate().getCSSCode();

      for (int i = 0; i < s.length(); i++) {
        fout.write(s.charAt(i));
      }
      fout.close();
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }

  public void writeSourceCode(){
    StringBuffer buf = new StringBuffer();

    buf.append("<tr>");
    /* source code column */
    buf.append("<td class = sourceCode align=\"left\" valign=\"top\" >");
    buf.append(sb);
    buf.append("</td>");
    buf.append("</tr>");

    /* end */
    data.append(buf);
  }

  public void writeSourceCodeTitle(){
    StringBuffer buf = new StringBuffer();

    /* source code title */
    buf.append("<tr>");
    buf.append("<td width = 100% class = sourceTitle>");

    buf.append("<b>");
    buf.append("Source Code:\t\t\t");
    buf.append(new java.io.File(fileName).getName());
    buf.append("</b>");
    buf.append("</td>");
    buf.append("</tr>");

    /* end */
    data.append(buf);
  }

  public void writeProductComment(){
    StringBuffer buf = new StringBuffer();
    String productName = "jHyperDoc", creatorName = "http://microprogrammers.150m.com";

    /* source code title */
    buf.append("<tr>");
    buf.append("<td class = docComment>");
    buf.append("Doc Created at( Date & Time ): " + new java.util.Date() +
               "<br>");
    buf.append("Doc Created by " + productName);
    buf.append(makeData("   "));
    buf.append("Copyright(c) " + creatorName + "<br>");

    buf.append(makeLink(creatorName));

    buf.append("</td>");
    buf.append("</tr>");

    /* end of table */

    data.append(buf);
  }

}