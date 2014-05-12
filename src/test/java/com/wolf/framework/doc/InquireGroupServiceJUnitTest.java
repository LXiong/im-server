package com.wolf.framework.doc;

import com.nd.im.AbstractImTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class InquireGroupServiceJUnitTest extends AbstractImTest {

    public InquireGroupServiceJUnitTest() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    //

    @Test
    public void test() {
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        String result = this.testHandler.execute("WOLF_INQUIRE_GROUP", parameterMap);
        System.out.println(result);
    }
}