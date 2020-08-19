package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.List;
import java.util.Map;

/**
 * Abstract builtin head node representing the head of >/2 builtin clause.
 * This node will return a success if the left ProloGraalTermNode child inherited from ProloGraalBinaryHeadNode is greater than the second child.
 * You may create an instance by using the ProloGraalGTHeadNodeGen.create() method.
 * @author Tony Licata
 * @see ProloGraalBinaryHeadNode
 */
@NodeInfo(shortName = "ProloGraalGTHeadNode")
public abstract class ProloGraalGTHeadNode extends ProloGraalBinaryHeadNode {

    public ProloGraalGTHeadNode(ProloGraalTerm<?> value) {
        super(value);
    }

    @Specialization
    public ProloGraalBoolean greaterThan(ProloGraalNumber left, ProloGraalNumber right) {
        return left.asNumber().asDouble() > right.asNumber().asDouble() ? new ProloGraalSuccess() : new ProloGraalFailure();
    }

    @Override
    public ProloGraalBuiltinHeadNode copyBuiltin(Map<ProloGraalVariable, ProloGraalVariable> variables){
        List<Node> childrens = NodeUtil.findNodeChildren(this);
        ProloGraalTermNode left = (ProloGraalTermNode) childrens.get(0);
        ProloGraalTermNode right = (ProloGraalTermNode) childrens.get(1);
        return ProloGraalGTHeadNodeGen.create(value.copy(variables), left.copyTermNode(variables), right.copyTermNode(variables));
    }
}
