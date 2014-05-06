package com.nd.im.timer;

import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;

/**
 *
 * @author jianying9
 */
@Stateless
@Startup
public class ImTimerSessionBean implements ImTimerSessionBeanLocal {

    @Schedule(minute = "*", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*", persistent = false)
    @Override
    public void allotWaitCustomer() {
        
    }
}
