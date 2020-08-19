package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.runtime.ProloGraalTerm;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Abstract builtin head node, extended by builtin head nodes containing two args.
 * @author Tony Licata
 * @see ProloGraalBuiltinHeadNode
 */
@NodeInfo(shortName = "ProloGraalBinaryHeadNode")
@NodeChild(value = "left", type = ProloGraalTermNode.class)
@NodeChild(value = "right", type = ProloGraalTermNode.class)
public abstract class ProloGraalBinaryHeadNode extends ProloGraalBuiltinHeadNode {

    public ProloGraalBinaryHeadNode(ProloGraalTerm<?> value) {
        super(value);
    }

}
