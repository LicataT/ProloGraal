package ch.heiafr.prolograal.runtime;

/**
 * Abstract class representing a boolean in Prolog : either a success or a fail.
 * @see ProloGraalSuccess
 * @see ProloGraalFailure
 * @author Martin Spoto
 */
public abstract class ProloGraalBoolean extends ProloGraalObject {
   public abstract boolean asBoolean();
}