package com.nd.im.utils;

/**
 *
 * @author jianying9
 */
public class SessionUtils {

    private final static String SERVICE_PREFIX = "s_";
    private final static String CUSTOMER_PREFIX = "c_";

    public static String createServiceSessionId(String userId) {
        return SERVICE_PREFIX.concat(userId);
    }

    public static String getServiceUserIdFromSessionId(String sid) {
        String userId = "-1";
        int index = sid.indexOf(SERVICE_PREFIX);
        if (index == 0) {
            userId = sid.substring(index + SERVICE_PREFIX.length());
        }
        return userId;
    }

    public static boolean isServiceSession(String sid) {
        int index = sid.indexOf(SERVICE_PREFIX);
        return index == 0;
    }

    public static String createCustomerSessionId(String userId) {
        return CUSTOMER_PREFIX.concat(userId);
    }

    public static String getCustomerUserIdFromSessionId(String sid) {
        String userId = "-1";
        int index = sid.indexOf(CUSTOMER_PREFIX);
        if (index == 0) {
            userId = sid.substring(index + CUSTOMER_PREFIX.length());
        }
        return userId;
    }
}
