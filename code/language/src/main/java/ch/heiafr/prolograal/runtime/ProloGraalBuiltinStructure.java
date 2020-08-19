package ch.heiafr.prolograal.runtime;

import ch.heiafr.prolograal.nodes.*;
import ch.heiafr.prolograal.nodes.ProloGraalBuiltinHeadNode;
import ch.heiafr.prolograal.nodes.ProloGraalIsHeadNode;
import ch.heiafr.prolograal.nodes.ProloGraalIsOpTermNode;
import ch.heiafr.prolograal.nodes.ProloGraalPolyglotEvalHeadNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Class representing the builtin clauses structures (being the head of the clause).
 * We need to use this special structure to let it store the ProloGraalBuiltinHeadNode that represent this builtin.
 * @author Tony Licata
 */
public class ProloGraalBuiltinStructure extends ProloGraalStructure{

    private final ProloGraalBuiltinHeadNode builtin;

    //we save the context statically to use it later in useinterpreter/0 builtin
    private static ProloGraalContext context = null;

    public static void setContext(ProloGraalContext context){
        ProloGraalBuiltinStructure.context = context;
    }

    public ProloGraalBuiltinStructure(ProloGraalBuiltinHeadNode builtin){
        super(new HashMap<>());
        this.builtin = builtin;
    }

    public ProloGraalBuiltinStructure(ProloGraalStructure copiedStruct, ProloGraalBuiltinHeadNode builtin) {
        super(copiedStruct);
        this.builtin = builtin;
    }

    public ProloGraalBuiltinHeadNode getBuiltin(){
        return builtin;
    }

    @Override
    public ProloGraalBuiltinStructure copy(Map<ProloGraalVariable, ProloGraalVariable> variables) {
        ProloGraalBuiltinStructure struct = new ProloGraalBuiltinStructure(builtin.copyBuiltin(variables));
        struct.setFunctor(this.functor.copy(variables));
        this.subterms.forEach(x -> struct.addSubterm(x.copy(variables)));
        return struct;
    }

    /////////////////////////////////////////////////////////////
    //TODO: builtins can be atoms, should be ProloGraalBuiltinTerm?
    //builtin initialization as ProloGraalTerm. It let us execute them as Truffle Nodes,
    //even if builtin clauses calls are stored into regular clauses goals (since goals are a list of terms).
    ////////////////////////////////////////////////////////////

    private static final Map<String, Function<ProloGraalTerm, ProloGraalStructure>> builtinsInitializers = fillBuiltinsInitializers();

    private static Map<String, Function<ProloGraalTerm, ProloGraalStructure>> fillBuiltinsInitializers(){
        Map<String, Function<ProloGraalTerm, ProloGraalStructure>> initializers = new HashMap<>();
        initializers.put("is", ProloGraalBuiltinStructure::initializeIs);
        initializers.put("isOp", ProloGraalBuiltinStructure::initializeIsOp);
        initializers.put("polyglot_eval", ProloGraalBuiltinStructure::initializePolyglotEval);
        initializers.put("=", ProloGraalBuiltinStructure::initializeEqual);
        initializers.put(">", ProloGraalBuiltinStructure::initializeGT);
        initializers.put(">=", ProloGraalBuiltinStructure::initializeGE);
        initializers.put("<", ProloGraalBuiltinStructure::initializeLT);
        initializers.put("=<", ProloGraalBuiltinStructure::initializeLE);
        initializers.put("real_time", ProloGraalBuiltinStructure::initializeRealTime);
        initializers.put("useinterpreter", ProloGraalBuiltinStructure::initializeUseInterpreter);
        initializers.put("consult", ProloGraalBuiltinStructure::initializeConsult);
        initializers.put("consultstring", ProloGraalBuiltinStructure::initializeConsultString);
        initializers.put("var", ProloGraalBuiltinStructure::initializeVar);
        initializers.put("write", ProloGraalBuiltinStructure::initializeWrite);
        initializers.put("trace", ProloGraalBuiltinStructure::initializeTrace);
        initializers.put("notrace", ProloGraalBuiltinStructure::initializeNoTrace);
        return initializers;
    }

