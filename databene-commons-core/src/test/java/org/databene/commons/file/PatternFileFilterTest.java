/*
 * (c) Copyright 2010 by Volker Bergmann. All rights reserved.
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

package org.databene.commons.file;

import static org.junit.Assert.*;

import java.io.File;

import org.databene.commons.FileUtil;
import org.databene.commons.IOUtil;
import org.junit.Test;

/**
 * Tests the {@link PatternFileFilter}.<br/><br/>
 * Created: 26.02.2010 10:05:08
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class PatternFileFilterTest {
	
	File targetDir = new File("target");
	File targetFile = new File("target", getClass().getSimpleName() + ".txt");

	@Test
	public void testPattern() {
		assertTrue(new PatternFileFilter(null, true, true).accept(targetDir));
		assertTrue(new PatternFileFilter("t.*", true, true).accept(targetDir));
		assertFalse(new PatternFileFilter("x.*", true, true).accept(targetDir));
	}

	@Test
	public void testAcceptFolders() {
		assertTrue(new PatternFileFilter(null, false, true).accept(targetDir));
		assertFalse(new PatternFileFilter(null, true, false).accept(targetDir));
	}

	@Test
	public void testAcceptFiles() throws Exception {
		IOUtil.writeTextFile(targetFile.getAbsolutePath(), "test");
		try {
			assertFalse(new PatternFileFilter(null, false, true).accept(targetFile));
			assertTrue(new PatternFileFilter(null, true, false).accept(targetFile));
		} finally {
			FileUtil.deleteIfExists(targetFile);
		}
	}

}
