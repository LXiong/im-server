package com.nd.im;

import com.nd.im.utils.SessionUtils;
import com.wolf.framework.config.FrameworkConfig;
import com.wolf.framework.context.ApplicationContext;
import com.wolf.framework.session.Session;
import com.wolf.framework.session.SessionImpl;
import com.wolf.framework.test.TestHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;

/**
 *
 * @author aladdin
 */
public abstract class AbstractImTest {

    protected final TestHandler testHandler;

    public AbstractImTest() {
        Map<String, String> parameterMap = new HashMap<String, String>(8, 1);
        parameterMap.put(FrameworkConfig.ANNOTATION_SCAN_PACKAGES, "com.nd.im");
        parameterMap.put(FrameworkConfig.TASK_CORE_POOL_SIZE, "1");
        parameterMap.put(FrameworkConfig.TASK_MAX_POOL_SIZE, "2");
        //
        parameterMap.put(FrameworkConfig.REDIS_SERVER_HOST, "192.168.59.99");
        parameterMap.put(FrameworkConfig.REDIS_SERVER_PORT, "6379");
        parameterMap.put(FrameworkConfig.REDIS_MAX_POOL_SIZE, "1");
        parameterMap.put(FrameworkConfig.REDIS_MIN_POOL_SIZE, "2");
        parameterMap.put("file.path", "/data/file");
        this.testHandler = new TestHandler(parameterMap);
    }

    @AfterClass
    public static void tearDownClass() {
        ApplicationContext.CONTEXT.contextDestroyed();
    }

    public final void setServiceSession(String userId) {
        String sid = SessionUtils.createServiceSessionId(userId);
        Session session = new SessionImpl(sid);
        this.testHandler.setSession(session);
    }

    public final void setCustomerSession(String userId) {
        String sid = SessionUtils.createCustomerSessionId(userId);
        Session session = new SessionImpl(sid);
        this.testHandler.setSession(session);
    }
}
