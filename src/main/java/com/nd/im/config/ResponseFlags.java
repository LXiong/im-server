package com.nd.im.config;

/**
 *
 * @author aladdin
 */
public class ResponseFlags {

    //用户id不存在
    public final static String FAILURE_USER_ID_NOT_EXIST = "FAILURE_USER_ID_NOT_EXIST";
    //密码错误
    public final static String FAILURE_PASSWORD_ERROR = "FAILURE_PASSWORD_ERROR";
    //userId已经被使用
    public final static String FAILURE_USER_ID_USED = "FAILURE_USER_ID_USED";
    //没有客服，等待
    public final static String FAILURE_WAIT = "FAILURE_WAIT";
}
