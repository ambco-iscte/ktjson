package antlr;// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JSONParser}.
 */
public interface JSONListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JSONParser#json}.
	 * @param ctx the parse tree
	 */
	void enterJson(JSONParser.JsonContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONParser#json}.
	 * @param ctx the parse tree
	 */
	void exitJson(JSONParser.JsonContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(JSONParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(JSONParser.ObjectContext ctx);
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
	 * Enter a parse tree produced by {@link JSONParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(JSONParser.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(JSONParser.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSONParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(JSONParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSONParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(JSONParser.ArrayContext ctx);
}