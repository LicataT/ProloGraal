package ch.heiafr.prolograal;

import ch.heiafr.prolograal.nodes.ProloGraalEvalRootNode;
import ch.heiafr.prolograal.parser.ProloGraalParserImpl;
import ch.heiafr.prolograal.runtime.*;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLanguage.ContextPolicy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the Truffle language for Prolog, ProloGraal.
 * Most parts are adapted from the SimpleLanguage demo implementation.
 * @author Martin Spoto
 */
@TruffleLanguage.Registration(id = ProloGraalLanguage.ID, name = "Prolog", defaultMimeType =
        ProloGraalLanguage.MIME_TYPE, characterMimeTypes = ProloGraalLanguage.MIME_TYPE, contextPolicy =
        ContextPolicy.SHARED, fileTypeDetectors = ProloGraalFileDetector.class)
public class ProloGraalLanguage extends TruffleLanguage<ProloGraalContext> {
   public static volatile int counter;

   public static final String ID = "pl";
   public static final String MIME_TYPE = "application/x-prolog";

   // controls debug output for the whole application
   public static final boolean DEBUG_MODE = false;

   static {
      if (DEBUG_MODE) {
         System.setOut(new PrintStream(System.out) {
            @Override
            public void println(String x) {
               super.println("(DEBUG) " + x);
            }
         });
      }
   }

   public ProloGraalLanguage() {
      counter++;
   }

   @Override
   protected ProloGraalContext createContext(Env env) {
      return new ProloGraalContext(env);
   }

   /**
    * Parse the given request and starts the evaluation of the source file.
    * This method is called automatically by the launcher.
    * @param request the request to parse
    * @return a call target representing the "main" of the parsed file.
    */
   @Override
   protected CallTarget parse(ParsingRequest request) {
      Source source = request.getSource();
      List<ProloGraalQuery> queries = new ArrayList<>();
      ProloGraalContext context = this.getContextReference().get();

      //we set the context in the builtin structure, because it need it to initialize useinterpreter/0 builtin
      ProloGraalBuiltinStructure.setContext(context);

      if (request.getArgumentNames().isEmpty()) {
         long time = System.currentTimeMillis();
         queries = ProloGraalParserImpl.parseProloGraal(source);
         long time2 = System.currentTimeMillis();
         if (DEBUG_MODE) {
            System.out.println("Parsing time : " + (time2 - time) + "ms");
         }
      }

      ProloGraalRuntime runtime = new ProloGraalRuntime(context);

      RootNode eval = new ProloGraalEvalRootNode(this, runtime, queries);

      return Truffle.getRuntime().createCallTarget(eval);
   }

   // TODO find out what this function does
   @Override
   protected boolean isVisible(ProloGraalContext context, Object value) {
      return !InteropLibrary.getFactory().getUncached(value).isNull(value);
   }

   @Override
   protected boolean isObjectOfLanguage(Object object) {
      if (!(object instanceof TruffleObject)) {
         return false;
      }

      return object instanceof ProloGraalObject;
   }

   @Override
   protected String toString(ProloGraalContext context, Object value) {
      return toString(value);
   }

   public static String toString(Object value) {
      try {
         if (value == null) {
            return "ANY";
         } else if (value instanceof ProloGraalTerm<?> || value instanceof ProloGraalBoolean) {
            return value.toString();
         }
         InteropLibrary interop = InteropLibrary.getFactory().getUncached(value);
         if (interop.isString(value)) {
            return interop.asString(value);
         } else {
            return "Unsupported";
         }
      } catch (UnsupportedMessageException e) {
         CompilerDirectives.transferToInterpreter();
         throw new AssertionError();
      }
   }

   @Override
   protected Object findMetaObject(ProloGraalContext context, Object value) {
      return getMetaObject(value);
   }

   private static String getMetaObject(Object value) {
      if (value == null) {
         return "ANY";
      }
      InteropLibrary interop = InteropLibrary.getFactory().getUncached(value);
      if (interop.isString(value)) {
         return "String";
      } else {
         return "Unsupported";
      }
   }

   @Override
   protected SourceSection findSourceLocation(ProloGraalContext context, Object value) {
      // FIXME maybe ? seems not applicable for Prolog
      return null;
   }
}