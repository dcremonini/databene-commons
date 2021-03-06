/*
 * (c) Copyright 2014 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Tests the {@link TreeBuilder}.<br/><br/>
 * Created: 17.02.2014 12:54:36
 * @since 0.5.26
 * @author Volker Bergmann
 */

public class TreeBuilderTest {
	
	private static final String FILE_PROPERTIES_FILENAME = "src/test/resources/org/databene/commons/file/propsInFile.properties";
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRecursiveConstruction() {
		// construct tree
		TreeBuilder tree = new TreeBuilder(true);
		tree.openGroupNode("r");
		tree.openGroupNode("a");
		tree.openGroupNode("b");
		tree.addLeafAtCurrentPath("b11", "b11v");
		tree.addLeafAtCurrentPath("b12", "b12v");
		tree.closeGroupNode();
		tree.openGroupNode("b");
		tree.addLeafAtCurrentPath("b21", "b21v1");
		tree.addLeafAtCurrentPath("b21", "b21v2");
		tree.closeGroupNode();
		tree.closeGroupNode();
		tree.closeGroupNode();
		// check basic navigation
		assertEquals("r", tree.getRootName());
		Map<String, Object> root = tree.getRootNode();
		assertEquals(1, root.size());
		Map<String, Object> aMap = (Map<String, Object>) root.get("a");
		// check group node list
		List<?> bs = (List<?>) aMap.get("b");
		{
			// check plain leaf nodes
			Map<String, Object> b1s = (Map<String, Object>) bs.get(0);
			assertEquals(2, b1s.size());
			assertEquals("b11v", b1s.get("b11"));
			assertEquals("b12v", b1s.get("b12"));
			assertNull(b1s.get("b21"));
		}
		{
			// check leaf node list
			Map<String, Object> b2s = (Map<String, Object>) bs.get(1);
			assertEquals(1, b2s.size());
			List<?> b2list = (List<?>) b2s.get("b21");
			assertEquals(2, b2list.size());
			assertEquals("b21v1", b2list.get(0));
			assertEquals("b21v2", b2list.get(1));
			assertNull(b2s.get("b12"));
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAbsoluteConstruction() {
		// construct tree
		TreeBuilder tree = new TreeBuilder(true);
		tree.addLeafAtAbsolutePath("a/b/b11", "b11v");
		tree.addLeafAtAbsolutePath("a/b/b12", "b12v");
		tree.addLeafAtAbsolutePath("a/c", "cv");
		// check basic navigation
		assertEquals("a", tree.getRootName());
		Map<String, Object> root = tree.getRootNode();
		assertEquals(2, root.size());
		{
			Map<String, Object> bMap = (Map<String, Object>) root.get("b");
			assertEquals("b11v", bMap.get("b11"));
			assertEquals("b12v", bMap.get("b12"));
			assertNull(bMap.get("b21"));
		}
		assertEquals("cv", root.get("c"));
	}

	@Test
	public void testGetNodeValue_namedRoot() {
		// construct tree
		TreeBuilder tree = new TreeBuilder(true);
		tree.addLeafAtAbsolutePath("root/some/property", "v1");
		tree.addLeafAtAbsolutePath("root/some_other/property", "v2");
		assertEquals("v1", tree.getNodeValue("root/some/property"));
		assertEquals("v2", tree.getNodeValue("root/some_other/property"));
	}
	
	@Test
	public void testGetNodeValue_unnamedRoot() {
		// construct tree
		TreeBuilder tree = new TreeBuilder(false);
		tree.addLeafAtAbsolutePath("some/property", "v1");
		tree.addLeafAtAbsolutePath("some_other/property", "v2");
		assertEquals("v1", tree.getNodeValue("some/property"));
		assertEquals("v2", tree.getNodeValue("some_other/property"));
	}
	
	@Test
	public void testParseSimpleXML() throws FileNotFoundException, IOException {
		TreeBuilder expected = new TreeBuilder(true);
		expected.addLeafAtAbsolutePath("root/emptyProp", "");
		expected.addLeafAtAbsolutePath("root/topProp", "topValue");
		expected.addLeafAtAbsolutePath("root/group/groupProp", "groupValue");
		TreeBuilder actual = TreeBuilder.parseXML(new FileInputStream("src/test/resources/org/databene/commons/xml/properties.xml"));
		assertEquals(expected, actual);
	}

	@Test
	public void testParseComplexXML() throws FileNotFoundException, IOException {
		TreeBuilder tree = TreeBuilder.parseXML(new FileInputStream("src/test/resources/org/databene/commons/xml/complexProps1.xml"));
		Object itemNodeValue = tree.getNodeValue("root/list/item");
		assertTrue(itemNodeValue instanceof List);
		assertEquals(2, ((List<?>) itemNodeValue).size());
	}
	
	@Test
	public void testParsePropertiesFile() throws FileNotFoundException, IOException {
		TreeBuilder expected = new TreeBuilder(false);
		expected.addLeafAtAbsolutePath("file/property", "loaded_from_file");
		expected.addLeafAtAbsolutePath("common/property", "loaded_from_file");
		TreeBuilder actual = TreeBuilder.parseProperties(new FileInputStream(FILE_PROPERTIES_FILENAME));
		assertEquals(expected, actual);
	}

}
