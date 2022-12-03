package hi.xiaoyi311.event;

import hi.xiaoyi311.MiraiHttpSession;

import java.util.EventObject;
import java.util.Iterator;

/**
 * MiraiHttp 事件基类<br/>
 * 实现触发事件方法
 */
public class MiraiEventBase extends EventObject {
    /**
     * Session 管理
     */
    public MiraiHttpSession session;

    /**
     * 创建事件
     *
     * @param session 触发此事件的 Session
     */
    public MiraiEventBase(MiraiHttpSession session) {
        super(session);
        this.session = session;
    }

    /**
     * 触发事件<br/>
     * 遍历所有监听类
     */
    public void doEvent(MiraiHttpSession session) {
        Iterator iter = EventManager.getListener(session);
        if (iter != null){
            while (iter.hasNext()){
                onEvent((MiraiEventListener) iter.next());
            }
        }
    }

    /**
     * 触发事件<br/>
     * 此处为空，等待子类重写并通知监听类
     *
     * @param listener 监听类
     */
    protected void onEvent(MiraiEventListener listener) {}
}
