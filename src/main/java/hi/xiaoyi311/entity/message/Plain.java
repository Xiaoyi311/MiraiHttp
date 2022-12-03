package hi.xiaoyi311.entity.message;

import com.alibaba.fastjson.JSONObject;

/**
 * Mirai 信息: 普通文本
 */
public class Plain extends MessageChain {
    /**
     * 文本内容
     */
    public String msg;

    public Plain(String text){
        this.msg = text;
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
