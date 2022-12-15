package io.github.xiaoyi311.entity.message;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息链基类
 */
public class MessageChain {
    /**
     * 获取信息链
     *
     * @param dataArray 信息链数据
     * @return          信息链
     */
    public static MessageChain[] getMessageChain(JSONArray dataArray){
        List<MessageChain> ret = new ArrayList<>();
        dataArray.forEach((data) -> {
            try {
                Constructor<?> messageConst = Class.forName(
                        "io.github.xiaoyi311.entity.message."
                                + ((JSONObject) data).getString("type")
                ).getConstructor(JSONObject.class);
                ret.add((MessageChain) messageConst.newInstance(data));
            } catch (Exception ignored) { }
        });
        return ret.toArray(MessageChain[]::new);
    }

    /**
     * 将 MessageChain 数组传为 Mirai 码
     *
     * @param chains 信息数据
     * @return       转化后的数据
     */
    public static String toMiraiString(MessageChain[] chains) {
        StringBuilder ret = new StringBuilder();
        for (MessageChain chain : chains) {
            ret.append(chain.toMiraiString());
        }
        return ret.toString();
    }

    /**
     * 将 MessageChain 数组转化为 JSON 数组
     *
     * @param chains  信息数据
     * @return        转化后的 JSON 数组
     */
    public static JSONArray toJSONObject(MessageChain[] chains) {
        JSONArray ret = new JSONArray();
        for (MessageChain chain : chains) {
            ret.add(chain.toJSONObject());
        }
        return ret;
    }

    /**
     * 将 MessageChain 传为 Mirai 码<br/>
     * 此处为空，等待子类重写
     *
     * @return Mirai 码
     */
    public String toMiraiString() { return ""; }

    /**
     * 将 MessageChain 传为 JSON 信息<br/>
     * 此处为空，等待子类重写
     *
     * @return JSON 信息
     */
    public JSONObject toJSONObject() { return new JSONObject(); }
}
