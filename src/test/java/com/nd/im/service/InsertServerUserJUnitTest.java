package com.nd.im.service;

import com.nd.im.AbstractImTest;
import com.nd.im.config.ActionNames;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class InsertServerUserJUnitTest extends AbstractImTest {

    public InsertServerUserJUnitTest() {
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
        parameterMap.put("userId", "271411");
        parameterMap.put("userName", "jianying");
        parameterMap.put("type", "MEMBER");
        String result = this.testHandler.execute(ActionNames.INSERT_SERVER_USER, parameterMap);
        System.out.println(result);
    }
}