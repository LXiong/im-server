package com.nd.im.localservice;

import com.nd.im.entity.CustomerEntity;
import com.nd.im.entity.ServiceUserEntity;
import com.nd.im.entity.ServiceUserOnlineEntity;
import com.wolf.framework.local.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jianying9
 */
public interface ServiceUserLocalService extends Local{
    
    public String adminUserName = "admin";
    public String adminUserId = "10000";
    
    public void insertServerUser(Map<String, String> entityMap);
    
    public ServiceUserEntity inquireServerUserByUserId(String userId);
    
    public List<ServiceUserEntity> inquireServerUser(long pageIndex, long pageSize);
    
    public List<ServiceUserEntity> inquireServerUserDESC(long pageIndex, long pageSize);
    
    public long countServiceUser();
    
    public void online(ServiceUserEntity entity);
    
    public void offline(String userId);
    
    public ServiceUserOnlineEntity inquireOnlineUser();
    
    public void insertCustomer(Map<String, String> entityMap);
    
    public CustomerEntity inquireCustomer();
    
    public CustomerEntity inquireCustomerByUserId(String userId);
}
