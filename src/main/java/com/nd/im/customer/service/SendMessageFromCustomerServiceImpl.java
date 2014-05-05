package com.nd.im.customer.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.message.entity.MessageEntity;
import com.nd.im.message.localservice.MessageLocalService;
import com.nd.im.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.InputConfig;
import com.wolf.framework.service.parameter.OutputConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.SEND_MESSAGE_FROME_CUSTOMER,
        importantParameter = {
    @InputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @InputConfig(name = "message", typeEnum = TypeEnum.CHAR_255, desc = "消息")
},
        returnParameter = {
    @OutputConfig(name = "messageId", typeEnum = TypeEnum.CHAR_32, desc = "消息id"),
    @OutputConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
    @OutputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @OutputConfig(name = "message", typeEnum = TypeEnum.CHAR_32, desc = "消息"),
    @OutputConfig(name = "from", typeEnum = TypeEnum.CHAR_32, desc = "发起人,c:客户,s:客服"),
    @OutputConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型:text-文字,image-图片,file-文件"),
    @OutputConfig(name = "createTime", typeEnum = TypeEnum.DATE_TIME, desc = "发送时间")
},
        validateSession = true,
        response = true,
        broadcast = true,
        group = ActionGroupNames.IM,
        description = "客户发送消息至客服")
public class SendMessageFromCustomerServiceImpl implements Service {

    //
    @InjectLocalService()
    private MessageLocalService messageLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String sid = messageContext.getSession().getSid();
        String customerId = SessionUtils.getCustomerUserIdFromSessionId(sid);
        String serviceId = parameterMap.get("serviceId");
        String message = parameterMap.get("message");
        MessageEntity messageEntity = this.messageLocalService.insertTextMessageFormCustomer(serviceId, customerId, message);
        messageContext.setEntityData(messageEntity);
        String serviceSid = SessionUtils.createServiceSessionId(serviceId);
        messageContext.addBroadcastSid(serviceSid);
        messageContext.success();
    }
}
