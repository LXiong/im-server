package com.nd.im.customer.service;

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
public class SendMessageFromCustomerJUnitTest extends AbstractImTest {

    public SendMessageFromCustomerJUnitTest() {
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
        this.setCustomerSession("1158174740");
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("receiveId", "271411");
        parameterMap.put("message", "hello 271411");
        String result = this.testHandler.execute(ActionNames.SEND_MESSAGE_FROME_CUSTOMER, parameterMap);
        System.out.println(result);
    }
}