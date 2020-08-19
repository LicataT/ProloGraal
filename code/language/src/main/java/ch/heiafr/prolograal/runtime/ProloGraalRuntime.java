package ch.heiafr.prolograal.runtime;

import ch.heiafr.prolograal.builtins.predicates.*;
import ch.heiafr.prolograal.nodes.*;
import ch.heiafr.prolograal.nodes.ProloGraalIsHeadNode;
import ch.heiafr.prolograal.nodes.ProloGraalPolyglotEvalHeadNode;

import java.util.*;

/**
 * Class holding a runtime. A runtime is composed of a number of clauses, and of the context. <br>
 * Built-in predicates are also added by this class using the {@link #installBuiltins()} method.
 * @see ProloGraalBuiltinClause
 * @author Martin Spoto
 */
public final class ProloGraalRuntime {
   // the clauses. A map is used to allow fast filtering using the head of a clause.
   // each map entry contains the list of clauses sharing the same head.
   private final Map<ProloGraalTerm<?>, List<ProloGraalClause>> clauses;
   // context reference, in case we need to pass it to builtins.
   private final ProloGraalContext context;

   // reference to the first clause for fast retrieval
   private ProloGraalClause firstClause;

   /**
    * Creates a new runtime containing the clauses from the given list.
    * @param context The context of this runtime
    */
   public ProloGraalRuntime(ProloGraalContext context) {
      this.context = context;
      clauses = new HashMap<>();

      installBuiltins();
   }

