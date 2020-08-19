package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.builtins.predicates.ProloGraalNoTraceBuiltin;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Abstract builtin head node representing the head of no_trace/0 builtin clause.
 * This node will switch the prolog tracer to off
 * You may create an instance by using the ProloGraalNoTraceHeadNodeGen.create() method.
 * @author Tony Licata
 * @see ProloGraalBuiltinHeadNode
 */
@NodeInfo(shortName = "ProloGraalNoTraceHeadNode")
public abstract class ProloGraalNoTraceHeadNode extends ProloGraalBuiltinHeadNode {

    private final PrintWriter writer; // used for outputting
    private final ProloGraalContext context; // we keep the context for the copy method

    public ProloGraalNoTraceHeadNode(ProloGraalTerm<?> value, ProloGraalContext context) {
        super(value);
        this.context = context;
        this.writer = new PrintWriter(context.getOutput(), true);
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    public ProloGraalBoolean returnValue() {
        context.setTraceFlag(false);
        writer.print(ProloGraalNoTraceBuiltin.TRACE_OFF_TEXT);
        writer.flush();
        return new ProloGraalSuccess();
    }

    @Override
    public ProloGraalBuiltinHeadNode copyBuiltin(Map<ProloGraalVariable, ProloGraalVariable> variables){
        return ProloGraalNoTraceHeadNodeGen.create(value.copy(variables), context);
    }
}
