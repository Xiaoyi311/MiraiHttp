package io.github.xiaoyi311.err;

/**
 * Session 失效
 */
public class SessionOutDate extends Exception {
    /**
     * 创建错误
     */
    public SessionOutDate(){
        super("Session 已失效! 请重新创建!");
    }
}
