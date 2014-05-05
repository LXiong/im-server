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
import com.wolf.framework.service.parameter.InputConfig;
import com.wolf.framework.service.parameter.OutputConfig;
import com.wolf.framework.session.Session;
import com.wolf.framework.session.SessionImpl;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.ADMIN_LOGIN,
        importantParameter = {
    @InputConfig(name = "userId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @InputConfig(name = "password", typeEnum = TypeEnum.CHAR_32, desc = "密码")
},
        returnParameter = {
    @OutputConfig(name = "userId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @OutputConfig(name = "userName", typeEnum = TypeEnum.CHAR_32, desc = "名称"),
    @OutputConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        validateSession = false,
        sessionHandleTypeEnum = SessionHandleTypeEnum.SAVE,
        response = true,
        group = ActionGroupNames.IM,
        description = "管理员登录")
public class AdminLoginServiceImpl implements Service {

    @InjectLocalService()
    private ServiceLocalService serviceUserLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String userId = messageContext.getParameter("userId");
        String password = messageContext.getParameter("password");
        if (userId.equals(ServiceLocalService.adminUserId)) {
            ServiceEntity adminUserEntity = this.serviceUserLocalService.inquireServiceByUserId(userId);
            if (adminUserEntity.getPassword().equals(password)) {
                //登录成功
                String sid = SessionUtils.createServiceSessionId(userId);
                Session session = new SessionImpl(sid);
                messageContext.setNewSession(session);
                messageContext.setEntityData(adminUserEntity);
                messageContext.success();
            } else {
                messageContext.setFlag(ResponseFlags.FAILURE_PASSWORD_ERROR);
            }
        } else {
            messageContext.setFlag(ResponseFlags.FAILURE_USER_ID_NOT_EXIST);
        }
    }
}
