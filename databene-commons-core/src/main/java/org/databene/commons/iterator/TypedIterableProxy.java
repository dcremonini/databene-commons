/*
 * (c) Copyright 2007-2012 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.iterator;

import java.util.Iterator;

import org.databene.commons.HeavyweightIterator;
import org.databene.commons.HeavyweightTypedIterable;

/**
 * {@link Iterable} proxy that wraps an untyped Iterable and adds type information.<br/>
 * <br/>
 * Created: 02.09.2007 23:29:10
 * @author Volker Bergmann
 */
public class TypedIterableProxy<E> implements HeavyweightTypedIterable<E> {

    private Class<E> type;
    private Iterable<E> iterable;

    public TypedIterableProxy(Class<E> type, Iterable<E> iterable) {
        this.type = type;
        this.iterable = iterable;
    }

    @Override
	public Class<E> getType() {
        return type;
    }

    @Override
	public HeavyweightIterator<E> iterator() {
        Iterator<E> iterator = iterable.iterator();
    	return new HeavyweightIteratorProxy<E>(iterator);
    }
    
    @Override
    public String toString() {
    	return getClass().getSimpleName() + '[' + iterable + " -> " + type.getSimpleName() + ']';
    }
}
