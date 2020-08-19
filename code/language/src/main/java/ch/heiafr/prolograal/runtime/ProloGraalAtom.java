package ch.heiafr.prolograal.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

import java.util.Map;

/**
 * Class representing a Prolog atom.
 * @author Martin Spoto
 */
@ExportLibrary(InteropLibrary.class)
public final class ProloGraalAtom extends ProloGraalTerm<ProloGraalAtom> {
   // the name of the atom
   private final String name;

   public ProloGraalAtom(Map<ProloGraalVariable, ProloGraalVariable> variables, String name) {
      super(variables);
      this.name = name;
      this.hashCode = name.hashCode();
   }

   @Override
   public String toString() {
      return name;
   }

   public String getName() {
      return name;
   }

   @Override
   public boolean equals(Object obj) {
      // two atoms are equal if they have the same name
      return obj instanceof ProloGraalAtom && this.name.equals(((ProloGraalAtom) obj).name);
   }

   /**
    * Unify this atom with the given term.<br>
    * An atom is unifiable to another atom having the same name or to a variable, given that the variable is either
    * currently unbound or bound to an unifiable atom.
    * @return True if this atom is unifiable with the other term
    */
   @Override
   public boolean unify(ProloGraalTerm<?> other) {
      if (this.equals(other)) {
         return true;
      } else if (other instanceof ProloGraalVariable) {
         // if other is a variable we delegate the unification process to the variable
         return other.unify(this);
      }
      return false;
   }

   @Override
   public ProloGraalAtom copy(Map<ProloGraalVariable, ProloGraalVariable> variables) {
      return new ProloGraalAtom(variables, this.name);
   }

   @Override
   public boolean isAtom() {
      return true;
   }

   @Override
   @ExportMessage
   public boolean isNumber() {
      return false;
   }

   @Override
   public boolean isVariable() {
      return false;
   }

   @Override
   public boolean isBoundVariable() {
      return false;
   }

   @Override
   public boolean isStructure(){
      return false;
   }

   @ExportMessage
   public boolean isString(){
      return true;
   }

   @ExportMessage
   public String asString() throws UnsupportedMessageException {
      if(isString()){
         return getName();
      }else{
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage boolean fitsInByte() { return false; }
   @ExportMessage boolean fitsInShort() { return false; }
   @ExportMessage boolean fitsInInt() { return false; }
   @ExportMessage boolean fitsInLong() { return false; }
   @ExportMessage boolean fitsInFloat() { return false; }
   @ExportMessage boolean fitsInDouble() { return false; }
   @ExportMessage byte asByte() throws UnsupportedMessageException { return (byte) 0; }
   @ExportMessage short asShort() throws UnsupportedMessageException { return (short) 0; }
   @ExportMessage int asInt() throws UnsupportedMessageException { return 0; }
   @ExportMessage long asLong() throws UnsupportedMessageException { return 0L; }
   @ExportMessage float asFloat() throws UnsupportedMessageException { return 0.0F; }
   @ExportMessage double asDouble() throws UnsupportedMessageException { return 0.0D; }
}