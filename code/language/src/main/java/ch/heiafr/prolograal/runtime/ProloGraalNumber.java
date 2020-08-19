package ch.heiafr.prolograal.runtime;

import com.oracle.truffle.api.interop.UnsupportedMessageException;

import java.util.Map;

/**
 * Abstract class to represent Prolog numbers.
 * @param <T> Meant to be the type of the subclass itself,
 *            so that it can properly implement the {@link ProloGraalTerm#copy(Map)} method.
 * @see ProloGraalIntegerNumber
 * @see ProloGraalDoubleNumber
 * @author Martin Spoto
 */
public abstract class ProloGraalNumber<T extends ProloGraalTerm<T>> extends ProloGraalTerm<T> {
   protected ProloGraalNumber(Map<ProloGraalVariable, ProloGraalVariable> variables) {
      super(variables);
   }

   //Taken from SLBigNumber.java
   protected static final int INT_MAX_SAFE_FLOAT = 16777215;
   protected static boolean inSafeFloatRange(double d) {
      return d >= -INT_MAX_SAFE_FLOAT && d <= INT_MAX_SAFE_FLOAT;
   }


   public abstract boolean isInteger();

   public abstract int asInteger();

   public abstract boolean isNumber();

   public abstract double asDouble();

   public abstract int asInt() throws UnsupportedMessageException;

   public abstract float asFloat() throws UnsupportedMessageException;

   public abstract byte asByte() throws UnsupportedMessageException;

   public abstract short asShort() throws UnsupportedMessageException;

   public abstract long asLong() throws UnsupportedMessageException;

   public abstract boolean fitsInByte();

   public abstract boolean fitsInShort();

   public abstract boolean fitsInFloat();

   public abstract boolean fitsInDouble();

   public abstract boolean fitsInInt();

   public abstract boolean fitsInLong();


   @Override
   public boolean isAtom() {
      return true;
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
   public boolean equals(Object obj) {
      return obj instanceof ProloGraalNumber && ((ProloGraalNumber<?>) obj).asDouble() == this.asDouble();
   }

   @Override
   public boolean isStructure(){
      return false;
   }

   /**
    * Unify this number with another.
    * Assumes that the equals method is correctly redefined in child classes.
    */
   @Override
   public boolean unify(ProloGraalTerm<?> other) {
      if (other instanceof ProloGraalVariable) {
         return other.unify(this);
      } else {
         if(other instanceof ProloGraalNumber){
            return ((ProloGraalNumber)other).asDouble()==asDouble();
         }
         return this.equals(other);
      }
   }
}