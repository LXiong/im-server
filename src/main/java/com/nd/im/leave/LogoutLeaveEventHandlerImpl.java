package com.nd.im.leave;

import com.nd.im.customer.localservice.CustomerLocalService;
import com.nd.im.localservice.ServiceLocalService;
import com.nd.im.utils.SessionUtils;
import com.wolf.framework.comet.CometContext;
import com.wolf.framework.comet.LeaveEventConfig;
import com.wolf.framework.comet.LeaveEventHandler;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.session.Session;

/**
 *
 * @author jianying9
 */
@LeaveEventConfig()
public class LogoutLeaveEventHandlerImpl implements LeaveEventHandler {

    @InjectLocalService()
    private ServiceLocalService serviceUserLocalService;
    //
    @InjectLocalService()
    private CustomerLocalService customerLocalService;

    @Override
    public void execute(Session session, CometContext cometContext) {
        String sid = session.getSid();
        if(SessionUtils.isServiceSession(sid)) {
            //客服登出
            String serviceId = SessionUtils.getServiceUserIdFromSessionId(sid);
            this.serviceUserLocalService.offline(serviceId);
        } else {
            //客户登出
            String customerId = SessionUtils.getCustomerUserIdFromSessionId(sid);
            this.customerLocalService.deleteCustomerWait(customerId);
        }
//        cometContext.push("271411", "{\"flag\":\"SUCCESS\",\"act\":\"CUSTOMER_LOGOUT\",\"data\":{\"userId\":\"" + session.getSid() + "\",\"serviceId\":\"271411\"}}");
    }
}
