package jsb.com.notetaker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import jsb.com.notetaker.Activities.MainActivity;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class NoteTest {

	@Test
	public void addition_isCorrect() throws Exception {
        System.out.println("Adadad");
        assertEquals(4, 2 + 2);
	}

}