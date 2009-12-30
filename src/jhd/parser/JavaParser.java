package jhd.parser;

import java.io.FileInputStream;

import jhd.color.JavaLexer;
import jhd.util.ColorTemplates;
import antlr.Token;

public class JavaParser
    extends Parser {

  public JavaParser(String fileName) {
    this(fileName,"kawa");
  }

  public JavaParser(String fileName, String templateName) {
    super(fileName, ColorTemplates.get(templateName),"java");
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
          case JavaLexer.BRACKETS:
            doc.writeBracket(data);
            break;
          case JavaLexer.CHAR_LITERAL:
            doc.writeChar(data);
            break;
          case JavaLexer.ML_COMMENT:
          case JavaLexer.SL_COMMENT:
            doc.writeComment(data);
            break;
          case JavaLexer.NUM_DOUBLE:
          case JavaLexer.NUM_FLOAT:
          case JavaLexer.NUM_INT:
          case JavaLexer.NUM_LONG:
            doc.writeNumber(data);
            break;
          case JavaLexer.OPERATORS:
            doc.writeOperator(data);
            break;
          case JavaLexer.STRING_LITERAL:
            doc.writeString(data);
            break;
          case JavaLexer.WS:
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
      toks = new JavaLexer(in);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

}