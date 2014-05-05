package com.nd.im.message.localservice;

import com.nd.im.config.TableNames;
import com.nd.im.key.localservice.KeyLocalService;
import com.nd.im.message.entity.MessageEntity;
import com.wolf.framework.dao.REntityDao;
import com.wolf.framework.dao.annotation.InjectRDao;
import com.wolf.framework.dao.condition.InquireRedisIndexContext;
import com.wolf.framework.local.InjectLocalService;
import com.wolf.framework.local.LocalServiceConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jianying9
 */
@LocalServiceConfig(
        interfaceInfo = MessageLocalService.class,
        description = "消息管理管理")
public class MessageLocalServiceImpl implements MessageLocalService {

    @InjectLocalService()
    private KeyLocalService keyLocalService;
    //
    @InjectRDao(clazz = MessageEntity.class)
    private REntityDao<MessageEntity> messageEntityDao;

    @Override
    public void init() {
        //初始化消息表的主键值
        long maxKeyValue = this.keyLocalService.getMaxKeyValue(TableNames.MESSAGE);
        if (maxKeyValue < 100000) {
            this.keyLocalService.updateMaxKeyValue(TableNames.MESSAGE, 100000);
        }
    }

    private long nextMessageId() {
        return this.keyLocalService.nextKeyValue(TableNames.MESSAGE);
    }

    @Override
    public MessageEntity insertTextMessageFormCustomer(final String serviceId, final String customerId, final String message) {
        Map<String, String> entityMap = new HashMap<String, String>(8, 1);
        final long messageId = this.nextMessageId();
        final String from = "c";
        final String type = "text";
        final long createTime = System.currentTimeMillis();
        entityMap.put("messageId", Long.toString(messageId));
        entityMap.put("customerId", customerId);
        entityMap.put("serviceId", serviceId);
        entityMap.put("from", from);
        entityMap.put("type", type);
        entityMap.put("message", message);
        entityMap.put("createTime", Long.toString(createTime));
        return this.messageEntityDao.insertAndInquire(entityMap);
    }

    @Override
    public MessageEntity insertTextMessageFormService(String serviceId, String customerId, String message) {
        Map<String, String> entityMap = new HashMap<String, String>(8, 1);
        final long messageId = this.nextMessageId();
        final String from = "s";
        final String type = "text";
        final long createTime = System.currentTimeMillis();
        entityMap.put("messageId", Long.toString(messageId));
        entityMap.put("customerId", customerId);
        entityMap.put("serviceId", serviceId);
        entityMap.put("from", from);
        entityMap.put("type", type);
        entityMap.put("message", message);
        entityMap.put("createTime", Long.toString(createTime));
        return this.messageEntityDao.insertAndInquire(entityMap);
    }

    @Override
    public List<MessageEntity> inquireMessageByCustomerDESC(String customerId, long pageIndex, long pageSize) {
        InquireRedisIndexContext inquirePageContext = new InquireRedisIndexContext("customerId", customerId);
        inquirePageContext.setPageSize(1);
        inquirePageContext.setPageIndex(1);
        return this.messageEntityDao.inquireByIndexDESC(inquirePageContext);
    }
}
