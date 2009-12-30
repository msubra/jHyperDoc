header{
package color;
}
class VBLexer extends Lexer;

options {
	testLiterals=false;    // don't automatically test for literals
	k=1;                  
	charVocabulary='\u0003'..'\uFFFF';
	// without inlining some bitset tests, couldn't do unicode;
	// I need to make ANTLR generate smaller bitsets; see
	codeGenBitsetTestThreshold=20;
}

tokens{
HEXA;
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
		'!'		|
		'/'		|
		'\\'	|
		'+'		|
		'-'		|
		'*'		|
		'%'		|
		'>'	|
		'<'	|
		'^'	|
		';'	|
		'.'	|
		'$'	|
		'#'
	;

// Whitespace -- ignored
WS	:	(	' '
		|	'\t'
		|	'\f' 
		|	(	options {generateAmbigWarnings=false;}
			:	"\r\n" // Evil DOS
			|	'\r'    // Macintosh
			|	'\n'	    // Unix (the right way)
			)
			{newline();}
		)+
	;

// Single-line comments
SL_COMMENT
	:	'\'' (~('\n'|'\r'))* ('\n'|'\r'('\n')?)
		{newline();}
	;


// string literals
STRING_LITERAL
	:	'"' (~('"'))* '"'
	;


// an identifier.  Note that testLiterals is set to true!  This means
// that after we match the rule, we look in the literals table to see
// if it's a literal or really an identifer
IDENT
	options {testLiterals=true;}
	:	('a'..'z'|'A'..'Z'|'_') ('a'..'z'|'A'..'Z'|'_'|'0'..'9')*
	;

NUM_INT
	:	INT ( ( '.' INT ) (EXPONENT)? )?
	|	'&' { $setType(OPERATORS); }
		 ( 'H' (HEX_DIGIT)+ { $setType(NUM_INT); } )? 
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
REAL	
	:	INT '.' INT
	;
	
protected
INT	:	(DIGIT)+
	;
	
protected
DIGIT
	:	'0'..'9'
	;

// a couple protected methods to assist in matching floating point numbers
protected
EXPONENT
	:	('e'|'E') ('+'|'-')? ('0'..'9')+
	;
