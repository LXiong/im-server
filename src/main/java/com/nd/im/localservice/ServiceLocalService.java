package com.nd.im.localservice;

import com.nd.im.entity.ServiceEntity;
import com.nd.im.entity.ServiceStateEntity;
import com.wolf.framework.local.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jianying9
 */
public interface ServiceLocalService extends Local {

    public String adminUserName = "admin";
    public String adminUserId = "10000";

    public void insertService(Map<String, String> entityMap);

    public ServiceEntity inquireServiceById(String serviceId);
    
    public void deleteService(String serviceId);

    public List<ServiceEntity> inquireService(long pageIndex, long pageSize);

    public List<ServiceEntity> inquireServiceDESC(long pageIndex, long pageSize);

    public long countService();

    public void onService(ServiceEntity entity);

    public void offService(String serviceId, String offMessage);
    
    public List<ServiceStateEntity> inquireOnService(long pageIndex, long pageSize);
}
