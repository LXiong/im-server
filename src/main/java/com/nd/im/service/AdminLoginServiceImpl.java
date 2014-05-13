package com.nd.im.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.config.ResponseFlags;
import com.nd.im.entity.ServiceEntity;
import com.nd.im.localservice.ServiceLocalService;
import com.nd.im.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.SessionHandleTypeEnum;
import com.wolf.framework.service.parameter.RequestConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.session.Session;
import com.wolf.framework.session.SessionImpl;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.ADMIN_LOGIN,
        requestConfigs = {
    @RequestConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @RequestConfig(name = "password", typeEnum = TypeEnum.CHAR_32, desc = "密码")
},
        responseConfigs = {
    @ResponseConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @ResponseConfig(name = "serviceName", typeEnum = TypeEnum.CHAR_32, desc = "名称"),
    @ResponseConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        validateSession = false,
        sessionHandleTypeEnum = SessionHandleTypeEnum.SAVE,
        response = true,
        group = ActionGroupNames.SERVICE,
        description = "管理员登录")
public class AdminLoginServiceImpl implements Service {

    @InjectLocalService()
    private ServiceLocalService serviceUserLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String serviceId = messageContext.getParameter("serviceId");
        String password = messageContext.getParameter("password");
        if (serviceId.equals(ServiceLocalService.adminUserId)) {
            ServiceEntity adminUserEntity = this.serviceUserLocalService.inquireServiceById(serviceId);
            if (adminUserEntity.getPassword().equals(password)) {
                //登录成功
                String sid = SessionUtils.createServiceSessionId(serviceId);
                Session session = new SessionImpl(sid);
                messageContext.setNewSession(session);
                messageContext.setEntityData(adminUserEntity);
                messageContext.success();
            } else {
                messageContext.setFlag(ResponseFlags.FAILURE_PASSWORD_ERROR);
            }
        } else {
            messageContext.setFlag(ResponseFlags.FAILURE_ID_NOT_EXIST);
        }
    }
}
