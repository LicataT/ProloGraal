package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.runtime.ProloGraalTerm;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Abstract builtin head node, extended by builtin head nodes containing one args.
 * @author Tony Licata
 * @see ProloGraalBuiltinHeadNode
 */
@NodeInfo(shortName = "ProloGraalUnaryHeadNode")
@NodeChild(value = "child", type = ProloGraalTermNode.class)
public abstract class ProloGraalUnaryHeadNode extends ProloGraalBuiltinHeadNode {

    public ProloGraalUnaryHeadNode(ProloGraalTerm<?> value) {
        super(value);
    }

}
