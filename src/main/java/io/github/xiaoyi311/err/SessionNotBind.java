package io.github.xiaoyi311.err;

/**
 * Session 未绑定机器人
 */
public class SessionNotBind extends Error {
    /**
     * 创建错误
     */
    public SessionNotBind(){
        super("Session 未绑定机器人！");
    }
}
