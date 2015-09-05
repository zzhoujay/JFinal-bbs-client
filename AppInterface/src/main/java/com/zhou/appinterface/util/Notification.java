package com.zhou.appinterface.util;

/**
 * Created by zzhoujay on 2015/9/5 0005.
 */
public interface Notification {

    /**
     * 添加通知器
     *
     * @param notifier 通知器
     */
    void addNotifier(Notifier notifier);

    /**
     * 移出通知器
     *
     * @param notifier 通知器
     */
    void removeNotifier(Notifier notifier);

    /**
     * 移出所有通知器
     */
    void removeAllNotifier();
}
