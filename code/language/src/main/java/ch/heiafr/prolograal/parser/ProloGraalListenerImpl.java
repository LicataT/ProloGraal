package ch.heiafr.prolograal.parser;

import ch.heiafr.prolograal.ProloGraalLanguage;
import ch.heiafr.prolograal.builtins.predicates.ProloGraalIsBuiltin;
import ch.heiafr.prolograal.nodes.ProloGraalSimpleTermNode;
import ch.heiafr.prolograal.nodes.ProloGraalTermNode;
import ch.heiafr.prolograal.runtime.*;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class implementing the "listener" ANTLR parsing strategy.
 * Each method is called automatically by the default {@link org.antlr.v4.runtime.tree.ParseTreeWalker}.<br>
 * The idea is to build the representation of the Prolog source "bottom-up", meaning elements are added to a stack
 * once we hit a leaf of the parse tree (like atoms, numbers). This stack is then popped when we exit a container
 * like a structure or a list.
 * @see ProloGraalParserImpl
 * @see ProloGraalParseError
 * @author Martin Spoto
 */
public class ProloGraalListenerImpl extends ProloGraalBaseListener {

   private static String[] atomDelimiters = {"'","~"};

   // both deque are used as stack so we do not need to reverse everything, and because it is encouraged to use
   // deque in the stack javadoc

   // temporary stack of elements to store and manipulate them until they are finally pushed into a clause
   private Deque<ProloGraalTerm<?>> elements = new ArrayDeque<>();
   // stack of final clauses
   private Deque<ProloGraalClause> clauses = new ArrayDeque<>();
   // stack of final queries
   private Deque<ProloGraalQuery> queries = new ArrayDeque<>();

   private boolean parsingClauses = false;

   // provides temporary storage for the tail of a list since it needs special handling
   private ProloGraalTerm<?> tail;

   public void setParsingClauses(boolean parsingClauses){
      this.parsingClauses = parsingClauses;
   }

   /**
    * Returns the queries parsed from the file. Must be called after walking through the tree using the default
    * {@link org.antlr.v4.runtime.tree.ParseTreeWalker}.
    * @return The list of parsed clauses, in order of encounter in the source file
    */
   public List<ProloGraalQuery> getQueries() {
      List<ProloGraalQuery> r = new ArrayList<>();

      // we use a descending iterator to reverse the order of the clauses
      // since a stack is LIFO and we need to read FIFO to get the correct order
      Iterator<ProloGraalQuery> it = queries.descendingIterator();

      it.forEachRemaining(r::add);

      if (ProloGraalLanguage.DEBUG_MODE) {
         System.out.println(r);
      }

      return r;
   }

   /**
    * Returns the clauses parsed from the file. Must be called after walking through the tree using the default
    * {@link org.antlr.v4.runtime.tree.ParseTreeWalker}.
    * @return The list of parsed clauses, in order of encounter in the source file
    */
   public List<ProloGraalClause> getClauses() {
      List<ProloGraalClause> r = new ArrayList<>();

      // we use a descending iterator to reverse the order of the clauses
      // since a stack is LIFO and we need to read FIFO to get the correct order
      Iterator<ProloGraalClause> it = clauses.descendingIterator();

      it.forEachRemaining(r::add);

      if (ProloGraalLanguage.DEBUG_MODE) {
         System.out.println(r);
      }

      return r;
   }

   public void debug() {
      System.out.println("Clauses:\n"+clauses.stream().map(x -> x.toString() + "\n").collect(Collectors.joining()));
      System.out.println("Queries:\n"+queries.stream().map(x -> x.toString() + "\n").collect(Collectors.joining()));
   }

   private void throwParseError(ParserRuleContext ctx, String message) {
      ProloGraalParserImpl.throwParseError(ProloGraalParserImpl.source, ctx.getStart().getLine(), 0, ctx.getStart(),
              message);
   }

   private Map<ProloGraalVariable, ProloGraalVariable> getVariables(){
      if(parsingClauses)
         return clauses.peek().getVariables();
      return queries.peek().getVariables();
   }

   private void addGoal(ProloGraalTerm<?> goal){
      if(parsingClauses){
         clauses.peek().addGoal(goal);
      }else{
         queries.peek().addGoal(goal);
      }
   }

