package com.nd.im.customer.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.customer.localservice.CustomerLocalService;
import com.nd.im.customer.entity.CustomerEntity;
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
        actionName = ActionNames.CUSTOMER_WAIT,
        returnParameter = {
    @OutputConfig(name = "userId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
    @OutputConfig(name = "nickName", typeEnum = TypeEnum.CHAR_32, desc = "昵称"),
    @OutputConfig(name = "waitNum", typeEnum = TypeEnum.LONG, desc = "等待人数"),
    @OutputConfig(name = "waitOrder", typeEnum = TypeEnum.LONG, desc = "排队序号")
},
        validateSession = true,
        response = true,
        broadcast = true,
        group = ActionGroupNames.IM,
        description = "加入等待队列")
public class CustomerWaitServiceImpl implements Service {
    
    @InjectLocalService()
    private CustomerLocalService customerLocalService;
    
    @Override
    public void execute(MessageContext messageContext) {
        String sid = messageContext.getSession().getSid();
        String customerId = SessionUtils.getCustomerUserIdFromSessionId(sid);
        CustomerEntity customerEntity = this.customerLocalService.inquireCustomerByUserId(customerId);
        String nickName = customerEntity.getNickName();
        long waitNum = this.customerLocalService.countCustomerWaitNum();
        String createTime = Long.toString(System.currentTimeMillis());
        this.customerLocalService.insertCustomerWait(customerId, nickName, createTime);
        Map<String, String> resultMap = new HashMap<String, String>(4, 1);
        resultMap.put("userId", customerId);
        resultMap.put("nickName", nickName);
        resultMap.put("waitNum", Long.toString(waitNum));
        resultMap.put("waitOrder", createTime);
        messageContext.setMapData(resultMap);
        messageContext.success();
    }
}