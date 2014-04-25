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
        tableName = TableNames.SERVICE_USER)
public final class ServiceUserEntity extends Entity {

    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "客服组用户")
    private String userId;
    //
    @RColumnConfig(desc = "客服名称")
    private String userName;
    //
    @RColumnConfig(desc = "密码")
    private String password;
    //
    @RColumnConfig(desc = "客服类型:ADMIN-管理员,LEADER-组长,MEMBER-一般客服人员")
    private String type;

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getKeyValue() {
        return this.userId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("userId", this.userId);
        map.put("userName", this.userName);
        map.put("password", this.password);
        map.put("type", this.type);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.userId = entityMap.get("userId");
        this.userName = entityMap.get("userName");
        this.password = entityMap.get("password");
        this.type = entityMap.get("type");
    }
}
