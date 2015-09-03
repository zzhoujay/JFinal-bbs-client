package com.zhou.appinterface.data;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zzhoujay on 2015/8/28 0028.
 */
public class DataCenter {

    public HashMap<String, Object> datas;

    private static DataCenter dataCenter;

    private DataCenter() {
        datas = new HashMap<>();
    }

    public static DataCenter getInstance() {
        if (dataCenter == null) {
            dataCenter = new DataCenter();
        }
        return dataCenter;
    }

    public void share(String key, Object data) {
        datas.put(key, data);
    }

    public void clear(String key) {
        datas.remove(key);
    }

    public Object get(String key) {
        return datas.get(key);
    }

}
