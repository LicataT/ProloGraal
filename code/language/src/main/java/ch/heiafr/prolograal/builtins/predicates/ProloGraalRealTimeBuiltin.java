package ch.heiafr.prolograal.builtins.predicates;

import ch.heiafr.prolograal.nodes.*;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Class representing the real_time/1(X) built-in predicate.
 * Give the current real time spent since this class was built.
 * Will return a success if X unifies with the real_time value.
 * @author Tony Licata
 * @see ProloGraalBuiltinClause
 */
public abstract class ProloGraalRealTimeBuiltin extends ProloGraalBuiltinClause {

   public static final long startTime = System.currentTimeMillis();

   public ProloGraalRealTimeBuiltin() {
      super(new HashMap<>());
   }

   @Specialization
   public ProloGraalBoolean returnHead(ProloGraalBoolean head){
      return head;
   }

   // override the default copy so we do not lose the built-in status
   @Override
   public ProloGraalClause copy() {
      ProloGraalTerm copiedHead = copyHead();
      List<ProloGraalTerm<?>> headArgs = copiedHead.asStructure().getArguments();
      return ProloGraalRealTimeBuiltinNodeGen.create(ProloGraalRealTimeHeadNodeGen.create(getHead().copy(new HashMap<>()),new ProloGraalSimpleTermNode(headArgs.get(0))));
   }

   // override getHead method to handle AST node head
   @Override
   public ProloGraalTerm<?> getHead(){
      return ((ProloGraalRealTimeHeadNode) NodeUtil.findNodeChildren(this).get(0)).getValue();
   }
}
