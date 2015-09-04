package com.zhou.appinterface.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.zhou.appinterface.callback.LoadCallback;
import com.zhou.appinterface.util.LogKit;

import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

/**
 * Created by zzhoujay on 2015/8/9 0009.
 * 网络请求管理器
 */
public class NetworkManager {

    private static NetworkManager networkManager;

    public static NetworkManager getInstance() {
        return networkManager;
    }

    public static void init(Context context, Gson gson) {
        networkManager = new NetworkManager(context, gson);
    }

    private OkHttpClient client;
    private Gson gson;
    private Context context;
    private InterfaceResult defaultResult;

    private NetworkManager(Context context, Gson gson) {
        this.context = context;
        client = new OkHttpClient();
        client.setWriteTimeout(3, TimeUnit.SECONDS);
        client.setReadTimeout(3, TimeUnit.SECONDS);
        client.setConnectTimeout(3, TimeUnit.SECONDS);
        client.setCookieHandler(new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL));

        this.gson = gson;
    }

    @SuppressWarnings("unchecked")
    public <T> void request(Request request, @NonNull LoadCallback<T> loadCallback, Class<T> tClass) {
        new AsyncTask<Request, Void, T>() {
            @Override
            protected T doInBackground(Request... params) {
                InterfaceResult def = null;
                try {
                    String body = client.newCall(params[0]).execute().body().string();
                    LogKit.d("result-body", body);
                    return gson.fromJson(body, tClass);
                } catch (Exception e) {
                    def = getDefaultResult(e);
                    Log.d("request", "error", e);
                }
                if (tClass.isInstance(def)) {
                    return (T) def;
                }
                return null;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void onPostExecute(T result) {
                loadCallback.loadComplete(result);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
    }

    @SuppressWarnings("unchecked")
    public <T> void request(Request request, @NonNull LoadCallback<T> loadCallback, Type type) {
        new AsyncTask<Request, Void, T>() {
            @Override
            protected T doInBackground(Request... params) {
                InterfaceResult def;
                try {
                    String body = client.newCall(params[0]).execute().body().string();
                    LogKit.d("result-body", body);
                    return gson.fromJson(body, type);
                } catch (Exception e) {
                    Log.d("request", "error", e);
                    def = getDefaultResult(e);
                }
                try {
                    TypeToken<T> tTypeToken = (TypeToken<T>) TypeToken.get(type);
                    if (tTypeToken.getRawType().isInstance(def)) {
                        return (T) def;
                    }
                }catch (Exception e){
                    LogKit.d("request","type",e);
                }
                return null;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void onPostExecute(T result) {
                loadCallback.loadComplete(result);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, request);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }

    private InterfaceResult getDefaultResult(Throwable e) {
        return defaultResult == null ? null : defaultResult.error(e);
    }

    public void setDefaultResult(InterfaceResult defaultResult) {
        this.defaultResult = defaultResult;
    }
}
