package com.zhou.appinterface.data;

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
    void initData(T t);

    /**
     * 刷新数据
     */
    void refresh();

    /**
     * 数据加载失败时
     */
    void failure();

    /**
     * 加载中
     */
    void loading();
}
