package jhd.parser;

import java.io.FileInputStream;

import jhd.color.CCPPLexer;
import jhd.util.ColorTemplates;
import antlr.Token;

public class CCPPParser
    extends Parser {

  public CCPPParser(String fileName) {
    this(fileName, "kawa");
  }

  public CCPPParser(String fileName, String templateName) {
    super(fileName, ColorTemplates.get(templateName), "ccpp");
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
          case CCPPLexer.BRACKETS:
            doc.writeBracket(data);
            break;
          case CCPPLexer.CHAR_LITERAL:
            doc.writeChar(data);
            break;
          case CCPPLexer.ML_COMMENT:
          case CCPPLexer.SL_COMMENT:
            doc.writeComment(data);
            break;
          case CCPPLexer.NUM_DOUBLE:
          case CCPPLexer.NUM_FLOAT:
          case CCPPLexer.NUM_INT:
          case CCPPLexer.NUM_LONG:
            doc.writeNumber(data);
            break;
          case CCPPLexer.OPERATORS:
            doc.writeOperator(data);
            break;
          case CCPPLexer.STRING_LITERAL:
            doc.writeString(data);
            break;
          case CCPPLexer.WS:
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
      FileInputStream in = new FileInputStream(getFileName());
      toks = new CCPPLexer(in);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}