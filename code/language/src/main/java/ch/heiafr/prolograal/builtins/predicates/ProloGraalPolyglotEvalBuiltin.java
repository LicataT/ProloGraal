package ch.heiafr.prolograal.builtins.predicates;

import ch.heiafr.prolograal.nodes.*;
import ch.heiafr.prolograal.nodes.ProloGraalPolyglotEvalHeadNode;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.List;
import java.util.Map;

/**
 * Class representing the polyglot_bind/3(term,languageID,code) predicate.
 * Evaluate the foreign language code and try to unify the given term with the result.
 * @author Licata Tony
 * @see ProloGraalBuiltinClause
 */
@NodeInfo(shortName = "ProloGraalPolyglotBindBuiltin")
public abstract class ProloGraalPolyglotEvalBuiltin extends ProloGraalBuiltinClause {

   public ProloGraalPolyglotEvalBuiltin(Map<ProloGraalVariable,ProloGraalVariable> variables) {
      super(variables);
   }

   /**
    * Execute the polyglot_eval/3 builtin, evaluating foreign code (3rd arg) from foreign language (2nd arg),
    * and bound the result to a prolog var (1st arg)
    */
   @Specialization
   public ProloGraalBoolean returnHead(ProloGraalBoolean head){
      return head;
   }

   // override the default copy so we do not lose the built-in status
   @Override
   public ProloGraalClause copy() {
      ProloGraalTerm copiedHead = copyHead();
      List<ProloGraalTerm<?>> headArgs = copiedHead.asStructure().getArguments();
      return ProloGraalPolyglotEvalBuiltinNodeGen.create(copiedHead.getVariables(), ProloGraalPolyglotEvalHeadNodeGen.create(copiedHead,new ProloGraalSimpleTermNode(headArgs.get(0)),new ProloGraalSimpleTermNode(headArgs.get(1)),new ProloGraalSimpleTermNode(headArgs.get(2))));
   }

   // override getHead method to handle AST node head
   @Override
   public ProloGraalTerm<?> getHead(){
      return ((ProloGraalPolyglotEvalHeadNode)NodeUtil.findNodeChildren(this).get(0)).getValue();
   }
}
