package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.builtins.predicates.ProloGraalIsBuiltin;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeUtil;

import java.util.*;
import java.util.function.Function;

/**
 * Class representing is/2 operations as Truffle nodes.
 * It doesn't contain the term representing the is/2 operator, but only the Java function that the operator would compute.
 * @author Tony Licata
 * @see ProloGraalTermNode
 */
@NodeChild(value = "left", type = ProloGraalTermNode.class)
@NodeChild(value = "right", type = ProloGraalTermNode.class)
public abstract class ProloGraalIsOpTermNode extends ProloGraalTermNode {

    private final Function<List<ProloGraalNumber>,ProloGraalNumber> operation;

    public ProloGraalIsOpTermNode(ProloGraalTerm<?> value) {
        super(value);
        ProloGraalAtom functor;
        if(value.isStructure() || value.isAtom()){
            if(value.isAtom()){
                functor = value.asAtom();
                String atomName = functor.getName();
                if(atomName.equals("pi") || atomName.equals("e") || atomName.equals("tau")){
                    operation = ProloGraalIsBuiltin.getOperations().get(functor);
                }else{
                    operation = null;
                }
            }else{ // since surrounding if check if it's an atom or a struct, we are sure the value is a struct
                functor = value.asStructure().getFunctor();
                operation = ProloGraalIsBuiltin.getOperations().get(functor);
            }
        }else{
            operation = null;
        }
    }

    public Function<List<ProloGraalNumber>,ProloGraalNumber> getOperation(){
        return operation;
    }

    @Specialization
    public ProloGraalNumber computeNoArgs(ProloGraalAtom left, ProloGraalAtom right){
        return operation.apply(new ArrayList<>());
    }

    @Specialization
    public ProloGraalNumber computeUnary(ProloGraalTerm left, ProloGraalAtom right){
        return operation.apply(Arrays.asList(left.asNumber()));
    }

    @Specialization
    public ProloGraalNumber computeBinary(ProloGraalTerm left, ProloGraalTerm right){
        return operation.apply(Arrays.asList(left.asNumber(),right.asNumber()));
    }

    @Override
    public ProloGraalTermNode copyTermNode(Map<ProloGraalVariable, ProloGraalVariable> variables) {
        List<Node> childrens = NodeUtil.findNodeChildren(this);
        ProloGraalTermNode right = null;
        ProloGraalTermNode left = null;
        if(childrens.size()>0)
            left = (ProloGraalTermNode) childrens.get(0);
        if(childrens.size()>1)
            right = (ProloGraalTermNode) childrens.get(1);
        return ProloGraalIsOpTermNodeGen.create(value.copy(variables), left!=null?left.copyTermNode(variables):null, right!=null?right.copyTermNode(variables):null);
    }

    /* special node to handle unary and no args operators, used as a void node.
     * it simply return emptyNode, so we now if the current arg is used or not.
     * we use this workaround since is/2 operators only are binary structs, unary structs and atoms.
     */
    public static class ProloGraalEmptyTermNode extends ProloGraalTermNode{

        public ProloGraalEmptyTermNode() {
            super(new ProloGraalAtom(new HashMap<>(),"emptyNode"));
        }

        @Override
        public ProloGraalTerm executeTerm(VirtualFrame frame) {
            return value;
        }

        @Override
        public ProloGraalTermNode copyTermNode(Map<ProloGraalVariable, ProloGraalVariable> variables) {
            return this;
        }
    }
}
