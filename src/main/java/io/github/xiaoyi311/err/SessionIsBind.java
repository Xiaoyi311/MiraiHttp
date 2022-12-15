package io.github.xiaoyi311.err;

/**
 * Session 已经绑定机器人
 */
public class SessionIsBind extends Exception {
    /**
     * 创建错误
     */
    public SessionIsBind(){
        super("Session 已经绑定机器人！");
    }
}
