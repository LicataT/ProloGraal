package ch.heiafr.prolograal.builtins.predicates;

import ch.heiafr.prolograal.nodes.*;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing the var/1(X) built-in predicate.
 * Is unifiable only if X is an unbound variable.
 * @author Martin Spoto
 * @see ProloGraalBuiltinClause
 */
public abstract class ProloGraalVarBuiltin extends ProloGraalBuiltinClause {

   /**
    * Class representing the head of this predicate.
    * Necessary to override the unification behaviour.
    */
   private static class VarPredicateHead extends ProloGraalStructure {

      public VarPredicateHead(Map<ProloGraalVariable, ProloGraalVariable> variables) {
         super(variables);
         // add the correct functor for this predicate, and an anonymous variable since we do not need it
         setFunctor(new ProloGraalAtom(variables, "var"));
         addSubterm(new ProloGraalVariable(variables, "_"));
      }

      /**
       * Overrides the default structure unification behaviour.
       * Checks that the param has the same functor and exactly one argument, then checks that the argument
       * resolves to a variable
       * @return true if other has the same functor and exactly one argument, and that argument is an unbound variable
       */
      @Override
      public boolean unify(ProloGraalTerm<?> other) {
         if (other.isStructure()) {
            ProloGraalStructure struct = other.asStructure();
            if (struct.getFunctor().equals(getFunctor()) && struct.getArity() == 1) {
               return struct.getArguments().get(0).getRootValue() instanceof ProloGraalVariable;
            }
         }
         return false;
      }

      // override the default copy so we do not lose the custom unification behaviour
      @Override
      public ProloGraalStructure copy(Map<ProloGraalVariable, ProloGraalVariable> variables) {
         return new VarPredicateHead(variables);
      }
   }

   public ProloGraalVarBuiltin() {
      super(new HashMap<>());
   }

   /**
    * Execute var(X) predicate, giving a success if X is an unbound variable
    */
   @Specialization
   public ProloGraalBoolean returnHead(ProloGraalBoolean head){
      return head;
   }

   // override the default copy so we do not lose the custom head
   @Override
   public ProloGraalClause copy() {
      ProloGraalTerm copiedHead = copyHead();
      List<ProloGraalTerm<?>> headArgs = copiedHead.asStructure().getArguments();
      return ProloGraalVarBuiltinNodeGen.create(ProloGraalVarHeadNodeGen.create(getHead().copy(new HashMap<>()), new ProloGraalSimpleTermNode(headArgs.get(0))));
   }

   // override getHead method to handle AST node head
   @Override
   public ProloGraalTerm<?> getHead(){
      return ((ProloGraalVarHeadNode) NodeUtil.findNodeChildren(this).get(0)).getValue();
   }

   /**
    * Build a standard ProloGraalTerm that would tipically be the head of the builtin, with variables instead of "real args".
    * @return a new ProloGraalTerm representing the head of the builtin
    */
   public static ProloGraalTerm createStandardHead(Map<ProloGraalVariable,ProloGraalVariable> variables) {
      return new VarPredicateHead(variables);
   }
}
