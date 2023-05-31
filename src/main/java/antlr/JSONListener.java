// Generated from G:/Mestrado/Programa��o Avan�ada/PA_2023_AfonsoCani�o92494/src/mvc.main\JSON.g4 by ANTLR 4.12.0
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JSONParser}.
 */
public interface JSONListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JSONParser#document}.
	 * @param ctx the parse tree
	 */
	void enterDocument(JSONParser.DocumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONParser#document}.
	 * @param ctx the parse tree
	 */
	void exitDocument(JSONParser.DocumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONParser#composite}.
	 * @param ctx the parse tree
	 */
	void enterComposite(JSONParser.CompositeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONParser#composite}.
	 * @param ctx the parse tree
	 */
	void exitComposite(JSONParser.CompositeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONParser#property}.
	 * @param ctx the parse tree
	 */
	void enterProperty(JSONParser.PropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONParser#property}.
	 * @param ctx the parse tree
	 */
	void exitProperty(JSONParser.PropertyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literal}
	 * labeled alternative in {@link JSONParser#element}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(JSONParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literal}
	 * labeled alternative in {@link JSONParser#element}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(JSONParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code array}
	 * labeled alternative in {@link JSONParser#element}.
	 * @param ctx the parse tree
	 */
	void enterArray(JSONParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code array}
	 * labeled alternative in {@link JSONParser#element}.
	 * @param ctx the parse tree
	 */
	void exitArray(JSONParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code object}
	 * labeled alternative in {@link JSONParser#element}.
	 * @param ctx the parse tree
	 */
	void enterObject(JSONParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code object}
	 * labeled alternative in {@link JSONParser#element}.
	 * @param ctx the parse tree
	 */
	void exitObject(JSONParser.ObjectContext ctx);
	/**
	 * Enter a parse tree produced by the {@code string}
	 * labeled alternative in {@link JSONParser#primitive}.
	 * @param ctx the parse tree
	 */
	void enterString(JSONParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code string}
	 * labeled alternative in {@link JSONParser#primitive}.
	 * @param ctx the parse tree
	 */
	void exitString(JSONParser.StringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code number}
	 * labeled alternative in {@link JSONParser#primitive}.
	 * @param ctx the parse tree
	 */
	void enterNumber(JSONParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code number}
	 * labeled alternative in {@link JSONParser#primitive}.
	 * @param ctx the parse tree
	 */
	void exitNumber(JSONParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolean}
	 * labeled alternative in {@link JSONParser#primitive}.
	 * @param ctx the parse tree
	 */
	void enterBoolean(JSONParser.BooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolean}
	 * labeled alternative in {@link JSONParser#primitive}.
	 * @param ctx the parse tree
	 */
	void exitBoolean(JSONParser.BooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code null}
	 * labeled alternative in {@link JSONParser#primitive}.
	 * @param ctx the parse tree
	 */
	void enterNull(JSONParser.NullContext ctx);
	/**
	 * Exit a parse tree produced by the {@code null}
	 * labeled alternative in {@link JSONParser#primitive}.
	 * @param ctx the parse tree
	 */
	void exitNull(JSONParser.NullContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONParser#collection}.
	 * @param ctx the parse tree
	 */
	void enterCollection(JSONParser.CollectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONParser#collection}.
	 * @param ctx the parse tree
	 */
	void exitCollection(JSONParser.CollectionContext ctx);
}