package com.nd.im.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.customer.localservice.CustomerLocalService;
import com.nd.im.customer.entity.CustomerWaitEntity;
import com.nd.im.entity.ServiceEntity;
import com.nd.im.localservice.ServiceLocalService;
import com.nd.im.utils.SessionUtils;
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
 * @author jianying9
 */
@ServiceConfig(
        actionName = ActionNames.NEXT_CUSTOMER,
        returnParameter = {
    @OutputConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
    @OutputConfig(name = "nickName", typeEnum = TypeEnum.CHAR_32, desc = "客户昵称"),
    @OutputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @OutputConfig(name = "serviceName", typeEnum = TypeEnum.CHAR_32, desc = "客服昵称")
},
        validateSession = true,
        response = true,
        broadcast = true,
        group = ActionGroupNames.IM,
        description = "获取下以下等待的客户")
public class NextCustomerServiceImpl implements Service {

    @InjectLocalService()
    private ServiceLocalService serviceUserLocalService;
    //
    @InjectLocalService()
    private CustomerLocalService customerLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        CustomerWaitEntity customerWaitEntity = this.customerLocalService.nextCustomerWait();
        if (customerWaitEntity != null) {
            String sid = messageContext.getSession().getSid();
            String serviceId = SessionUtils.getServiceUserIdFromSessionId(sid);
            ServiceEntity serviceEntity = this.serviceUserLocalService.inquireServiceByUserId(serviceId);
            Map<String, String> resultMap = new HashMap<String, String>(4, 1);
            resultMap.put("serviceId", serviceId);
            resultMap.put("serviceName", serviceEntity.getUserName());
            resultMap.put("customerId", customerWaitEntity.getUserId());
            resultMap.put("nickName", customerWaitEntity.getNickName());
            messageContext.setMapData(resultMap);
            String customerSid = SessionUtils.createCustomerSessionId(customerWaitEntity.getUserId());
            messageContext.addBroadcastSid(customerSid);
            messageContext.success();
        }
    }
}
