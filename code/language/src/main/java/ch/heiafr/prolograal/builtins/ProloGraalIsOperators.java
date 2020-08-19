package ch.heiafr.prolograal.builtins;

import ch.heiafr.prolograal.runtime.ProloGraalAtom;
import ch.heiafr.prolograal.runtime.ProloGraalDoubleNumber;
import ch.heiafr.prolograal.runtime.ProloGraalIntegerNumber;
import ch.heiafr.prolograal.runtime.ProloGraalNumber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Class defining and initialing is/2 operators
 * @author Tony Licata
 * @see ch.heiafr.prolograal.builtins.predicates.ProloGraalIsBuiltin
 */
public final class ProloGraalIsOperators {
   /* is/2 operators */
   public static ProloGraalNumber pi(List<ProloGraalNumber> args) {
      if (!args.isEmpty()) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.PI);
   }

   public static ProloGraalNumber e(List<ProloGraalNumber> args) {
      if (!args.isEmpty()) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.E);
   }

   public static ProloGraalNumber tau(List<ProloGraalNumber> args) {
      if (!args.isEmpty()) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.PI * 2);
   }

   public static ProloGraalNumber add(List<ProloGraalNumber> args) {
      if (args.size() != 2) return null;
      ProloGraalNumber arg0 = args.get(0), arg1 = args.get(1);
      if (areAllIntegers(arg0, arg1))
         return new ProloGraalIntegerNumber(new HashMap<>(), arg0.asInteger() + arg1.asInteger());
      return new ProloGraalDoubleNumber(new HashMap<>(), arg0.asDouble() + arg1.asDouble());
   }

   public static ProloGraalNumber substract(List<ProloGraalNumber> args) {
      if (args.size() != 2) return null;
      ProloGraalNumber arg0 = args.get(0), arg1 = args.get(1);
      if (areAllIntegers(arg0, arg1))
         return new ProloGraalIntegerNumber(new HashMap<>(), arg0.asInteger() - arg1.asInteger());
      return new ProloGraalDoubleNumber(new HashMap<>(), arg0.asDouble() - arg1.asDouble());
   }

   public static ProloGraalNumber multiply(List<ProloGraalNumber> args) {
      if (args.size() != 2) return null;
      ProloGraalNumber arg0 = args.get(0), arg1 = args.get(1);
      if (areAllIntegers(arg0, arg1))
         return new ProloGraalIntegerNumber(new HashMap<>(), arg0.asInteger() * arg1.asInteger());
      return new ProloGraalDoubleNumber(new HashMap<>(), arg0.asDouble() * arg1.asDouble());
   }

   public static ProloGraalNumber divide(List<ProloGraalNumber> args) {
      if (args.size() != 2) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), args.get(0).asDouble() / args.get(1).asDouble());
   }

   public static ProloGraalNumber pow(List<ProloGraalNumber> args) {
      if (args.size() != 2) return null;
      ProloGraalNumber arg0 = args.get(0), arg1 = args.get(1);
      if (areAllIntegers(arg0, arg1) && Math.abs(Math.pow(arg0.asInteger(), arg1.asInteger())) < Integer.MAX_VALUE)
         return new ProloGraalIntegerNumber(new HashMap<>(), (int) Math.pow(arg0.asInteger(), arg1.asInteger()));
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.pow(arg0.asDouble(), arg1.asDouble()));
   }

   public static ProloGraalNumber sqrt(List<ProloGraalNumber> args) {
      if (args.size() != 1) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.sqrt(args.get(0).asDouble()));
   }

   public static ProloGraalNumber rem(List<ProloGraalNumber> args) {
      if (args.size() != 2) return null;
      ProloGraalNumber arg0 = args.get(0), arg1 = args.get(1);
      if (areAllIntegers(arg0, arg1))
         return new ProloGraalIntegerNumber(new HashMap<>(), arg0.asInteger() % arg1.asInteger());
      return new ProloGraalDoubleNumber(new HashMap<>(), arg0.asDouble() % arg1.asDouble());
   }

   public static ProloGraalNumber mod(List<ProloGraalNumber> args) {
      if (args.size() != 2) return null;
      ProloGraalNumber arg0 = args.get(0), arg1 = args.get(1);
      double ad = arg0.asDouble();
      double bd = arg1.asDouble();
      double res = ((ad % bd + bd) % bd);
      if (areAllIntegers(arg0, arg1))
         return new ProloGraalIntegerNumber(new HashMap<>(), (int) res);
      return new ProloGraalDoubleNumber(new HashMap<>(), res);
   }

   public static ProloGraalNumber sign(List<ProloGraalNumber> args) {
      if (args.size() != 1) return null;
      double val = args.get(0).asDouble();
      int res = 0;
      if (val > 0) res = 1;
      if (val < 0) res = -1;
      return new ProloGraalIntegerNumber(new HashMap<>(), res);
   }

   public static ProloGraalNumber exp(List<ProloGraalNumber> args) {
      if (args.size() != 1) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.exp(args.get(0).asDouble()));
   }

   public static ProloGraalNumber log(List<ProloGraalNumber> args) {
      if (args.size() != 1) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.log(args.get(0).asDouble()));
   }

   public static ProloGraalNumber sin(List<ProloGraalNumber> args) {
      if (args.size() != 1) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.sin(args.get(0).asDouble()));
   }

   public static ProloGraalNumber cos(List<ProloGraalNumber> args) {
      if (args.size() != 1) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.cos(args.get(0).asDouble()));
   }

   public static ProloGraalNumber tan(List<ProloGraalNumber> args) {
      if (args.size() != 1) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.tan(args.get(0).asDouble()));
   }

   public static ProloGraalNumber asin(List<ProloGraalNumber> args) {
      if (args.size() != 1) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.asin(args.get(0).asDouble()));
   }

   public static ProloGraalNumber acos(List<ProloGraalNumber> args) {
      if (args.size() != 1) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.acos(args.get(0).asDouble()));
   }

   public static ProloGraalNumber atan(List<ProloGraalNumber> args) {
      if (args.size() != 1) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.atan(args.get(0).asDouble()));
   }

   public static ProloGraalNumber atan2(List<ProloGraalNumber> args) {
      if (args.size() != 2) return null;
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.atan2(args.get(0).asDouble(), args.get(1).asDouble()));
   }

   public static ProloGraalNumber abs(List<ProloGraalNumber> args) {
      if (args.size() != 1) return null;
      ProloGraalNumber arg0 = args.get(0);
      if (areAllIntegers(arg0))
         return new ProloGraalIntegerNumber(new HashMap<>(), Math.abs(arg0.asInteger()));
      return new ProloGraalDoubleNumber(new HashMap<>(), Math.abs(arg0.asDouble()));
   }

   private static boolean areAllIntegers(ProloGraalNumber<?>... args){
      for (ProloGraalNumber number : args)
         if(!number.isInteger())return false;
      return true;
   }

   public static Map<ProloGraalAtom, Function<List<ProloGraalNumber>,ProloGraalNumber>> getOperationsMap(){
      Map<ProloGraalAtom, Function<List<ProloGraalNumber>,ProloGraalNumber>> operationsMap = new HashMap<>();
      addOpperation(operationsMap,"pi", ProloGraalIsOperators::pi);
      addOpperation(operationsMap,"e",ProloGraalIsOperators::e);
      addOpperation(operationsMap,"tau",ProloGraalIsOperators::tau);
      addOpperation(operationsMap,"+",ProloGraalIsOperators::add);
      addOpperation(operationsMap,"-",ProloGraalIsOperators::substract);
      addOpperation(operationsMap,"*",ProloGraalIsOperators::multiply);
      addOpperation(operationsMap,"/",ProloGraalIsOperators::divide);
      addOpperation(operationsMap,"**",ProloGraalIsOperators::pow);
      addOpperation(operationsMap,"^",ProloGraalIsOperators::pow);
      addOpperation(operationsMap,"sqrt",ProloGraalIsOperators::sqrt);
      addOpperation(operationsMap,"rem",ProloGraalIsOperators::rem);
      addOpperation(operationsMap,"mod",ProloGraalIsOperators::mod);
      addOpperation(operationsMap,"sign",ProloGraalIsOperators::sign);
      addOpperation(operationsMap,"exp",ProloGraalIsOperators::exp);
      addOpperation(operationsMap,"log",ProloGraalIsOperators::log);
      addOpperation(operationsMap,"sin",ProloGraalIsOperators::sin);
      addOpperation(operationsMap,"cos",ProloGraalIsOperators::cos);
      addOpperation(operationsMap,"tan",ProloGraalIsOperators::tan);
      addOpperation(operationsMap,"asin",ProloGraalIsOperators::asin);
      addOpperation(operationsMap,"acos",ProloGraalIsOperators::acos);
      addOpperation(operationsMap,"atan",ProloGraalIsOperators::atan);
      addOpperation(operationsMap,"atan2",ProloGraalIsOperators::atan2);
      addOpperation(operationsMap,"abs",ProloGraalIsOperators::abs);
      return operationsMap;
   }

   private static void addOpperation(Map<ProloGraalAtom, Function<List<ProloGraalNumber>,ProloGraalNumber>> operations,
                                     String atomName,
                                     Function<List<ProloGraalNumber>,ProloGraalNumber> function){
      operations.put(new ProloGraalAtom(new HashMap<>(),atomName), function);
   }
}