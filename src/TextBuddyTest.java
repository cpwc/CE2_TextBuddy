import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * CS2103T (AY2014/15 Semester 1) CE2: TextBuddy++ Group: T17-3J
 * 
 * Unit test for TextBuddy++ Sort and Search functions using TDD method.
 * 
 * @author WeiCheng (A0111815R)
 *
 */
public class TextBuddyTest {

	private static final String FILE_NAME = "mytextfile.txt";

	@BeforeClass
	public static void initialize() throws IOException {
		TextBuddy tb = new TextBuddy(FILE_NAME);
		tb.checkFileStatus();
	}

	@Test
	public void testSortEmpty() {
		TextBuddy.clearText();

		List<String> list = TextBuddy.sortText();
		assertThat(list.isEmpty(), is(true));
	}

	@Test
	public void testSort1() {
		TextBuddy.clearText();

		TextBuddy.addText("cccc");
		TextBuddy.addText("aaaa");
		TextBuddy.addText("bbbb");

		List<String> list = TextBuddy.sortText();

		// Not empty.
		assertThat(list.isEmpty(), is(false));

		// Check sorted.
		assertEquals("[aaaa, bbbb, cccc]", list.toString());
	}

	@Test
	public void testSort2() {
		TextBuddy.clearText();

		TextBuddy.addText("CCCC");
		TextBuddy.addText("aaaa");
		TextBuddy.addText("xxxx");

		List<String> list = TextBuddy.sortText();

		// Not empty.
		assertThat(list.isEmpty(), is(false));

		// Check sorted.
		assertEquals("[aaaa, CCCC, xxxx]", list.toString());
	}

	@Test
	public void testSort3() {
		TextBuddy.clearText();

		TextBuddy.addText("CCCC");
		TextBuddy.addText("dsfbgtddd2");
		TextBuddy.addText("dfhvi341j12e32");

		List<String> list = TextBuddy.sortText();

		// Not empty.
		assertThat(list.isEmpty(), is(false));

		// Check sorted.
		assertEquals("[CCCC, dfhvi341j12e32, dsfbgtddd2]", list.toString());
	}

	@Test
	public void testSearchEmpty() {
		TextBuddy.clearText();

		List<String> list = TextBuddy.searchText("test");
		assertThat(list.isEmpty(), is(true));
	}

	@Test
	public void testSearch1() {
		TextBuddy.clearText();
		TextBuddy.addText("test");
		TextBuddy.addText("my case");
		TextBuddy.addText("your test case");
		TextBuddy.addText("their case test");

		List<String> list = TextBuddy.searchText("test");

		// Not empty.
		assertThat(list.isEmpty(), is(false));

		// Check search result.
		assertEquals("[test, your test case, their case test]", list.toString());
	}

	@Test
	public void testSearch2() {
		TextBuddy.clearText();
		TextBuddy.addText("test");
		TextBuddy.addText("my case");
		TextBuddy.addText("your TEST case");
		TextBuddy.addText("their case test");

		List<String> list = TextBuddy.searchText("test");

		// Not empty.
		assertThat(list.isEmpty(), is(false));

		// Check search result.
		assertEquals("[test, your TEST case, their case test]", list.toString());
	}

}
