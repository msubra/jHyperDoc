package jhd.parser;

import java.io.FileInputStream;

import jhd.color.VBLexer;
import jhd.util.ColorTemplates;
import antlr.Token;

public class VBParser
    extends Parser {

  public VBParser(String fileName) {
    this(fileName, "vb");
  }

  public VBParser(String fileName, String templateName) {
    super(fileName, ColorTemplates.get(templateName), "vb");
  }

  protected void format() {
    int type = 0;
    Token tok;
    String data = null;

    open();

    try {
      do {
        tok = toks.nextToken();
        type = tok.getType();
        data = tok.getText();

        switch (type) {
          case VBLexer.BRACKETS:
            doc.writeBracket(data);
            break;
          case VBLexer.SL_COMMENT:
            doc.writeComment(data);
            break;
          case VBLexer.NUM_INT:
            doc.writeNumber(data);
            break;
          case VBLexer.OPERATORS:
            doc.writeOperator(data);
            break;
          case VBLexer.STRING_LITERAL:
            doc.writeString(data);
            break;
          case VBLexer.WS:
            doc.writeWhiteSpace(data);
            break;
          default:
            if (isKeyword(data)) {
              doc.writeKeyword(data);
            }
            else if (isDataType(data)) {
              doc.writeDataType(data);
            }
            else {
              doc.writeData(data);
            }
            break;
        }
      }
      while (type != Token.EOF_TYPE);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void open() {
    try {
      FileInputStream fis = new FileInputStream(getFileName());
      toks = new VBLexer(fis);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}