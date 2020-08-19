package ch.heiafr.prolograal.builtins.predicates;

import ch.heiafr.prolograal.nodes.*;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing the consult/1 predicate.
 * It permit the user to load Prolog rules into the runtime from a file.
 * @author Tony Licata
 * @see ProloGraalBuiltinClause
 */
public abstract class ProloGraalConsultBuiltin extends ProloGraalBuiltinClause {

   private final ProloGraalContext context; // we keep the context for the copy method

   public ProloGraalConsultBuiltin(ProloGraalContext context) {
      super(new HashMap<>());
      // get printer from context
      this.context = context;
   }

   /**
    * Consult the filename present in arg variable.
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
      return ProloGraalConsultBuiltinNodeGen.create(context, ProloGraalConsultHeadNodeGen.create(getHead().copy(new HashMap<>()), context, new ProloGraalSimpleTermNode(headArgs.get(0))));
   }

   // override getHead method to handle AST node head
   @Override
   public ProloGraalTerm<?> getHead(){
      return ((ProloGraalConsultHeadNode) NodeUtil.findNodeChildren(this).get(0)).getValue();
   }

   /**
    * Build a standard ProloGraalTerm that would tipically be the head of the builtin, with variables instead of "real args".
    * @return a new ProloGraalTerm representing the head of the builtin
    */
   public static ProloGraalTerm createStandardHead(Map<ProloGraalVariable,ProloGraalVariable> variables) {
      ProloGraalStructure head = new ProloGraalStructure(variables);
      ProloGraalAtom consultAtom = new ProloGraalAtom(variables,"consult");
      head.setFunctor(consultAtom);
      ProloGraalVariable arg = new ProloGraalVariable(variables, "A");
      head.addSubterm(arg);
      return head;
   }

}
