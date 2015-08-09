package com.zhou.appinterface.net;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.zhou.appinterface.callback.LoadCallback;

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

    public static void init(Context context) {
        networkManager = new NetworkManager(context);
    }

    private OkHttpClient client;
    private Gson gson;

    private NetworkManager(Context context) {
        client = new OkHttpClient();
        client.setWriteTimeout(3, TimeUnit.SECONDS);
        client.setReadTimeout(3, TimeUnit.SECONDS);
        client.setConnectTimeout(3, TimeUnit.SECONDS);
        client.setCookieHandler(new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL));

        gson = new Gson();
    }

    public void request(Request request, @NonNull LoadCallback<Result> loadCallback) {
        new AsyncTask<Request, Void, Result>() {

            @Override
            protected Result doInBackground(Request... params) {
                try {
                    String body = client.newCall(params[0]).execute().body().string();
                    return gson.fromJson(body, Result.class);
                } catch (Exception e) {
                    Log.d("request", "error", e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Result result) {
                super.onPostExecute(result);
                loadCallback.loadComplete(result);
            }
        }.execute(request);
    }

}
