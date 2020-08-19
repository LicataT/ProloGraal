package ch.heiafr.prolograal.runtime;

import ch.heiafr.prolograal.builtins.predicates.ProloGraalBuiltinClause;
import ch.heiafr.prolograal.nodes.ProloGraalGenericNode;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class representing a clause.
 * @see ProloGraalBuiltinClause
 * @author Martin Spoto
 */
@NodeInfo(shortName = "ProloGraalGenericClauseNode")
public class ProloGraalClause extends ProloGraalGenericNode {

   // the head of this clause
   private ProloGraalTerm<?> head;
   // the goals of this clause
   private List<ProloGraalTerm<?>> goals;
   // the variables contained in this clause and its goals
   // mapping Variable to Variable to be able to retrieve elements by their hash (a Set can only tell us if the hash
   // is there or not, we cannot get the element)
   private Map<ProloGraalVariable, ProloGraalVariable> variables;

   public ProloGraalClause(Map<ProloGraalVariable, ProloGraalVariable> variables) {
      goals = new ArrayList<>();
      this.variables = variables;
   }

   // copy constructor
   public ProloGraalClause(ProloGraalClause other) {
      variables = new HashMap<>();
      this.head = other.head.copy(variables);
      this.goals = other.goals.stream().sequential().map(x -> x.copy(variables)).collect(Collectors.toList());
   }

   public boolean isFact() {
      return goals.size() == 0;
   }

   public ProloGraalTerm<?> getHead() {
      return head;
   }

   public void setHead(ProloGraalTerm<?> head) {
      this.head = head;
   }

   public void addGoal(ProloGraalTerm<?> goal) {
      goals.add(goal);
   }

   public List<ProloGraalTerm<?>> getGoals() {
      return goals;
   }

   public Map<ProloGraalVariable, ProloGraalVariable> getVariables() {
      return variables;
   }

   @Override
   public String toString() {
      String res = "";
      if(head!=null){
         res+=head.toString() + ":-";
      }
      res += goals.stream().map(Object::toString).collect(Collectors.joining(",")) + ".";
      return res;
   }

   public ProloGraalClause copy() {
      return new ProloGraalClause(this);
   }
}