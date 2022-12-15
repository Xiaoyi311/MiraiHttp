package io.github.xiaoyi311.event;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.github.xiaoyi311.MiraiHttpSession;
import io.github.xiaoyi311.entity.message.MessageChain;

/**
 * MiraiHttp 信息事件基类<br/>
 * 存储通用参数，读取信息链
 */
public class MessageEventBase extends MiraiEventBase {
    /**
     * 信息ID
     */
    public Integer messageId;

    /**
     * 发送时间戳
     */
    public Integer time;

    /**
     * 信息链
     */
    public MessageChain[] messages;

    /**
     * 创建事件
     *
     * @param session 触发此事件的 Session
     */
    public MessageEventBase(MiraiHttpSession session) {
        super(session);
    }

    /**
     * 获取信息链
     *
     * @param chain 信息链数据
     */
    protected MessageChain[] getMessageChain(JSONArray chain){
        //获取消息信息，位于第一个
        JSONObject data = (JSONObject) chain.get(0);
        messageId = data.getInteger("id");
        time = data.getInteger("time");
        chain.remove(0);

        return MessageChain.getMessageChain(chain);
    }
}
