package com.nd.im.timer;

import javax.ejb.Local;

/**
 *
 * @author jianying9
 */
@Local
public interface ImTimerSessionBeanLocal {
    
    public void allotWaitCustomer();
}
