package com.zhou.appinterface.model;

import java.io.Serializable;

/**
 * Created by zzhoujay on 2015/8/10 0010.
 */
public class Model implements Serializable {
    public static final Model EMPTY = new Model();

    public boolean isEmpty() {
        return EMPTY.equals(this);
    }
}