   private ProloGraalTermNode createHead(){
      return new ProloGraalSimpleTermNode(new ProloGraalVariable(new HashMap<>(),"A"));
   }

   private ProloGraalTermNode[] createGoals(){
      ProloGraalTermNode[] goals = new ProloGraalTermNode[1];
      goals[0] = new ProloGraalSimpleTermNode(new ProloGraalVariable(new HashMap<>(),"B"));
      return goals;
   }

   @Override
   public void enterQuery(ProloGraalParser.QueryContext ctx) {
      // when we enter a query, we need to create a new "context" for it.
      // Queries amd clauses are the "top-level" of Prolog.
      if(!parsingClauses) {
         queries.push(new ProloGraalQuery());
      }else{
         clauses.push(new ProloGraalClause(new HashMap<>()));
      }
   }

   //TODO: some prolog texts, could both be parsed into a clause (fact) or query having only one goal.
   //      this fix using the parsingClausesBoolean works for the moment,
   //      but could have side effects like
   //      could throw an exception if it parses a real clause (head + goalList) while parsing queries.

   @Override
   public void enterClause(ProloGraalParser.ClauseContext ctx) {
      // when we enter a clause, we need to create a new "context" for it.
      // Clauses and queries are the "top-level" of Prolog.
      if(parsingClauses) {
         clauses.push(new ProloGraalClause(new HashMap<>()));
      }else{
         queries.push(new ProloGraalQuery());
      }
   }

   @Override
   public void exitGoal(ProloGraalParser.GoalContext ctx) {
      // add the goal to the current clause
     addGoal(elements.pop());
   }

   @Override
   public void exitHead(ProloGraalParser.HeadContext ctx) {
      // set the head of the current clause
      if(parsingClauses){
         clauses.peek().setHead(elements.pop());
      }else{
         addGoal(elements.pop());
      }
   }

   @Override
   public void enterAtom(ProloGraalParser.AtomContext ctx) {
      // create an atom an push it to the element stack
      String atomName = ctx.getText();
      for (String atomDelimiter: atomDelimiters) {
         if(atomName.startsWith(atomDelimiter) && atomName.endsWith(atomDelimiter)){
            atomName = atomName.substring(1,atomName.length()-1);
            break;
         }
      }
      ProloGraalAtom atom = new ProloGraalAtom(getVariables(), atomName);
      elements.push(atom);
   }

   @Override
   public void exitAtom(ProloGraalParser.AtomContext ctx) {
      // if the atom represent a builtin clause, it will be built as it, else, the atom won't change.
      //System.out.println(elements.peek());
      buildBuiltin(elements.peek());
   }

   @Override
   public void enterNumber(ProloGraalParser.NumberContext ctx) {
      // create a number according to its type (Integer or Double) and push it to the element stack
      String n = ctx.getText();
      try {
         elements.push(new ProloGraalIntegerNumber(getVariables(), Integer.parseInt(n)));
      } catch (NumberFormatException ex) {
         elements.push(new ProloGraalDoubleNumber(getVariables(), Double.parseDouble(n)));
      }
   }

   @Override
   public void enterVariable(ProloGraalParser.VariableContext ctx) {
      // create a new variable
      ProloGraalVariable variable = new ProloGraalVariable(getVariables(), ctx.getText());
      // check if the current clause's variables list already contains a reference to this new variable (using its name to compare)
      if (getVariables().containsKey(variable)) {
         // if the variable already exists in the clause, we must not add a new one but use a reference instead
         elements.push(getVariables().get(variable));
      } else {
         // else we can just add the variable to the element stack
         elements.push(variable);
         // and we must directly add it to the current clause's variables list as well
         getVariables().put(variable, variable);
      }
   }

   @Override
   public void enterComposedTerm(ProloGraalParser.ComposedTermContext ctx) {
      // when we enter a composed term, we push a new structure immediately to "mark" its beginning
      elements.push(new ProloGraalStructure(getVariables()));
   }

