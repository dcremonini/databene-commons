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

import org.databene.commons.ArrayFormat;
import org.databene.commons.ArrayUtil;
import org.databene.commons.ConversionException;
import org.databene.commons.Converter;

/**
 * Converts a {@link String} with a comma-separated list to an array.<br/><br/>
 * Created: 27.02.2010 12:13:49
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class CommaSeparatedListConverter<T> extends ConverterWrapper<String, T> implements Converter<String, Object> {

	private Class<T> targetComponentType;
	private Class<T[]> targetType;

	@SuppressWarnings("unchecked")
    protected CommaSeparatedListConverter(Class<T> targetComponentType) {
	    super(ConverterManager.getInstance().createConverter(String.class, targetComponentType));
	    this.targetComponentType = targetComponentType;
	    this.targetType = ArrayUtil.arrayType(targetComponentType);
    }

	@Override
	public Object convert(String sourceValue) throws ConversionException {
		return ConverterManager.convertAll(ArrayFormat.parse(sourceValue, ",", String.class), realConverter, targetComponentType);
    }

	@Override
	public Class<String> getSourceType() {
	    return String.class;
    }

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
    public Class<Object> getTargetType() {
	    return (Class) targetType;
    }

}
