package com.zhou.appinterface.model;

import android.support.annotation.Nullable;

import com.zhou.appinterface.net.InterfaceResult;

import java.io.Serializable;

/**
 * Created by zzhoujay on 2015/8/10 0010.
 */
public class InterfaceModel implements Serializable {

    public static final InterfaceModel EMPTY = new InterfaceModel();

    @Nullable
    public transient InterfaceResult result;

    public boolean isEmpty() {
        return EMPTY.equals(this);
    }
}
