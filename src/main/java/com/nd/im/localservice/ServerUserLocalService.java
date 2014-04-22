package com.nd.im.localservice;

import com.nd.im.entity.ServerUserEntity;
import com.wolf.framework.local.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jianying9
 */
public interface ServerUserLocalService extends Local{
    
    public String adminUserName = "admin";
    public String adminUserId = "10000";
    
    public void insertServerUser(Map<String, String> entityMap);
    
    public ServerUserEntity inquireServerUserByUserId(String userId);
    
    public List<ServerUserEntity> inquireServerUser(long pageIndex, long pageSize);
    
}
