package ch.heiafr.prolograal.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a list of ProloGraalBoolean, used to collect ProloGraal call answers.
 * @author Tony Licata
 */
@ExportLibrary(InteropLibrary.class)
public class ProloGraalBooleanList extends ProloGraalObject {
   private final List<ProloGraalBoolean> booleanList;

   public ProloGraalBooleanList(List<ProloGraalBoolean> booleanList){
      this.booleanList = new ArrayList<>(booleanList);
   }

   @ExportMessage
   public boolean hasArrayElements(){
      return !booleanList.isEmpty();
   }

   @ExportMessage
   public Object readArrayElement(long index) throws UnsupportedMessageException, InvalidArrayIndexException{
       if(hasArrayElements()){
          if(isArrayElementReadable(index)){
             return booleanList.get((int)index);
          }else{
             throw InvalidArrayIndexException.create(index);
          }
       }else{
          throw UnsupportedMessageException.create();
       }
   }

   @ExportMessage
   public long getArraySize(){
      return booleanList.size();
   }

   @ExportMessage
   public boolean isArrayElementReadable(long index){
      return index<booleanList.size();
   }

   @ExportMessage
   public void writeArrayElement(long index, Object value) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException{
      if (hasArrayElements()) {
         if(value instanceof ProloGraalBoolean){
            if(isArrayElementReadable(index)){
               booleanList.set((int)index, (ProloGraalBoolean) value);
            }else if(isArrayElementInsertable(index)){
               booleanList.add((ProloGraalBoolean) value);
            }else{
               throw InvalidArrayIndexException.create(index);
            }
         }else{
            Object[] valueArray = {value};
            throw UnsupportedTypeException.create(valueArray,"Sent object if of type "+value.getClass().getCanonicalName()+" but should be ProloGraalBoolean");
         }
      }else{
         throw UnsupportedMessageException.create();
      }

   }

   @ExportMessage
   public void removeArrayElement(long index) throws UnsupportedMessageException, InvalidArrayIndexException {
      if(hasArrayElements()){
         if(isArrayElementReadable(index)){
            booleanList.remove(index);
         }else{
            throw InvalidArrayIndexException.create(index);
         }
      }else{
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   public boolean isArrayElementModifiable(long index){
      return isArrayElementReadable(index);
   }

   @ExportMessage
   public boolean isArrayElementInsertable(long index){
      return index==booleanList.size();
   }

   @ExportMessage
   public boolean isArrayElementRemovable(long index){
      return isArrayElementReadable(index);
   }
}