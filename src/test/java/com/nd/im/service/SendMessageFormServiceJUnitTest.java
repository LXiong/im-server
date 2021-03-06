package com.nd.im.service;

import com.nd.im.AbstractImTest;
import com.nd.im.config.ActionNames;
import com.wolf.framework.session.Session;
import com.wolf.framework.session.SessionImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class SendMessageFormServiceJUnitTest extends AbstractImTest {

    public SendMessageFormServiceJUnitTest() {
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
        Session session = new SessionImpl("1158174740");
        this.testHandler.setSession(session);
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("customerId", "1158174740");
        parameterMap.put("message", "hello 1158174740");
        String result = this.testHandler.execute(ActionNames.SEND_MESSAGE_FROM_SERVICE, parameterMap);
        System.out.println(result);
    }
}