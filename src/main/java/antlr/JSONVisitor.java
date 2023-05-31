// Generated from G:/Mestrado/Programa��o Avan�ada/PA_2023_AfonsoCani�o92494/src/mvc.main\JSON.g4 by ANTLR 4.12.0
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
	 * Visit a parse tree produced by {@link JSONParser#document}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDocument(JSONParser.DocumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link JSONParser#composite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComposite(JSONParser.CompositeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JSONParser#property}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProperty(JSONParser.PropertyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literal}
	 * labeled alternative in {@link JSONParser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(JSONParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code array}
	 * labeled alternative in {@link JSONParser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray(JSONParser.ArrayContext ctx);
	/**
	 * Visit a parse tree produced by the {@code object}
	 * labeled alternative in {@link JSONParser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObject(JSONParser.ObjectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code string}
	 * labeled alternative in {@link JSONParser#primitive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString(JSONParser.StringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code number}
	 * labeled alternative in {@link JSONParser#primitive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(JSONParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolean}
	 * labeled alternative in {@link JSONParser#primitive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolean(JSONParser.BooleanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code null}
	 * labeled alternative in {@link JSONParser#primitive}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNull(JSONParser.NullContext ctx);
	/**
	 * Visit a parse tree produced by {@link JSONParser#collection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollection(JSONParser.CollectionContext ctx);
}