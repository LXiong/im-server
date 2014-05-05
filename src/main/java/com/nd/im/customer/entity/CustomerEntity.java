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
        tableName = TableNames.CUSTOMER)
public final class CustomerEntity extends Entity {

    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "客户用户id")
    private String customerId;
    //
    @RColumnConfig(desc = "昵称")
    private String customerName;

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    @Override
    public String getKeyValue() {
        return this.customerId;
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(2, 1);
        map.put("customerId", this.customerId);
        map.put("customerName", this.customerName);
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.customerId = entityMap.get("customerId");
        this.customerName = entityMap.get("customerName");
    }
}
