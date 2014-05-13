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
public class InquireServiceJUnitTest extends AbstractImTest {

    public InquireServiceJUnitTest() {
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
        parameterMap.put("groupName", "客户接口");
        String result = this.testHandler.execute("WOLF_INQUIRE_SERVICE", parameterMap);
        System.out.println(result);
    }
}