package ch.heiafr.prolograal.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class representing a Prolog query.
 * @author Tony Licata
 */
public class ProloGraalQuery {

   // the goals of this query
   private List<ProloGraalTerm<?>> goals;
   // the variables contained in this query and its goals
   // mapping Variable to Variable to be able to retrieve elements by their hash (a Set can only tell us if the hash
   // is there or not, we cannot get the element)
   private Map<ProloGraalVariable, ProloGraalVariable> variables;

   public ProloGraalQuery() {
      goals = new ArrayList<>();
      variables = new HashMap<>();
   }

   // copy constructor
   public ProloGraalQuery(ProloGraalQuery other) {
      variables = new HashMap<>();
      this.goals = other.goals.stream().sequential().map(x -> x.copy(variables)).collect(Collectors.toList());
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
      return goals.stream().map(Object::toString).collect(Collectors.joining(",")) + ".";
   }

   public ProloGraalQuery copy() {
      return new ProloGraalQuery(this);
   }
}