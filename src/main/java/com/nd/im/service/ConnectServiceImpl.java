package com.nd.im.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.config.ResponseFlags;
import com.nd.im.entity.CustomerEntity;
import com.nd.im.entity.ServiceUserOnlineEntity;
import com.nd.im.localservice.ServiceUserLocalService;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.OutputConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.CONNECT_SERVICE,
        returnParameter = {
    @OutputConfig(name = "userId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @OutputConfig(name = "nickName", typeEnum = TypeEnum.CHAR_32, desc = "昵称"),
    @OutputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id:-1表示没有分配到客服"),
    @OutputConfig(name = "serviceName", typeEnum = TypeEnum.CHAR_32, desc = "客服昵称")
},
        validateSession = true,
        response = true,
        broadcast = true,
        group = ActionGroupNames.IM,
        description = "分配客服")
public class ConnectServiceImpl implements Service {

    @InjectLocalService()
    private ServiceUserLocalService serviceUserLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        //分配客服
        ServiceUserOnlineEntity serviceEntity = this.serviceUserLocalService.inquireOnlineUser();
        if (serviceEntity != null) {
            Map<String, String> resultMap = new HashMap<String, String>(4, 1);
            resultMap.put("serviceId", serviceEntity.getUserId());
            resultMap.put("serviceName", serviceEntity.getUserName());
            String userId = messageContext.getSession().getSid();
            CustomerEntity customerEntity = this.serviceUserLocalService.inquireCustomerByUserId(userId);
            String nickName = customerEntity.getNickName();
            resultMap.put("userId", userId);
            resultMap.put("nickName", nickName);
            messageContext.setMapData(resultMap);
            messageContext.addBroadcastUserId(serviceEntity.getUserId());
            messageContext.success();
        } else {
            messageContext.setFlag(ResponseFlags.FAILURE_WAIT);
        }
    }
}
