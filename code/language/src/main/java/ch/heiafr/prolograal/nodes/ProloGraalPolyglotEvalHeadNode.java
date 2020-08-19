package ch.heiafr.prolograal.nodes;

import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.NodeUtil;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract builtin head node representing the head of polyglot_eval/0 builtin clause.
 * This node will eval the foreign code contained in the 3rd child node
 * only if this code source language is the same as the source language id contained in the 2nd child node.
 * If the evalutation return a value that can be casted as a Prolog term,
 * unify it with the variable contained in the 1st child node.
 * You may create an instance by using the ProloGraalPolyglotEvalHeadNodeGen.create() method.
 * @author Tony Licata
 * @see ProloGraalTernaryHeadNode
 */
@NodeInfo(shortName = "ProloGraalPolyglotEvalHeadNode")
public abstract class ProloGraalPolyglotEvalHeadNode extends ProloGraalTernaryHeadNode {

    public ProloGraalPolyglotEvalHeadNode(ProloGraalTerm<?> value) {
        super(value);
    }

    @Specialization(guards = {"center.isAtom()","right.isAtom()"})
    @CompilerDirectives.TruffleBoundary
    public ProloGraalBoolean returnValue(ProloGraalTerm left, ProloGraalTerm center, ProloGraalTerm right) {
        Context context = Context.newBuilder().allowAllAccess(true).build();
        Value res = context.eval(center.asAtom().getName(),right.asAtom().getName());
        ProloGraalTerm resAsTerm = genericValueToProloGraalTerm(res);
        if(resAsTerm != null && left.unify(resAsTerm)){
            return new ProloGraalSuccess();
        }
        System.out.println(res+" can not be casted into ProloGraalTerm (from ProloGraalPolyglotEvalHeadNode)");
        System.out.println("or "+res+" and "+left+" can not be unified");

        return new ProloGraalFailure();
    }

    @Specialization
    public ProloGraalBoolean wrongParams(ProloGraalTerm left, ProloGraalTerm center, ProloGraalTerm right) {
        System.out.println(center+" and "+right+" have to be atoms (2nd = languageID, 3rd = codeToEval)");
        return new ProloGraalFailure();
    }

    private ProloGraalTerm genericValueToProloGraalTerm(Value value){
        ProloGraalTerm res = null;
        if(value.isBoolean()){
            res = value.asBoolean()?new ProloGraalAtom(new HashMap<>(),"true"):new ProloGraalAtom(new HashMap<>(),"false");
        }else if(value.isNumber()){
            if(value.asDouble() % 1 == 0 && value.fitsInInt()){
                res = new ProloGraalIntegerNumber(new HashMap<>(), value.asInt());
            }else{
                res = new ProloGraalDoubleNumber(new HashMap<>(), value.asDouble());
            }
        }else if(value.isString()){
            res = new ProloGraalAtom(new HashMap<>(), value.asString());
        }
        return res;
    }

    @Override
    public ProloGraalBuiltinHeadNode copyBuiltin(Map<ProloGraalVariable, ProloGraalVariable> variables){
        List<Node> childrens = NodeUtil.findNodeChildren(this);
        ProloGraalTermNode left = (ProloGraalTermNode) childrens.get(0);
        ProloGraalTermNode center = (ProloGraalTermNode) childrens.get(1);
        ProloGraalTermNode right = (ProloGraalTermNode) childrens.get(2);
        return ProloGraalPolyglotEvalHeadNodeGen.create(value.copy(variables), left.copyTermNode(variables), center.copyTermNode(variables), right.copyTermNode(variables));
    }
}
