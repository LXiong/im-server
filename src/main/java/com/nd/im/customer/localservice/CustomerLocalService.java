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

    public CustomerEntity inquireCustomerById(String customerId);

    public long countCustomerWaitNum();

    public void insertCustomerWait(String customerId, String customerName, String createTime);
    
    public CustomerWaitEntity inquireCustomerWaitById(String customerId);

    public CustomerWaitEntity nextCustomerWait();
    
    public void deleteCustomerWait(String customerId);
    
    public List<CustomerWaitEntity> inquireCustomerWait(long pageIndex, long pageSize);
}
