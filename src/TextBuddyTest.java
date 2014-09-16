import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

public class TextBuddyTest {
	
	private static final String FILE_NAME = "mytextfile.txt";

	@BeforeClass
	public static void initialize() throws IOException {
		TextBuddy tb = new TextBuddy(FILE_NAME);
		tb.checkFileStatus();
	}

	@Test
	public void testSortEmpty() {
		assertEquals(FILE_NAME + " is empty", TextBuddy.sortText());
	}

}
