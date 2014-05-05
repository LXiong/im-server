package com.nd.im.customer.service;

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
public class CustomerWaitServiceJUnitTest extends AbstractImTest {

    public CustomerWaitServiceJUnitTest() {
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
        String result = this.testHandler.execute(ActionNames.CUSTOMER_WAIT, parameterMap);
        System.out.println(result);
    }
}