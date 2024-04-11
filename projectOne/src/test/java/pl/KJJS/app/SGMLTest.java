package pl.KJJS.app;

import junit.framework.TestCase;
import pl.KJJS.app.parser.SGML;

public class SGMLTest extends TestCase {

    public void testParseToken() {
        assertEquals("hello", SGML.parseToken("<h>hello</h>","h").token);
    }
    public void testParseTokenFAIL() {
        assertNull(SGML.parseToken("<s>hello</s>", "h").token);
    }


    public void testMany() {
        String [] expected = {"hi","hello"};
        String [] result = SGML.many("<D>hi</D><D>hello</D>","D").token;
        System.out.println(result[0]);
        for (int i=0;i<expected.length;i++)
            assertEquals(expected[i],result[i]);
    }
    public void testManyFAIL() {
        String [] result = SGML.many("<S>hi</S><S>hello</S>","D").token;
        assertEquals(0, result.length);
    }

    public void testCombine(){
        String [] expected = {"hi","hello"};
        String [] result = SGML.sequence("<D>hi</D><T>hello</T>",new String[]{"D", "T"}).token;
        for (int i=0;i<expected.length;i++)
            assertEquals(expected[i],result[i]);
    }
    public void testCombineFAIL(){
        String [] result = SGML.sequence("<T>hi</T><T>hello</T>",new String[]{"D", "T"}).token;
        assertNull(result);
        result = SGML.sequence("<D>hi</D><D>hello</D>",new String[]{"D", "T"}).token;
        assertNull(result);
    }

}