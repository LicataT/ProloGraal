package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.runtime.ProloGraalTerm;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Abstract builtin head node, extended by builtin head nodes containing three args.
 * @author Tony Licata
 * @see ProloGraalBuiltinHeadNode
 */
@NodeInfo(shortName = "ProloGraalTernaryHeadNode")
@NodeChild(value = "left", type = ProloGraalTermNode.class)
@NodeChild(value = "center", type = ProloGraalTermNode.class)
@NodeChild(value = "right", type = ProloGraalTermNode.class)
public abstract class ProloGraalTernaryHeadNode extends ProloGraalBuiltinHeadNode {

    public ProloGraalTernaryHeadNode(ProloGraalTerm<?> value) {
        super(value);
    }

}
