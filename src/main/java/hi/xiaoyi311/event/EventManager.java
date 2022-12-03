package hi.xiaoyi311.event;

import hi.xiaoyi311.MiraiHttpSession;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * MiraiHttp 监听管理器<br/>
 * 管理并通知所有监听类
 */
public class EventManager {
    /**
     * 所有监听类与 Session 对应表
     */
    private static Map<MiraiHttpSession, Collection<MiraiEventListener>> listeners;

    /**
     * 注册监听类到指定机器人
     *
     * @param session  机器人 QQ
     * @param listener 监听类
     */
    public static void addListener(MiraiHttpSession session, MiraiEventListener listener){
        Collection<MiraiEventListener> temp = listeners.get(session);
        temp.add(listener);
        listeners.put(session, temp);
    }

    /**
     * 卸载指定机器人的监听类
     *
     * @param session  机器人 QQ
     * @param listener 监听类
     */
    public static void removeListener(MiraiHttpSession session, MiraiEventListener listener){
        Collection<MiraiEventListener> temp = listeners.get(session);
        temp.remove(listener);
        listeners.put(session, temp);
    }

    /**
     * 获取机器人对应监听类
     *
     * @param session 机器人 QQ
     * @return        对应的监听类租
     */
    protected static Iterator getListener(MiraiHttpSession session){
        Collection<MiraiEventListener> mel = listeners.get(session);
        return mel != null ? mel.iterator() : null;
    }
}
