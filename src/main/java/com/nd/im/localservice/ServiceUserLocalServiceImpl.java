package com.nd.im.localservice;

import com.nd.im.entity.CustomerEntity;
import com.nd.im.entity.ServiceUserEntity;
import com.nd.im.entity.ServiceUserOnlineEntity;
import com.nd.im.entity.ServiceUserTypeEnum;
import com.wolf.framework.dao.REntityDao;
import com.wolf.framework.dao.annotation.InjectRDao;
import com.wolf.framework.dao.condition.InquirePageContext;
import com.wolf.framework.local.LocalServiceConfig;
import com.wolf.framework.utils.SecurityUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jianying9
 */
@LocalServiceConfig(
        interfaceInfo = ServiceUserLocalService.class,
        description = "客服用户管理")
public class ServiceUserLocalServiceImpl implements ServiceUserLocalService {

    @InjectRDao(clazz = ServiceUserEntity.class)
    private REntityDao<ServiceUserEntity> serverUserEntityDao;
    //
    @InjectRDao(clazz = ServiceUserOnlineEntity.class)
    private REntityDao<ServiceUserOnlineEntity> serverUserOnlineEntityDao;
    //
    @InjectRDao(clazz = CustomerEntity.class)
    private REntityDao<CustomerEntity> customerEntityDao;

    @Override
    public void init() {
        //检测管理员用户是否存在
        ServiceUserEntity adminUserEntity = this.serverUserEntityDao.inquireByKey(ServiceUserLocalService.adminUserId);
        if (adminUserEntity == null) {
            //管理员用户不存在，初始化
            Map<String, String> entityMap = new HashMap<String, String>(4, 1);
            entityMap.put("userId", ServiceUserLocalService.adminUserId);
            entityMap.put("userName", ServiceUserLocalService.adminUserName);
            entityMap.put("type", ServiceUserTypeEnum.ADMIN.name());
            entityMap.put("password", SecurityUtils.encryptByMd5(ServiceUserLocalService.adminUserName));
            this.serverUserEntityDao.insert(entityMap);
        }
    }

    @Override
    public void insertServerUser(Map<String, String> entityMap) {
        this.serverUserEntityDao.insert(entityMap);
    }

    @Override
    public ServiceUserEntity inquireServerUserByUserId(String userId) {
        return this.serverUserEntityDao.inquireByKey(userId);
    }

    @Override
    public List<ServiceUserEntity> inquireServerUser(long pageIndex, long pageSize) {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(pageSize);
        inquirePageContext.setPageIndex(pageIndex);
        return this.serverUserEntityDao.inquire(inquirePageContext);
    }

    @Override
    public long countServiceUser() {
        return this.serverUserEntityDao.count();
    }

    @Override
    public List<ServiceUserEntity> inquireServerUserDESC(long pageIndex, long pageSize) {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(pageSize);
        inquirePageContext.setPageIndex(pageIndex);
        return this.serverUserEntityDao.inquireDESC(inquirePageContext);
    }

    @Override
    public void online(ServiceUserEntity entity) {
        Map<String, String> entityMap = entity.toMap();
        entityMap.put("state", "ON");
        this.serverUserOnlineEntityDao.insert(entityMap);
    }

    @Override
    public void offline(String userId) {
        this.serverUserOnlineEntityDao.delete(userId);
    }

    @Override
    public synchronized ServiceUserOnlineEntity inquireOnlineUser() {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(1);
        inquirePageContext.setPageIndex(1);
        List<ServiceUserOnlineEntity> entityList = this.serverUserOnlineEntityDao.inquire(inquirePageContext);
        ServiceUserOnlineEntity entity;
        if (entityList.isEmpty()) {
            entity = null;
        } else {
            entity = entityList.get(0);
            this.serverUserOnlineEntityDao.updateKeySorce(entity.getUserId(), System.currentTimeMillis());
        }
        return entity;
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
}
