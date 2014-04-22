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
    public final static String LOGIN = "LOGIN";
    //登出
    public final static String LOGOUT = "LOGOUT";
    //新增客服用户
    public final static String INSERT_SERVER_USER = "INSERT_SERVER_USER";
    //查询客服用户
    public final static String INQUIRE_SERVER_USER = "INQUIRE_SERVER_USER";
}
