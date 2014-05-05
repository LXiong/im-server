package com.nd.im.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.config.ResponseFlags;
import com.nd.im.entity.ServiceEntity;
import com.nd.im.localservice.ServiceLocalService;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.InputConfig;
import com.wolf.framework.service.parameter.OutputConfig;
import com.wolf.framework.utils.SecurityUtils;
import com.wolf.framework.worker.context.MessageContext;
import java.util.Map;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INSERT_SERVICE,
        importantParameter = {
    @InputConfig(name = "userId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @InputConfig(name = "userName", typeEnum = TypeEnum.CHAR_32, desc = "用户名称"),
    @InputConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        returnParameter = {
    @OutputConfig(name = "userId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @OutputConfig(name = "userName", typeEnum = TypeEnum.CHAR_32, desc = "名称"),
    @OutputConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        validateSession = true,
        response = true,
        group = ActionGroupNames.IM,
        description = "新增客服帐号")
public class InsertServiceImpl implements Service {

    @InjectLocalService()
    private ServiceLocalService serviceUserLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        Map<String, String> parameterMap = messageContext.getParameterMap();
        String userId = parameterMap.get("userId");
        ServiceEntity userEntity = this.serviceUserLocalService.inquireServiceByUserId(userId);
        if(userEntity == null) {
            parameterMap.put("password", SecurityUtils.encryptByMd5(userId));
            this.serviceUserLocalService.insertService(parameterMap);
            messageContext.setMapData(parameterMap);
            messageContext.success();
        } else {
            messageContext.setFlag(ResponseFlags.FAILURE_USER_ID_USED);
        }
    }
}