    public static ProloGraalBuiltinStructure initializeIs(ProloGraalTerm isTerm){
        if( !(isTerm.isStructure() && isTerm.asStructure().getArity() == 2) )
            return null;
        List<ProloGraalTerm<?>> isArgs = isTerm.asStructure().getArguments();
        ProloGraalIsHeadNode isBuiltinHead = ProloGraalIsHeadNodeGen.create(isTerm,buildTermNodeForIsOp(isArgs.get(0)),buildTermNodeForIsOp(isArgs.get(1)));
        return new ProloGraalBuiltinStructure(isTerm.asStructure(),isBuiltinHead);
    }

    public static ProloGraalIsOpStructure initializeIsOp(ProloGraalTerm isOpTerm){
        if( !(isOpTerm.isStructure() || isOpTerm.isAtom()) )
            return null;
        ProloGraalTermNode emptyNode = new ProloGraalIsOpTermNode.ProloGraalEmptyTermNode();
        if(isOpTerm.isAtom()){
            ProloGraalIsOpTermNode isOpTermNode = ProloGraalIsOpTermNodeGen.create(isOpTerm,emptyNode,emptyNode);
            if(isOpTermNode.getOperation() == null)
                return null;
            ProloGraalStructure isOpStruct = new ProloGraalStructure(isOpTerm.getVariables());
            isOpStruct.setFunctor(isOpTerm.asAtom());
            return new ProloGraalIsOpStructure(isOpStruct, isOpTermNode);
        }
        ProloGraalTermNode left = emptyNode;
        ProloGraalTermNode right = emptyNode;
        List<ProloGraalTerm<?>> isOpArgs = isOpTerm.asStructure().getArguments();
        if(isOpArgs.size()>1)
            right = buildTermNodeForIsOp(isOpArgs.get(1));
        if(isOpArgs.size()>0)
            left = buildTermNodeForIsOp(isOpArgs.get(0));
        ProloGraalIsOpTermNode isOpBuiltinHead = ProloGraalIsOpTermNodeGen.create(isOpTerm,left,right);
        return new ProloGraalIsOpStructure(isOpTerm.asStructure(),isOpBuiltinHead);
    }

    public static ProloGraalBuiltinStructure initializePolyglotEval(ProloGraalTerm polyglotEvalTerm){
        if( !(polyglotEvalTerm.isStructure() && polyglotEvalTerm.asStructure().getArity() == 3) )
            return null;
        List<ProloGraalTerm<?>> polyglotEvalArgs = polyglotEvalTerm.asStructure().getArguments();
        ProloGraalPolyglotEvalHeadNode polyglotEvalBuiltinHead = ProloGraalPolyglotEvalHeadNodeGen.create(polyglotEvalTerm,
              new ProloGraalSimpleTermNode(polyglotEvalArgs.get(0)),
              new ProloGraalSimpleTermNode(polyglotEvalArgs.get(1)),
              new ProloGraalSimpleTermNode(polyglotEvalArgs.get(2)));
        return new ProloGraalBuiltinStructure(polyglotEvalTerm.asStructure(),polyglotEvalBuiltinHead);
    }

    public static ProloGraalBuiltinStructure initializeEqual(ProloGraalTerm EqualTerm){
        if( !(EqualTerm.isStructure() && EqualTerm.asStructure().getArity() == 2) )
            return null;
        List<ProloGraalTerm<?>> equalArgs = EqualTerm.asStructure().getArguments();
        ProloGraalEqualHeadNode equalBuiltinHead = ProloGraalEqualHeadNodeGen.create(EqualTerm,new ProloGraalSimpleTermNode(equalArgs.get(0)),new ProloGraalSimpleTermNode(equalArgs.get(1)));
        return new ProloGraalBuiltinStructure(EqualTerm.asStructure(),equalBuiltinHead);
    }

