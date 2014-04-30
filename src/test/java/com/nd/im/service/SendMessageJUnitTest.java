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
public class SendMessageJUnitTest extends AbstractImTest {

    public SendMessageJUnitTest() {
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
        parameterMap.put("receiveId", "271411");
        parameterMap.put("message", "hello");
        String result = this.testHandler.execute(ActionNames.SEND_MESSAGE, parameterMap);
        System.out.println(result);
    }
}