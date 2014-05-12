package com.nd.im.message.entity;

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
 * @author jianying9
 */
@RDaoConfig(
        tableName = TableNames.MESSAGE,
        dbIndex = TableNames.MESSAGE_INDEX)
public final class MessageEntity extends Entity {

    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.KEY, desc = "消息id")
    private long messageId;
    //
    @RColumnConfig(columnTypeEnum = ColumnTypeEnum.INDEX, desc = "客户id")
    private String customerId;
    //
    @RColumnConfig(desc = "客服id")
    private String serviceId;
    //
    @RColumnConfig(desc = "发起人,c:客户,s:客服")
    private String from;
    //
    @RColumnConfig(desc = "类型:text-文字,image-图片,file-文件")
    private String type;
    //
    @RColumnConfig(desc = "消息主体,文字类型为字符,图片和文件类型为路径")
    private String message;
    //
    @RColumnConfig(desc = "时间")
    private long createTime;

    @Override
    public String getKeyValue() {
        return Long.toString(this.messageId);
    }

    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>(8, 1);
        map.put("messageId", Long.toString(this.messageId));
        map.put("customerId", this.customerId);
        map.put("serviceId", this.serviceId);
        map.put("from", this.from);
        map.put("type", this.type);
        map.put("message", this.message);
        map.put("createTime", Long.toString(this.createTime));
        return map;
    }

    @Override
    protected void parseMap(Map<String, String> entityMap) {
        this.messageId = Long.parseLong(entityMap.get("messageId"));
        this.customerId = entityMap.get("customerId");
        this.serviceId = entityMap.get("serviceId");
        this.from = entityMap.get("from");
        this.type = entityMap.get("type");
        this.message = entityMap.get("message");
        this.createTime = Long.parseLong(entityMap.get("createTime"));
    }
}
