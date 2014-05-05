package com.nd.im.localservice;

import com.nd.im.entity.ServiceEntity;
import com.nd.im.entity.ServiceOnlineEntity;
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

    public ServiceEntity inquireServiceByUserId(String userId);

    public List<ServiceEntity> inquireService(long pageIndex, long pageSize);

    public List<ServiceEntity> inquireServiceDESC(long pageIndex, long pageSize);

    public long countService();

    public void online(ServiceEntity entity);

    public void offline(String userId);

    public ServiceOnlineEntity inquireOnlineService();
}
