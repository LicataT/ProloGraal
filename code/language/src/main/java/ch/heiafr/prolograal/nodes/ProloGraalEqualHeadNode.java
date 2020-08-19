package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.List;
import java.util.Map;

/**
 * Abstract builtin head node representing the head of =/2 builtin clause.
 * This node unifies the two ProloGraalTermNode children inherited from ProloGraalBinaryHeadNode.
 * You may create an instance by using the ProloGraalEqualHeadNodeGen.create() method.
 * @author Tony Licata
 * @see ProloGraalBinaryHeadNode
 */
@NodeInfo(shortName = "ProloGraalEqualHeadNode")
public abstract class ProloGraalEqualHeadNode extends ProloGraalBinaryHeadNode {

    public ProloGraalEqualHeadNode(ProloGraalTerm<?> value) {
        super(value);
    }

    @Specialization
    public ProloGraalBoolean equal(ProloGraalTerm left, ProloGraalTerm right) {
        return left.unify(right)||right.unify(left)?new ProloGraalSuccess():new ProloGraalFailure();
    }

    @Override
    public ProloGraalBuiltinHeadNode copyBuiltin(Map<ProloGraalVariable, ProloGraalVariable> variables){
        List<Node> childrens = NodeUtil.findNodeChildren(this);
        ProloGraalTermNode left = (ProloGraalTermNode) childrens.get(0);
        ProloGraalTermNode right = (ProloGraalTermNode) childrens.get(1);
        return ProloGraalEqualHeadNodeGen.create(value.copy(variables), left.copyTermNode(variables), right.copyTermNode(variables));
    }
}
