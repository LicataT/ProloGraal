package ch.heiafr.prolograal.exceptions;

import ch.heiafr.prolograal.runtime.ProloGraalTerm;
import com.oracle.truffle.api.nodes.ControlFlowException;

/**
 * Class representing an error when trying to get a typed value of a ProloGraalTerm that can't be this type.
 * @see ch.heiafr.prolograal.runtime.ProloGraalTerm
 * @author Tony Licata
 */
public class ProloGraalTypeCastingError extends ControlFlowException {

   // cannot use built-in because of inheritance from ControlFlowException
   private String term;
   private String type;

   public ProloGraalTypeCastingError(ProloGraalTerm<?> what, String type) {
      term = what.toString();
      this.type = type;
   }

   @Override
   public String getMessage() {
      return "Cannot cast "+term+" into "+type;
   }

   private static final long serialVersionUID = 0xd4cc78cfb9efA054L;
}