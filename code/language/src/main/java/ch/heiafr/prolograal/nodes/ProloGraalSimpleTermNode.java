package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.runtime.ProloGraalIsOpStructure;
import ch.heiafr.prolograal.runtime.ProloGraalTerm;
import ch.heiafr.prolograal.runtime.ProloGraalVariable;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.util.Map;

/**
 * Represent a ProloGraalTerm as a ProloGraalTermNode
 * Simply return the stocked value, or execute it's root value if the stocked value is a variable bound to a is/2 operator.
 * @author Tony Licata
 * @see ProloGraalTermNode
 */
@NodeInfo(shortName = "ProloGraalSimpleTermNode")
public class ProloGraalSimpleTermNode extends ProloGraalTermNode {

    public ProloGraalSimpleTermNode(ProloGraalTerm value){
        super(value);
    }

    @Override
    public ProloGraalTerm executeTerm(VirtualFrame frame) {
        return value instanceof ProloGraalVariable ?
              //work around to execute is operators contained into variables.
              ( value.getRootValue() instanceof ProloGraalIsOpStructure ? ((ProloGraalIsOpStructure)value.getRootValue()).getIsOpTermNode().executeTerm(frame) : value.getRootValue() )
              //if value is not a variable, just
              : value;
    }

    @Override
    public ProloGraalTermNode copyTermNode(Map<ProloGraalVariable, ProloGraalVariable> variables) {
        return new ProloGraalSimpleTermNode(value.copy(variables));
    }
}
