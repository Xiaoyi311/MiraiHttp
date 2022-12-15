package io.github.xiaoyi311;

import com.alibaba.fastjson.JSONObject;
import io.github.xiaoyi311.entity.message.MessageChain;
import io.github.xiaoyi311.err.SessionNotBind;
import io.github.xiaoyi311.util.Network;

/**
 * MiraiHttp Api 管理
 */
public class MiraiHttpApi {
    /**
     * Session
     */
    private MiraiHttpSession session;

    /**
     * 创建一个 Api
     *
     * @param session Session
     */
    protected MiraiHttpApi(MiraiHttpSession session){
        this.session = session;
    }

    /**
     * 发送群信息
     *
     * @param group   群 ID
     * @param message 信息内容
     * @return        信息 ID，如果发送失败返回 null
     */
    public String sendGroupMessage(Long group, MessageChain[] message){
        //Session 是否绑定机器人
        if (!session.isBind()){
            throw new SessionNotBind();
        }

        //构建参数
        JSONObject data = new JSONObject();
        data.put("sessionKey", session.session);
        data.put("target", group);
        data.put("messageChain", MessageChain.toJSONObject(message));

        //获取 Session
        Network.NetworkReturn ret = Network.sendPost(session.getHost() + "/sendGroupMessage", data.toJSONString());

        return ret.data.getString("messageId");
    }

    /**
     * 发送回复的群信息
     *
     * @param group   群 ID
     * @param id      要回复的信息 ID
     * @param message 信息内容
     * @return        信息 ID，如果发送失败返回 null
     */
    public String sendGroupMessage(Long group, Long id, MessageChain[] message){
        //Session 是否绑定机器人
        if (!session.isBind()){
            throw new SessionNotBind();
        }

        //构建参数
        JSONObject data = new JSONObject();
        data.put("sessionKey", session.session);
        data.put("target", group);
        data.put("quote", id);
        data.put("messageChain", MessageChain.toJSONObject(message));

        //获取 Session
        Network.NetworkReturn ret = Network.sendPost(session.getHost() + "/sendGroupMessage", data.toJSONString());

        return ret.data.getString("messageId");
    }
}
