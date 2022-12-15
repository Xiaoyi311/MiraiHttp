package io.github.xiaoyi311.entity.message;

import com.alibaba.fastjson.JSONObject;

/**
 * Mirai 信息: 普通文本
 */
public class Plain extends MessageChain {
    /**
     * 文本内容
     */
    public String msg;

    /**
     * 由文本获取
     *
     * @param text 文本
     */
    public Plain(String text){
        this.msg = text;
    }

    /**
     * 由数据信息获取
     *
     * @param data 数据信息
     */
    public Plain(JSONObject data){
        this.msg = data.getString("text");
    }

    /**
     * 将信息传为 Mirai 码
     *
     * @return Mirai 码
     */
    @Override
    public String toMiraiString() {
        return msg;
    }

    /**
     * 将信息传为 JSON 信息
     *
     * @return JSON 信息
     */
    @Override
    public JSONObject toJSONObject() {
        JSONObject data = new JSONObject();
        data.put("type", "Plain");
        data.put("text", msg);

        return data;
    }
}
