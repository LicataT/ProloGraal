package ch.heiafr.prolograal.parser;

import com.oracle.truffle.api.TruffleException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

/**
 * Class representing an error during the parsing process.
 * Contains information about the location of the parsing error.
 * @see ProloGraalListenerImpl
 * @see ProloGraalParserImpl
 * @author Martin Spoto
 */
public class ProloGraalParseError extends RuntimeException implements TruffleException {

    public static final long serialVersionUID = 1L;
    private final Source source;
    private final int line;
    private final int column;
    private final int length;

    public ProloGraalParseError(Source source, int line, int column, int length, String message) {
        super(message);
        this.source = source;
        this.line = line;
        this.column = column;
        this.length = length;
    }

    @Override
    public SourceSection getSourceLocation() {
        return source.createSection(line, column, length);
    }

    @Override
    public Node getLocation() {
        return null;
    }

    @Override
    public boolean isSyntaxError() {
        return true;
    }

}