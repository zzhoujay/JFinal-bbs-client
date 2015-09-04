package com.zhou.appinterface.net;

import java.io.Serializable;

/**
 * Created by zzhoujay on 2015/8/9 0009.
 * 网络请求的结果
 */
public abstract class InterfaceResult implements Serializable {

    public abstract boolean isSuccessful();

    public abstract InterfaceResult error(Throwable e);

}
