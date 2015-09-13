package zhou.app.jfbs.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zhou.appinterface.callback.LoadCallback;
import com.zhou.appinterface.data.DataProvider;
import com.zhou.appinterface.net.NetworkManager;
import com.zhou.appinterface.util.LogKit;
import com.zhou.appinterface.util.Notifier;

import java.io.File;

import zhou.app.jfbs.App;
import zhou.app.jfbs.R;
import zhou.app.jfbs.model.UserResult;
import zhou.app.jfbs.util.FileKit;
import zhou.app.jfbs.util.NetworkKit;

/**
 * Created by zhou on 15/9/11.
 */
public class UserProvider implements DataProvider<UserResult> {


    private UserResult userResult;
    private String key;
    private static final File FILE = new File(App.cacheFile(), App.USER_KEY);
    private static final File TOKEN_FILE = new File(App.cacheFile(), App.TOKEN_KEY);
    private String token;

    public UserProvider(String token) {
        this.token = token;
        key = App.USER_KEY;
    }

    @Override
    public void persistence() {
        if (hasLoad()) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        FileKit.writeObject(FILE, userResult);
                        FileKit.writeString(TOKEN_FILE, token);
                    } catch (Exception e) {
                        LogKit.d("persistence", "user", e);
                    }
                }
            }.start();
        }
    }

    @Nullable
    @Override
    public UserResult get() {
        return userResult;
    }

    @Override
    public void set(@Nullable UserResult userResult, boolean more) {
        this.userResult = userResult;
    }

    @Override
    public void loadByCache(@NonNull LoadCallback<UserResult> loadCallback) {
        UserResult ur = null;
        if (FILE.exists()) {
            try {
                ur = (UserResult) FileKit.readObject(FILE);
            } catch (Exception e) {
                LogKit.d("loadByCache", "user", e);
            }
        }
        loadCallback.loadComplete(ur);
    }

    @Override
    public void load(@NonNull LoadCallback<UserResult> loadCallback, boolean more) {
        if (NetworkManager.getInstance().isNetworkConnected()) {
            NetworkKit.userInfo(token, result -> {
                UserResult ur = null;
                if (result.isSuccessful()) {
                    ur = result.detail;
                } else {
                    App.toast(result.description);
                    LogKit.d("load", "user,failure,code:" + result.code + ",msg:" + result.description);
                }
                loadCallback.loadComplete(ur);
            });
        } else {
            App.toast(R.string.error_network);
            loadCallback.loadComplete(null);
        }
    }

    @Override
    public boolean hasLoad() {
        return userResult != null;
    }

    @Override
    public boolean needCache() {
        return true;
    }

    @Override
    public boolean clearCache() {
        return FILE.exists() && FILE.delete() && TOKEN_FILE.exists() && TOKEN_FILE.delete();
    }

    @NonNull
    @Override
    public String key() {
        return key;
    }

    @Override
    public void addNotifier(Notifier notifier) {

    }

    @Override
    public void removeNotifier(Notifier notifier) {

    }

    @Override
    public void removeAllNotifier() {

    }

    public String getToken(){
        return this.token;
    }

    public static UserProvider loadFromCache() {
        if(TOKEN_FILE.exists()){
            return new UserProvider(FileKit.readString(TOKEN_FILE));
        }
        return null;
    }

    public static String loadToken(){
        return FileKit.readString(TOKEN_FILE);
    }
}
