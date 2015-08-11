package zhou.app.jfbs;

import android.app.Application;

import com.zhou.appinterface.net.NetworkManager;

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


    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        NetworkManager.init(this);
    }

    public static App getInstance() {
        return app;
    }
}