    public static ProloGraalBuiltinStructure initializeGT(ProloGraalTerm gtTerm){
        if( !(gtTerm.isStructure() && gtTerm.asStructure().getArity() == 2) )
            return null;
        List<ProloGraalTerm<?>> gtArgs = gtTerm.asStructure().getArguments();
        ProloGraalGTHeadNode gtBuiltinHead = ProloGraalGTHeadNodeGen.create(gtTerm,new ProloGraalSimpleTermNode(gtArgs.get(0)),new ProloGraalSimpleTermNode(gtArgs.get(1)));
        return new ProloGraalBuiltinStructure(gtTerm.asStructure(),gtBuiltinHead);
    }

    public static ProloGraalBuiltinStructure initializeGE(ProloGraalTerm geTerm){
        if( !(geTerm.isStructure() && geTerm.asStructure().getArity() == 2) )
            return null;
        List<ProloGraalTerm<?>> geArgs = geTerm.asStructure().getArguments();
        ProloGraalGEHeadNode geBuiltinHead = ProloGraalGEHeadNodeGen.create(geTerm,new ProloGraalSimpleTermNode(geArgs.get(0)),new ProloGraalSimpleTermNode(geArgs.get(1)));
        return new ProloGraalBuiltinStructure(geTerm.asStructure(),geBuiltinHead);
    }

    public static ProloGraalBuiltinStructure initializeLT(ProloGraalTerm ltTerm){
        if( !(ltTerm.isStructure() && ltTerm.asStructure().getArity() == 2) )
            return null;
        List<ProloGraalTerm<?>> ltArgs = ltTerm.asStructure().getArguments();
        ProloGraalLTHeadNode ltBuiltinHead = ProloGraalLTHeadNodeGen.create(ltTerm,new ProloGraalSimpleTermNode(ltArgs.get(0)),new ProloGraalSimpleTermNode(ltArgs.get(1)));
        return new ProloGraalBuiltinStructure(ltTerm.asStructure(),ltBuiltinHead);
    }

    public static ProloGraalBuiltinStructure initializeLE(ProloGraalTerm leTerm){
        if( !(leTerm.isStructure() && leTerm.asStructure().getArity() == 2) )
            return null;
        List<ProloGraalTerm<?>> leArgs = leTerm.asStructure().getArguments();
        ProloGraalLEHeadNode leBuiltinHead = ProloGraalLEHeadNodeGen.create(leTerm,new ProloGraalSimpleTermNode(leArgs.get(0)),new ProloGraalSimpleTermNode(leArgs.get(1)));
        return new ProloGraalBuiltinStructure(leTerm.asStructure(),leBuiltinHead);
    }

    public static ProloGraalBuiltinStructure initializeRealTime(ProloGraalTerm realTimeTerm){
        if( !(realTimeTerm.isStructure() && realTimeTerm.asStructure().getArity() == 1) )
            return null;
        List<ProloGraalTerm<?>> realTimeArgs = realTimeTerm.asStructure().getArguments();
        ProloGraalRealTimeHeadNode realTimeBuiltinHead = ProloGraalRealTimeHeadNodeGen.create(realTimeTerm,new ProloGraalSimpleTermNode(realTimeArgs.get(0)));
        return new ProloGraalBuiltinStructure(realTimeTerm.asStructure(),realTimeBuiltinHead);
    }

    public static ProloGraalBuiltinStructure initializeConsult(ProloGraalTerm consultTerm){
        if( !(consultTerm.isStructure() && consultTerm.asStructure().getArity() == 1) )
            return null;
        List<ProloGraalTerm<?>> consultArgs = consultTerm.asStructure().getArguments();
        ProloGraalConsultHeadNode consultBuiltinHead = ProloGraalConsultHeadNodeGen.create(consultTerm, context, new ProloGraalSimpleTermNode(consultArgs.get(0)));
        return new ProloGraalBuiltinStructure(consultTerm.asStructure(),consultBuiltinHead);
    }

    public static ProloGraalBuiltinStructure initializeConsultString(ProloGraalTerm consultStringTerm){
        if( !(consultStringTerm.isStructure() && consultStringTerm.asStructure().getArity() == 1) )
            return null;
        List<ProloGraalTerm<?>> consultStringArgs = consultStringTerm.asStructure().getArguments();
        ProloGraalConsultStringHeadNode consultStringBuiltinHead = ProloGraalConsultStringHeadNodeGen.create(consultStringTerm, context, new ProloGraalSimpleTermNode(consultStringArgs.get(0)));
        return new ProloGraalBuiltinStructure(consultStringTerm.asStructure(),consultStringBuiltinHead);
    }

