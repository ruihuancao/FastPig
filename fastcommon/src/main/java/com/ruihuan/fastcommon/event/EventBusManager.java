package com.ruihuan.fastcommon.event;

import org.greenrobot.eventbus.EventBus;

/**
 * Description:
 * Data：2018/4/26-10:27
 * Author: caoruihuan
 */
public class EventBusManager {

    /**
     * 注册事件监听
     * @param subscriber
     */
    public static void register(Object subscriber) {
        if (!EventBus.getDefault().isRegistered(subscriber)) {
            EventBus.getDefault().register(subscriber);
        }
    }

    /**
     * 解除注册事件
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发布事件
     * @param event
     */
    public static void post(Object event) {
        EventBus.getDefault().post(event);
    }
}
