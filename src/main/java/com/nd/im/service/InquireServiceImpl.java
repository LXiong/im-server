package com.nd.im.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.entity.ServiceEntity;
import com.nd.im.localservice.ServiceLocalService;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.OutputConfig;
import com.wolf.framework.worker.context.MessageContext;
import java.util.List;

/**
 *
 * @author aladdin
 */
@ServiceConfig(
        actionName = ActionNames.INQUIRE_SERVICE,
        returnParameter = {
    @OutputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "用户id"),
    @OutputConfig(name = "serviceName", typeEnum = TypeEnum.CHAR_32, desc = "名称"),
    @OutputConfig(name = "type", typeEnum = TypeEnum.CHAR_32, desc = "类型")
},
        validateSession = true,
        page = true,
        response = true,
        group = ActionGroupNames.SERVICE,
        description = "查询客服帐号")
public class InquireServiceImpl implements Service {

    @InjectLocalService()
    private ServiceLocalService serviceUserLocalService;

    @Override
    public void execute(MessageContext messageContext) {
        long pageIndex = messageContext.getPageIndex();
        long pageSize = messageContext.getPageSize();
        long pageTotal = this.serviceUserLocalService.countService();
        messageContext.setPageTotal(pageTotal);
        List<ServiceEntity> userEntityList = this.serviceUserLocalService.inquireServiceDESC(pageIndex, pageSize);
        messageContext.setEntityListData(userEntityList);
        messageContext.success();
    }
}
