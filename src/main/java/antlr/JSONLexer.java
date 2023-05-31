// Generated from G:/Mestrado/Programa��o Avan�ada/PA_2023_AfonsoCani�o92494/src/mvc.main\JSON.g4 by ANTLR 4.12.0

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class JSONLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, STRING=7, NUMBER=8, BOOLEAN=9, 
		NULL=10, NEWLINE=11, WHITESPACE=12;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "STRING", "NUMBER", "BOOLEAN", 
			"NULL", "NEWLINE", "WHITESPACE"
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
			"NULL", "NEWLINE", "WHITESPACE"
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


	public JSONLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "JSON.g4"; }

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
		"\u0004\u0000\f\\\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0005\u0006(\b\u0006\n\u0006\f\u0006"+
		"+\t\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0003\u00070\b\u0007\u0001"+
		"\u0007\u0004\u00073\b\u0007\u000b\u0007\f\u00074\u0001\u0007\u0003\u0007"+
		"8\b\u0007\u0001\u0007\u0004\u0007;\b\u0007\u000b\u0007\f\u0007<\u0003"+
		"\u0007?\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\bJ\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0003\nT\b\n\u0001\u000b\u0004\u000bW\b\u000b"+
		"\u000b\u000b\f\u000bX\u0001\u000b\u0001\u000b\u0001)\u0000\f\u0001\u0001"+
		"\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f"+
		"\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0001\u0000\u0004\u0002\u0000+"+
		"+--\u0001\u000009\u0002\u0000\n\n\r\r\u0002\u0000\t\t  d\u0000\u0001\u0001"+
		"\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001"+
		"\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000"+
		"\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000"+
		"\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000"+
		"\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000"+
		"\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0001\u0019\u0001\u0000\u0000"+
		"\u0000\u0003\u001b\u0001\u0000\u0000\u0000\u0005\u001d\u0001\u0000\u0000"+
		"\u0000\u0007\u001f\u0001\u0000\u0000\u0000\t!\u0001\u0000\u0000\u0000"+
		"\u000b#\u0001\u0000\u0000\u0000\r%\u0001\u0000\u0000\u0000\u000f/\u0001"+
		"\u0000\u0000\u0000\u0011I\u0001\u0000\u0000\u0000\u0013K\u0001\u0000\u0000"+
		"\u0000\u0015S\u0001\u0000\u0000\u0000\u0017V\u0001\u0000\u0000\u0000\u0019"+
		"\u001a\u0005{\u0000\u0000\u001a\u0002\u0001\u0000\u0000\u0000\u001b\u001c"+
		"\u0005,\u0000\u0000\u001c\u0004\u0001\u0000\u0000\u0000\u001d\u001e\u0005"+
		"}\u0000\u0000\u001e\u0006\u0001\u0000\u0000\u0000\u001f \u0005:\u0000"+
		"\u0000 \b\u0001\u0000\u0000\u0000!\"\u0005[\u0000\u0000\"\n\u0001\u0000"+
		"\u0000\u0000#$\u0005]\u0000\u0000$\f\u0001\u0000\u0000\u0000%)\u0005\""+
		"\u0000\u0000&(\t\u0000\u0000\u0000\'&\u0001\u0000\u0000\u0000(+\u0001"+
		"\u0000\u0000\u0000)*\u0001\u0000\u0000\u0000)\'\u0001\u0000\u0000\u0000"+
		"*,\u0001\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000,-\u0005\"\u0000\u0000"+
		"-\u000e\u0001\u0000\u0000\u0000.0\u0007\u0000\u0000\u0000/.\u0001\u0000"+
		"\u0000\u0000/0\u0001\u0000\u0000\u000002\u0001\u0000\u0000\u000013\u0007"+
		"\u0001\u0000\u000021\u0001\u0000\u0000\u000034\u0001\u0000\u0000\u0000"+
		"42\u0001\u0000\u0000\u000045\u0001\u0000\u0000\u00005>\u0001\u0000\u0000"+
		"\u000068\u0005.\u0000\u000076\u0001\u0000\u0000\u000078\u0001\u0000\u0000"+
		"\u00008:\u0001\u0000\u0000\u00009;\u0007\u0001\u0000\u0000:9\u0001\u0000"+
		"\u0000\u0000;<\u0001\u0000\u0000\u0000<:\u0001\u0000\u0000\u0000<=\u0001"+
		"\u0000\u0000\u0000=?\u0001\u0000\u0000\u0000>7\u0001\u0000\u0000\u0000"+
		">?\u0001\u0000\u0000\u0000?\u0010\u0001\u0000\u0000\u0000@A\u0005t\u0000"+
		"\u0000AB\u0005r\u0000\u0000BC\u0005u\u0000\u0000CJ\u0005e\u0000\u0000"+
		"DE\u0005f\u0000\u0000EF\u0005a\u0000\u0000FG\u0005l\u0000\u0000GH\u0005"+
		"s\u0000\u0000HJ\u0005e\u0000\u0000I@\u0001\u0000\u0000\u0000ID\u0001\u0000"+
		"\u0000\u0000J\u0012\u0001\u0000\u0000\u0000KL\u0005n\u0000\u0000LM\u0005"+
		"u\u0000\u0000MN\u0005l\u0000\u0000NO\u0005l\u0000\u0000O\u0014\u0001\u0000"+
		"\u0000\u0000PT\u0007\u0002\u0000\u0000QR\u0005\r\u0000\u0000RT\u0005\n"+
		"\u0000\u0000SP\u0001\u0000\u0000\u0000SQ\u0001\u0000\u0000\u0000T\u0016"+
		"\u0001\u0000\u0000\u0000UW\u0007\u0003\u0000\u0000VU\u0001\u0000\u0000"+
		"\u0000WX\u0001\u0000\u0000\u0000XV\u0001\u0000\u0000\u0000XY\u0001\u0000"+
		"\u0000\u0000YZ\u0001\u0000\u0000\u0000Z[\u0006\u000b\u0000\u0000[\u0018"+
		"\u0001\u0000\u0000\u0000\n\u0000)/47<>ISX\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}