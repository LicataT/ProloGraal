package ch.heiafr.prolograal.builtins.predicates;

import ch.heiafr.prolograal.nodes.*;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing the consultstring/1 predicate.
 * It permit the user to load Prolog rules into the runtime.
 * @author Tony Licata
 * @see ProloGraalBuiltinClause
 */
public abstract class ProloGraalConsultStringBuiltin extends ProloGraalBuiltinClause {

   private final ProloGraalContext context; // we keep the context for the copy method

   public ProloGraalConsultStringBuiltin(ProloGraalContext context) {
      super(new HashMap<>());
      // get printer from context
      this.context = context;
   }

   /**
    * Consult the clauses string present in arg variable.
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
      return ProloGraalConsultStringBuiltinNodeGen.create(context, ProloGraalConsultStringHeadNodeGen.create(getHead().copy(new HashMap<>()), context, new ProloGraalSimpleTermNode(headArgs.get(0))));
   }

   // override getHead method to handle AST node head
   @Override
   public ProloGraalTerm<?> getHead(){
      return ((ProloGraalConsultStringHeadNode) NodeUtil.findNodeChildren(this).get(0)).getValue();
   }

   /**
    * Build a standard ProloGraalTerm that would tipically be the head of the builtin, with variables instead of "real args".
    * @return a new ProloGraalTerm representing the head of the builtin
    */
   public static ProloGraalTerm createStandardHead(Map<ProloGraalVariable,ProloGraalVariable> variables) {
      ProloGraalStructure head = new ProloGraalStructure(variables);
      ProloGraalAtom consultStringAtom = new ProloGraalAtom(variables,"consultstring");
      head.setFunctor(consultStringAtom);
      ProloGraalVariable arg = new ProloGraalVariable(variables, "A");
      head.addSubterm(arg);
      return head;
   }

}
