package antlr;// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JSONParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JSONVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JSONParser#json}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJson(JSONParser.JsonContext ctx);
	/**
	 * Visit a parse tree produced by {@link JSONParser#object}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject(JSONParser.ObjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link JSONParser#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(JSONParser.PropertyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JSONParser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement(JSONParser.ElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JSONParser#array}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(JSONParser.ArrayContext ctx);
}