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
            provider.loadByCache((t) -> {
                if (t != null) {
                    provider.set(t,false);
                    if (provider.needCache()) {
                        provider.persistence();
                    }
                    loadCallback.loadComplete(provider.get());
                } else {
                    provider.load((tn) -> {
                        provider.set(tn,false);
                        if (provider.needCache()) {
                            provider.persistence();
                        }
                        loadCallback.loadComplete(provider.get());
                    },false);
                }
            });
        }
    }

    /**
     * 加载数据
     *
     * @param key          key
     * @param loadCallback 回调
     * @param <T>          type
     */
    @SuppressWarnings("unchecked")
    public <T> void load(String key, @NonNull LoadCallback<T> loadCallback, boolean more) {
        try {
            DataProvider<T> provider = (DataProvider<T>) providers.get(key);
            load(provider, loadCallback, more);
        } catch (Exception e) {
            Log.d("load", "DataManager", e);
            loadCallback.loadComplete(null);
        }
    }

    /**
     * 加载数据
     *
     * @param provider     数据提供器
     * @param loadCallback 回调
     * @param <T>          type
     */
    public <T> void load(DataProvider<T> provider, @NonNull LoadCallback<T> loadCallback, boolean more) {
        provider.load((t) -> {
            provider.set(t,more);
            if (provider.needCache()) {
                provider.persistence();
            }
            loadCallback.loadComplete(provider.get());
        }, more);
    }

    public <T> void update(DataProvider<T> provider, @NonNull LoadCallback<T> loadCallback) {
        load(provider, loadCallback, false);
    }

    public <T> void update(String key, @NonNull LoadCallback<T> loadCallback) {
        load(key, loadCallback, false);
    }

    public <T> void more(DataProvider<T> provider, @NonNull LoadCallback<T> loadCallback) {
        load(provider, loadCallback, true);
    }

    public <T> void more(String key, @NonNull LoadCallback<T> loadCallback) {
        load(key, loadCallback, true);
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

    public void clearAllCache() {
        for (Map.Entry<String, DataProvider> entry : providers.entrySet()) {
            DataProvider provider = entry.getValue();
            provider.clearCache();
        }
    }

    public void clearCache(String key) {
        DataProvider provider = providers.get(key);
        if (provider != null) {
            provider.clearCache();
        }
    }

    public boolean hasLoad(String key){
        DataProvider provider=providers.get(key);
        return provider != null && provider.hasLoad();
    }

    public boolean exist(String key){
        return providers.containsKey(key);
    }

}
