package ch.heiafr.prolograal.builtins.predicates;

import ch.heiafr.prolograal.nodes.ProloGraalTraceHeadNode;
import ch.heiafr.prolograal.nodes.ProloGraalTraceHeadNodeGen;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing the trace/0 predicate.
 * It reproduces the trace/0 predicate behaviour from the standard Prolog language.
 * @author Tony Licata
 * @see ProloGraalBuiltinClause
 */
public abstract class ProloGraalTraceBuiltin extends ProloGraalBuiltinClause {

   private final ProloGraalContext context; // we keep the context to use it later
   public static final String TRACE_ON_TEXT = "The debugger will first creep -- showing everything (trace)";

   public ProloGraalTraceBuiltin(ProloGraalContext context) {
      super(new HashMap<>());
      this.context = context;
   }


   /**
    * Execute the trace, even if the trace is already ON (like usual Prolog behaviour).
    */
   @Specialization
   public ProloGraalBoolean returnHead(ProloGraalBoolean head){
      return head;
   }

   // override the default copy so we do not lose the built-in status
   @Override
   public ProloGraalClause copy() {
      return ProloGraalTraceBuiltinNodeGen.create(context,  ProloGraalTraceHeadNodeGen.create(getHead().copy(new HashMap<>()),context));
   }

   // override getHead method to handle AST node head
   @Override
   public ProloGraalTerm<?> getHead(){
      return ((ProloGraalTraceHeadNode) NodeUtil.findNodeChildren(this).get(0)).getValue();
   }

   /**
    * Build a standard ProloGraalTerm that would tipically be the head of the builtin, with variables instead of "real args".
    * @return a new ProloGraalTerm representing the head of the builtin
    */
   public static ProloGraalTerm createStandardHead(Map<ProloGraalVariable,ProloGraalVariable> variables) {
      ProloGraalStructure traceStructure = new ProloGraalStructure(variables);
      ProloGraalAtom head = new ProloGraalAtom(variables,"trace");
      traceStructure.setFunctor(head);
      return traceStructure;
   }

}
