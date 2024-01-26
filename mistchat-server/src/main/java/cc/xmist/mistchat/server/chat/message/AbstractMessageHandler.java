package cc.xmist.mistchat.server.chat.message;

import cc.xmist.mistchat.server.chat.message.req.MessageRequest;
import cc.xmist.mistchat.server.chat.model.dao.MessageDao;
import cc.xmist.mistchat.server.chat.model.entity.Message;
import cc.xmist.mistchat.server.chat.model.enums.MessageType;
import cc.xmist.mistchat.server.chat.model.req.ChatMessageRequest;
import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractMessageHandler<T extends MessageRequest> {
    @Resource
    protected MessageDao messageDao;

    protected MessageType msgType;
    protected Class<T> requestClass;

    protected abstract MessageType getMsgType();
    /**
     * 在子类注入容器的时候注册处理器
     * getGenericSuperclass: 获取当前类的带有泛型参数的直接父类的类型
     * getActualTypeArguments： 获取表示参数化类型的实际类型参数的Type对象数组
     */
    @PostConstruct
    protected void registerHandle() {
        msgType = getMsgType();
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.requestClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        MessageHandleFactory.registerHandle(msgType, this);
    }

    public void saveMsg(Long uid, ChatMessageRequest request) {
        Message message = buildBaseMessage(uid, request);
        customSaveMsg(message, BeanUtil.toBean(request.getData(), requestClass));
    }

    protected void customSaveMsg(Message message, T data) {
        messageDao.save(message);
    }

    private Message buildBaseMessage(Long uid, ChatMessageRequest request) {
        Message message = Message.builder()
                .uid(uid)
                .type(request.getType())
                .roomId(request.getRoomId())
                .build();
        return message;
    }

    public abstract void recall();
}
