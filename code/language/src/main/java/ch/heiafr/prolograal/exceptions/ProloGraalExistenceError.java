package ch.heiafr.prolograal.exceptions;

import ch.heiafr.prolograal.runtime.ProloGraalTerm;
import com.oracle.truffle.api.nodes.ControlFlowException;

/**
 * Class representing an error caused by the non-existence of a clause.
 * Thrown when looking for possible clauses for the current goal, and finding nothing.
 * @see ch.heiafr.prolograal.nodes.ProloGraalProofTreeNode
 * @author Martin Spoto
 */
public class ProloGraalExistenceError extends ControlFlowException {

   // cannot use built-in because of inheritance from ControlFlowException
   private String message;

   public ProloGraalExistenceError(ProloGraalTerm<?> what) {
      message = what.toString();
   }

   @Override
   public String getMessage() {
      return message;
   }

   private static final long serialVersionUID = 0xd4cc78cfb9ef9000L;
}