package zhou.app.jfbs;

import android.app.Application;
import android.widget.Toast;

import com.bettervectordrawable.VectorDrawableCompat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhou.appinterface.callback.LoadCallback;
import com.zhou.appinterface.data.DataManager;
import com.zhou.appinterface.net.NetworkManager;
import com.zhou.appinterface.util.Notifier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import zhou.app.jfbs.data.UserProvider;
import zhou.app.jfbs.model.Result;
import zhou.app.jfbs.model.UserResult;
import zhou.app.jfbs.util.HashKit;

/**
 * Created by zzhoujay on 2015/8/11 0011.
 */
public class App extends Application {

    public static final String SITE_URL = "http://jfbbs.tomoya.cn";
    public static final String SECTIONS_URL = SITE_URL + "/api/section";
    public static final String INDEX_URL = SITE_URL + "/api/index";
    public static final String TOPIC_URL = SITE_URL + "/api/topic";
    public static final String CREATE_TOPIC_URL = SITE_URL + "/api/topic/create";
    public static final String COLLECT_URL = SITE_URL + "/api/collect";
    public static final String COLLECT_DELETE_URL = SITE_URL + "/api/collect/delete";
    public static final String REPLY_CREATE_URL = SITE_URL + "/api/reply/create";
    public static final String USER_URL = SITE_URL + "/api/user";
    public static final String MISSION_DAILY_URL = SITE_URL + "/api/mission/daily";
    public static final String NOTIFICATION_COUNT_URL = SITE_URL + "/api/notification/countnotread";
    public static final String NOTIFICATION_URL = SITE_URL + "/api/notification";

    public static final String SAVE_SECTIONS = HashKit.md5("sections.cache");
    // 保存用户信息使用的key
    public static final String USER_KEY = HashKit.md5("user.cache");
    // 持久化token使用的key
    public static final String TOKEN_KEY = HashKit.md5("token.cache");

    public static final String TOKEN = "token";

    public static final int ID_NOTICE_USER_UPDATE = 0x788990;

    private static App app;

    private String token;

    private List<Notifier> userInfoUpdateNotifier;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        NetworkManager.init(this, gson);
        NetworkManager.getInstance().setDefaultResult(new Result<>());

        VectorDrawableCompat.enableResourceInterceptionFor(getResources(),
                R.drawable.ic_add_white_48px,
                R.drawable.ic_close_white_48px,
                R.drawable.ic_message_white_48px,
                R.drawable.ic_done_white_48px,
                R.drawable.ic_account_circle_white_48px,
                R.drawable.ic_home_white_48px,
                R.drawable.ic_mood_white_48px,
                R.drawable.ic_question_answer_white_48px,
                R.drawable.ic_description_white_48px,
                R.drawable.ic_dashboard_white_48px,
                R.drawable.ic_supervisor_account_white_48px,
                R.drawable.ic_mode_edit_white_48px,
                R.drawable.ic_settings_white_48px,
                R.drawable.ic_info_white_48px,
                R.drawable.ic_turned_in_not_white_48px,
                R.drawable.ic_turned_in_white_48px);

        UserProvider up = UserProvider.loadFromCache();
        if (up != null) {
            DataManager.getInstance().add(up);
            setToken(up.getToken());
        }

        userInfoUpdateNotifier = new ArrayList<>();
    }

    public static App getInstance() {
        return app;
    }

    public static File cacheFile() {
        return app.getCacheDir();
    }

    public static void toast(String msg) {
        Toast.makeText(app, msg, Toast.LENGTH_SHORT).show();
    }

    public static void toast(int id) {
        Toast.makeText(app, id, Toast.LENGTH_SHORT).show();
    }

    public static boolean isLogin() {
        return app.getToken() != null && DataManager.getInstance().exist(USER_KEY);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void requestRefreshUserInfo(LoadCallback<UserResult> loadCallback) {
        if (isLogin()) {
            DataManager.getInstance().update(USER_KEY, loadCallback);
        } else {
            loadCallback.loadComplete(null);
        }
    }

    public void addUserUpdateNotifier(Notifier notifier) {
        userInfoUpdateNotifier.add(notifier);
    }

    public void removeUserUpdateNotifier(Notifier notifier) {
        userInfoUpdateNotifier.remove(notifier);
    }

    public void noticeUserUpdate() {
        for (Notifier notifier : userInfoUpdateNotifier) {
            notifier.notice(ID_NOTICE_USER_UPDATE);
        }
    }
}
