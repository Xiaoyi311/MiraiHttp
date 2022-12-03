package hi.xiaoyi311.entity;

/**
 * 群成员实体类
 */
public class Member {
    /**
     * QQ 号
     */
    public Integer qq;

    /**
     * 群名称
     */
    public String memberName;

    /**
     * 权限
     */
    public Permission permission;

    /**
     * 群加入时间
     */
    public Integer joinTime;

    /**
     * 最后讲话时间
     */
    public Integer lastSpeckTime;

    /**
     * 禁言剩余时间
     */
    public Integer muteTimeRemaining;

    /**
     * 所在的群
     */
    public Group group;
}
