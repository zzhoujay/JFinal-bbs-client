package com.zhou.appinterface.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.zhou.appinterface.callback.LoadCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzhoujay on 2015/8/9 0009.
 * 数据管理器
 */
public class DataManager {

    private static DataManager dataManager;

    public static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    private HashMap<String, DataProvider> providers;

    private DataManager() {
        providers = new HashMap<>();
    }

    /**
     * 添加数据提供器（不进行重复检查）
     *
     * @param provider 提供器
     */
    public void add(DataProvider provider) {
        if (provider != null)
            providers.put(provider.key(), provider);
    }

    /**
     * 添加数据提供器
     *
     * @param provider 数据提供器
     * @param check    是否进行重复检查
     */
    public void add(DataProvider provider, boolean check) {
        if (provider == null) {
            return;
        }
        if (check) {
            if (!providers.containsKey(provider.key())) {
                providers.put(provider.key(), provider);
            }
        } else {
            providers.put(provider.key(), provider);
        }
    }

    /**
     * 移除数据提供器
     *
     * @param key key
     */
    public void remove(String key) {
        providers.remove(key);
    }

    /**
     * 移出数据提供器
     *
     * @param provider 数据提供器
     */
    public void remove(DataProvider provider) {
        if (provider != null) {
            providers.remove(provider.key());
        }
    }

    /**
     * 获取数据 按照（内存->本地缓存->网络）的顺序获取
     *
     * @param key          key
     * @param loadCallback 回调
     * @param <T>          type
     */
    @SuppressWarnings("unchecked")
    public <T> void get(String key, @NonNull LoadCallback<T> loadCallback) {
        try {
            DataProvider<T> provider = (DataProvider<T>) providers.get(key);
            get(provider, loadCallback);
        } catch (Exception e) {
            Log.d("get", "DataManager", e);
            loadCallback.loadComplete(null);
        }
    }

    /**
     * 获取数据
     *
     * @param provider     数据提供器
     * @param loadCallback 回调
     * @param <T>          type
     */
    public <T> void get(DataProvider<T> provider, @NonNull LoadCallback<T> loadCallback) {
        if (provider.hasLoad()) {
            loadCallback.loadComplete(provider.get());
        } else {
            provider.loadFromLocal((t) -> {
                if (t != null) {
                    provider.set(t);
                    if (provider.needCache()) {
                        provider.persistence();
                    }
                    loadCallback.loadComplete(provider.get());
                } else {
                    provider.loadFromNetwork((tn) -> {
                        provider.set(tn);
                        if (provider.needCache()) {
                            provider.persistence();
                        }
                        loadCallback.loadComplete(provider.get());
                    });
                }
            });
        }
    }

    /**
     * 更新数据
     *
     * @param key          key
     * @param loadCallback 回调
     * @param <T>          type
     */
    @SuppressWarnings("unchecked")
    public <T> void update(String key, @NonNull LoadCallback<T> loadCallback) {
        try {
            DataProvider<T> provider = (DataProvider<T>) providers.get(key);
            update(provider, loadCallback);
        } catch (Exception e) {
            Log.d("update", "DataManager", e);
            loadCallback.loadComplete(null);
        }
    }

    /**
     * 更新数据
     *
     * @param provider     数据提供器
     * @param loadCallback 回调
     * @param <T>          type
     */
    public <T> void update(DataProvider<T> provider, @NonNull LoadCallback<T> loadCallback) {
        provider.loadFromNetwork((t) -> {
            provider.set(t);
            if (provider.needCache()) {
                provider.persistence();
            }
            loadCallback.loadComplete(provider.get());
        });
    }

    /**
     * 持久化所有数据
     */
    public void persistence() {
        for (Map.Entry<String, DataProvider> entry : providers.entrySet()) {
            DataProvider dataProvider = entry.getValue();
            if (dataProvider.needCache()) {
                dataProvider.persistence();
            }
        }
    }

}
