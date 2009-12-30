header{
package color;
}
class JavaScriptLexer extends Lexer;

options {
	testLiterals=false;    // don't automatically test for literals
	k=4;                   // four characters of lookahead
	charVocabulary='\u0003'..'\uFFFF';
	// without inlining some bitset tests, couldn't do unicode;
	// I need to make ANTLR generate smaller bitsets; see
	codeGenBitsetTestThreshold=20;
}


BRACKETS
	:	'('		|
		')'		|
		'['		|
		']'		|
		'{'		|
		'}'	
	;

OPERATORS	
	:	'?'		|
		','		|
		':'		|
		'='		|
		"=="	|
		'!'		|
		'~'		|
		"!="	|
		'/'		|
		"/="	|
		'+'		|
		"+="	|
		"++"	|
		'-'		|
		"-="	|
		"--"	|
		'*'		|
		"*="	|
		'%'		|
		"%="	|
		">>"	|
		">>="	|
		">="	|
		">"		|
		"<<"	|
		"<<="	|
		"<<<"	|
		"<<<="	|
		">>>"	|
		">>>="	|
		"<="	|
		'<'	|
		'^'	|
		"^="	|
		'|'	|
		"|="	|
		"||"	|
		'&'	|
		"&="	|
		"&&"	|
		';'	|
		'.'
	;

// Whitespace -- ignored
WS	:	(	' '
		|	'\t'
		|	'\f' 
		|	(	options {generateAmbigWarnings=false;}
			:	"\r\n" // Evil DOS
			|	'\r'    // Macintosh
			|	'\n'    // Unix (the right way)
			)
			{newline();}
		)+
	;

// Single-line comments
SL_COMMENT
	:	"//"
		(~('\n'|'\r'))* ('\n'|'\r'('\n')?)
		{newline();}
	;

// multiple-line comments
ML_COMMENT
	:	"/*"
		(	/*	'\r' '\n' can be matched in one alternative or by matching
				'\r' in one iteration and '\n' in another.  I am trying to
				handle any flavor of newline that comes in, but the language
				that allows both "\r\n" and "\r" and "\n" to all be valid
				newline is ambiguous.  Consequently, the resulting grammar
				must be ambiguous.  I'm shutting this warning off.
			 */
			options {
				generateAmbigWarnings=false;
			}
		:
			{ LA(2)!='/' }? '*'
		|	'\r' '\n'	{newline();}
		|	'\r'		{newline();}
		|	'\n'		{newline();}
		|	~('*'|'\n'|'\r')
		)*
		"*/"
	;

STRING_LITERAL2
	:	'\'' (ESC | ~'\'' )* '\''
	;

// string literals
STRING_LITERAL
	:	'"' (ESC | ~('"' | '\\') )* '"'
	;


// escape sequence -- note that this is protected; it can only be called
//   from another lexer rule -- it will not ever directly return a token to
//   the parser
// There are various ambiguities hushed in this rule.  The optional
// '0'...'9' digit matches should be matched here rather than letting
// them go back to STRING_LITERAL to be matched.  ANTLR does the
// right thing by matching immediately; hence, it's ok to shut off
// the FOLLOW ambig warnings.
protected
ESC
	:	'\\'
		(	'n'
		|	'r'
		|	't'
		|	'b'
		|	'f'
		|	'a'
		|	'"'
		|	'\''
		|	'\\'
		|	('u')+ HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT 
		)
	;


// an identifier.  Note that testLiterals is set to true!  This means
// that after we match the rule, we look in the literals table to see
// if it's a literal or really an identifer
IDENT
	options {testLiterals=true;}
	:	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*
	;

NUM_INT
	:	REAL
	|	HEX_NUM
	;

protected
OCTAL_DIGIT
	:	'0'..'7'
	;

// hexadecimal digit (again, note it's protected!)
protected
HEX_DIGIT
	:	('0'..'9'|'A'..'F'|'a'..'f')
	;


// a dummy rule to force vocabulary to be all characters (except special
//   ones that ANTLR uses internally (0 to 2)
protected
VOCAB
	:	'\3'..'\377'
	;

protected
HEX_NUM
	:	'0' 'x' (HEX_DIGIT)+
	;

protected
INT	: (DIGIT)+ ( 'l' | 'L' )?
	;

protected
REAL	
	: INT ( '.' INT | EXPONENT )? (FLOAT_SUFFIX)?
	;

protected
DIGIT
	: '0'..'9'
	;
// a couple protected methods to assist in matching floating point numbers
protected
EXPONENT
	:	('e'|'E') ('+'|'-')? ('0'..'9')+
	;
protected
FLOAT_SUFFIX
	:	('f'|'F'|'d'|'D')
	;