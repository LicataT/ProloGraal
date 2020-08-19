package ch.heiafr.prolograal.builtins.predicates;

import ch.heiafr.prolograal.nodes.*;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing the write/1(X) predicate.
 * Writes the string representation of X.
 * @author Martin Spoto
 * @see ProloGraalBuiltinClause
 */
public abstract class ProloGraalWriteBuiltin extends ProloGraalBuiltinClause {

   private final ProloGraalContext context;

   public ProloGraalWriteBuiltin(ProloGraalContext context) {
      super(new HashMap<>());
      this.context = context;
   }

   /**
    * Execute the write, printing the string representation of whatever the variable is currently bound to.
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
      return ProloGraalWriteBuiltinNodeGen.create(context, ProloGraalWriteHeadNodeGen.create(getHead().copy(new HashMap<>()), context, new ProloGraalSimpleTermNode(headArgs.get(0))));
   }

   // override getHead method to handle AST node head
   @Override
   public ProloGraalTerm<?> getHead(){
      return ((ProloGraalWriteHeadNode) NodeUtil.findNodeChildren(this).get(0)).getValue();
   }

   /**
    * Build a standard ProloGraalTerm that would tipically be the head of the builtin, with variables instead of "real args".
    * @return a new ProloGraalTerm representing the head of the builtin
    */
   public static ProloGraalTerm createStandardHead(Map<ProloGraalVariable,ProloGraalVariable> variables) {
      // create the head of this clause
      // since we do not need custom unification, a simple structure is enough
      ProloGraalStructure head = new ProloGraalStructure(variables);
      head.setFunctor(new ProloGraalAtom(variables, "write"));
      // we create and store the variable to access it more easily later in the execute method
      ProloGraalVariable arg = new ProloGraalVariable(variables, "_");
      head.addSubterm(arg);
      return head;
   }

}
