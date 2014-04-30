package com.nd.im.leave;

import com.nd.im.localservice.ServiceUserLocalService;
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
    private ServiceUserLocalService serviceUserLocalService;

    @Override
    public void execute(Session session, CometContext cometContext) {
        cometContext.push("271411", "{\"flag\":\"SUCCESS\",\"act\":\"CUSTOMER_LOGOUT\",\"data\":{\"userId\":\"" + session.getSid() + "\",\"serviceId\":\"271411\"}}");
    }
}
