package com.nd.im.customer.entity;

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
        tableName = TableNames.CUSTOMER_WAIT)
public final class CustomerWaitEntity extends Entity {
    
    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "客户用户id")
    private String userId;
    //
    @RColumnConfig(desc = "昵称")
    private String nickName;
    //
    @RColumnConfig(desc = "创建时间")
    private long createTime;

    public String getUserId() {
        return userId;
    }

    public String getNickName() {
        return nickName;
    }

    public long getCreateTime() {
        return createTime;
    }

    @Override
    public String getKeyValue() {
        return this.userId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("userId", this.userId);
        map.put("nickName", this.nickName);
        map.put("createTime", Long.toString(this.createTime));
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.userId = entityMap.get("userId");
        this.nickName = entityMap.get("nickName");
        this.createTime = Long.parseLong(entityMap.get("createTime"));
    }
}
