package ch.heiafr.prolograal.builtins.predicates;

import ch.heiafr.prolograal.nodes.ProloGraalNoTraceHeadNode;
import ch.heiafr.prolograal.nodes.ProloGraalNoTraceHeadNodeGen;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Class representing the trace/0 predicate.
 * It reproduces the notrace/0 predicate behaviour from the standard Prolog language.
 * @author Tony Licata
 * @see ProloGraalBuiltinClause
 */
public abstract class ProloGraalNoTraceBuiltin extends ProloGraalBuiltinClause {

   private final ProloGraalContext context; // we keep the context to use it later
   public static final String TRACE_OFF_TEXT = "The debugger is switched off\n";

   public ProloGraalNoTraceBuiltin(ProloGraalContext context) {
      super(new HashMap<>());
      // get printer from context
      this.context = context;

      // create the head of this clause
      // since we do not need unification, an atom is enough
      ProloGraalAtom head = new ProloGraalAtom(getVariables(),"notrace");

      setHead(head);
   }

   /**
    * Execute the notrace, even if the trace is already OFF (like usual Prolog interpreter).
    */
   @Specialization
   public ProloGraalBoolean returnHead(ProloGraalBoolean head){
      return head;
   }

   // override the default copy so we do not lose the built-in status
   @Override
   public ProloGraalClause copy() {
      return ProloGraalNoTraceBuiltinNodeGen.create(context, ProloGraalNoTraceHeadNodeGen.create(getHead().copy(new HashMap<>()),context));
   }

   // override getHead method to handle AST node head
   @Override
   public ProloGraalTerm<?> getHead(){
      return ((ProloGraalNoTraceHeadNode) NodeUtil.findNodeChildren(this).get(0)).getValue();
   }

   /**
    * Build a standard ProloGraalTerm that would tipically be the head of the builtin, with variables instead of "real args".
    * @return a new ProloGraalTerm representing the head of the builtin
    */
   public static ProloGraalTerm createStandardHead(Map<ProloGraalVariable,ProloGraalVariable> variables) {
      ProloGraalStructure traceStructure = new ProloGraalStructure(variables);
      ProloGraalAtom head = new ProloGraalAtom(variables,"notrace");
      traceStructure.setFunctor(head);
      return traceStructure;
   }

}
