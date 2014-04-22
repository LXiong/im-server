package com.nd.im.localservice;

import com.nd.im.entity.ServerUserEntity;
import com.nd.im.entity.ServerUserTypeEnum;
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
        interfaceInfo = ServerUserLocalService.class,
        description = "客服用户管理")
public class ServerUserLocalServiceImpl implements ServerUserLocalService {

    @InjectRDao(clazz = ServerUserEntity.class)
    private REntityDao<ServerUserEntity> serverUserEntityDao;

    @Override
    public void init() {
        //检测管理员用户是否存在
        ServerUserEntity adminUserEntity = this.serverUserEntityDao.inquireByKey(ServerUserLocalService.adminUserId);
        if (adminUserEntity == null) {
            //管理员用户不存在，初始化
            Map<String, String> entityMap = new HashMap<String, String>(4, 1);
            entityMap.put("userId", ServerUserLocalService.adminUserId);
            entityMap.put("userName", ServerUserLocalService.adminUserName);
            entityMap.put("type", ServerUserTypeEnum.ADMIN.name());
            entityMap.put("password", SecurityUtils.encryptByMd5(ServerUserLocalService.adminUserName));
            this.serverUserEntityDao.insert(entityMap);
        }
    }

    @Override
    public void insertServerUser(Map<String, String> entityMap) {
        this.serverUserEntityDao.insert(entityMap);
    }

    @Override
    public ServerUserEntity inquireServerUserByUserId(String userId) {
        return this.serverUserEntityDao.inquireByKey(userId);
    }

    @Override
    public List<ServerUserEntity> inquireServerUser(long pageIndex, long pageSize) {
        InquirePageContext inquirePageContext = new InquirePageContext();
        inquirePageContext.setPageSize(pageSize);
        inquirePageContext.setPageIndex(pageIndex);
        return this.serverUserEntityDao.inquire(inquirePageContext);
    }
}
