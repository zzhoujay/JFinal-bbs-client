package com.zhou.appinterface.callback;

import android.support.annotation.Nullable;

/**
 * Created by zzhoujay on 2015/8/9 0009.
 * 加载回调
 */
public interface LoadCallback<T> {
    /**
     * 加载完成
     *
     * @param t 数据
     */
    void loadComplete(@Nullable T t);
}
