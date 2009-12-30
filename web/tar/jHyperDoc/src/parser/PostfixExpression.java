package parser;

import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;

public class PostfixExpression {
	public final static char SYM_AND = '*';
	public final static char SYM_NAND = '@';
	public final static char SYM_NOR = '|';
	public final static char SYM_NOT = '~';
	public final static char SYM_OR = '+';
	public final static char SYM_XOR = '^';

	/** ------------variables declaration-------------* */

	private String delim = "+*~()^@|";

	private String infix, postfix;

	public int NOT_VALID_SYMBOL = -1;

	private StringTokenizer tokens;
	private LinkedList toks;
	private Vector varList;

	public PostfixExpression() {
		this.postfix = null;
	}

	private void addVariable(String s) {
		if (!varList.contains(s) && isVariable(s))
			varList.add(s);
	}

	public String getExpression() {
		return infix;
	}

	private int getPrecedence(String s) {
		char c = s.charAt(0);

		switch (c) {
			case '(' :
				return 0;
			case '+' :
			case '*' :
			case '^' :
			case '|' :
			case '@' :
				return 1;
			case '~' :
				return 2;
			default :
				return NOT_VALID_SYMBOL;
		}

	}

	public LinkedList getTokens() {
		return toks;
	}

	public Vector getVariables() {
		return varList;
	}

	private void intoPostFix() {

		String cur_oper, old_oper;
		Stack opr = new Stack();

		String tempStr = removeSpace(infix);
		varList = new Vector();

		toks = new LinkedList();
		tokens = new StringTokenizer(tempStr, delim, true);

		while (tokens.hasMoreTokens()) {
			String s = tokens.nextToken();

			addVariable(s);

			if (isVariable(s)) {
				toks.add(s);
			} else {
				cur_oper = s;

				if (cur_oper.equalsIgnoreCase("("))
					opr.push(cur_oper);
				else if (cur_oper.equalsIgnoreCase(")")) {
					boolean match = false;
					while (!opr.isEmpty()
							&& (old_oper = opr.pop().toString()) != null) {
						if (old_oper.equals("(")) {
							match = true;
							break;
						}
						toks.add(old_oper);
					}

					if (!match)
						throw new IllegalArgumentException(
								"Error In Paranthesis");

				} else {
					while (!opr.isEmpty()
							&& getPrecedence(opr.peek().toString()) >= getPrecedence(cur_oper)) {
						old_oper = opr.pop().toString();
						toks.add(old_oper);
					}

					opr.push(cur_oper);
				}
			}

		}

		while (!opr.isEmpty()) {
			old_oper = opr.pop().toString();
			if (old_oper.equals("(") || old_oper.equals(")"))
				throw new IllegalArgumentException("Error In Paranthesis");
			toks.add(old_oper);
		}

		postfix = toks.toString();
	}

	private boolean isAlphaNumeric(String v) {
		boolean flag = false;
		for (int i = 0; i < v.length(); i++) {
			flag = Character.isLetterOrDigit(v.charAt(i))
					|| Character.isDefined(v.charAt(i));

			if (false == flag)
				return flag;
		}
		return flag;
	}

	public boolean isVariable(String v) {
		if (Character.isLetter(v.charAt(0)) && isAlphaNumeric(v))
			return true;

		return false;
	}

	private String removeSpace(String s) {
		StringBuffer temp = new StringBuffer();
		int i = 0;
		for (; i < s.length(); i++) {
			if (s.charAt(i) == ' ')
				continue;
			temp.append(s.charAt(i));
		}
		return temp.toString();
	}

	public String toPostFix(String expression) {
		this.infix = expression;
		intoPostFix();
		return postfix;
	}

}