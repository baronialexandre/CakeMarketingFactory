package l3info.projet.cakemarketingfactory;

import org.junit.Test;

import l3info.projet.cakemarketingfactory.utils.FunctionUtil;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void cashShortner_isCorrect() {
        assertEquals(FunctionUtil.scoreShorten(-158156158L), "---");
        assertEquals(FunctionUtil.scoreShorten(0L), "0");
        assertEquals(FunctionUtil.scoreShorten(125L), "125");
        assertEquals(FunctionUtil.scoreShorten(999L), "999");
        assertEquals(FunctionUtil.scoreShorten(9999L), "9K");
        assertEquals(FunctionUtil.scoreShorten(9999999L), "9M");
        assertEquals(FunctionUtil.scoreShorten(9999999999L), "9B");
        assertEquals(FunctionUtil.scoreShorten(9999999999999L), "9T");
        assertEquals(FunctionUtil.scoreShorten(9999999999999999L), "9+");
        assertEquals(FunctionUtil.scoreShorten(999999999999999999L), "999+");
        assertEquals(FunctionUtil.scoreShorten(1999999999999999999L), "+++");
    }
}