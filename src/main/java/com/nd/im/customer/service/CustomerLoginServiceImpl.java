package com.nd.im.customer.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.config.ResponseFlags;
import com.nd.im.customer.localservice.CustomerLocalService;
import com.nd.im.customer.entity.CustomerEntity;
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
        actionName = ActionNames.CUSTOMER_LOGIN,
        importantParameter = {
    @InputConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id")
},
        returnParameter = {
    @OutputConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
    @OutputConfig(name = "customerName", typeEnum = TypeEnum.CHAR_32, desc = "昵称")
},
        validateSession = false,
        sessionHandleTypeEnum = SessionHandleTypeEnum.SAVE,
        response = true,
        group = ActionGroupNames.IM,
        description = "客户用户登录")
public class CustomerLoginServiceImpl implements Service {

    @InjectLocalService()
    private CustomerLocalService customerLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        String customerId = messageContext.getParameter("customerId");
        CustomerEntity entity = this.customerLocalService.inquireCustomerById(customerId);
        if (entity != null) {
            String sid = SessionUtils.createCustomerSessionId(customerId);
            Session session = new SessionImpl(sid);
            messageContext.setNewSession(session);
            messageContext.setEntityData(entity);
            messageContext.success();
        } else {
            messageContext.setFlag(ResponseFlags.FAILURE_ID_NOT_EXIST);
        }
    }
}