   @Override
   public void exitComposedTerm(ProloGraalParser.ComposedTermContext ctx) {
      // when we exit a composed term, we need to pop from the element stack
      // until we reach the beginning of the structure, indicated by the structure itself

      // temporary list to store elements after we pop them
      List<ProloGraalTerm<?>> subterms = new ArrayList<>();
      while (true) {
         // save elements to the temporary list until we reach a structure
         // this operation also reverses the elements in the stack into the correct order
         while (!(elements.peek() instanceof ProloGraalStructure)) {
            subterms.add(elements.pop());
         }
         // since we can have nested structures, we need to check that we are at an empty one (nested ones would have
         // already been called earlier and wouldn't be empty)
         if (((ProloGraalStructure) elements.peek()).getArity() > 0) {
            // if it is a nested structure, add it to subterms like normal
            subterms.add(elements.pop());
         } else {
            // else we reached the "root" of this structure and we can break out
            break;
         }
      }

      // add elements to the structure
      ProloGraalStructure struct = (ProloGraalStructure) elements.peek();
      for (int i = subterms.size() - 1; i >= 0; i--) {
         struct.addSubterm(subterms.get(i));
      }
      // if the struct is a builtin, it will be built by buildBuiltin, else, the structure won't be changed.
      buildBuiltin(struct);
      // no need to do anything else since it was already in the element stack (we do not remove it here)
   }

   private boolean buildBuiltin(ProloGraalTerm term){
      // check if the current structure is a builtin, and initialize it as a builtin if it is one
      ProloGraalAtom functor = term instanceof ProloGraalStructure?((ProloGraalStructure)term).getFunctor():(ProloGraalAtom)term;
      ProloGraalStructure builtinStructure = null;
      if(ProloGraalBuiltinStructure.getBuiltinsInitializers().containsKey(functor.getName())){
         builtinStructure = ProloGraalBuiltinStructure.getBuiltinsInitializers().get(functor.getName()).apply(term);
      }else if(ProloGraalIsBuiltin.getOperations().containsKey(functor)){ //if the structure is an is/2 operator (by functor name), build it
         if(term.isStructure()) //we currently not allow use of constants for is/2 builtin (e, pi, tau), currently not working
            builtinStructure = ProloGraalBuiltinStructure.getBuiltinsInitializers().get("isOp").apply(term);
      }
      if(builtinStructure != null){
         elements.pop();
         elements.push(builtinStructure);
      }
      return builtinStructure != null;
   }

   @Override
   public void exitFunctor(ProloGraalParser.FunctorContext ctx) {
      try {
         // a functor is always an atom so we can just pop from the element stack
         ProloGraalAtom functor = (ProloGraalAtom) elements.pop();
         // it will also always be preceded by a structure so we can retrieve it
         ProloGraalStructure struct = (ProloGraalStructure) elements.peek();
         // and set its functor correctly
         struct.setFunctor(functor);
      } catch (ClassCastException ex) {
         throwParseError(ctx, "Invalid state : " + ex.getLocalizedMessage());
      }
   }

   @Override
   public void exitTail(ProloGraalParser.TailContext ctx) {
      // retrieve the tail which is the latest element in the stack
      // since we do this on exit, this will also work even if the tail is itself a list
      tail = elements.pop();
   }

   @Override
   public void enterList(ProloGraalParser.ListContext ctx) {
      // similar to structures, add a new list to the stack when we enter one
      elements.push(new ProloGraalList(getVariables()));
   }

   @Override
   public void exitList(ProloGraalParser.ListContext ctx) {
      // similar logic to exitComposedTerm, except that we need to handle the tail correctly
      List<ProloGraalTerm<?>> items = new ArrayList<>();

      while (true) {
         while (!(elements.peek() instanceof ProloGraalList)) {
            items.add(elements.pop());
         }
         if (((ProloGraalList) elements.peek()).size() > 0) { // element is a list already handled
            items.add(elements.pop());
         } else {
            break;
         }
      }

      ProloGraalList list = (ProloGraalList) elements.peek();
      for (int i = items.size() - 1; i >= 0; i--) {
         list.addItem(items.get(i));
      }
      // sets the tail if one was found
      if (tail != null) {
         list.setTail(tail);
         tail = null;
      }
      // and build the internal representation of the list
      list.buildInteralRepresentation();
   }

}