package hi.xiaoyi311;

import com.alibaba.fastjson.JSONObject;
import hi.xiaoyi311.event.MessageEventBase;
import hi.xiaoyi311.event.MiraiEventBase;
import hi.xiaoyi311.util.Network;

/**
 * Http 轮询多线程<br/>
 * 使用多线程获取事件与信息
 */
public class MiraiHttpMessageGet extends Thread{
    /**
     * Session 管理
     */
    private final MiraiHttpSession session;

    /**
     * 初始化 Http 请求轮回
     *
     * @param session Session 管理器
     */
    protected MiraiHttpMessageGet(MiraiHttpSession session){
        this.session = session;
    }

    @Override
    public void run() {
        //直到机器人取消绑定
        while (session.isBind()){
            //获取信息队列大小
            Network.NetworkReturn ret = Network.sendGet(
                    session.getHost() + "/countMessage",
                    "sessionKey=" + session.session
            );

            //Session 是否过期，过期则等待
            if (ret.code != 3){
                //如果有信息缓存
                if (ret.data.getInteger("data") > 0){
                    //获取头部信息
                    ret = Network.sendGet(
                            session.getHost() + "/fetchMessage",
                            "sessionKey=" + session.session + "&count=5"
                    );

                    //Session 是否过期，过期则等待
                    if (ret.code != 3){

                        //遍历所有信息
                        ret.data.getJSONArray("data").forEach((data) -> {
                            JSONObject relData = (JSONObject) data;

                            //尝试获取对应事件或信息
                            //如果获取不到，便为暂不支持，不处理此事件
                            try {
                                MiraiEventBase event = (MessageEventBase) Class.forName(
                                        "hi.xiaoyi311.event."
                                        + relData.getString("type")
                                        + (relData.containsKey("messageChain") ? "Event" : "")
                                ).getConstructor().newInstance(session, relData);
                                event.doEvent(session);
                            } catch (Exception ignored) { }
                        });
                    }
                }
            }

            //等待
            try {
                //noinspection BusyWait
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
