package ch.heiafr.prolograal.runtime;

import ch.heiafr.prolograal.nodes.ProloGraalIsOpTermNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing the is/2 operators structures.
 * We need to use this special structure to let it store the ProloGraalIsOpTermNode that represent this operation.
 * @author Tony Licata
 */
public class ProloGraalIsOpStructure extends  ProloGraalStructure {

    private final ProloGraalIsOpTermNode isOpTermNode;

    public ProloGraalIsOpStructure(ProloGraalIsOpTermNode isOpTermNode){
        super(new HashMap<>());
        this.isOpTermNode = isOpTermNode;
    }

    public ProloGraalIsOpStructure(ProloGraalStructure copiedStruct, ProloGraalIsOpTermNode isOpTermNode) {
        super(copiedStruct);
        this.isOpTermNode = isOpTermNode;
    }

    public ProloGraalIsOpTermNode getIsOpTermNode(){
        return isOpTermNode;
    }

    @Override
    public ProloGraalIsOpStructure copy(Map<ProloGraalVariable, ProloGraalVariable> variables) {
        ProloGraalIsOpStructure struct = new ProloGraalIsOpStructure((ProloGraalIsOpTermNode) isOpTermNode.copyTermNode(variables));
        struct.setFunctor(this.functor.copy(variables));
        this.subterms.forEach(x -> struct.addSubterm(x.copy(variables)));
        return struct;
    }
}
