package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.ProloGraalTypes;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.util.Map;

/**
 * Abstract builtin head extended by every builtin head node.
 * This node is extended by Unary, Binary and Ternary head nodes, to correctly handle different amount of args
 * TODO: Use of Truffle array of nodes declaration to handle any amount of args: https://github.com/graalvm/simplelanguage/blob/master/language/src/main/java/com/oracle/truffle/sl/builtins/SLBuiltinNode.java
 * @author Tony Licata
 * @see ProloGraalGenericNode
 */
@TypeSystemReference(ProloGraalTypes.class)
@NodeInfo(shortName = "ProloGraalBuiltinHeadNode")
@GenerateWrapper
public abstract class ProloGraalBuiltinHeadNode extends ProloGraalGenericNode implements InstrumentableNode {

    protected final ProloGraalTerm<?> value;

    public ProloGraalBuiltinHeadNode(ProloGraalBuiltinHeadNode other){
        this.value=other.value;
    }

    public ProloGraalBuiltinHeadNode(ProloGraalTerm<?> value){
        this.value=value;
    }

    public abstract ProloGraalBoolean executeBuiltin(VirtualFrame frame);

    public abstract ProloGraalBuiltinHeadNode copyBuiltin(Map<ProloGraalVariable, ProloGraalVariable> variables);

    public boolean isInstrumentable() {
        return true;
    }

    public WrapperNode createWrapper(ProbeNode probe) {
        // ASTNodeWrapper is generated by @GenerateWrapper
        return new ProloGraalBuiltinHeadNodeWrapper(this, this, probe);
    }

    public ProloGraalTerm getValue(){
        return value;
    }
}
