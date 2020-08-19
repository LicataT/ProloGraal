package ch.heiafr.prolograal.runtime;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

/**
 * Class representing a failure during the resolution process.
 * @author Martin Spoto
 */
@ExportLibrary(InteropLibrary.class)
public class ProloGraalFailure extends ProloGraalBoolean {
   @Override
   public String toString() {
      return "no";
   }

   @ExportMessage
   public boolean isBoolean(){
      return true;
   }

   @Override
   @ExportMessage
   public boolean asBoolean() {
      return false;
   }
}