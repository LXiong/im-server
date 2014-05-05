package com.nd.im.entity;

import com.nd.im.config.TableNames;
import com.wolf.framework.dao.Entity;
import com.wolf.framework.dao.annotation.ColumnTypeEnum;
import com.wolf.framework.dao.annotation.RColumnConfig;
import com.wolf.framework.dao.annotation.RDaoConfig;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author aladdin
 */
@RDaoConfig(
        tableName = TableNames.SERVICE)
public final class ServiceEntity extends Entity {

    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "客服组用户id")
    private String serviceId;
    //
    @RColumnConfig(desc = "客服名称")
    private String serviceName;
    //
    @RColumnConfig(desc = "密码")
    private String password;
    //
    @RColumnConfig(desc = "客服类型:ADMIN-管理员,LEADER-组长,MEMBER-一般客服人员")
    private String type;

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getKeyValue() {
        return this.serviceId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("serviceId", this.serviceId);
        map.put("serviceName", this.serviceName);
        map.put("password", this.password);
        map.put("type", this.type);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.serviceId = entityMap.get("serviceId");
        this.serviceName = entityMap.get("serviceName");
        this.password = entityMap.get("password");
        this.type = entityMap.get("type");
    }
}
