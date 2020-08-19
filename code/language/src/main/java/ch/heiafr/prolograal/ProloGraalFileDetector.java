package ch.heiafr.prolograal;

import com.oracle.truffle.api.TruffleFile;

import java.nio.charset.Charset;

/**
 * Class implementing a detector for Prolog files.
 * @author Martin Spoto
 */
public final class ProloGraalFileDetector implements TruffleFile.FileTypeDetector {

   @Override
   public String findMimeType(TruffleFile file) {
      String name = file.getName();
      if (name != null && name.endsWith(".pl")) {
         return ProloGraalLanguage.MIME_TYPE;
      }
      return null;
   }

   @Override
   public Charset findEncoding(TruffleFile file) {
      return null;
   }
}