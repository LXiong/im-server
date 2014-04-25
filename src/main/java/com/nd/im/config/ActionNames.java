package com.nd.im.config;

/**
 *
 * @author aladdin
 */
public class ActionNames {
    //-----------------------文件------------------------//
    //保存图片

    public final static String INSERT_IMAGE = "INSERT_IMAGE";
    //根据imageId查询图片数据
    public final static String INQUIRE_IMAGE_BY_KEY = "INQUIRE_IMAGE_BY_KEY";
    //-----------------------管理员接口-------------------------//
    
    //-----------------------客服用户接口------------------------//
    //管理员登录
    public final static String ADMIN_LOGIN = "ADMIN_LOGIN";
    //普通用户登录
    public final static String SERVICE_LOGIN = "SERVICE_LOGIN";
    //登出
    public final static String SERVICE_LOGOUT = "SERVICE_LOGOUT";
    //新增客服用户
    public final static String INSERT_SERVICE_USER = "INSERT_SERVICE_USER";
    //查询客服用户
    public final static String INQUIRE_SERVICE_USER = "INQUIRE_SERVICE_USER";
    //----------------------------客户接口---------------------------//
    //随机查询
    public final static String INQUIRE_CUSTOMER = "INQUIRE_CUSTOMER";
    //客户登录
    public final static String CUSTOMER_LOGIN = "CUSTOMER_LOGIN";
    //客户登出
    public final static String CUSTOMER_LOGOUT = "CUSTOMER_LOGOUT";
    //请求客服
    public final static String CONNECT_SERVICE   = "CONNECT_SERVICE";
    //-----------------------------消息------------------------------//
    //发送消息
    public final static String SEND_MESSAGE   = "SEND_MESSAGE";
}
