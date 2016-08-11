/*
 * (c) Copyright 2006-2012 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License.
 *
 * For redistributing this software or a derivative work under a license other
 * than the GPL-compatible Free Software License as defined by the Free
 * Software Foundation or approved by OSI, you must first obtain a commercial
 * license to this software product from Volker Bergmann.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.databene.commons;

/**
 * Supports iterating the characters of a String.
 * This is especially useful for writing parsers that iterate over Strings,
 * since it encapsulates the cursor index.<br/>
 * <br/>
 * Created: 18.08.2006 19:21:45
 * @author Volker Bergmann
 */
public class StringCharacterIterator implements CharacterIterator {

    /** The String to iterate */
    private String source;

    /** The cursor offset */
    private int offset;

    private int line;
    
    private int column;
    
    private int lastLineLength;
    
    // constructors ----------------------------------------------------------------------------------------------------

    /** Creates an iterator that starts at the String's beginning */
    public StringCharacterIterator(String source) {
        this(source, 0);
    }

    /** Creates an iterator that starts at a specified position */
    public StringCharacterIterator(String source, int offset) {
        if (source == null)
            throw new IllegalArgumentException("source string must not be null");
        this.source = source;
        this.offset = offset;
        this.line = 1;
        this.column = 1;
        this.lastLineLength = 1;
    }

    // java.util.Iterator interface ------------------------------------------------------------------------------------

    /**
     * Tells if the iterator has not reached the String's end.
     * java.util.Iterator#hasNext()
     * @return true if there are more characters available, false, if the end was reached.
     */
    @Override
	public boolean hasNext() {
        return offset < source.length();
    }

    /**
     * @see java.util.Iterator#next()
     * @return the next character.
     */
    @Override
	public char next() {
        if (offset >= source.length())
            throw new IllegalStateException("Reached the end of the string");
        if (source.charAt(offset) == '\n') {
        	lastLineLength = column;
        	line++;
        	column = 1;
        } else
        	column++;
        return source.charAt(offset++);
    }

    /**
     * Implements the remove() operation of the Iterator interface,
     * raising an UnsupportedOperationException.
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    // Convenience interface -------------------------------------------------------------------------------------------
    
	public char peekNext() {
		if (!hasNext())
			return 0;
        return source.charAt(offset);
	}

    /** Pushes back the cursor by one character. */
    public void pushBack() {
        if (offset > 0) {
            if (offset - 1 < source.length() && source.charAt(offset - 1) == '\n') {
            	line--;
            	column = lastLineLength;
            } else
                column--;
            offset--;
        } else
            throw new IllegalStateException("cannot pushBack before start of string: " + source);
    }

    /** @return the cursor offset. */
    public int getOffset() {
        return offset;
    }
    
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
    public void skipWhitespace() {
        while (offset < source.length() && Character.isWhitespace(source.charAt(offset)))
            offset++;
    }

    public String parseLetters() {
        StringBuilder builder = new StringBuilder();
        while (offset < source.length() && Character.isLetter(source.charAt(offset)))
            builder.append(source.charAt(offset++));
        return builder.toString();
    }

    public String remainingText() {
        return source.substring(offset);
    }

	public void assertNext(char c) {
		if (!hasNext())
			throw new ParseException("Expected '" + c + "', but no more character is available", source, line, column);
		char next = next();
		if (next != c)
			throw new ParseException("Expected '" + c + "', but found '" + next + "'", source, line, column);
	}
	
	public int line() {
		return line;
	}
	
	public int column() {
		return column;
	}

    // java.lang.Object overrides --------------------------------------------------------------------------------------

    /**
     * @return the String that is iterated.
     */
    @Override
	public String toString() {
        return source;
    }

}
