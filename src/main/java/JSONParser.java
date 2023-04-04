// Generated from java-escape by ANTLR 4.11.1

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class JSONParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, STRING=7, NUMBER=8, BOOLEAN=9, 
		NULL=10, NEWLINE=11, SPACE=12;
	public static final int
		RULE_json = 0, RULE_object = 1, RULE_property = 2, RULE_element = 3, RULE_array = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"json", "object", "property", "element", "array"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "','", "'}'", "':'", "'['", "']'", null, null, null, "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, "STRING", "NUMBER", "BOOLEAN", 
			"NULL", "NEWLINE", "SPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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
	public String getGrammarFileName() { return "java-escape"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JSONParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JsonContext extends ParserRuleContext {
		public ObjectContext object() {
			return getRuleContext(ObjectContext.class,0);
		}
		public TerminalNode EOF() { return getToken(JSONParser.EOF, 0); }
		public JsonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_json; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).enterJson(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).exitJson(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JSONVisitor ) return ((JSONVisitor<? extends T>)visitor).visitJson(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonContext json() throws RecognitionException {
		JsonContext _localctx = new JsonContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_json);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			object();
			setState(11);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ObjectContext extends ParserRuleContext {
		public List<TerminalNode> NEWLINE() { return getTokens(JSONParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(JSONParser.NEWLINE, i);
		}
		public List<PropertyContext> property() {
			return getRuleContexts(PropertyContext.class);
		}
		public PropertyContext property(int i) {
			return getRuleContext(PropertyContext.class,i);
		}
		public ObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).enterObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).exitObject(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JSONVisitor ) return ((JSONVisitor<? extends T>)visitor).visitObject(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ObjectContext object() throws RecognitionException {
		ObjectContext _localctx = new ObjectContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_object);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			match(T__0);
			setState(15);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(14);
				match(NEWLINE);
				}
				break;
			}
			setState(28);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==STRING) {
				{
				setState(17);
				property();
				setState(25);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					{
					setState(18);
					match(T__1);
					setState(20);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==NEWLINE) {
						{
						setState(19);
						match(NEWLINE);
						}
					}

					}
					setState(22);
					property();
					}
					}
					setState(27);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(31);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NEWLINE) {
				{
				setState(30);
				match(NEWLINE);
				}
			}

			setState(33);
			match(T__2);
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

	@SuppressWarnings("CheckReturnValue")
	public static class PropertyContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(JSONParser.STRING, 0); }
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public PropertyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_property; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).enterProperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).exitProperty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JSONVisitor ) return ((JSONVisitor<? extends T>)visitor).visitProperty(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyContext property() throws RecognitionException {
		PropertyContext _localctx = new PropertyContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_property);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			match(STRING);
			setState(37); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(36);
				match(T__3);
				}
				}
				setState(39); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__3 );
			setState(41);
			element();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ElementContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(JSONParser.STRING, 0); }
		public TerminalNode NUMBER() { return getToken(JSONParser.NUMBER, 0); }
		public TerminalNode BOOLEAN() { return getToken(JSONParser.BOOLEAN, 0); }
		public TerminalNode NULL() { return getToken(JSONParser.NULL, 0); }
		public ArrayContext array() {
			return getRuleContext(ArrayContext.class,0);
		}
		public ObjectContext object() {
			return getRuleContext(ObjectContext.class,0);
		}
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).exitElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JSONVisitor ) return ((JSONVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_element);
		try {
			setState(49);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(43);
				match(STRING);
				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				match(NUMBER);
				}
				break;
			case BOOLEAN:
				enterOuterAlt(_localctx, 3);
				{
				setState(45);
				match(BOOLEAN);
				}
				break;
			case NULL:
				enterOuterAlt(_localctx, 4);
				{
				setState(46);
				match(NULL);
				}
				break;
			case T__4:
				enterOuterAlt(_localctx, 5);
				{
				setState(47);
				array();
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 6);
				{
				setState(48);
				object();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ArrayContext extends ParserRuleContext {
		public List<TerminalNode> NEWLINE() { return getTokens(JSONParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(JSONParser.NEWLINE, i);
		}
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public ArrayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).enterArray(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSONListener ) ((JSONListener)listener).exitArray(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JSONVisitor ) return ((JSONVisitor<? extends T>)visitor).visitArray(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayContext array() throws RecognitionException {
		ArrayContext _localctx = new ArrayContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_array);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(T__4);
			setState(53);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(52);
				match(NEWLINE);
				}
				break;
			}
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((_la) & ~0x3f) == 0 && ((1L << _la) & 1954L) != 0) {
				{
				setState(55);
				element();
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__1) {
					{
					{
					{
					setState(56);
					match(T__1);
					setState(58);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==NEWLINE) {
						{
						setState(57);
						match(NEWLINE);
						}
					}

					}
					setState(60);
					element();
					}
					}
					setState(65);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NEWLINE) {
				{
				setState(68);
				match(NEWLINE);
				}
			}

			setState(71);
			match(T__5);
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
		"\u0004\u0001\fJ\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001\u0010"+
		"\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001\u0015\b\u0001"+
		"\u0001\u0001\u0005\u0001\u0018\b\u0001\n\u0001\f\u0001\u001b\t\u0001\u0003"+
		"\u0001\u001d\b\u0001\u0001\u0001\u0003\u0001 \b\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0002\u0001\u0002\u0004\u0002&\b\u0002\u000b\u0002\f\u0002"+
		"\'\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0003\u00032\b\u0003\u0001\u0004\u0001\u0004"+
		"\u0003\u00046\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004"+
		";\b\u0004\u0001\u0004\u0005\u0004>\b\u0004\n\u0004\f\u0004A\t\u0004\u0003"+
		"\u0004C\b\u0004\u0001\u0004\u0003\u0004F\b\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0000\u0000\u0005\u0000\u0002\u0004\u0006\b\u0000\u0000T"+
		"\u0000\n\u0001\u0000\u0000\u0000\u0002\r\u0001\u0000\u0000\u0000\u0004"+
		"#\u0001\u0000\u0000\u0000\u00061\u0001\u0000\u0000\u0000\b3\u0001\u0000"+
		"\u0000\u0000\n\u000b\u0003\u0002\u0001\u0000\u000b\f\u0005\u0000\u0000"+
		"\u0001\f\u0001\u0001\u0000\u0000\u0000\r\u000f\u0005\u0001\u0000\u0000"+
		"\u000e\u0010\u0005\u000b\u0000\u0000\u000f\u000e\u0001\u0000\u0000\u0000"+
		"\u000f\u0010\u0001\u0000\u0000\u0000\u0010\u001c\u0001\u0000\u0000\u0000"+
		"\u0011\u0019\u0003\u0004\u0002\u0000\u0012\u0014\u0005\u0002\u0000\u0000"+
		"\u0013\u0015\u0005\u000b\u0000\u0000\u0014\u0013\u0001\u0000\u0000\u0000"+
		"\u0014\u0015\u0001\u0000\u0000\u0000\u0015\u0016\u0001\u0000\u0000\u0000"+
		"\u0016\u0018\u0003\u0004\u0002\u0000\u0017\u0012\u0001\u0000\u0000\u0000"+
		"\u0018\u001b\u0001\u0000\u0000\u0000\u0019\u0017\u0001\u0000\u0000\u0000"+
		"\u0019\u001a\u0001\u0000\u0000\u0000\u001a\u001d\u0001\u0000\u0000\u0000"+
		"\u001b\u0019\u0001\u0000\u0000\u0000\u001c\u0011\u0001\u0000\u0000\u0000"+
		"\u001c\u001d\u0001\u0000\u0000\u0000\u001d\u001f\u0001\u0000\u0000\u0000"+
		"\u001e \u0005\u000b\u0000\u0000\u001f\u001e\u0001\u0000\u0000\u0000\u001f"+
		" \u0001\u0000\u0000\u0000 !\u0001\u0000\u0000\u0000!\"\u0005\u0003\u0000"+
		"\u0000\"\u0003\u0001\u0000\u0000\u0000#%\u0005\u0007\u0000\u0000$&\u0005"+
		"\u0004\u0000\u0000%$\u0001\u0000\u0000\u0000&\'\u0001\u0000\u0000\u0000"+
		"\'%\u0001\u0000\u0000\u0000\'(\u0001\u0000\u0000\u0000()\u0001\u0000\u0000"+
		"\u0000)*\u0003\u0006\u0003\u0000*\u0005\u0001\u0000\u0000\u0000+2\u0005"+
		"\u0007\u0000\u0000,2\u0005\b\u0000\u0000-2\u0005\t\u0000\u0000.2\u0005"+
		"\n\u0000\u0000/2\u0003\b\u0004\u000002\u0003\u0002\u0001\u00001+\u0001"+
		"\u0000\u0000\u00001,\u0001\u0000\u0000\u00001-\u0001\u0000\u0000\u0000"+
		"1.\u0001\u0000\u0000\u00001/\u0001\u0000\u0000\u000010\u0001\u0000\u0000"+
		"\u00002\u0007\u0001\u0000\u0000\u000035\u0005\u0005\u0000\u000046\u0005"+
		"\u000b\u0000\u000054\u0001\u0000\u0000\u000056\u0001\u0000\u0000\u0000"+
		"6B\u0001\u0000\u0000\u00007?\u0003\u0006\u0003\u00008:\u0005\u0002\u0000"+
		"\u00009;\u0005\u000b\u0000\u0000:9\u0001\u0000\u0000\u0000:;\u0001\u0000"+
		"\u0000\u0000;<\u0001\u0000\u0000\u0000<>\u0003\u0006\u0003\u0000=8\u0001"+
		"\u0000\u0000\u0000>A\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000\u0000"+
		"?@\u0001\u0000\u0000\u0000@C\u0001\u0000\u0000\u0000A?\u0001\u0000\u0000"+
		"\u0000B7\u0001\u0000\u0000\u0000BC\u0001\u0000\u0000\u0000CE\u0001\u0000"+
		"\u0000\u0000DF\u0005\u000b\u0000\u0000ED\u0001\u0000\u0000\u0000EF\u0001"+
		"\u0000\u0000\u0000FG\u0001\u0000\u0000\u0000GH\u0005\u0006\u0000\u0000"+
		"H\t\u0001\u0000\u0000\u0000\f\u000f\u0014\u0019\u001c\u001f\'15:?BE";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}