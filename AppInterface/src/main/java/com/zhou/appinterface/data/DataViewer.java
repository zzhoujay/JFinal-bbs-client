package com.zhou.appinterface.data;

import android.support.annotation.Nullable;

/**
 * Created by zzhoujay on 2015/8/9 0009.
 * 数据展示器
 */
public interface DataViewer<T> {

    /**
     * 初始化数据
     *
     * @param t 数据
     */
    void setupData(@Nullable T t);

    /**
     * 刷新数据
     */
    void refresh();

    /**
     * 加载更多
     */
    void loadMore();

    /**
     * 获取状态
     *
     * @return 当前状态
     */
    State getState();

    enum State {
        loading,// 加载中
        failure,// 加载失败
        empty,// 空数据
        success //加载成功
    }
}
