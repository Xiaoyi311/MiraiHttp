package io.github.xiaoyi311;

import com.alibaba.fastjson.JSONObject;
import io.github.xiaoyi311.entity.Robot;
import io.github.xiaoyi311.err.*;
import io.github.xiaoyi311.util.Network;

/**
 * Mirai Session 存储<br/>
 * 管理 Session，创建 Api 类
 */
public class MiraiHttpSession {
    /**
     * 连接地址
     */
    private final String host;

    /**
     * 验证密钥
     */
    private final String verifyKey;

    /**
     * 是否已绑定机器人
     */
    private boolean isBind = false;

    /**
     * 轮询
     */
    private MiraiHttpMessageGet messageGet;

    /**
     * Session 信息
     */
    protected final String session;

    /**
     * 创建 Session
     *
     * @param verifyKey       密钥
     * @param host            地址，类似于：127.0.0.1:8080
     * @throws VerifyKeyError 验证密钥错误
     */
    protected MiraiHttpSession(String verifyKey, String host) throws VerifyKeyError {
        this.host = host;
        this.verifyKey = verifyKey;
        this.session = getSessionKey();
    }

    /**
     * 创建 Session，并绑定机器人<br/>
     * 需要快速绑定监听器
     *
     * @param verifyKey       密钥
     * @param host            地址，类似于：127.0.0.1:8080
     * @param qq              机器人 QQ 号
     * @throws VerifyKeyError 验证密钥错误
     * @throws RobotNotFound  机器人未找到
     */
    protected MiraiHttpSession(String verifyKey, String host, Long qq) throws VerifyKeyError, RobotNotFound {
        this.host = host;
        this.verifyKey = verifyKey;
        this.session = getSessionKey();
        try {
            bindRobot(qq);
        } catch (SessionOutDate e) {
            //获取完直接绑定也能失效？这就离谱了，直接丢错吧，我不管了
            throw new RuntimeException(e);
        } catch (SessionIsBind e) {
            //我刚新建的也能被绑定！？！？离谱死我了
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取 Session
     *
     * @return                Session
     * @throws VerifyKeyError 验证密钥错误
     */
    private String getSessionKey() throws VerifyKeyError {
        //构建参数
        JSONObject data = new JSONObject();
        data.put("verifyKey", verifyKey);

        //获取 Session
        Network.NetworkReturn ret = Network.sendPost(host + "/verify", data.toJSONString());

        //是否验证密钥错误
        if (ret.code == 1){
            throw new VerifyKeyError(verifyKey);
        }
        return ret.data.getString("session");
    }

    /**
     * 启动 Http 轮询，获取事件信息
     */
    private void messageGet() {
        messageGet = new MiraiHttpMessageGet(this);
        messageGet.start();
    }

    /**
     * 获取连接地址
     *
     * @return 链接地址
     */
    public String getHost() {
        return host;
    }

    /**
     * 是否绑定机器人完成
     *
     * @return 是否完成
     */
    public boolean isBind() {
        return isBind;
    }

    /**
     * 释放 Session，用于取消机器人绑定<br/>
     * 注意！一旦释放 Session，Session 会失效，此类也不应再使用，需重新新建<br/>
     * 在绑定成功以前不应该释放，会抛出未绑定错误
     */
    public void releaseSession() {
        //Session 是否绑定机器人
        if (!isBind){
            throw new SessionNotBind();
        }

        //构建参数
        JSONObject data = new JSONObject();
        data.put("verifyKey", verifyKey);
        data.put("qq", getRobot().qq);

        //释放 Session
        Network.sendPost(host + "/release", data.toJSONString());
        isBind = false;
    }

    /**
     * 绑定到机器人<br/>
     * 一旦绑定，事件系统就会启动，所以需在绑定前绑定事件
     *
     * @param qq               指定机器人 QQ
     * @throws RobotNotFound   指定机器人未找到
     * @throws SessionIsBind   Session 已绑定，不可重复绑定
     * @throws SessionNotBind  Session 已失效，需要重新新建
     */
    public Robot bindRobot(Long qq) throws RobotNotFound, SessionIsBind, SessionOutDate {
        //Session 是否绑定机器人
        if (isBind){
            throw new SessionIsBind();
        }

        //构建参数
        JSONObject data = new JSONObject();
        data.put("sessionKey", session);
        data.put("qq", qq);

        //绑定
        Network.NetworkReturn ret = Network.sendPost(
                host + "/bind",
                data.toJSONString()
        );

        //机器人是否存在
        if (ret.code == 2){
            throw new RobotNotFound(qq);
        }

        //Session 是否失效
        if (ret.code == 3){
            throw new SessionOutDate();
        }

        isBind = true;
        messageGet();
        return getRobot();
    }

    /**
     * 获取机器人信息<br/>
     * 在绑定成功以前不应该获取，会抛出未绑定错误
     *
     * @return 机器人实体
     */
    public Robot getRobot() {
        //Session 是否绑定机器人
        if (!isBind){
            throw new SessionNotBind();
        }

        //获取会话信息
        Network.NetworkReturn ret = Network.sendGet(
                host + "/sessionInfo",
                "sessionKey=" + session
        );

        //制作回参
        Robot robot = new Robot();
        robot.qq = ret.data.getLong("id");
        robot.name = ret.data.getString("nickname");
        robot.remark = ret.data.getString("remark");
        robot.session = this;
        return robot;
    }

    /**
     * 获取 Api 管理器<br/>
     * 可进行 Api 操作
     *
     * @return Api 管理器
     */
    public MiraiHttpApi getApi(){
        return new MiraiHttpApi(this);
    }

    /**
     * 设置新信息查询间隔时间
     * @param time 时间（毫秒）
     */
    public void setCheckTime(Integer time){ messageGet.setCheckTime(time); }
}
