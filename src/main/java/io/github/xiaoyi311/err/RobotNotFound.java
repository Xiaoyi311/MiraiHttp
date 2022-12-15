package io.github.xiaoyi311.err;

/**
 * 指定机器人不存在
 */
public class RobotNotFound extends Exception {
    /**
     * 登录的机器人
     */
    public Long qq;

    /**
     * 创建错误
     *
     * @param qq 使用的验证密钥
     */
    public RobotNotFound(Long qq){
        super("指定机器人不存在!");
        this.qq = qq;
    }
}
