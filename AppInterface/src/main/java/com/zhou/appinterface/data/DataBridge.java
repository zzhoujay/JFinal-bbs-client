package com.zhou.appinterface.data;

/**
 * Created by zzhoujay on 2015/8/10 0010.
 */
public class DataBridge<T> {

    private DataProvider<T> provider;
    private DataViewer<T> viewer;

    public DataBridge(DataProvider<T> provider, DataViewer<T> viewer) {
        this.provider = provider;
        this.viewer = viewer;
    }

    public void start(){
    }

    public void update(){
    }
}
