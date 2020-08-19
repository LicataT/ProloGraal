// Generated from ProloGraal.g4 by ANTLR 4.7.1
package ch.heiafr.prolograal.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ProloGraalParser}.
 */
public interface ProloGraalListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#prolograal}.
	 * @param ctx the parse tree
	 */
	void enterProlograal(ProloGraalParser.ProlograalContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#prolograal}.
	 * @param ctx the parse tree
	 */
	void exitProlograal(ProloGraalParser.ProlograalContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(ProloGraalParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(ProloGraalParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(ProloGraalParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(ProloGraalParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(ProloGraalParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(ProloGraalParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#functor}.
	 * @param ctx the parse tree
	 */
	void enterFunctor(ProloGraalParser.FunctorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#functor}.
	 * @param ctx the parse tree
	 */
	void exitFunctor(ProloGraalParser.FunctorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#composedTerm}.
	 * @param ctx the parse tree
	 */
	void enterComposedTerm(ProloGraalParser.ComposedTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#composedTerm}.
	 * @param ctx the parse tree
	 */
	void exitComposedTerm(ProloGraalParser.ComposedTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(ProloGraalParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(ProloGraalParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#head}.
	 * @param ctx the parse tree
	 */
	void enterHead(ProloGraalParser.HeadContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#head}.
	 * @param ctx the parse tree
	 */
	void exitHead(ProloGraalParser.HeadContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#fact}.
	 * @param ctx the parse tree
	 */
	void enterFact(ProloGraalParser.FactContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#fact}.
	 * @param ctx the parse tree
	 */
	void exitFact(ProloGraalParser.FactContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(ProloGraalParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(ProloGraalParser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#goalList}.
	 * @param ctx the parse tree
	 */
	void enterGoalList(ProloGraalParser.GoalListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#goalList}.
	 * @param ctx the parse tree
	 */
	void exitGoalList(ProloGraalParser.GoalListContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#composedClause}.
	 * @param ctx the parse tree
	 */
	void enterComposedClause(ProloGraalParser.ComposedClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#composedClause}.
	 * @param ctx the parse tree
	 */
	void exitComposedClause(ProloGraalParser.ComposedClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(ProloGraalParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(ProloGraalParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#clause}.
	 * @param ctx the parse tree
	 */
	void enterClause(ProloGraalParser.ClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#clause}.
	 * @param ctx the parse tree
	 */
	void exitClause(ProloGraalParser.ClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#tail}.
	 * @param ctx the parse tree
	 */
	void enterTail(ProloGraalParser.TailContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#tail}.
	 * @param ctx the parse tree
	 */
	void exitTail(ProloGraalParser.TailContext ctx);
	/**
	 * Enter a parse tree produced by {@link ProloGraalParser#list}.
	 * @param ctx the parse tree
	 */
	void enterList(ProloGraalParser.ListContext ctx);
	/**
	 * Exit a parse tree produced by {@link ProloGraalParser#list}.
	 * @param ctx the parse tree
	 */
	void exitList(ProloGraalParser.ListContext ctx);
}