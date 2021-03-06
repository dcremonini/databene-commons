/*
 * (c) Copyright 2007-2014 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.depend;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the DependencyModel class.
 * @author Volker Bergmann
 * @since 0.3.04
 */
public class DependencyModelTest {
    
    /**
     * <pre>
     *   a - b - c
     * </pre>
     */
	@Test
    public void testLineDependencies() {
        Dep zero = new Dep("0");
        Dep a = new Dep("a");
        Dep b = new Dep("b", a);
        Dep c = new Dep("c", b);
        expectOrder(c, b, a, zero, 
                zero, a, b, c);
    }

    /**
     * <pre>
     *   a   d
     *    \ / \
     *     c   f
     *    / \ /
     *   b   e
     * </pre>
     */
	@Test
    public void testNetDependencies() {
        // create nodes
        Dep a = new Dep("a");
        Dep b = new Dep("b");
        Dep c = new Dep("c", a, b);
        Dep d = new Dep("d", c);
        Dep e = new Dep("e", c);
        Dep f = new Dep("f", d, e);
        // build model
        DependencyModel<Dep> model = new DependencyModel<Dep>();
        model.addNode(f);
        model.addNode(e);
        model.addNode(d);
        model.addNode(c);
        model.addNode(b);
        model.addNode(a);
        // check
        List<Dep> oo = model.dependencyOrderedObjects(false);
        assertTrue((oo.get(0) == a && oo.get(1) == b) || (oo.get(0) == b && oo.get(1) == a));
        assertEquals(c, oo.get(2));
        assertTrue((oo.get(3) == d && oo.get(4) == e) || (oo.get(3) == e && oo.get(4) == d));
        assertEquals(f, oo.get(5));
    }

	@Test
    public void testOptionalCycle() {
        Dep a = new Dep("a");
        Dep b = new Dep("b");
        b.addRequiredProvider(a);
        a.addOptionalProvider(b);
        expectOrder(true, b, 
                a, a, b);
    }

	@Test(expected = CyclicDependencyException.class)
    public void testUnacceptedCycle() {
        Dep a = new Dep("a");
        Dep b = new Dep("b", a);
        a.addRequiredProvider(b);
        expectOrder(b, a, a, b);
    }

	@Test
    public void testAcceptedSelfCycle() {
        Dep a = new Dep("a");
        a.addRequiredProvider(a);
        expectOrder(true, a, a);
    }
   
	@Test
    public void testAcceptedCycle2() {
        DependencyModel<Dep> model = new DependencyModel<Dep>();
        Dep a = new Dep("a");
        Dep b = new Dep("b", a);
        a.addRequiredProvider(b);
        model.addNode(b);
        model.addNode(a);
        List<Dep> oo = model.dependencyOrderedObjects(true);
        assertTrue((oo.get(0) == a && oo.get(1) == b) || (oo.get(0) == b && oo.get(1) == a));
    }

	@Test
    public void testAcceptedCycle3() {
        Dep zero = new Dep("0");
        Dep a = new Dep("a", zero);
        Dep b = new Dep("b", a);
        Dep c = new Dep("c", b);
        a.addRequiredProvider(c);
        expectOrder(true, c, b, a, zero, 
                zero, a, b, c);
    }

//    @Ignore(value = "I thing it never worked, ask to Volker Bergmann")
	@Test
    public void testIssue() {
        Dep actReProcDef = new Dep("act_re_procdef");
        Dep actRuExecution = new Dep("act_ru_execution");
        actRuExecution.addRequiredProvider(actRuExecution);
        Dep actRuTask = new Dep("act_ru_task");
        actRuTask.addRequiredProvider(actReProcDef);
        actRuTask.addRequiredProvider(actRuExecution);
        actRuTask.addRequiredProvider(actRuExecution);
        Dep actRuIdentityLink = new Dep("act_ru_identitylink");
        actRuIdentityLink.addRequiredProvider(actReProcDef);
        actRuIdentityLink.addRequiredProvider(actRuTask);
        expectOrder(true, actRuIdentityLink, actRuTask, actRuExecution, actReProcDef,
                actReProcDef, actRuExecution, actRuTask, actRuIdentityLink);
    }
    
    // private helper -------------------------------------------------------------------------------
    
    private static void expectOrder(Dep... nodes) {
        expectOrder(false, nodes);
    }

    private static void expectOrder(boolean acceptingCycles, Dep... nodes) {
        DependencyModel<Dep> model = new DependencyModel<Dep>();
        for (int i = 0; i < nodes.length / 2; i++)
            model.addNode(nodes[i]);
        List<Dep> oo = model.dependencyOrderedObjects(acceptingCycles);
        for (int i = nodes.length / 2; i < nodes.length; i++)
            assertEquals(nodes[i], oo.get(i - nodes.length / 2));
    }
    
}
