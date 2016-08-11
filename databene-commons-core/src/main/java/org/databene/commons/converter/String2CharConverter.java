/*
 * (c) Copyright 2010-2012 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License (GPL).
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

package org.databene.commons.converter;

import org.databene.commons.ConversionException;

/**
 * Converts {@link String}s of length 1 to {@link Character}s, Strings of length 0 to <code>null</code>.<br/><br/>
 * Created: 27.02.2010 10:16:03
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class String2CharConverter extends ThreadSafeConverter<String, Character> {

	public String2CharConverter() {
	    super(String.class, Character.class);
    }

	@Override
	public Character convert(String sourceValue) throws ConversionException {
		if (sourceValue == null)
			return null;
    	switch (sourceValue.length()) {
			case 0 : return null; 
			case 1 : return Character.valueOf(sourceValue.charAt(0));
			default: throw new ConversionException("'" + sourceValue + "' cannot be converted to a character");
    	}
    }

}
