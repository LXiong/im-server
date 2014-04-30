package com.nd.im.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.localservice.ServiceUserLocalService;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.SessionHandleTypeEnum;
import com.wolf.framework.service.parameter.InputConfig;
import com.wolf.framework.service.parameter.OutputConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.CUSTOMER_LOGOUT,
        importantParameter = {
    @InputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id")
},
        returnParameter = {
    @OutputConfig(name = "userId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @OutputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id")
},
        validateSession = true,
        sessionHandleTypeEnum = SessionHandleTypeEnum.REMOVE,
        response = true,
        broadcast = true,
        group = ActionGroupNames.IM,
        description = "客户用户登出")
public class CustomerLogoutServiceImpl implements Service {
    
    @InjectLocalService()
    private ServiceUserLocalService serviceUserLocalService;
    
    @Override
    public void execute(MessageContext messageContext) {
        String userId = messageContext.getSession().getSid();
        messageContext.setNewSession(null);
        String serviceId = messageContext.getParameter("serviceId");
        Map<String, String> resultMap = new HashMap<String, String>(2, 1);
        resultMap.put("userId", userId);
        resultMap.put("serviceId", serviceId);
        messageContext.addBroadcastUserId(serviceId);
        messageContext.setMapData(resultMap);
        messageContext.success();
    }
}
