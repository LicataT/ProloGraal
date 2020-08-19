package ch.heiafr.prolograal;

import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeCheck;
import com.oracle.truffle.api.dsl.TypeSystem;

/**
 * Class defining the accepted types in the truffle DSL as NodeChild.
 * @author Tony Licata
 */
@TypeSystem({int.class, double.class, boolean.class})
public abstract class ProloGraalTypes {
    @TypeCheck(ProloGraalTerm.class)
    public static boolean isProloGraalTerm(Object value) {
        return value instanceof ProloGraalTerm;
    }

    @TypeCheck(ProloGraalBoolean.class)
    public static boolean isProloGraalBoolean(Object value) {
        return value instanceof ProloGraalBoolean;
    }

    @TypeCheck(ProloGraalObject.class)
    public static boolean isProloGraalObject(Object value) {
        return value instanceof ProloGraalObject;
    }


    /**
     * implicit Truffle casts, currently not working
     * taken from: https://github.com/graalvm/simplelanguage/blob/master/language/src/main/java/com/oracle/truffle/sl/nodes/SLTypes.java
     */

    /*
    @ImplicitCast
    public static ProloGraalTerm castVarToTerm(ProloGraalVariable value) {
        return value;
    }

    @ImplicitCast
    public static ProloGraalTerm castAtomToTerm(ProloGraalAtom value) {
        return value;
    }


    @ImplicitCast
    public static ProloGraalTerm castNumberToTerm(ProloGraalNumber value) {
        return value;
    }

    @ImplicitCast
    public static ProloGraalTerm castStructureToTerm(ProloGraalStructure value) {
        return value;
    }
    */
}
