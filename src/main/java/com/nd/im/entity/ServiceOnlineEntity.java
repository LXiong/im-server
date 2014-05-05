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
        tableName = TableNames.SERVICE_ONLINE)
public final class ServiceOnlineEntity extends Entity {

    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "客服组用户id")
    private String userId;
    //
    @RColumnConfig(desc = "状态")
    private String state;
    //
    @RColumnConfig(desc = "名称")
    private String userName;

    public String getUserId() {
        return userId;
    }

    public String getState() {
        return state;
    }

    public String getUserName() {
        return userName;
    }
    
    @Override
    public String getKeyValue() {
        return this.userId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("userId", this.userId);
        map.put("state", this.state);
        map.put("userName", this.userName);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.userId = entityMap.get("userId");
        this.state = entityMap.get("state");
        this.userName = entityMap.get("userName");
    }
}
