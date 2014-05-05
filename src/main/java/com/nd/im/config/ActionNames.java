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
    //-----------------------客服用户接口------------------------//
    //管理员登录
    public final static String ADMIN_LOGIN = "ADMIN_LOGIN";
    //普通用户登录
    public final static String SERVICE_LOGIN = "SERVICE_LOGIN";
    //登出
    public final static String SERVICE_LOGOUT = "SERVICE_LOGOUT";
    //新增客服用户
    public final static String INSERT_SERVICE = "INSERT_SERVICE";
    //查询客服用户
    public final static String INQUIRE_SERVICE = "INQUIRE_SERVICE";
    //获取下一个等待的客户
    public final static String NEXT_CUSTOMER = "NEXT_CUSTOMER";
    //----------------------------客户接口---------------------------//
    //随机查询
    public final static String INQUIRE_CUSTOMER = "INQUIRE_CUSTOMER";
    //客户登录
    public final static String CUSTOMER_LOGIN = "CUSTOMER_LOGIN";
    //客户登出
    public final static String CUSTOMER_LOGOUT = "CUSTOMER_LOGOUT";
    //呼叫客服,开始排队
    public final static String CUSTOMER_WAIT = "CUSTOMER_WAIT";
    //-----------------------------消息------------------------------//
    //客户发送消息至客服
    public final static String SEND_MESSAGE_FROME_CUSTOMER = "SEND_MESSAGE_FROME_CUSTOMER";
    //客服发送消息至客户
    public final static String SEND_MESSAGE_FROM_SERVICE = "SEND_MESSAGE_FROM_SERVICE";
}
