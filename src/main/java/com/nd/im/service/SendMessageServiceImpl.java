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
import com.wolf.framework.service.parameter.InputConfig;
import com.wolf.framework.service.parameter.OutputConfig;
import com.wolf.framework.session.Session;
import com.wolf.framework.worker.context.MessageContext;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.SEND_MESSAGE,
        importantParameter = {
    @InputConfig(name = "receiveId", typeEnum = TypeEnum.CHAR_32, desc = "目标用户id"),
    @InputConfig(name = "message", typeEnum = TypeEnum.CHAR_255, desc = "消息")
},
        returnParameter = {
    @OutputConfig(name = "sendId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @OutputConfig(name = "receiveId", typeEnum = TypeEnum.CHAR_32, desc = "昵称"),
    @OutputConfig(name = "message", typeEnum = TypeEnum.CHAR_32, desc = ""),
    @OutputConfig(name = "messageId", typeEnum = TypeEnum.CHAR_32, desc = "消息id"),
    @OutputConfig(name = "createTime", typeEnum = TypeEnum.DATE_TIME, desc = "发送时间")
},
        validateSession = true,
        response = true,
        broadcast = true,
        group = ActionGroupNames.IM,
        description = "发送消息")
public class SendMessageServiceImpl implements Service {
    
    private AtomicLong messageIdAtomic = new AtomicLong(10000);

    @InjectLocalService()
    private ServiceUserLocalService serviceUserLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Session session = messageContext.getSession();
        String userId = session.getUserId();
        Map<String, String> parameterMap = messageContext.getParameterMap();
        long messageId = this.messageIdAtomic.incrementAndGet();
        String receiveId = parameterMap.get("receiveId");
        String message = parameterMap.get("message");
        Map<String, String> resultMap = new HashMap<String, String>(4, 1);
        resultMap.put("messageId", Long.toString(messageId));
        resultMap.put("sendId", userId);
        resultMap.put("receiveId", receiveId);
        resultMap.put("message", message);
        resultMap.put("createTime", Long.toString(System.currentTimeMillis()));
        messageContext.setMapData(resultMap);
        messageContext.addBroadcastUserId(receiveId);
        messageContext.success();
    }
}
