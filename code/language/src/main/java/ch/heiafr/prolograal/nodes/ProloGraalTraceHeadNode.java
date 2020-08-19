package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.builtins.predicates.ProloGraalTraceBuiltin;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Abstract builtin head node representing the head of trace/0 builtin clause.
 * This node will switch the prolog tracer to on
 * You may create an instance by using the ProloGraalTraceHeadNodeGen.create() method.
 * @author Tony Licata
 * @see ProloGraalBuiltinHeadNode
 */
@NodeInfo(shortName = "ProloGraalTraceHeadNode")
public abstract class ProloGraalTraceHeadNode extends ProloGraalBuiltinHeadNode {

    private final PrintWriter writer; // used for outputting
    private final ProloGraalContext context; // we keep the context for the copy method

    public ProloGraalTraceHeadNode(ProloGraalTerm<?> value, ProloGraalContext context) {
        super(value);
        this.context = context;
        this.writer = new PrintWriter(context.getOutput(), true);
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    public ProloGraalBoolean returnValue() {
        context.setTraceFlag(true);
        writer.print(ProloGraalTraceBuiltin.TRACE_ON_TEXT);
        writer.flush();
        return new ProloGraalSuccess();
    }

    @Override
    public ProloGraalBuiltinHeadNode copyBuiltin(Map<ProloGraalVariable, ProloGraalVariable> variables){
        return ProloGraalTraceHeadNodeGen.create(value.copy(variables), context);
    }
}
