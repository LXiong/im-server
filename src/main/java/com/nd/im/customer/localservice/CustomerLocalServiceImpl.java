package com.nd.im.customer.localservice;

import com.nd.im.customer.entity.CustomerEntity;
import com.nd.im.customer.entity.WaitCustomerEntity;
import com.wolf.framework.dao.REntityDao;
import com.wolf.framework.dao.annotation.InjectRDao;
import com.wolf.framework.dao.condition.InquirePageContext;
import com.wolf.framework.local.LocalServiceConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jianying9
 */
@LocalServiceConfig(
        interfaceInfo = CustomerLocalService.class,
        description = "客户管理")
public class CustomerLocalServiceImpl implements CustomerLocalService {

    @InjectRDao(clazz = CustomerEntity.class)
    private REntityDao<CustomerEntity> customerEntityDao;
    //
    @InjectRDao(clazz = WaitCustomerEntity.class)
    private REntityDao<WaitCustomerEntity> customerWaitEntityDao;

    @Override
    public void init() {
    }

    @Override
    public void insertCustomer(Map<String, String> entityMap) {
        this.customerEntityDao.insert(entityMap);
    }

    @Override
    public synchronized CustomerEntity inquireCustomer() {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(1);
        inquirePageContext.setPageIndex(1);
        List<String> keyList = this.customerEntityDao.inquireKeys(inquirePageContext);
        CustomerEntity entity = this.customerEntityDao.inquireByKey(keyList.get(0));
        this.customerEntityDao.updateKeySorce(entity.getCustomerId(), System.currentTimeMillis());
        return entity;
    }

    @Override
    public CustomerEntity inquireCustomerById(String customerId) {
        return this.customerEntityDao.inquireByKey(customerId);
    }

    @Override
    public long countWaitCustomerNum() {
        return this.customerWaitEntityDao.count();
    }

    @Override
    public void insertWaitCustomer(String customerId, String customerName, String createTime) {
        Map<String, String> entityMap = new HashMap<String, String>(4, 1);
        entityMap.put("customerId", customerId);
        entityMap.put("customerName", customerName);
        entityMap.put("createTime", createTime);
        this.customerWaitEntityDao.insert(entityMap);
    }

    @Override
    public WaitCustomerEntity inquireWaitCustomerById(String customerId) {
        return this.customerWaitEntityDao.inquireByKey(customerId);
    }

    @Override
    public synchronized WaitCustomerEntity nextWaitCustomer() {
        WaitCustomerEntity customerWaitEntity = null;
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(1);
        inquirePageContext.setPageIndex(1);
        List<WaitCustomerEntity> customerWaitEntityList = this.customerWaitEntityDao.inquire(inquirePageContext);
        if (customerWaitEntityList.isEmpty() == false) {
            customerWaitEntity = customerWaitEntityList.get(0);
            this.customerWaitEntityDao.delete(customerWaitEntity.getCustomerId());
        }
        return customerWaitEntity;
    }

    @Override
    public void deleteCustomerWait(String customerId) {
        this.customerWaitEntityDao.delete(customerId);
    }

    @Override
    public List<WaitCustomerEntity> inquireCustomerWait(long pageIndex, long pageSize) {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(pageSize);
        inquirePageContext.setPageIndex(pageIndex);
        return this.customerWaitEntityDao.inquire(inquirePageContext);
    }
}
