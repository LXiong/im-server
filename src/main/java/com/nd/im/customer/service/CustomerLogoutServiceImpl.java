package com.nd.im.customer.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.customer.entity.WaitCustomerEntity;
import com.nd.im.customer.localservice.CustomerLocalService;
import com.nd.im.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.SessionHandleTypeEnum;
import com.wolf.framework.service.parameter.InputConfig;
import com.wolf.framework.service.parameter.OutputConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.CUSTOMER_LOGOUT,
        importantParameter = {
    @InputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id,-1代表还未分配客服")
},
        minorParameter = {
    @InputConfig(name = "waitOrder", typeEnum = TypeEnum.LONG, desc = "客户排队序号")
},
        returnParameter = {
    @OutputConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
    @OutputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @OutputConfig(name = "waitOrder", typeEnum = TypeEnum.LONG, desc = "客户排队序号")
},
        validateSession = true,
        sessionHandleTypeEnum = SessionHandleTypeEnum.REMOVE,
        response = true,
        group = ActionGroupNames.CUSTOMER,
        description = "客户用户登出")
public class CustomerLogoutServiceImpl implements Service {

    @InjectLocalService()
    private CustomerLocalService customerLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String sid = messageContext.getSession().getSid();
        String customerId = SessionUtils.getCustomerUserIdFromSessionId(sid);
        messageContext.setNewSession(null);
        String serviceId = messageContext.getParameter("serviceId");
        Map<String, String> resultMap = new HashMap<String, String>(2, 1);
        resultMap.put("customerId", customerId);
        resultMap.put("serviceId", serviceId);
        resultMap.put("waitOrder", "-1");
        messageContext.setMapData(resultMap);
        messageContext.success();
        if (serviceId.equals("-1") == true) {
            //还未分配客服
            //判断是否在等待队列
            String waitOrder = messageContext.getParameter("waitOrder");
            if (waitOrder != null) {
                resultMap.put("waitOrder", waitOrder);
                this.customerLocalService.deleteCustomerWait(customerId);
                //通知排队前50等待的客户,更新等待人数
                List<WaitCustomerEntity> customerWaitEntityList = this.customerLocalService.inquireCustomerWait(1, 50);
                if (customerWaitEntityList.isEmpty() == false) {
                    String customerSid;
                    String message = messageContext.getResponseMessage();
                    for (WaitCustomerEntity customerWaitEntity : customerWaitEntityList) {
                        customerSid = SessionUtils.createCustomerSessionId(customerWaitEntity.getCustomerId());
                        messageContext.push(customerSid, message);
                    }
                }
            }
        } else {
            //通知客服，当前客户退出
            String serviceSid = SessionUtils.createServiceSessionId(serviceId);
            String message = messageContext.getResponseMessage();
            messageContext.push(serviceSid, message);
        }
    }
}
