package ch.heiafr.prolograal.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

import java.util.Map;

/**
 * Class representing a Prolog integer number. Unification is handled in the super class.
 * @author Martin Spoto
 */
@ExportLibrary(InteropLibrary.class)
public final class ProloGraalIntegerNumber extends ProloGraalNumber<ProloGraalIntegerNumber> {
   private final int value;

   public ProloGraalIntegerNumber(Map<ProloGraalVariable, ProloGraalVariable> variables, int value) {
      super(variables);
      this.value = value;
      this.hashCode = Integer.hashCode(value);
   }

   @Override
   public String toString() {
      return value + "";
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof ProloGraalIntegerNumber && this.value == ((ProloGraalIntegerNumber) obj).value;
   }

   @Override
   public ProloGraalIntegerNumber copy(Map<ProloGraalVariable, ProloGraalVariable> variables) {
      return new ProloGraalIntegerNumber(variables, this.value);
   }

   @Override
   public int asInteger(){
      return value;
   }

   @Override
   public boolean isInteger(){
      return true;
   }

   @Override
   @ExportMessage
   public boolean isNumber() {
      return true;
   }

   @Override
   @ExportMessage
   public double asDouble() {
      return (double) value;
   }

   @Override
   @ExportMessage
   public int asInt() {
      return value;
   }

   @Override
   @ExportMessage
   public long asLong() {
      return value;
   }

   @Override
   @ExportMessage
   public float asFloat() throws UnsupportedMessageException {
      if (fitsInFloat()) {
         return (float)value;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @Override
   @ExportMessage
   public byte asByte() throws UnsupportedMessageException {
      if (fitsInByte()) {
         return (byte)value;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @Override
   @ExportMessage
   public short asShort() throws UnsupportedMessageException {
      if (fitsInShort()) {
         return (short)value;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @Override
   @ExportMessage
   public boolean fitsInByte() {
      return value<=Byte.MAX_VALUE && value>=Byte.MIN_VALUE;
   }

   @Override
   @ExportMessage
   public boolean fitsInShort() {
      return value<=Short.MAX_VALUE && value>=Short.MIN_VALUE;
   }

   @Override
   @ExportMessage
   public boolean fitsInFloat() {
      return inSafeFloatRange(value);
   }

   @Override
   @ExportMessage
   public boolean fitsInLong() {
      return true;
   }

   @Override
   @ExportMessage
   public boolean fitsInInt() {
      return true;
   }

   @Override
   @ExportMessage
   public boolean fitsInDouble() {
      return true;
   }
}