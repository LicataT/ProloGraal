package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.ProloGraalLanguage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Abstract node representing the base node for all ProloGraal nodes that represent a Prolog Term.
 * We except to this rule the nodes used during Prolog goal solving and also the interpreter, since they aren't part of the Prolog syntax :
 * (ProloGraalEvalRootNode, ProloGraalResolverNode, ProloGraalProofTreeNode, ProloGraalInterpreterNode)
 * @author Tony Licata
 */
@NodeInfo(language = ProloGraalLanguage.ID, shortName = "ProloGraalGenericNode")
public abstract class ProloGraalGenericNode extends Node {
}
