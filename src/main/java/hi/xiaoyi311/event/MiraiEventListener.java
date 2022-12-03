package hi.xiaoyi311.event;

import java.util.EventListener;

/**
 * MiraiHttp 事件监听类<br/>
 * 触发事件时调用对应方法
 */
public interface MiraiEventListener extends EventListener {
    /**
     * 收到群信息时
     *
     * @param event 事件信息
     */
    void onGroupMessage(GroupMessageEvent event);
}
