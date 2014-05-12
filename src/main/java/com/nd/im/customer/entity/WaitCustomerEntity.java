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
        tableName = TableNames.WAIT_CUSTOMER,
        dbIndex = TableNames.WAIT_CUSTOMER_INDEX)
public final class WaitCustomerEntity extends Entity {
    
    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "客户用户id")
    private String customerId;
    //
    @RColumnConfig(desc = "昵称")
    private String customerName;
    //
    @RColumnConfig(desc = "排队序号")
    private long waitOrder;

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public long getWaitOrder() {
        return waitOrder;
    }

    @Override
    public String getKeyValue() {
        return this.customerId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(4, 1);
        map.put("customerId", this.customerId);
        map.put("customerName", this.customerName);
        map.put("waitOrder", Long.toString(this.waitOrder));
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.customerId = entityMap.get("customerId");
        this.customerName = entityMap.get("customerName");
        this.waitOrder = Long.parseLong(entityMap.get("waitOrder"));
    }
}
