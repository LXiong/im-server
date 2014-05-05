package com.nd.im.customer.localservice;

import com.nd.im.customer.entity.CustomerEntity;
import com.nd.im.customer.entity.CustomerWaitEntity;
import com.wolf.framework.local.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jianying9
 */
public interface CustomerLocalService extends Local {

    public void insertCustomer(Map<String, String> entityMap);

    public CustomerEntity inquireCustomer();

    public CustomerEntity inquireCustomerByUserId(String userId);

    public long countCustomerWaitNum();

    public void insertCustomerWait(String userId, String nickName, String createTime);
    
    public CustomerWaitEntity inquireCustomerWaitByUserId(String userId);

    public CustomerWaitEntity nextCustomerWait();
    
    public void deleteCustomerWait(String userId);
    
    public List<CustomerWaitEntity> inquireCustomerWait(long pageIndex, long pageSize);
}
