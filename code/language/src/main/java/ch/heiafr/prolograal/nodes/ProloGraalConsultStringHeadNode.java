package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.ProloGraalLanguage;
import ch.heiafr.prolograal.parser.ProloGraalParserImpl;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.source.Source;

import java.util.List;
import java.util.Map;

/**
 * Abstract builtin head node representing the head of consultstring/1 builtin clause.
 * This node consult the ProloGraalTermNode child representing an atom containing clauses, inherited from ProloGraalUnaryHeadNode.
 * You may create an instance by using the ProloGraalConsultStringHeadNodeGen.create() method.
 * @author Tony Licata
 * @see ProloGraalUnaryHeadNode
 */
@NodeInfo(shortName = "ProloGraalConsultStringHeadNode")
public abstract class ProloGraalConsultStringHeadNode extends ProloGraalUnaryHeadNode {

    private final ProloGraalContext context;

    public ProloGraalConsultStringHeadNode(ProloGraalTerm<?> value, ProloGraalContext context) {
        super(value);
        this.context = context;
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    public ProloGraalBoolean returnValue(ProloGraalTerm arg) {
        if(arg.isAtom()){
            String consultContentString = ((ProloGraalAtom) arg).getName();
            Source source = Source.newBuilder(ProloGraalLanguage.ID, consultContentString, null).build();
            //we filter clauses to remove goal lists from returned clauses
            List<ProloGraalClause> clauses = ProloGraalParserImpl.parseClauses(source);
            context.getRuntime().addProloGraalClauses(clauses);
            return new ProloGraalSuccess();
        }else{
            System.out.println("consultstring/1 predicate takes an atom as argument.");
            return new ProloGraalFailure();
        }
    }

    @Override
    public ProloGraalBuiltinHeadNode copyBuiltin(Map<ProloGraalVariable, ProloGraalVariable> variables){
        List<Node> childrens = NodeUtil.findNodeChildren(this);
        ProloGraalTermNode child = (ProloGraalTermNode) childrens.get(0);
        return ProloGraalConsultStringHeadNodeGen.create(value.copy(variables), context, child.copyTermNode(variables));
    }
}
