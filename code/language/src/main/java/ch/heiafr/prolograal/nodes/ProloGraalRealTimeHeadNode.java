package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.builtins.predicates.ProloGraalRealTimeBuiltin;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract builtin head node representing the head of real_time/1 builtin clause.
 * This node unify the ProloGraalTermNode child inherited from ProloGraalUnaryHeadNode
 * with the current elapsed time since the program started (as ms).
 * You may create an instance by using the ProloGraalRealTimeHeadNodeGen.create() method.
 * @author Tony Licata
 * @see ProloGraalUnaryHeadNode
 */
@NodeInfo(shortName = "ProloGraalRealTimeHeadNode")
public abstract class ProloGraalRealTimeHeadNode extends ProloGraalUnaryHeadNode {

    public ProloGraalRealTimeHeadNode(ProloGraalTerm<?> value) {
        super(value);
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    public ProloGraalBoolean returnValue(ProloGraalTerm arg) {
        if(arg.isNumber() || arg.isVariable())
            if(arg.unify(new ProloGraalDoubleNumber(new HashMap<>(),System.currentTimeMillis()-ProloGraalRealTimeBuiltin.startTime)))
                return new ProloGraalSuccess();
        return new ProloGraalFailure();
    }

    @Override
    public ProloGraalBuiltinHeadNode copyBuiltin(Map<ProloGraalVariable, ProloGraalVariable> variables){
        List<Node> childrens = NodeUtil.findNodeChildren(this);
        ProloGraalTermNode child = (ProloGraalTermNode) childrens.get(0);
        return ProloGraalRealTimeHeadNodeGen.create(value.copy(variables), child.copyTermNode(variables));
    }
}
