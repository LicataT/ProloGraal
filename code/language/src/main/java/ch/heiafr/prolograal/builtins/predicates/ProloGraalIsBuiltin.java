package ch.heiafr.prolograal.builtins.predicates;

import ch.heiafr.prolograal.builtins.ProloGraalIsOperators;
import ch.heiafr.prolograal.nodes.*;
import ch.heiafr.prolograal.nodes.ProloGraalIsHeadNode;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.*;
import java.util.function.Function;

/**
 * Class representing the is/2(A,B) predicate.
 * Compute B if B is a mathematical expression and unify it with A if A is an unbound variable.
 * @author Licata Tony
 * @see ProloGraalBuiltinClause
 */
@NodeInfo(shortName = "ProloGraalIsBuiltinNode")
public abstract class ProloGraalIsBuiltin extends ProloGraalBuiltinClause {


   // we fill the operations map to check the operator used by the user later in the execute method
   private static final Map<ProloGraalAtom, Function<List<ProloGraalNumber>,ProloGraalNumber>> operations = ProloGraalIsOperators.getOperationsMap();

   public ProloGraalIsBuiltin(Map<ProloGraalVariable,ProloGraalVariable> variables) {
      super(variables);
   }

   /**
    * Execute the is/2 predicate, unifying the left variable with the result
    */
   @Specialization
   public ProloGraalBoolean returnHead(ProloGraalBoolean head){
      return head;
   }

   /**
    * Get is/2 operators as a Java Map< atom , function >
    * @return is/2 operators as a Map
    */
   public static Map<ProloGraalAtom, Function<List<ProloGraalNumber>, ProloGraalNumber>> getOperations() {
      return operations;
   }

   // override the default copy so we do not lose the built-in status
   @Override
   public ProloGraalClause copy() {
      ProloGraalTerm copiedHead = copyHead();
      List<ProloGraalTerm<?>> headArgs = copiedHead.asStructure().getArguments();
      return ProloGraalIsBuiltinNodeGen.create(copiedHead.getVariables(), ProloGraalIsHeadNodeGen.create(copiedHead,new ProloGraalSimpleTermNode(headArgs.get(0)),new ProloGraalSimpleTermNode(headArgs.get(1))));
   }

   // override getHead method to handle AST node head
   @Override
   public ProloGraalTerm<?> getHead(){
      return ((ProloGraalIsHeadNode)NodeUtil.findNodeChildren(this).get(0)).getValue();
   }

   /**
    * Build a standard ProloGraalTerm that would tipically be the head of the builtin, with variables instead of "real args".
    * @return a new ProloGraalTerm representing the head of the builtin
    */
   public static ProloGraalTerm createStandardHead(Map<ProloGraalVariable,ProloGraalVariable> variables) {
      ProloGraalStructure head = new ProloGraalStructure(variables);
      head.setFunctor(new ProloGraalAtom(variables, "is"));

      // we create and store the variables to access them more easily later in the execute method
      ProloGraalVariable arg = new ProloGraalVariable(variables, "_");
      head.addSubterm(arg);

      ProloGraalVariable arg2 = new ProloGraalVariable(variables, "op");
      head.addSubterm(arg2);
      return head;
   }
}
