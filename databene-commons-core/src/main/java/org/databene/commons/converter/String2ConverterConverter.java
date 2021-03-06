/*
 * (c) Copyright 2008-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.converter;

import java.text.Format;

import org.databene.commons.BeanUtil;
import org.databene.commons.ConfigurationError;
import org.databene.commons.ConversionException;
import org.databene.commons.Converter;
import org.databene.commons.LoggerEscalator;
import org.databene.commons.StringUtil;

/**
 * Converts Strings to Converters and vice versa.<br/><br/>
 * Created: 15.03.2008 12:49:10
 * @since 0.4.0
 * @author Volker Bergmann
 * @deprecated The class is obsolete and will be removed soon
 */
@Deprecated
@SuppressWarnings({ "unchecked", "rawtypes" })
public class String2ConverterConverter extends ThreadSafeConverter<String, Converter> {
	
	private static LoggerEscalator escalator = new LoggerEscalator();

    public String2ConverterConverter() {
        super(String.class, Converter.class);
        escalator.escalate("Class is deprecated: " + getClass(), this, null);
    }

	@Override
	public Converter convert(String sourceValue) throws ConversionException {
        if (StringUtil.isEmpty(sourceValue))
            return null;
        Object result = BeanUtil.newInstance(sourceValue);
        if (result instanceof Format)
            return new ParseFormatConverter(Object.class, (Format) result, false);
        else if (result instanceof Converter)
            return (Converter) result;
        else
            throw new ConfigurationError("Class is neither Converter nor Format: " + result.getClass());
    }

}
