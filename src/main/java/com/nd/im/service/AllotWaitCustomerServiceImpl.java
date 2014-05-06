package com.nd.im.service;

import com.nd.im.config.ActionGroupNames;
import com.nd.im.config.ActionNames;
import com.nd.im.customer.localservice.CustomerLocalService;
import com.nd.im.customer.entity.WaitCustomerEntity;
import com.nd.im.entity.ServiceStateEntity;
import com.nd.im.localservice.ServiceLocalService;
import com.nd.im.utils.SessionUtils;
import com.wolf.framework.data.TypeEnum;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.service.Service;
import com.wolf.framework.service.ServiceConfig;
import com.wolf.framework.service.parameter.InputConfig;
import com.wolf.framework.service.parameter.OutputConfig;
import com.wolf.framework.task.InjectTaskExecutor;
import com.wolf.framework.task.Task;
import com.wolf.framework.task.TaskExecutor;
import com.wolf.framework.worker.context.MessageContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jianying9
 */
@ServiceConfig(
        actionName = ActionNames.ALLOT_WAIT_CUSTOMER,
        importantParameter = {
    @InputConfig(name = "operate", typeEnum = TypeEnum.CHAR_32, desc = "操作:check,stop")
},
        returnParameter = {
    @OutputConfig(name = "customerId", typeEnum = TypeEnum.CHAR_32, desc = "客户id"),
    @OutputConfig(name = "customerName", typeEnum = TypeEnum.CHAR_32, desc = "客户昵称"),
    @OutputConfig(name = "serviceId", typeEnum = TypeEnum.CHAR_32, desc = "客服id"),
    @OutputConfig(name = "serviceName", typeEnum = TypeEnum.CHAR_32, desc = "客服昵称"),
    @OutputConfig(name = "state", typeEnum = TypeEnum.CHAR_32, desc = "调度服务状态:stop-停止,running-运行")
},
        validateSession = false,
        response = true,
        group = ActionGroupNames.IM,
        description = "分配等待队列中的客户")
public class AllotWaitCustomerServiceImpl implements Service {

    //状态：stop-正常停止,running-运行中
    private volatile String state = "stop";
    //
    @InjectLocalService()
    private ServiceLocalService serviceLocalService;
    //
    @InjectLocalService()
    private CustomerLocalService customerLocalService;
    //
    @InjectTaskExecutor
    private TaskExecutor taskExecutor;

    @Override
    public void execute(MessageContext messageContext) {
        String operate = messageContext.getParameter("operate");
        if (operate.equals("stop")) {
            this.state = "stop";
        } else if (operate.equals("check")) {
            synchronized (this) {
                if (this.state.equals("stop")) {
                    Task task = new AllotTaskImpl(messageContext);
                    this.taskExecutor.submit(task);
                    this.state = "running";
                }
            }
        }
        Map<String, String> resultMap = new HashMap<String, String>(2, 1);
        resultMap.put("state", this.state);
        messageContext.setMapData(resultMap);
        messageContext.success();
    }

    /**
     * 执行任务
     */
    private class AllotTaskImpl extends Task {

        private final MessageContext messageContext;

        public AllotTaskImpl(MessageContext messageContext) {
            this.messageContext = messageContext;
        }

        @Override
        public void doWhenRejected() {
            state = "stop";
        }

        @Override
        protected void execute() {
            List<WaitCustomerEntity> waitCustomerEntityList;
            List<ServiceStateEntity> serviceStateEntityList;
            while (state.equals("running")) {
                //获取在线的前50处于接收状态(on)的客服列表
                serviceStateEntityList = serviceLocalService.inquireOnService(1, 50);
                if (serviceStateEntityList.isEmpty()) {
                    //没有在线客服,退出
                    state = "stop";
                } else {
                    //获取2倍与接收状态的客服数量的等待客户数量客户
                    waitCustomerEntityList = customerLocalService.inquireCustomerWait(1, serviceStateEntityList.size() * 4);
                    if (waitCustomerEntityList.isEmpty()) {
                        state = "stop";
                    } else {
                        int serviceIndex = 0;
                        ServiceStateEntity serviceStateEntity;
                        Map<String, String> resultMap = new HashMap<String, String>(4, 1);
                        String customerSid;
                        String serviceSid;
                        String responseMessage;
                        for (WaitCustomerEntity waitCustomerEntity : waitCustomerEntityList) {
                            if (serviceIndex >= serviceStateEntityList.size()) {
                                serviceIndex = serviceIndex % serviceStateEntityList.size();
                            }
                            serviceStateEntity = serviceStateEntityList.get(0);
                            resultMap.put("serviceId", serviceStateEntity.getServiceId());
                            resultMap.put("serviceName", serviceStateEntity.getServiceName());
                            resultMap.put("customerId", waitCustomerEntity.getCustomerId());
                            resultMap.put("customerName", waitCustomerEntity.getCustomerName());
                            this.messageContext.setMapData(resultMap);
                            responseMessage = this.messageContext.getResponseMessage(false);
                            customerSid = SessionUtils.createCustomerSessionId(waitCustomerEntity.getCustomerId());
                            serviceSid = SessionUtils.createServiceSessionId(serviceStateEntity.getServiceId());
                            this.messageContext.push(customerSid, responseMessage);
                            this.messageContext.push(serviceSid, responseMessage);
                            serviceIndex++;
                            customerLocalService.deleteCustomerWait(waitCustomerEntity.getCustomerId());
                        }
                    }
                }
            }
        }
    }
}
