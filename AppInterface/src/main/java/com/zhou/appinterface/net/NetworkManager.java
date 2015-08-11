package com.zhou.appinterface.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    private Context context;

    private NetworkManager(Context context) {
        this.context = context;
        client = new OkHttpClient();
        client.setWriteTimeout(3, TimeUnit.SECONDS);
        client.setReadTimeout(3, TimeUnit.SECONDS);
        client.setConnectTimeout(3, TimeUnit.SECONDS);
        client.setCookieHandler(new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL));

        gson = new Gson();
    }

    @SuppressWarnings("unchecked")
    public <T extends InterfaceResult> void request(Request request, @NonNull LoadCallback<T> loadCallback) {
        new AsyncTask<Request, Void, InterfaceResult>() {

            @Override
            protected InterfaceResult doInBackground(Request... params) {
                try {
                    String body = client.newCall(params[0]).execute().body().string();
                    return gson.fromJson(body, InterfaceResult.class);
                } catch (Exception e) {
                    Log.d("request", "error", e);
                }
                return null;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void onPostExecute(InterfaceResult result) {
                T t = null;
                try {
                    t = (T) result;
                } catch (Exception e) {
                    Log.d("request", "cast error", e);
                }
                loadCallback.loadComplete(t);
            }
        }.execute(request);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }


}
