package ch.heiafr.prolograal.builtins.predicates;

import ch.heiafr.prolograal.nodes.*;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing the '='(A,B) predicate.
 * return success if A can unify to B or B to A.
 * @author Licata Tony
 * @see ProloGraalBuiltinClause
 */
public abstract class ProloGraalEqualBuiltin extends ProloGraalBuiltinClause {

   public ProloGraalEqualBuiltin() {
      super(new HashMap<>());
   }

   /**
    * Execute the '='/2 predicate, returning success if a part can be unified with the other one
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
      return ProloGraalEqualBuiltinNodeGen.create(ProloGraalEqualHeadNodeGen.create(copiedHead,new ProloGraalSimpleTermNode(headArgs.get(0)),new ProloGraalSimpleTermNode(headArgs.get(1))));
   }

   // override getHead method to handle AST node head
   @Override
   public ProloGraalTerm<?> getHead(){
      return ((ProloGraalEqualHeadNode)NodeUtil.findNodeChildren(this).get(0)).getValue();
   }

   /**
    * Build a standard ProloGraalTerm that would tipically be the head of the builtin, with variables instead of "real args".
    * @return a new ProloGraalTerm representing the head of the builtin
    */
   public static ProloGraalTerm createStandardHead(Map<ProloGraalVariable,ProloGraalVariable> variables) {
      ProloGraalStructure head = new ProloGraalStructure(variables);
      head.setFunctor(new ProloGraalAtom(variables, "="));
      ProloGraalVariable arg = new ProloGraalVariable(variables, "A");
      head.addSubterm(arg);
      ProloGraalVariable arg2 = new ProloGraalVariable(variables, "B");
      head.addSubterm(arg2);
      return head;
   }
}
