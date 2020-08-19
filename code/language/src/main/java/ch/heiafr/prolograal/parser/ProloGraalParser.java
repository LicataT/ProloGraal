// Generated from ProloGraal.g4 by ANTLR 4.7.1
package ch.heiafr.prolograal.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ProloGraalParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, WHITESPACE=3, COMMENT=4, TERMINATOR=5, CLAUSE_MARKER=6, 
		SEPARATOR=7, LIST_START=8, LIST_END=9, LIST_ENDING=10, ATOM=11, VARIABLE=12, 
		NUMBER=13;
	public static final int
		RULE_prolograal = 0, RULE_atom = 1, RULE_number = 2, RULE_variable = 3, 
		RULE_functor = 4, RULE_composedTerm = 5, RULE_term = 6, RULE_head = 7, 
		RULE_fact = 8, RULE_goal = 9, RULE_goalList = 10, RULE_composedClause = 11, 
		RULE_query = 12, RULE_clause = 13, RULE_tail = 14, RULE_list = 15;
	public static final String[] ruleNames = {
		"prolograal", "atom", "number", "variable", "functor", "composedTerm", 
		"term", "head", "fact", "goal", "goalList", "composedClause", "query", 
		"clause", "tail", "list"
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

	@Override
	public String getGrammarFileName() { return "ProloGraal.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ProloGraalParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProlograalContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ProloGraalParser.EOF, 0); }
		public List<ClauseContext> clause() {
			return getRuleContexts(ClauseContext.class);
		}
		public ClauseContext clause(int i) {
			return getRuleContext(ClauseContext.class,i);
		}
		public List<QueryContext> query() {
			return getRuleContexts(QueryContext.class);
		}
		public QueryContext query(int i) {
			return getRuleContext(QueryContext.class,i);
		}
		public ProlograalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prolograal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterProlograal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitProlograal(this);
		}
	}

	public final ProlograalContext prolograal() throws RecognitionException {
		ProlograalContext _localctx = new ProlograalContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prolograal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LIST_START || _la==ATOM) {
				{
				setState(34);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(32);
					clause();
					}
					break;
				case 2:
					{
					setState(33);
					query();
					}
					break;
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(39);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public TerminalNode ATOM() { return getToken(ProloGraalParser.ATOM, 0); }
		public TerminalNode LIST_START() { return getToken(ProloGraalParser.LIST_START, 0); }
		public TerminalNode LIST_END() { return getToken(ProloGraalParser.LIST_END, 0); }
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitAtom(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_atom);
		try {
			setState(44);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ATOM:
				enterOuterAlt(_localctx, 1);
				{
				setState(41);
				match(ATOM);
				}
				break;
			case LIST_START:
				enterOuterAlt(_localctx, 2);
				{
				setState(42);
				match(LIST_START);
				setState(43);
				match(LIST_END);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(ProloGraalParser.NUMBER, 0); }
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitNumber(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_number);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(NUMBER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableContext extends ParserRuleContext {
		public TerminalNode VARIABLE() { return getToken(ProloGraalParser.VARIABLE, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitVariable(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			match(VARIABLE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctorContext extends ParserRuleContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public FunctorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterFunctor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitFunctor(this);
		}
	}

	public final FunctorContext functor() throws RecognitionException {
		FunctorContext _localctx = new FunctorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_functor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			atom();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComposedTermContext extends ParserRuleContext {
		public FunctorContext functor() {
			return getRuleContext(FunctorContext.class,0);
		}
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public ComposedTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_composedTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterComposedTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitComposedTerm(this);
		}
	}

	public final ComposedTermContext composedTerm() throws RecognitionException {
		ComposedTermContext _localctx = new ComposedTermContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_composedTerm);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			functor();
			{
			setState(53);
			match(T__0);
			setState(54);
			term();
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEPARATOR) {
				{
				{
				setState(55);
				match(SEPARATOR);
				setState(56);
				term();
				}
				}
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(62);
			match(T__1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TermContext extends ParserRuleContext {
		public ComposedTermContext composedTerm() {
			return getRuleContext(ComposedTermContext.class,0);
		}
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public ListContext list() {
			return getRuleContext(ListContext.class,0);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_term);
		try {
			setState(69);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				composedTerm();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				atom();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(66);
				number();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(67);
				variable();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(68);
				list();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HeadContext extends ParserRuleContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public ComposedTermContext composedTerm() {
			return getRuleContext(ComposedTermContext.class,0);
		}
		public HeadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_head; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterHead(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitHead(this);
		}
	}

	public final HeadContext head() throws RecognitionException {
		HeadContext _localctx = new HeadContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_head);
		try {
			setState(73);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(71);
				atom();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(72);
				composedTerm();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FactContext extends ParserRuleContext {
		public HeadContext head() {
			return getRuleContext(HeadContext.class,0);
		}
		public TerminalNode TERMINATOR() { return getToken(ProloGraalParser.TERMINATOR, 0); }
		public FactContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fact; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterFact(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitFact(this);
		}
	}

	public final FactContext fact() throws RecognitionException {
		FactContext _localctx = new FactContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_fact);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			head();
			setState(76);
			match(TERMINATOR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GoalContext extends ParserRuleContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public ComposedTermContext composedTerm() {
			return getRuleContext(ComposedTermContext.class,0);
		}
		public GoalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterGoal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitGoal(this);
		}
	}

	public final GoalContext goal() throws RecognitionException {
		GoalContext _localctx = new GoalContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_goal);
		try {
			setState(80);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				atom();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				composedTerm();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GoalListContext extends ParserRuleContext {
		public List<GoalContext> goal() {
			return getRuleContexts(GoalContext.class);
		}
		public GoalContext goal(int i) {
			return getRuleContext(GoalContext.class,i);
		}
		public TerminalNode TERMINATOR() { return getToken(ProloGraalParser.TERMINATOR, 0); }
		public List<TerminalNode> SEPARATOR() { return getTokens(ProloGraalParser.SEPARATOR); }
		public TerminalNode SEPARATOR(int i) {
			return getToken(ProloGraalParser.SEPARATOR, i);
		}
		public GoalListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_goalList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterGoalList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitGoalList(this);
		}
	}

	public final GoalListContext goalList() throws RecognitionException {
		GoalListContext _localctx = new GoalListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_goalList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			goal();
			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEPARATOR) {
				{
				{
				setState(83);
				match(SEPARATOR);
				setState(84);
				goal();
				}
				}
				setState(89);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(90);
			match(TERMINATOR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComposedClauseContext extends ParserRuleContext {
		public HeadContext head() {
			return getRuleContext(HeadContext.class,0);
		}
		public TerminalNode CLAUSE_MARKER() { return getToken(ProloGraalParser.CLAUSE_MARKER, 0); }
		public GoalListContext goalList() {
			return getRuleContext(GoalListContext.class,0);
		}
		public ComposedClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_composedClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterComposedClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitComposedClause(this);
		}
	}

	public final ComposedClauseContext composedClause() throws RecognitionException {
		ComposedClauseContext _localctx = new ComposedClauseContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_composedClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			head();
			setState(93);
			match(CLAUSE_MARKER);
			setState(94);
			goalList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryContext extends ParserRuleContext {
		public GoalListContext goalList() {
			return getRuleContext(GoalListContext.class,0);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitQuery(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_query);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			goalList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClauseContext extends ParserRuleContext {
		public FactContext fact() {
			return getRuleContext(FactContext.class,0);
		}
		public ComposedClauseContext composedClause() {
			return getRuleContext(ComposedClauseContext.class,0);
		}
		public ClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitClause(this);
		}
	}

	public final ClauseContext clause() throws RecognitionException {
		ClauseContext _localctx = new ClauseContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_clause);
		try {
			setState(100);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(98);
				fact();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(99);
				composedClause();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TailContext extends ParserRuleContext {
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TailContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tail; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterTail(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitTail(this);
		}
	}

	public final TailContext tail() throws RecognitionException {
		TailContext _localctx = new TailContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_tail);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			term();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListContext extends ParserRuleContext {
		public TerminalNode LIST_START() { return getToken(ProloGraalParser.LIST_START, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public TerminalNode LIST_END() { return getToken(ProloGraalParser.LIST_END, 0); }
		public List<TerminalNode> SEPARATOR() { return getTokens(ProloGraalParser.SEPARATOR); }
		public TerminalNode SEPARATOR(int i) {
			return getToken(ProloGraalParser.SEPARATOR, i);
		}
		public TerminalNode LIST_ENDING() { return getToken(ProloGraalParser.LIST_ENDING, 0); }
		public TailContext tail() {
			return getRuleContext(TailContext.class,0);
		}
		public ListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).enterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ProloGraalListener ) ((ProloGraalListener)listener).exitList(this);
		}
	}

	public final ListContext list() throws RecognitionException {
		ListContext _localctx = new ListContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(LIST_START);
			setState(105);
			term();
			setState(110);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEPARATOR) {
				{
				{
				setState(106);
				match(SEPARATOR);
				setState(107);
				term();
				}
				}
				setState(112);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(115);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LIST_ENDING) {
				{
				setState(113);
				match(LIST_ENDING);
				setState(114);
				tail();
				}
			}

			setState(117);
			match(LIST_END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\17z\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\7\2%\n"+
		"\2\f\2\16\2(\13\2\3\2\3\2\3\3\3\3\3\3\5\3/\n\3\3\4\3\4\3\5\3\5\3\6\3\6"+
		"\3\7\3\7\3\7\3\7\3\7\7\7<\n\7\f\7\16\7?\13\7\3\7\3\7\3\b\3\b\3\b\3\b\3"+
		"\b\5\bH\n\b\3\t\3\t\5\tL\n\t\3\n\3\n\3\n\3\13\3\13\5\13S\n\13\3\f\3\f"+
		"\3\f\7\fX\n\f\f\f\16\f[\13\f\3\f\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\17\3"+
		"\17\5\17g\n\17\3\20\3\20\3\21\3\21\3\21\3\21\7\21o\n\21\f\21\16\21r\13"+
		"\21\3\21\3\21\5\21v\n\21\3\21\3\21\3\21\2\2\22\2\4\6\b\n\f\16\20\22\24"+
		"\26\30\32\34\36 \2\2\2w\2&\3\2\2\2\4.\3\2\2\2\6\60\3\2\2\2\b\62\3\2\2"+
		"\2\n\64\3\2\2\2\f\66\3\2\2\2\16G\3\2\2\2\20K\3\2\2\2\22M\3\2\2\2\24R\3"+
		"\2\2\2\26T\3\2\2\2\30^\3\2\2\2\32b\3\2\2\2\34f\3\2\2\2\36h\3\2\2\2 j\3"+
		"\2\2\2\"%\5\34\17\2#%\5\32\16\2$\"\3\2\2\2$#\3\2\2\2%(\3\2\2\2&$\3\2\2"+
		"\2&\'\3\2\2\2\')\3\2\2\2(&\3\2\2\2)*\7\2\2\3*\3\3\2\2\2+/\7\r\2\2,-\7"+
		"\n\2\2-/\7\13\2\2.+\3\2\2\2.,\3\2\2\2/\5\3\2\2\2\60\61\7\17\2\2\61\7\3"+
		"\2\2\2\62\63\7\16\2\2\63\t\3\2\2\2\64\65\5\4\3\2\65\13\3\2\2\2\66\67\5"+
		"\n\6\2\678\7\3\2\28=\5\16\b\29:\7\t\2\2:<\5\16\b\2;9\3\2\2\2<?\3\2\2\2"+
		"=;\3\2\2\2=>\3\2\2\2>@\3\2\2\2?=\3\2\2\2@A\7\4\2\2A\r\3\2\2\2BH\5\f\7"+
		"\2CH\5\4\3\2DH\5\6\4\2EH\5\b\5\2FH\5 \21\2GB\3\2\2\2GC\3\2\2\2GD\3\2\2"+
		"\2GE\3\2\2\2GF\3\2\2\2H\17\3\2\2\2IL\5\4\3\2JL\5\f\7\2KI\3\2\2\2KJ\3\2"+
		"\2\2L\21\3\2\2\2MN\5\20\t\2NO\7\7\2\2O\23\3\2\2\2PS\5\4\3\2QS\5\f\7\2"+
		"RP\3\2\2\2RQ\3\2\2\2S\25\3\2\2\2TY\5\24\13\2UV\7\t\2\2VX\5\24\13\2WU\3"+
		"\2\2\2X[\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z\\\3\2\2\2[Y\3\2\2\2\\]\7\7\2\2]"+
		"\27\3\2\2\2^_\5\20\t\2_`\7\b\2\2`a\5\26\f\2a\31\3\2\2\2bc\5\26\f\2c\33"+
		"\3\2\2\2dg\5\22\n\2eg\5\30\r\2fd\3\2\2\2fe\3\2\2\2g\35\3\2\2\2hi\5\16"+
		"\b\2i\37\3\2\2\2jk\7\n\2\2kp\5\16\b\2lm\7\t\2\2mo\5\16\b\2nl\3\2\2\2o"+
		"r\3\2\2\2pn\3\2\2\2pq\3\2\2\2qu\3\2\2\2rp\3\2\2\2st\7\f\2\2tv\5\36\20"+
		"\2us\3\2\2\2uv\3\2\2\2vw\3\2\2\2wx\7\13\2\2x!\3\2\2\2\r$&.=GKRYfpu";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}