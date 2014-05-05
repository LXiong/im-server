package com.nd.im.localservice;

import com.nd.im.entity.ServiceEntity;
import com.nd.im.entity.ServiceOnlineEntity;
import com.nd.im.entity.UserTypeEnum;
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
        interfaceInfo = ServiceLocalService.class,
        description = "客服用户管理")
public class ServiceLocalServiceImpl implements ServiceLocalService {

    @InjectRDao(clazz = ServiceEntity.class)
    private REntityDao<ServiceEntity> serviceEntityDao;
    //
    @InjectRDao(clazz = ServiceOnlineEntity.class)
    private REntityDao<ServiceOnlineEntity> serviceOnlineEntityDao;

    @Override
    public void init() {
        //检测管理员用户是否存在
        ServiceEntity adminUserEntity = this.serviceEntityDao.inquireByKey(ServiceLocalService.adminUserId);
        if (adminUserEntity == null) {
            //管理员用户不存在，初始化
            Map<String, String> entityMap = new HashMap<String, String>(4, 1);
            entityMap.put("userId", ServiceLocalService.adminUserId);
            entityMap.put("userName", ServiceLocalService.adminUserName);
            entityMap.put("type", UserTypeEnum.ADMIN.name());
            entityMap.put("password", SecurityUtils.encryptByMd5(ServiceLocalService.adminUserName));
            this.serviceEntityDao.insert(entityMap);
        }
    }

    @Override
    public void insertService(Map<String, String> entityMap) {
        this.serviceEntityDao.insert(entityMap);
    }

    @Override
    public ServiceEntity inquireServiceByUserId(String userId) {
        return this.serviceEntityDao.inquireByKey(userId);
    }

    @Override
    public List<ServiceEntity> inquireService(long pageIndex, long pageSize) {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(pageSize);
        inquirePageContext.setPageIndex(pageIndex);
        return this.serviceEntityDao.inquire(inquirePageContext);
    }

    @Override
    public long countService() {
        return this.serviceEntityDao.count();
    }

    @Override
    public List<ServiceEntity> inquireServiceDESC(long pageIndex, long pageSize) {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(pageSize);
        inquirePageContext.setPageIndex(pageIndex);
        return this.serviceEntityDao.inquireDESC(inquirePageContext);
    }

    @Override
    public void online(ServiceEntity entity) {
        Map<String, String> entityMap = entity.toMap();
        entityMap.put("state", "ON");
        this.serviceOnlineEntityDao.insert(entityMap);
    }

    @Override
    public void offline(String userId) {
        this.serviceOnlineEntityDao.delete(userId);
    }

    @Override
    public synchronized ServiceOnlineEntity inquireOnlineService() {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(1);
        inquirePageContext.setPageIndex(1);
        List<ServiceOnlineEntity> entityList = this.serviceOnlineEntityDao.inquire(inquirePageContext);
        ServiceOnlineEntity entity;
        if (entityList.isEmpty()) {
            entity = null;
        } else {
            entity = entityList.get(0);
            this.serviceOnlineEntityDao.updateKeySorce(entity.getUserId(), System.currentTimeMillis());
        }
        return entity;
    }
}
