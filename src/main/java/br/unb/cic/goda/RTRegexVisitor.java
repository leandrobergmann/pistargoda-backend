// Generated from br/unb/cic/RTRegex.g4 by ANTLR 4.3
package br.unb.cic.goda;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link RTRegexParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface RTRegexVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code parens}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParens(@NotNull RTRegexParser.ParensContext ctx);

	/**
	 * Visit a parse tree produced by the {@code blank}
	 * labeled alternative in {@link RTRegexParser#rt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlank(@NotNull RTRegexParser.BlankContext ctx);

	/**
	 * Visit a parse tree produced by the {@code gId}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGId(@NotNull RTRegexParser.GIdContext ctx);

	/**
	 * Visit a parse tree produced by the {@code gTry}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGTry(@NotNull RTRegexParser.GTryContext ctx);

	/**
	 * Visit a parse tree produced by the {@code gSkip}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGSkip(@NotNull RTRegexParser.GSkipContext ctx);

	/**
	 * Visit a parse tree produced by the {@code gTime}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGTime(@NotNull RTRegexParser.GTimeContext ctx);

	/**
	 * Visit a parse tree produced by the {@code gOpt}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGOpt(@NotNull RTRegexParser.GOptContext ctx);

	/**
	 * Visit a parse tree produced by the {@code gCard}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGCard(@NotNull RTRegexParser.GCardContext ctx);

	/**
	 * Visit a parse tree produced by the {@code gAlt}
	 * labeled alternative in {@link RTRegexParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGAlt(@NotNull RTRegexParser.GAltContext ctx);

	/**
	 * Visit a parse tree produced by the {@code printExpr}
	 * labeled alternative in {@link RTRegexParser#rt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrintExpr(@NotNull RTRegexParser.PrintExprContext ctx);
}