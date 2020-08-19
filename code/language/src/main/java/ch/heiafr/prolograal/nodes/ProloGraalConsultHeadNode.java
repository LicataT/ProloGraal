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

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Abstract builtin head node representing the head of consult/1 builtin clause.
 * This node consult the ProloGraalTermNode child representing the choosen filename, inherited from ProloGraalUnaryHeadNode.
 * You may create an instance by using the ProloGraalConsultHeadNodeGen.create() method.
 * @author Tony Licata
 * @see ProloGraalUnaryHeadNode
 */
@NodeInfo(shortName = "ProloGraalConsultHeadNode")
public abstract class ProloGraalConsultHeadNode extends ProloGraalUnaryHeadNode {

    private final ProloGraalContext context;

    public ProloGraalConsultHeadNode(ProloGraalTerm<?> value, ProloGraalContext context) {
        super(value);
        this.context = context;
    }

    @Specialization
    @CompilerDirectives.TruffleBoundary
    public ProloGraalBoolean returnValue(ProloGraalTerm arg) {
        if(arg.isAtom()){
            String filename = ((ProloGraalAtom) arg).getName();
            File loadedFile = new File(filename);
            if(loadedFile.exists()) {
                String fileContent;
                try {
                    fileContent = Files.readAllLines(loadedFile.toPath(), Charset.forName("UTF-8")).stream().collect(Collectors.joining("\n"));
                }catch (Exception e){
                    System.out.println("could not read file "+filename);
                    return new ProloGraalFailure();
                }
                Source source = Source.newBuilder(ProloGraalLanguage.ID, fileContent, null).build();
                //we filter clauses to remove goal lists from returned clauses
                List<ProloGraalClause> clauses = ProloGraalParserImpl.parseClauses(source).stream().filter(proloGraalClause -> proloGraalClause.getHead() != null).collect(Collectors.toList());

                context.getRuntime().addProloGraalClauses(clauses);
                return new ProloGraalSuccess();
            }else{
                System.out.println("file "+filename+" does not exist");
                return new ProloGraalFailure();
            }
        }else{
            System.out.println("consult/1 predicate takes an atom as argument.");
            return new ProloGraalFailure();
        }
    }

    @Override
    public ProloGraalBuiltinHeadNode copyBuiltin(Map<ProloGraalVariable, ProloGraalVariable> variables){
        List<Node> childrens = NodeUtil.findNodeChildren(this);
        ProloGraalTermNode child = (ProloGraalTermNode) childrens.get(0);
        return ProloGraalConsultHeadNodeGen.create(value.copy(variables), context, child.copyTermNode(variables));
    }
}