   /**
    * Install built-ins predicate. Every new built-in should be added here.
    */
   private void installBuiltins() {
      HashMap<ProloGraalVariable,ProloGraalVariable> varVars = new HashMap<>();
      ProloGraalStructure varHead = ProloGraalVarBuiltin.createStandardHead(varVars).asStructure();
      ProloGraalVarHeadNode varNode = ProloGraalVarHeadNodeGen.create(varHead,
            new ProloGraalSimpleTermNode(varHead.getArguments().get(0)));
      ProloGraalClause varBuiltin = ProloGraalVarBuiltinNodeGen.create(varNode);
      clauses.put(varBuiltin.getHead(), Collections.singletonList(varBuiltin));

      HashMap<ProloGraalVariable,ProloGraalVariable> writeVars = new HashMap<>();
      ProloGraalStructure writeHead = ProloGraalWriteBuiltin.createStandardHead(writeVars).asStructure();
      ProloGraalWriteHeadNode writeNode = ProloGraalWriteHeadNodeGen.create(writeHead, context,
            new ProloGraalSimpleTermNode(writeHead.getArguments().get(0)));
      ProloGraalClause writeBuiltin = ProloGraalWriteBuiltinNodeGen.create(context, writeNode);
      clauses.put(writeBuiltin.getHead(), Collections.singletonList(writeBuiltin));

      ProloGraalStructure traceHead = ProloGraalTraceBuiltin.createStandardHead(new HashMap<>()).asStructure();
      ProloGraalTraceHeadNode traceNode = ProloGraalTraceHeadNodeGen.create(traceHead, context);
      ProloGraalClause traceBuiltin = ProloGraalTraceBuiltinNodeGen.create(context, traceNode);
      clauses.put(traceBuiltin.getHead(), Collections.singletonList(traceBuiltin));

      ProloGraalStructure noTraceHead = ProloGraalNoTraceBuiltin.createStandardHead(new HashMap<>()).asStructure();
      ProloGraalNoTraceHeadNode noTraceNode = ProloGraalNoTraceHeadNodeGen.create(noTraceHead, context);
      ProloGraalClause notraceBuiltin = ProloGraalNoTraceBuiltinNodeGen.create(context, noTraceNode);
      clauses.put(notraceBuiltin.getHead(), Collections.singletonList(notraceBuiltin));

      HashMap<ProloGraalVariable,ProloGraalVariable> isVars = new HashMap<>();
      ProloGraalStructure isHead = ProloGraalIsBuiltin.createStandardHead(isVars).asStructure();
      ProloGraalIsHeadNode isHeadNode = ProloGraalIsHeadNodeGen.create(isHead,
            new ProloGraalSimpleTermNode(isHead.getArguments().get(0)), new ProloGraalSimpleTermNode(isHead.getArguments().get(1)));
      ProloGraalClause isBuiltin = ProloGraalIsBuiltinNodeGen.create(isVars, isHeadNode);
      clauses.put(isBuiltin.getHead(), Collections.singletonList(isBuiltin));

      HashMap<ProloGraalVariable,ProloGraalVariable> polyglotEvalVars = new HashMap<>();
      ProloGraalStructure polyglotEvalHead = new ProloGraalStructure(polyglotEvalVars);
      polyglotEvalHead.setFunctor(new ProloGraalAtom(polyglotEvalVars, "polyglot_eval"));
      ProloGraalVariable pEarg1 = new ProloGraalVariable(polyglotEvalVars, "Left");
      polyglotEvalHead.addSubterm(pEarg1);
      ProloGraalVariable pEarg2 = new ProloGraalVariable(polyglotEvalVars, "Center");
      polyglotEvalHead.addSubterm(pEarg2);
      ProloGraalVariable pEarg3 = new ProloGraalVariable(polyglotEvalVars, "Right");
      polyglotEvalHead.addSubterm(pEarg3);

      ProloGraalPolyglotEvalHeadNode polyglotEvalHeadNode = ProloGraalPolyglotEvalHeadNodeGen.create(polyglotEvalHead, new ProloGraalSimpleTermNode(pEarg1), new ProloGraalSimpleTermNode(pEarg2), new ProloGraalSimpleTermNode(pEarg3));

      ProloGraalClause polyglotEvalBuiltin = ProloGraalPolyglotEvalBuiltinNodeGen.create(polyglotEvalVars, polyglotEvalHeadNode);
      clauses.put(polyglotEvalBuiltin.getHead(), Collections.singletonList(polyglotEvalBuiltin));

      HashMap<ProloGraalVariable,ProloGraalVariable> useInterpreterVars = new HashMap<>();
      ProloGraalStructure useInterpreterStructure = new ProloGraalStructure(useInterpreterVars);
      useInterpreterStructure.setFunctor(new ProloGraalAtom(useInterpreterVars,"useinterpreter"));

      ProloGraalUseInterpreterHeadNode useInterpreterHeadNode = ProloGraalUseInterpreterHeadNodeGen.create(useInterpreterStructure,context);

      ProloGraalClause useinterpreterBuiltin = ProloGraalUseInterpreterBuiltinNodeGen.create(context,useInterpreterHeadNode);
      clauses.put(useinterpreterBuiltin.getHead(), Collections.singletonList(useinterpreterBuiltin));


      HashMap<ProloGraalVariable,ProloGraalVariable> consultStringVars = new HashMap<>();
      ProloGraalStructure consultStringHead = ProloGraalConsultStringBuiltin.createStandardHead(consultStringVars).asStructure();
      ProloGraalConsultStringHeadNode consultStringNode = ProloGraalConsultStringHeadNodeGen.create(consultStringHead, context,
            new ProloGraalSimpleTermNode(consultStringHead.getArguments().get(0)));
      ProloGraalClause consultStringBuiltin = ProloGraalConsultStringBuiltinNodeGen.create(context, consultStringNode);
      clauses.put(consultStringBuiltin.getHead(), Collections.singletonList(consultStringBuiltin));

      HashMap<ProloGraalVariable,ProloGraalVariable> consultVars = new HashMap<>();
      ProloGraalStructure consultHead = ProloGraalConsultBuiltin.createStandardHead(consultVars).asStructure();
      ProloGraalConsultHeadNode consultNode = ProloGraalConsultHeadNodeGen.create(consultHead, context,
            new ProloGraalSimpleTermNode(consultHead.getArguments().get(0)));
      ProloGraalClause consultBuiltin = ProloGraalConsultBuiltinNodeGen.create(context, consultNode);
      clauses.put(consultBuiltin.getHead(), Collections.singletonList(consultBuiltin));

      ProloGraalStructure equalStructure = ProloGraalEqualBuiltin.createStandardHead(new HashMap<>()).asStructure();
      ProloGraalEqualHeadNode equalHeadNode = ProloGraalEqualHeadNodeGen.create(equalStructure,
            new ProloGraalSimpleTermNode(equalStructure.getArguments().get(0)), new ProloGraalSimpleTermNode(equalStructure.getArguments().get(1)));
      ProloGraalClause equalBuiltin = ProloGraalEqualBuiltinNodeGen.create(equalHeadNode);
      clauses.put(equalBuiltin.getHead(), Collections.singletonList(equalBuiltin));

      ProloGraalStructure gtStructure = ProloGraalGTBuiltin.createStandardHead(new HashMap<>()).asStructure();
      ProloGraalGTHeadNode gtHeadNode = ProloGraalGTHeadNodeGen.create(gtStructure,
            new ProloGraalSimpleTermNode(gtStructure.getArguments().get(0)), new ProloGraalSimpleTermNode(gtStructure.getArguments().get(1)));
      ProloGraalClause gtBuiltin = ProloGraalGTBuiltinNodeGen.create(gtHeadNode);
      clauses.put(gtBuiltin.getHead(), Collections.singletonList(gtBuiltin));

      ProloGraalStructure geStructure = ProloGraalGEBuiltin.createStandardHead(new HashMap<>()).asStructure();
      ProloGraalGEHeadNode geHeadNode = ProloGraalGEHeadNodeGen.create(geStructure,
            new ProloGraalSimpleTermNode(geStructure.getArguments().get(0)), new ProloGraalSimpleTermNode(geStructure.getArguments().get(1)));
      ProloGraalClause geBuiltin = ProloGraalGEBuiltinNodeGen.create(geHeadNode);
      clauses.put(geBuiltin.getHead(), Collections.singletonList(geBuiltin));

      ProloGraalStructure ltStructure = ProloGraalLTBuiltin.createStandardHead(new HashMap<>()).asStructure();
      ProloGraalLTHeadNode ltHeadNode = ProloGraalLTHeadNodeGen.create(ltStructure,
            new ProloGraalSimpleTermNode(ltStructure.getArguments().get(0)), new ProloGraalSimpleTermNode(ltStructure.getArguments().get(1)));
      ProloGraalClause ltBuiltin = ProloGraalLTBuiltinNodeGen.create(ltHeadNode);
      clauses.put(ltBuiltin.getHead(), Collections.singletonList(ltBuiltin));

      ProloGraalStructure leStructure = ProloGraalLEBuiltin.createStandardHead(new HashMap<>()).asStructure();
      ProloGraalLEHeadNode leHeadNode = ProloGraalLEHeadNodeGen.create(leStructure,
            new ProloGraalSimpleTermNode(leStructure.getArguments().get(0)), new ProloGraalSimpleTermNode(leStructure.getArguments().get(1)));
      ProloGraalClause leBuiltin = ProloGraalLEBuiltinNodeGen.create(leHeadNode);
      clauses.put(leBuiltin.getHead(), Collections.singletonList(leBuiltin));


      HashMap<ProloGraalVariable,ProloGraalVariable> realTimeVars = new HashMap<>();
      ProloGraalStructure realTimeHead = new ProloGraalStructure(realTimeVars);
      realTimeHead.setFunctor(new ProloGraalAtom(realTimeVars, "real_time"));
      ProloGraalVariable realTimeArg = new ProloGraalVariable(realTimeVars, "Arg");
      realTimeHead.addSubterm(realTimeArg);

      ProloGraalRealTimeHeadNode realTimeNode = ProloGraalRealTimeHeadNodeGen.create(realTimeHead, new ProloGraalSimpleTermNode(realTimeArg));

      ProloGraalClause realTimeBuiltin = ProloGraalRealTimeBuiltinNodeGen.create(realTimeNode);
      clauses.put(realTimeBuiltin.getHead(), Collections.singletonList(realTimeBuiltin));
   }

   public final Map<ProloGraalTerm<?>, List<ProloGraalClause>> getClauses() {
      return clauses;
   }

   public final ProloGraalClause getFirstClause() {
      if (firstClause == null) {
         throw new IllegalStateException("Runtime is empty !");
      }
      return firstClause;

   }

   private final void setFirstClauseIfNull(ProloGraalClause clause){
      if (firstClause == null && clause != null) {
         firstClause = clause;
      }
   }

   public final void addProloGraalClause(ProloGraalClause clause){
      setFirstClauseIfNull(clause);

      clauses.putIfAbsent(clause.getHead(), new ArrayList<>());
      List<ProloGraalClause> clauses1 = clauses.get(clause.getHead());
      clauses1.add(clause);
   }

   public final void addProloGraalClauses(List<ProloGraalClause> clauseList){
      // put every clauses into the map
      for (ProloGraalClause clause : clauseList) {
        addProloGraalClause(clause);
      }
   }
}