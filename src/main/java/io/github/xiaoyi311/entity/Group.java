package io.github.xiaoyi311.entity;

/**
 * 群实体类
 */
public class Group {
    /**
     * 群号码
     */
    public Long id;

    /**
     * 群名称
     */
    public String groupName;

    /**
     * 机器人在群里的权限
     */
    public Permission permission;
}
