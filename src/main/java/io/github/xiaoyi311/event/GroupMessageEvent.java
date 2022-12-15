package io.github.xiaoyi311.event;

import com.alibaba.fastjson.JSONObject;
import io.github.xiaoyi311.MiraiHttpSession;
import io.github.xiaoyi311.entity.Group;
import io.github.xiaoyi311.entity.Member;
import io.github.xiaoyi311.entity.Permission;

/**
 * MiraiHttp 收到群信息事件
 */
public class GroupMessageEvent extends MessageEventBase {
    /**
     * 触发事件的群成员
     */
    public Member sender;

    /**
     * 触发事件
     *
     * @param listener 监听类
     */
    @Override
    public void onEvent(MiraiEventListener listener) {
        listener.onGroupMessage(this);
    }

    /**
     * 创建事件
     *
     * @param session 触发事件的 Session
     * @param data 事件数据
     */
    public GroupMessageEvent(MiraiHttpSession session, JSONObject data){
        super(session);

        //生成群成员数据
        JSONObject temp = data.getJSONObject("sender");
        Group temp2 = new Group();
        Member temp1 = new Member();
        temp1.qq = temp.getInteger("id");
        temp1.memberName = temp.getString("memberName");
        temp1.permission = Permission.valueOf(temp.getString("permission"));
        temp1.joinTime = temp.getInteger("joinTimestamp");
        temp1.lastSpeckTime = temp.getInteger("lastSpeakTimestamp");
        temp1.muteTimeRemaining = temp.getInteger("joinTimestamp");temp1.joinTime = temp.getInteger("muteTimeRemaining");

        //生成群数据
        temp = temp.getJSONObject("group");
        temp2.id = temp.getLong("id");
        temp2.groupName = temp.getString("name");
        temp2.permission = Permission.valueOf(temp.getString("permission"));

        //互相绑定
        temp1.group = temp2;
        sender = temp1;

        //获取信息链
        messages = getMessageChain(data.getJSONArray("messageChain"));
    }
}
