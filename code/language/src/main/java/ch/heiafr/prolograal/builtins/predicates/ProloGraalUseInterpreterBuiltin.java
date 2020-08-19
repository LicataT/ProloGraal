package ch.heiafr.prolograal.builtins.predicates;

import ch.heiafr.prolograal.nodes.ProloGraalUseInterpreterHeadNode;
import ch.heiafr.prolograal.nodes.ProloGraalUseInterpreterHeadNodeGen;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.HashMap;

/**
 * Class representing the useinterpreter/0 predicate.
 * It permit the user to interact with the console of the standard Prolog language.
 * @author Tony Licata
 * @see ProloGraalBuiltinClause
 */
public abstract class ProloGraalUseInterpreterBuiltin extends ProloGraalBuiltinClause {

   private final ProloGraalContext context; // we keep the context for the copy method

   public ProloGraalUseInterpreterBuiltin(ProloGraalContext context) {
      super(new HashMap<>());
      // get printer from context
      this.context = context;
   }

   /**
    * Launch an interpreter
    */
   @Specialization
   public ProloGraalBoolean returnHead(ProloGraalBoolean head){
      return head;
   }

   // override the default copy so we do not lose the built-in status
   @Override
   public ProloGraalClause copy() {
      return ProloGraalUseInterpreterBuiltinNodeGen.create(context,ProloGraalUseInterpreterHeadNodeGen.create(getHead().copy(new HashMap<>()),context));
   }

   // override getHead method to handle AST node head
   @Override
   public ProloGraalTerm<?> getHead(){
      return ((ProloGraalUseInterpreterHeadNode) NodeUtil.findNodeChildren(this).get(0)).getValue();
   }

}
