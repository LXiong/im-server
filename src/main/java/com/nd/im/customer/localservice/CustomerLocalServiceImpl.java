package com.nd.im.customer.localservice;

import com.nd.im.customer.entity.CustomerEntity;
import com.nd.im.customer.entity.CustomerWaitEntity;
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
    @InjectRDao(clazz = CustomerWaitEntity.class)
    private REntityDao<CustomerWaitEntity> customerWaitEntityDao;

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
        this.customerEntityDao.updateKeySorce(entity.getUserId(), System.currentTimeMillis());
        return entity;
    }

    @Override
    public CustomerEntity inquireCustomerByUserId(String userId) {
        return this.customerEntityDao.inquireByKey(userId);
    }

    @Override
    public long countCustomerWaitNum() {
        return this.customerWaitEntityDao.count();
    }

    @Override
    public void insertCustomerWait(String userId, String nickName, String createTime) {
        Map<String, String> entityMap = new HashMap<String, String>(4, 1);
        entityMap.put("userId", userId);
        entityMap.put("nickName", nickName);
        entityMap.put("createTime", createTime);
        this.customerWaitEntityDao.insert(entityMap);
    }

    @Override
    public CustomerWaitEntity inquireCustomerWaitByUserId(String userId) {
        return this.customerWaitEntityDao.inquireByKey(userId);
    }

    @Override
    public synchronized CustomerWaitEntity nextCustomerWait() {
        CustomerWaitEntity customerWaitEntity = null;
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(1);
        inquirePageContext.setPageIndex(1);
        List<CustomerWaitEntity> customerWaitEntityList = this.customerWaitEntityDao.inquire(inquirePageContext);
        if (customerWaitEntityList.isEmpty() == false) {
            customerWaitEntity = customerWaitEntityList.get(0);
            this.customerWaitEntityDao.delete(customerWaitEntity.getUserId());
        }
        return customerWaitEntity;
    }

    @Override
    public void deleteCustomerWait(String userId) {
        this.customerWaitEntityDao.delete(userId);
    }

    @Override
    public List<CustomerWaitEntity> inquireCustomerWait(long pageIndex, long pageSize) {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(50);
        inquirePageContext.setPageIndex(1);
        return this.customerWaitEntityDao.inquire(inquirePageContext);
    }
}
