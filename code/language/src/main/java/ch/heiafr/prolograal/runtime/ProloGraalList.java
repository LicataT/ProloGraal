package ch.heiafr.prolograal.runtime;

import ch.heiafr.prolograal.builtins.ProloGraalBuiltinAtoms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class representing a Prolog list, extending a structure to provide additional functionalities.<br>
 * @implNote The current implementation is not very useful, but this class could be handy to provide performance
 * enhancement for built-in list operations.
 * @author Martin Spoto
 */
public class ProloGraalList extends ProloGraalStructure {

   // attributes for the list are distinct from the internal structure
   // to allow per use selection of the best internal data representation
   private List<ProloGraalTerm<?>> items;
   private ProloGraalTerm<?> tail = ProloGraalBuiltinAtoms.EMPTY_LIST;

   public ProloGraalList(Map<ProloGraalVariable, ProloGraalVariable> variables) {
      super(variables);
      items = new ArrayList<>();
   }

   public List<ProloGraalTerm<?>> getItems() {
      return items;
   }

   public ProloGraalTerm<?> getTail() {
      return tail;
   }

   /**
    * Creates a new list from the given structure, assuming it has the correct format.
    * @param internal The structure that serves as a base for a new list
    * @throws IllegalArgumentException if the given structure cannot be converted to a list
    * @return The new list with the elements of the given structure.
    */
   public static ProloGraalList fromInternal(ProloGraalStructure internal) throws IllegalArgumentException {
      ProloGraalList r = new ProloGraalList(null);

      while (true) {
         if (!internal.getFunctor().equals(ProloGraalBuiltinAtoms.DOT_OPERATOR) || internal.getArity() != 2) {
            throw new IllegalArgumentException("Cannot build list from internal representation");
         }
         ProloGraalTerm<?> head = internal.getArguments().get(0).getRootValue();
         ProloGraalTerm<?> tail = internal.getArguments().get(1).getRootValue();
         if (head instanceof ProloGraalStructure && ((ProloGraalStructure) head).getFunctor().equals(ProloGraalBuiltinAtoms.DOT_OPERATOR)) {
            r.items.add(ProloGraalList.fromInternal((ProloGraalStructure) head));
         } else {
            r.items.add(head);
         }
         if (!(tail instanceof ProloGraalStructure) || !((ProloGraalStructure) tail).getFunctor().equals(ProloGraalBuiltinAtoms.DOT_OPERATOR)) {
            r.tail = tail;
            break;
         } else {
            internal = (ProloGraalStructure) tail;
         }
      }

      return r;
   }

   public void addItem(ProloGraalTerm<?> item) {
      items.add(item);
   }

   public void setTail(ProloGraalTerm<?> tail) {
      this.tail = tail;
   }

   public int size() {
      return items.size();
   }

   /**
    * Creates the internal representation of this list, by populating the parent structure.
    */
   public void buildInteralRepresentation() {
      assert items != null && !items.isEmpty();

      ProloGraalStructure internal = this;
      ProloGraalStructure struct = internal;
      internal.setFunctor(ProloGraalBuiltinAtoms.DOT_OPERATOR);

      // for each item until the tail
      for (int i = 0; i < items.size() - 1; i++) {
         ProloGraalTerm<?> item = items.get(i);
         // add "left side" to the representation
         struct.addSubterm(item);
         // create "right side" of the representation
         ProloGraalStructure next = new ProloGraalStructure(variables);
         next.setFunctor(ProloGraalBuiltinAtoms.DOT_OPERATOR);
         // add "right side"
         struct.addSubterm(next);
         // switch to the new structure on the right side
         struct = next;
      }
      // add the tail
      ProloGraalTerm<?> item = items.get(items.size() - 1);
      struct.addSubterm(item);
      struct.addSubterm(this.tail);
   }

   @Override
   public ProloGraalList copy(Map<ProloGraalVariable, ProloGraalVariable> variables) {
      ProloGraalList list = new ProloGraalList(variables);
      for (ProloGraalTerm<?> item : this.items) {
         list.addItem(item.copy(variables));
      }
      list.tail = this.tail.copy(variables);
      list.buildInteralRepresentation();
      return list;
   }
}