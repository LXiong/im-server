package com.nd.im.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.message.entity.MessageEntity;
import com.nd.im.message.localservice.MessageLocalService;
import com.nd.im.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.RequestConfig;
import com.wolf.framework.service.parameter.ResponseConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.SEND_MESSAGE_FROM_SERVICE,
        requestConfigs = {
    @RequestConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
    @RequestConfig(name = "message", typeEnum = TypeEnum.CHAR_255, desc = "消息")
},
        responseConfigs = {
    @ResponseConfig(name = "messageId", typeEnum = TypeEnum.CHAR_32, desc = "消息id"),
    @ResponseConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
    @ResponseConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @ResponseConfig(name = "message", typeEnum = TypeEnum.CHAR_32, desc = "消息主体,文字类型为字符,图片和文件类型为路径"),
    @ResponseConfig(name = "from", typeEnum = TypeEnum.CHAR_32, desc = "发起人,c:客户,s:客服"),
    @ResponseConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型:text-文字,image-图片,file-文件"),
    @ResponseConfig(name = "createTime", typeEnum = TypeEnum.DATE_TIME, desc = "发送时间")
},
        validateSession = true,
        response = true,
        group = ActionGroupNames.SERVICE,
        description = "客户发送消息至客服")
public class SendMessageFromServiceImpl implements Service {

    //
    @InjectLocalService()
    private MessageLocalService messageLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String sid = messageContext.getSession().getSid();
        String serviceId = SessionUtils.getServiceUserIdFromSessionId(sid);
        String customerId = parameterMap.get("customerId");
        String message = parameterMap.get("message");
        MessageEntity messageEntity = this.messageLocalService.insertTextMessageFormService(serviceId, customerId, message);
        messageContext.setEntityData(messageEntity);
        String customerSid = SessionUtils.createCustomerSessionId(customerId);
        messageContext.success();
        //
        String responseMessage = messageContext.getResponseMessage();
        messageContext.push(customerSid, responseMessage);
    }
}
