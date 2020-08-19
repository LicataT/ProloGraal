package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.util.Map;

/**
 * Abstract builtin head node representing the head of useinterpreter/0 builtin clause.
 * This node will launch a new interpreter, and returning a success when this interpreter will be exited.
 * You may create an instance by using the ProloGraalUseInterpreterHeadNodeGen.create() method.
 * @author Tony Licata
 * @see ProloGraalBuiltinHeadNode
 */
@NodeInfo(shortName = "ProloGraalUseInterpreterHeadNode")
public abstract class ProloGraalUseInterpreterHeadNode extends ProloGraalBuiltinHeadNode {

    private final ProloGraalContext context; // we keep the context for the copy method

    public ProloGraalUseInterpreterHeadNode(ProloGraalTerm<?> value, ProloGraalContext context) {
        super(value);
        this.context = context;
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    public ProloGraalBoolean returnValue() {
        Truffle.getRuntime().createCallTarget(context.getInterpreterNode()).call();
        return new ProloGraalSuccess();
    }

    @Override
    public ProloGraalBuiltinHeadNode copyBuiltin(Map<ProloGraalVariable, ProloGraalVariable> variables){
        return ProloGraalUseInterpreterHeadNodeGen.create(value.copy(variables), context);
    }
}
