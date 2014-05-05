package com.nd.im.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.entity.ServiceEntity;
import com.nd.im.localservice.ServiceLocalService;
import com.nd.im.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.SessionHandleTypeEnum;
import com.wolf.framework.service.parameter.OutputConfig;
import com.wolf.framework.session.Session;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.SERVICE_LOGOUT,
        returnParameter = {
    @OutputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @OutputConfig(name = "serviceName", typeEnum = TypeEnum.CHAR_32, desc = "名称"),
    @OutputConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        validateSession = true,
        sessionHandleTypeEnum = SessionHandleTypeEnum.REMOVE,
        response = true,
        group = ActionGroupNames.IM,
        description = "客服登出")
public class ServiceLogoutServiceImpl implements Service {

    @InjectLocalService()
    private ServiceLocalService serviceUserLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Session session = messageContext.getSession();
        String serviceId = SessionUtils.getServiceUserIdFromSessionId(session.getSid());
        ServiceEntity userEntity = this.serviceUserLocalService.inquireServiceById(serviceId);
        if (userEntity != null) {
            messageContext.setEntityData(userEntity);
            messageContext.success();
            //记录登出状态
            this.serviceUserLocalService.offline(userEntity.getServiceId());
        }
    }
}
