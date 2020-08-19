package ch.heiafr.prolograal.runtime;

import ch.heiafr.prolograal.exceptions.ProloGraalTypeCastingError;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

import java.util.Map;

/**
 * Class representing a Prolog floating point number. Unification is handled in the super class.
 * @author Martin Spoto
 */
@ExportLibrary(InteropLibrary.class)
public final class ProloGraalDoubleNumber extends ProloGraalNumber<ProloGraalDoubleNumber> {
   private final double value;

   public ProloGraalDoubleNumber(Map<ProloGraalVariable, ProloGraalVariable> variables, double value) {
      super(variables);
      this.value = value;
      this.hashCode = Double.hashCode(value);
   }

   @Override
   public String toString() {
      return value + "";
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof ProloGraalDoubleNumber && this.value == ((ProloGraalDoubleNumber) obj).value;
   }

   @Override
   public ProloGraalDoubleNumber copy(Map<ProloGraalVariable, ProloGraalVariable> variables) {
      return new ProloGraalDoubleNumber(variables, this.value);
   }

   @Override
   public int asInteger(){
      if(isInteger())
         return (int)value;
      throw new ProloGraalTypeCastingError(this, "integer");
   }

   @Override
   public boolean isInteger(){
      return (value*10%10==0 && fitsInInt());
   }

   @Override
   @ExportMessage
   public boolean isNumber() {
      return true;
   }

   @Override
   @ExportMessage
   public double asDouble(){
      return value;
   }

   @Override
   @ExportMessage
   public int asInt() throws UnsupportedMessageException {
      if (fitsInInt()) {
         return (int) value;
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @Override
   @ExportMessage
   public long asLong() {
      return (long)value;
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
      return value<=Integer.MAX_VALUE && value>=Integer.MIN_VALUE;
   }

   @Override
   @ExportMessage
   public boolean fitsInDouble() {
      return true;
   }
}