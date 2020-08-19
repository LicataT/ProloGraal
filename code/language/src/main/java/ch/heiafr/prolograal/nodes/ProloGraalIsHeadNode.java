package ch.heiafr.prolograal.nodes;


import ch.heiafr.prolograal.builtins.predicates.ProloGraalIsBuiltin;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Abstract builtin head node representing the head of is/2 builtin clause.
 * This node will return a success if the left ProloGraalTermNode child inherited from ProloGraalBinaryHeadNode can unify with the second child.
 * The second child will typically be an ProloGraalIsOpTermNode computed as a ProloGraalNumber or a ProloGraalTermNode representing a number
 * You may create an instance by using the ProloGraalIsHeadNodeGen.create() method.
 * @author Tony Licata
 * @see ProloGraalBinaryHeadNode
 */
@NodeInfo(shortName = "ProloGraalIsHeadNode")
public abstract class ProloGraalIsHeadNode extends ProloGraalBinaryHeadNode {

    public ProloGraalIsHeadNode(ProloGraalTerm<?> value) {
        super(value);
    }


    @Specialization
    public ProloGraalBoolean returnValue(ProloGraalVariable left, ProloGraalStructure right) {
        System.out.println("@Specialization: returnValue");
        ProloGraalNumber res = ProloGraalIsBuiltin.getOperations().get(right.asStructure().getFunctor()).apply(right.asStructure().getArguments().stream().map(x -> x.asNumber()).collect(Collectors.toList()));
        return left.unify(res)?new ProloGraalSuccess():new ProloGraalFailure();
    }

    /*
    @Specialization
    public ProloGraalBoolean returnWithInteger(VirtualFrame frame, ProloGraalVariable left, ProloGraalIntegerNumber right) {
        System.out.println("@Specialization: returnValue");
        ProloGraalNumber res = ProloGraalIsBuiltin.getOperations().get(right.asStructure().getFunctor()).apply(right.asStructure().getArguments().stream().map(x -> x.asNumber()).collect(Collectors.toList()));
        return left.unify(res)?new ProloGraalSuccess():new ProloGraalFailure();
    }*/

    @Specialization
    public ProloGraalBoolean testVariable(VirtualFrame frame, ProloGraalTerm left, ProloGraalTerm right) {
        // System.out.println("@Specialization: testVariable");
        return left.unify(right)||right.unify(left)?new ProloGraalSuccess():new ProloGraalFailure();
    }

    @Override
    public ProloGraalBuiltinHeadNode copyBuiltin(Map<ProloGraalVariable, ProloGraalVariable> variables){
        List<Node> childrens = NodeUtil.findNodeChildren(this);
        ProloGraalTermNode left = (ProloGraalTermNode) childrens.get(0);
        ProloGraalTermNode right = (ProloGraalTermNode) childrens.get(1);
        return ProloGraalIsHeadNodeGen.create(value.copy(variables), left.copyTermNode(variables), right.copyTermNode(variables));
    }
}
