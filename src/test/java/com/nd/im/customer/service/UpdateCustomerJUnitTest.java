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
public class UpdateCustomerJUnitTest extends AbstractImTest {

    public UpdateCustomerJUnitTest() {
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
        String result = this.testHandler.execute(ActionNames.UPDATE, parameterMap);
        System.out.println(result);
    }
}