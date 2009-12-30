package jhd.parser;

import java.io.FileInputStream;

import jhd.color.PythonLexer;
import jhd.util.ColorTemplates;
import antlr.Token;

public class PythonParser
    extends Parser {

  public PythonParser(String fileName) {
    this(fileName, "python");
  }

  public PythonParser(String fileName, String templateName) {
    super(fileName, ColorTemplates.get(templateName), "python");
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
          case PythonLexer.BRACKETS:
            doc.writeBracket(data);
            break;
          case PythonLexer.SL_COMMENT:
            doc.writeComment(data);
            break;
          case PythonLexer.NUM_INT:
            doc.writeNumber(data);
            break;
          case PythonLexer.OPERATORS:
            doc.writeOperator(data);
            break;
          case PythonLexer.STRING_LITERAL:
          case PythonLexer.STRING_LITERAL2:
            doc.writeString(data);
            break;
          case PythonLexer.WS:
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
      toks = new PythonLexer(fis);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}