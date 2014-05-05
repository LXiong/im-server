package com.nd.im.customer.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.customer.localservice.CustomerLocalService;
import com.nd.im.customer.entity.CustomerEntity;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.OutputConfig;
import com.wolf.framework.worker.context.MessageContext;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INQUIRE_CUSTOMER,
        returnParameter = {
    @OutputConfig(name = "userId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @OutputConfig(name = "nickName", typeEnum = TypeEnum.CHAR_32, desc = "名称")
},
        validateSession = false,
        response = true,
        group = ActionGroupNames.IM,
        description = "随机获取客户帐号")
public class InquireCustomerServiceImpl implements Service {

    @InjectLocalService()
    private CustomerLocalService customerLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        CustomerEntity entity = this.customerLocalService.inquireCustomer();
        messageContext.setEntityData(entity);
        messageContext.success();
    }
}
