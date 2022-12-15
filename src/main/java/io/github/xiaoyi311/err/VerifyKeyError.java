package io.github.xiaoyi311.err;

/**
 * 验证密钥错误
 */
public class VerifyKeyError extends Exception {
    /**
     * 使用的验证密钥
     */
    public String verifyKey;

    /**
     * 创建错误
     *
     * @param verifyKey 使用的验证密钥
     */
    public VerifyKeyError(String verifyKey){
        super("验证密钥错误!");
        this.verifyKey = verifyKey;
    }
}
