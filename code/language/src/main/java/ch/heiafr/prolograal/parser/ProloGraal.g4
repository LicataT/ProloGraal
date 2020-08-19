// This file contains the ANTLR4 grammar for our subset of Prolog
//

grammar ProloGraal;



// parser

prolograal :
(clause | query)* EOF // zero or more clauses or queries and the EOF
;

// an atom can be the lexical definition of an atom or an empty list
atom :
ATOM |
LIST_START LIST_END
;

number :
NUMBER
;

variable :
VARIABLE
;

functor :
atom // a functor can only be an atom
;

// a composed term is a functor followed by one or more parenthesis enclosed, comma separated terms.
composedTerm :
functor ('(' term (',' term)* ')') 
;

term :
composedTerm |
atom | 
number |
variable | 
list
;

head :
atom |
composedTerm
;

fact :
head TERMINATOR
;

// a goal can only be either an atom or a composed term, it cannot be a number or something else.
goal :
atom |
composedTerm
;

goalList :
goal (SEPARATOR goal)* TERMINATOR
;

// a composed clause is an head follow by one or more goals separated with a comma
composedClause :
head CLAUSE_MARKER goalList
;

// a query is a goalList.
query :
goalList
;

// a clause is either a fact, a goal list or a composed clause
clause :
fact |
composedClause
;

// the tail of a list can be any term
tail :
term
;

// a list is bracket enclosed, and contains one or more terms followed by a possible tail
// note that it cannot be empty since the empty list is an atom
list :
LIST_START
term (SEPARATOR term)* (LIST_ENDING tail)?
LIST_END
;

// lexer

WHITESPACE : [ \t\r\n]+ -> skip;
COMMENT : '%' ~[\r\n]* -> skip;
TERMINATOR : '.';
CLAUSE_MARKER : ':-';
SEPARATOR : ',';
LIST_START : '[';
LIST_END : ']';
LIST_ENDING : '|';

fragment UNDERSCORE : '_';
fragment LOWERCASE : [a-z];
fragment UPPERCASE : [A-Z];
fragment DIGITS : [0-9];

// an atom must start with a lowercase
// it can also be anything enclosed in single quotes
ATOM : (LOWERCASE (LOWERCASE | UPPERCASE | DIGITS | UNDERSCORE)*) | ('~' .*? '~') | ('\'' .*? '\'');
// a variable must start by an uppercase or an underscore
// that means that during parsing no difference is made between single use (anonymous) and "normal" variables.
VARIABLE : ((UPPERCASE | UNDERSCORE) (LOWERCASE | UPPERCASE | DIGITS | UNDERSCORE)*);
// a number is one or more digits followed by possibly a dot and more digits
// that means that during parsing no difference is made between integer and floating points.
NUMBER : DIGITS+ ('.' DIGITS+)?;