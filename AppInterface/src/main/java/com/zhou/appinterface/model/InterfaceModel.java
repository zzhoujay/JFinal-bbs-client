package com.zhou.appinterface.model;

import java.io.Serializable;

/**
 * Created by zzhoujay on 2015/8/10 0010.
 */
public class InterfaceModel implements Serializable {

    public static final InterfaceModel EMPTY = new InterfaceModel();

    public boolean isEmpty() {
        return EMPTY.equals(this);
    }
}
