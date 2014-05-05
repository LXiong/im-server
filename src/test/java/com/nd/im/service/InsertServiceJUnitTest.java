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
public class InsertServiceJUnitTest extends AbstractImTest {

    public InsertServiceJUnitTest() {
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
        this.setServiceSession("10000");
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("serviceId", "271411");
        parameterMap.put("serviceName", "jianying");
        parameterMap.put("type", "MEMBER");
        String result = this.testHandler.execute(ActionNames.INSERT_SERVICE, parameterMap);
        System.out.println(result);
    }
}