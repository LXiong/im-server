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
        actionName = ActionNames.SERVICE_LOGIN,
        importantParameter = {
    @InputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id")
},
        returnParameter = {
    @OutputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @OutputConfig(name = "serviceName", typeEnum = TypeEnum.CHAR_32, desc = "名称"),
    @OutputConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        validateSession = false,
        sessionHandleTypeEnum = SessionHandleTypeEnum.SAVE,
        response = true,
        group = ActionGroupNames.SERVICE,
        description = "客服登录")
public class ServiceLoginServiceImpl implements Service {

    @InjectLocalService()
    private ServiceLocalService serviceUserLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String serviceId = messageContext.getParameter("serviceId");
        ServiceEntity serviceEntity = this.serviceUserLocalService.inquireServiceById(serviceId);
        if (serviceEntity != null) {
            String sid = SessionUtils.createServiceSessionId(serviceId);
            Session session = new SessionImpl(sid);
            messageContext.setNewSession(session);
            messageContext.setEntityData(serviceEntity);
            messageContext.success();
            //记录登录状态
            this.serviceUserLocalService.onService(serviceEntity);
        } else {
            messageContext.setFlag(ResponseFlags.FAILURE_ID_NOT_EXIST);
        }
    }
}
