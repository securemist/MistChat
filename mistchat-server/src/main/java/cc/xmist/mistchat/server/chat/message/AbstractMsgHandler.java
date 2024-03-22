package cc.xmist.mistchat.server.chat.message;

import cc.xmist.mistchat.server.chat.dao.MessageDao;
import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.req.MessageRequest;
import cc.xmist.mistchat.server.common.enums.MessageType;
import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractMsgHandler<T> {
    @Resource
    protected MessageDao messageDao;

    protected MessageType msgType;
    protected Class<T> bodyClass;

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
        this.bodyClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
        MessageHandleFactory.registerHandle(msgType, this);
    }


    protected abstract Message customSaveMsg(Message message, T data);

    public abstract void recall();


    private T toBean(Object body) {
        if (bodyClass.isAssignableFrom(body.getClass())) {
            return (T) body;
        }
        return BeanUtil.toBean(body, bodyClass);
    }

    public Message saveMsg(Integer uid, String  roomId, MessageRequest req) {
        T body = toBean(req.getBody());
        Message m = Message.builder()
                .uid(uid)
                .roomId(roomId)
                .type(req.getType())
                .build();
        Message message = customSaveMsg(m, body);
        messageDao.save(message);
        return m;
    }
}
