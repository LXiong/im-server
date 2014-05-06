package com.nd.im.timer;

import com.nd.im.config.ActionNames;
import com.wolf.framework.timer.AbstractTimer;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;

/**
 *
 * @author jianying9
 */
@Stateless
@Startup
public class ImTimerSessionBean extends AbstractTimer implements ImTimerSessionBeanLocal {

    @Schedule(minute = "*", second = "0,5,10,15,20,25,30,35,40,45,50,55", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*", persistent = false)
    @Override
    public void allotWaitCustomer() {
        System.out.println("timer:allotWaitCustomer----ALLOT_CUSTOMER:分配客户");
        Map<String, String> parameterMap = new HashMap<String, String>(2, 1);
        parameterMap.put("operate", "check");
        String result = this.executeService(ActionNames.ALLOT_WAIT_CUSTOMER, parameterMap);
        System.out.println(result);
    }
}
