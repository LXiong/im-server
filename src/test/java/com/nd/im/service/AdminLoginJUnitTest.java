package com.nd.im.service;

import com.nd.im.AbstractImTest;
import com.nd.im.config.ActionNames;
import com.nd.im.localservice.ServiceLocalService;
import com.wolf.framework.utils.SecurityUtils;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aladdin
 */
public class AdminLoginJUnitTest extends AbstractImTest {

    public AdminLoginJUnitTest() {
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
        parameterMap.put("serviceId", "10000");
        parameterMap.put("password", SecurityUtils.encryptByMd5(ServiceLocalService.adminUserName));
        String result = this.testHandler.execute(ActionNames.ADMIN_LOGIN, parameterMap);
        System.out.println(result);
    }
}