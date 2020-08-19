package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.List;
import java.util.Map;

@NodeInfo(shortName = "ProloGraalVarHeadNode")
public abstract class ProloGraalVarHeadNode extends ProloGraalUnaryHeadNode {

    public ProloGraalVarHeadNode(ProloGraalTerm<?> value) {
        super(value);
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    public ProloGraalBoolean returnValue(ProloGraalTerm arg) {
        return arg.isVariable() ? new ProloGraalSuccess() : new ProloGraalFailure();
    }

    @Override
    public ProloGraalBuiltinHeadNode copyBuiltin(Map<ProloGraalVariable, ProloGraalVariable> variables){
        List<Node> childrens = NodeUtil.findNodeChildren(this);
        ProloGraalTermNode child = (ProloGraalTermNode) childrens.get(0);
        return ProloGraalVarHeadNodeGen.create(value.copy(variables), child.copyTermNode(variables));
    }
}
