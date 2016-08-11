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

import org.databene.commons.iterator.BidirectionalIterator;

import java.util.List;

/**
 * A {@link BidirectionalIterator} for {@link List}s.<br/><br/>
 * Created: 08.05.2007 19:50:20
 * @author Volker Bergmann
 */
public class BidirectionalListIterator<E> implements BidirectionalIterator<E> {

    private List<E> list;
    private int index;

    public BidirectionalListIterator(List<E> list) {
        this.list = list;
        this.index = -1;
    }

    @Override
	public E first() {
        index = 0;
        return list.get(index);
    }

    @Override
	public boolean hasPrevious() {
        return (index > 0);
    }

    @Override
	public E previous() {
        if (!hasPrevious())
            throw new IllegalStateException("No previous object exists");
        index--;
        return list.get(index);
    }

    @Override
	public E last() {
        index = list.size() - 1;
        return list.get(index);
    }

    @Override
	public boolean hasNext() {
        return (index < list.size() - 1);
    }

    @Override
	public E next() {
        if (!hasNext())
            throw new IllegalStateException("No next object exists");
        index++;
        return list.get(index);
    }

    @Override
	public void remove() {
        throw new UnsupportedOperationException("Not implemented");
    }
    
}
