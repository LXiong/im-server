package com.nd.im.message.localservice;

import com.nd.im.message.entity.MessageEntity;
import com.wolf.framework.local.Local;
import java.util.List;

/**
 *
 * @author jianying9
 */
public interface MessageLocalService extends Local {

    public MessageEntity insertTextMessageFormCustomer(String serviceId, String customerId, String message);

    public MessageEntity insertTextMessageFormService(String serviceId, String customerId, String message);

    public List<MessageEntity> inquireMessageByCustomerDESC(String customerId, long pageIndex, long pageSize);
}
