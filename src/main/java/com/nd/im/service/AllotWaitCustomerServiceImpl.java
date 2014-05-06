package com.nd.im.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.customer.localservice.CustomerLocalService;
import com.nd.im.customer.entity.WaitCustomerEntity;
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
        actionName = ActionNames.TIMER_ALLOT_CUSTOMER,
        returnParameter = {
    @OutputConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
    @OutputConfig(name = "customerName", typeEnum = TypeEnum.CHAR_32, desc = "客户昵称"),
    @OutputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @OutputConfig(name = "serviceName", typeEnum = TypeEnum.CHAR_32, desc = "客服昵称")
},
        validateSession = true,
        response = true,
        group = ActionGroupNames.IM,
        description = "获取下以下等待的客户")
public class AllotWaitCustomerServiceImpl implements Service {

    @InjectLocalService()
    private ServiceLocalService serviceUserLocalService;
    //
    @InjectLocalService()
    private CustomerLocalService customerLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        WaitCustomerEntity customerWaitEntity = this.customerLocalService.nextWaitCustomer();
        if (customerWaitEntity != null) {
            String sid = messageContext.getSession().getSid();
            String serviceId = SessionUtils.getServiceUserIdFromSessionId(sid);
            ServiceEntity serviceEntity = this.serviceUserLocalService.inquireServiceById(serviceId);
            Map<String, String> resultMap = new HashMap<String, String>(4, 1);
            resultMap.put("serviceId", serviceId);
            resultMap.put("serviceName", serviceEntity.getServiceName());
            resultMap.put("customerId", customerWaitEntity.getCustomerId());
            resultMap.put("customerName", customerWaitEntity.getCustomerName());
            messageContext.setMapData(resultMap);
            String customerSid = SessionUtils.createCustomerSessionId(customerWaitEntity.getCustomerId());
            messageContext.success();
            //
            String responseMessage = messageContext.getResponseMessage();
            messageContext.push(customerSid, responseMessage);
        }
    }
}