    public static ProloGraalBuiltinStructure initializeVar(ProloGraalTerm varTerm){
        if( !(varTerm.isStructure() && varTerm.asStructure().getArity() == 1) )
            return null;
        List<ProloGraalTerm<?>> varArgs = varTerm.asStructure().getArguments();
        ProloGraalVarHeadNode varBuiltinHead = ProloGraalVarHeadNodeGen.create(varTerm, new ProloGraalSimpleTermNode(varArgs.get(0)));
        return new ProloGraalBuiltinStructure(varTerm.asStructure(),varBuiltinHead);
    }

    public static ProloGraalBuiltinStructure initializeWrite(ProloGraalTerm writeTerm){
        if( !(writeTerm.isStructure() && writeTerm.asStructure().getArity() == 1) )
            return null;
        List<ProloGraalTerm<?>> writeArgs = writeTerm.asStructure().getArguments();
        ProloGraalWriteHeadNode writeBuiltinHead = ProloGraalWriteHeadNodeGen.create(writeTerm, context, new ProloGraalSimpleTermNode(writeArgs.get(0)));
        return new ProloGraalBuiltinStructure(writeTerm.asStructure(),writeBuiltinHead);
    }

    //TODO: why using structure (useinterpreter and following builtins are atom)
    public static ProloGraalBuiltinStructure initializeUseInterpreter(ProloGraalTerm useInterpreterTerm){
        if( !(useInterpreterTerm.isAtom()) )
            return null;
        ProloGraalUseInterpreterHeadNode useInterpreterBuiltinHead = ProloGraalUseInterpreterHeadNodeGen.create(useInterpreterTerm, context);
        ProloGraalStructure useInterpreterStructure = new ProloGraalStructure(useInterpreterTerm.getVariables());
        useInterpreterStructure.setFunctor(useInterpreterTerm.asAtom());
        return new ProloGraalBuiltinStructure(useInterpreterStructure,useInterpreterBuiltinHead);
    }

    public static ProloGraalBuiltinStructure initializeTrace(ProloGraalTerm traceTerm){
        if( !(traceTerm.isAtom()) )
            return null;
        ProloGraalTraceHeadNode traceBuiltinHead = ProloGraalTraceHeadNodeGen.create(traceTerm, context);
        ProloGraalStructure traceStructure = new ProloGraalStructure(traceTerm.getVariables());
        traceStructure.setFunctor(traceTerm.asAtom());
        return new ProloGraalBuiltinStructure(traceStructure,traceBuiltinHead);
    }

    public static ProloGraalBuiltinStructure initializeNoTrace(ProloGraalTerm noTraceTerm){
        if( !(noTraceTerm.isAtom()) )
            return null;
        ProloGraalNoTraceHeadNode noTraceBuiltinHead = ProloGraalNoTraceHeadNodeGen.create(noTraceTerm, context);
        ProloGraalStructure noTraceStructure = new ProloGraalStructure(noTraceTerm.getVariables());
        noTraceStructure.setFunctor(noTraceTerm.asAtom());
        return new ProloGraalBuiltinStructure(noTraceStructure,noTraceBuiltinHead);
    }


    /**
     * Build a term node specifically to the given opArg term.
     * @param opArg the term to build as a ProloGraalTermNode
     * @return the built ProloGraalTermNode
     */
    private static ProloGraalTermNode buildTermNodeForIsOp(ProloGraalTerm opArg){
        if(opArg instanceof ProloGraalIsOpStructure)
            return ((ProloGraalIsOpStructure)opArg).getIsOpTermNode();
        return new ProloGraalSimpleTermNode(opArg);
    }

    public static Map<String, Function<ProloGraalTerm, ProloGraalStructure>> getBuiltinsInitializers() {
        return builtinsInitializers;
    }
}