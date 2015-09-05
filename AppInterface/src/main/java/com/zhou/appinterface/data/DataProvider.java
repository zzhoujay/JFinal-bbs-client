package com.zhou.appinterface.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zhou.appinterface.callback.LoadCallback;
import com.zhou.appinterface.util.Notification;

/**
 * Created by zzhoujay on 2015/8/9 0009.
 * 数据提供器
 */
public interface DataProvider<T> extends Notification {

    /**
     * 数据持久化（应异步进行）
     */
    void persistence();

    /**
     * 获取数据
     *
     * @return 数据
     */
    @Nullable
    T get();

    /**
     * 设置数据
     *
     * @param t 数据
     */
    void set(@Nullable T t, boolean more);

    /**
     * 从缓存中加载数据（应异步实现）
     *
     * @param loadCallback 回调
     */
    void loadByCache(@NonNull LoadCallback<T> loadCallback);

    /**
     * 加载数据（必须异步实现）
     *
     * @param loadCallback 回调
     */
    void load(@NonNull LoadCallback<T> loadCallback, boolean more);

    /**
     * 是否已经加载
     *
     * @return boolean
     */
    boolean hasLoad();

    /**
     * 是否需要缓存
     *
     * @return boolean
     */
    boolean needCache();

    /**
     * 清空缓存
     */
    boolean clearCache();

    /**
     * 获取该加载器的唯一标识
     *
     * @return key
     */
    @NonNull
    String key();


}
