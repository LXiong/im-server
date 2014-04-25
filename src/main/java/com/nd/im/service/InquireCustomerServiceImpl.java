package com.nd.im.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.entity.CustomerEntity;
import com.nd.im.localservice.ServiceUserLocalService;
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
    private ServiceUserLocalService serviceUserLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        CustomerEntity entity = this.serviceUserLocalService.inquireCustomer();
        messageContext.setEntityData(entity);
        messageContext.success();
    }
}
