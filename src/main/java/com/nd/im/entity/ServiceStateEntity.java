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
        tableName = TableNames.SERVICE_STATE,
        dbIndex = TableNames.SERVICE_STATE_INDEX)
public final class ServiceStateEntity extends Entity {

    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "客服组用户id")
    private String serviceId;
    //
    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.INDEX, desc = "状态:on,off")
    private String state;
    //
    @RColumnConfig(desc = "off状态原因")
    private String offMessage;
    //
    @RColumnConfig(desc = "名称")
    private String serviceName;

    public String getServiceId() {
        return serviceId;
    }

    public String getState() {
        return state;
    }

    public String getServiceName() {
        return serviceName;
    }
    
    @Override
    public String getKeyValue() {
        return this.serviceId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("serviceId", this.serviceId);
        map.put("state", this.state);
        map.put("serviceName", this.serviceName);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.serviceId = entityMap.get("serviceId");
        this.state = entityMap.get("state");
        this.serviceName = entityMap.get("serviceName");
    }
}
