// Generated from ProloGraal.g4 by ANTLR 4.7.1
package ch.heiafr.prolograal.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ProloGraalLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, WHITESPACE=3, COMMENT=4, TERMINATOR=5, CLAUSE_MARKER=6, 
		SEPARATOR=7, LIST_START=8, LIST_END=9, LIST_ENDING=10, ATOM=11, VARIABLE=12, 
		NUMBER=13;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "WHITESPACE", "COMMENT", "TERMINATOR", "CLAUSE_MARKER", 
		"SEPARATOR", "LIST_START", "LIST_END", "LIST_ENDING", "UNDERSCORE", "LOWERCASE", 
		"UPPERCASE", "DIGITS", "ATOM", "VARIABLE", "NUMBER"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", null, null, "'.'", "':-'", "','", "'['", "']'", "'|'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "WHITESPACE", "COMMENT", "TERMINATOR", "CLAUSE_MARKER", 
		"SEPARATOR", "LIST_START", "LIST_END", "LIST_ENDING", "ATOM", "VARIABLE", 
		"NUMBER"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public ProloGraalLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ProloGraal.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\17\u0084\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\3\2\3\2\3\3\3\3\3\4\6\4+\n\4\r\4\16\4,\3\4\3\4\3\5\3\5\7\5\63\n"+
		"\5\f\5\16\5\66\13\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3"+
		"\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\20\3"+
		"\20\7\20T\n\20\f\20\16\20W\13\20\3\20\3\20\7\20[\n\20\f\20\16\20^\13\20"+
		"\3\20\3\20\3\20\7\20c\n\20\f\20\16\20f\13\20\3\20\5\20i\n\20\3\21\3\21"+
		"\5\21m\n\21\3\21\3\21\3\21\3\21\7\21s\n\21\f\21\16\21v\13\21\3\22\6\22"+
		"y\n\22\r\22\16\22z\3\22\3\22\6\22\177\n\22\r\22\16\22\u0080\5\22\u0083"+
		"\n\22\4\\d\2\23\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\2\31"+
		"\2\33\2\35\2\37\r!\16#\17\3\2\7\5\2\13\f\17\17\"\"\4\2\f\f\17\17\3\2c"+
		"|\3\2C\\\3\2\62;\2\u0091\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5\'\3\2\2\2\7*"+
		"\3\2\2\2\t\60\3\2\2\2\139\3\2\2\2\r;\3\2\2\2\17>\3\2\2\2\21@\3\2\2\2\23"+
		"B\3\2\2\2\25D\3\2\2\2\27F\3\2\2\2\31H\3\2\2\2\33J\3\2\2\2\35L\3\2\2\2"+
		"\37h\3\2\2\2!l\3\2\2\2#x\3\2\2\2%&\7*\2\2&\4\3\2\2\2\'(\7+\2\2(\6\3\2"+
		"\2\2)+\t\2\2\2*)\3\2\2\2+,\3\2\2\2,*\3\2\2\2,-\3\2\2\2-.\3\2\2\2./\b\4"+
		"\2\2/\b\3\2\2\2\60\64\7\'\2\2\61\63\n\3\2\2\62\61\3\2\2\2\63\66\3\2\2"+
		"\2\64\62\3\2\2\2\64\65\3\2\2\2\65\67\3\2\2\2\66\64\3\2\2\2\678\b\5\2\2"+
		"8\n\3\2\2\29:\7\60\2\2:\f\3\2\2\2;<\7<\2\2<=\7/\2\2=\16\3\2\2\2>?\7.\2"+
		"\2?\20\3\2\2\2@A\7]\2\2A\22\3\2\2\2BC\7_\2\2C\24\3\2\2\2DE\7~\2\2E\26"+
		"\3\2\2\2FG\7a\2\2G\30\3\2\2\2HI\t\4\2\2I\32\3\2\2\2JK\t\5\2\2K\34\3\2"+
		"\2\2LM\t\6\2\2M\36\3\2\2\2NU\5\31\r\2OT\5\31\r\2PT\5\33\16\2QT\5\35\17"+
		"\2RT\5\27\f\2SO\3\2\2\2SP\3\2\2\2SQ\3\2\2\2SR\3\2\2\2TW\3\2\2\2US\3\2"+
		"\2\2UV\3\2\2\2Vi\3\2\2\2WU\3\2\2\2X\\\7\u0080\2\2Y[\13\2\2\2ZY\3\2\2\2"+
		"[^\3\2\2\2\\]\3\2\2\2\\Z\3\2\2\2]_\3\2\2\2^\\\3\2\2\2_i\7\u0080\2\2`d"+
		"\7)\2\2ac\13\2\2\2ba\3\2\2\2cf\3\2\2\2de\3\2\2\2db\3\2\2\2eg\3\2\2\2f"+
		"d\3\2\2\2gi\7)\2\2hN\3\2\2\2hX\3\2\2\2h`\3\2\2\2i \3\2\2\2jm\5\33\16\2"+
		"km\5\27\f\2lj\3\2\2\2lk\3\2\2\2mt\3\2\2\2ns\5\31\r\2os\5\33\16\2ps\5\35"+
		"\17\2qs\5\27\f\2rn\3\2\2\2ro\3\2\2\2rp\3\2\2\2rq\3\2\2\2sv\3\2\2\2tr\3"+
		"\2\2\2tu\3\2\2\2u\"\3\2\2\2vt\3\2\2\2wy\5\35\17\2xw\3\2\2\2yz\3\2\2\2"+
		"zx\3\2\2\2z{\3\2\2\2{\u0082\3\2\2\2|~\7\60\2\2}\177\5\35\17\2~}\3\2\2"+
		"\2\177\u0080\3\2\2\2\u0080~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0083\3"+
		"\2\2\2\u0082|\3\2\2\2\u0082\u0083\3\2\2\2\u0083$\3\2\2\2\20\2,\64SU\\"+
		"dhlrtz\u0080\u0082\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}