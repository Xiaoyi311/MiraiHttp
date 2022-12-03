package hi.xiaoyi311;

import hi.xiaoyi311.err.RobotNotFound;
import hi.xiaoyi311.err.VerifyKeyError;
import hi.xiaoyi311.event.EventManager;
import hi.xiaoyi311.event.MiraiEventListener;

/**
 * MiraiHttp 主类
 */
public class MiraiHttp {
    /**
     * 创建 Session，并绑定机器人<br/>
     * 可避免 Session 超时，但是如果不快速注册监听器，部分信息可能无法及时收到
     *
     * @param verifyKey       验证密钥
     * @param host            连接地址，类似于：127.0.0.1:8080
     * @param qq              机器人 QQ
     * @return                Session 管理
     * @throws VerifyKeyError 验证密钥错误
     * @throws RobotNotFound  指定机器人未找到
     */
    public static MiraiHttpSession createSession(String verifyKey, String host, Long qq) throws VerifyKeyError, RobotNotFound {
        return new MiraiHttpSession(verifyKey, host, qq);
    }

    /**
     * 创建 Session<br/>
     * 并不推荐，如果长时间（30s）不绑定需要重新创建
     *
     * @param verifyKey       验证密钥
     * @param host            连接地址，类似于：127.0.0.1:8080
     * @return                Session 管理
     * @throws VerifyKeyError 验证密钥错误
     */
    public static MiraiHttpSession createSession(String verifyKey, String host) throws VerifyKeyError {
        return new MiraiHttpSession(verifyKey, host);
    }

    /**
     * 注册事件监听器<br/>
     * 建议在绑定机器人前进行注册，防止部分信息无法接收
     *
     * @param listener 监听器
     * @param session  对应的 Session
     */
    public static void registerListener(MiraiEventListener listener, MiraiHttpSession session){
        EventManager.addListener(session, listener);
    }

    /**
     * 卸载事件监听器
     *
     * @param listener 监听器
     * @param session  对应的 Session
     */
    public static void removeListener(MiraiEventListener listener, MiraiHttpSession session){
        EventManager.removeListener(session, listener);
    }
}
