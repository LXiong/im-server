package com.nd.im.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.config.ResponseFlags;
import com.nd.im.entity.CustomerEntity;
import com.nd.im.localservice.ServiceUserLocalService;
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
        actionName = ActionNames.CUSTOMER_LOGIN,
        importantParameter = {
    @InputConfig(name = "userId", typeEnum = TypeEnum.CHAR_32, desc = "用户id")
},
        returnParameter = {
    @OutputConfig(name = "userId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @OutputConfig(name = "nickName", typeEnum = TypeEnum.CHAR_32, desc = "昵称")
},
        validateSession = false,
        sessionHandleTypeEnum = SessionHandleTypeEnum.SAVE,
        response = true,
        group = ActionGroupNames.IM,
        description = "客户用户登录")
public class CustomerLoginServiceImpl implements Service {
    
    @InjectLocalService()
    private ServiceUserLocalService serviceUserLocalService;
    
    @Override
    public void execute(MessageContext messageContext) {
        String userId = messageContext.getParameter("userId");
        CustomerEntity entity = this.serviceUserLocalService.inquireCustomerByUserId(userId);
        if (entity != null) {
            Session session = new SessionImpl(userId);
            messageContext.setNewSession(session);
            messageContext.setEntityData(entity);
            messageContext.success();
        } else {
            messageContext.setFlag(ResponseFlags.FAILURE_USER_ID_NOT_EXIST);
        }
    }
}
