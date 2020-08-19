package ch.heiafr.prolograal.builtins;

import ch.heiafr.prolograal.runtime.ProloGraalAtom;

/**
 * Class containing the different built-in atoms.
 * Unlike built-in predicates, these are meant for internal use.
 * @author Martin Spoto
 * @see ProloGraalAtom
 */
public final class ProloGraalBuiltinAtoms {
   public static final ProloGraalAtom EMPTY_LIST = new ProloGraalAtom(null, "[]");
   public static final ProloGraalAtom DOT_OPERATOR = new ProloGraalAtom(null, ".");
}