package zhou.app.jfbs.util;

import com.zhou.appinterface.callback.LoadCallback;
import com.zhou.appinterface.data.DataManager;

import java.util.List;

import zhou.app.jfbs.App;
import zhou.app.jfbs.model.Topic;
import zhou.app.jfbs.model.UserResult;

/**
 * Created by zhou on 15/9/12.
 */
public class UserKit {

    public static void isCollected(Topic topic,LoadCallback<Boolean> loadCallback) {
        if (App.isLogin()) {
            DataManager.getInstance().get(App.USER_KEY, user -> {
                if (user != null && user instanceof UserResult) {
                    List<Topic> topics = ((UserResult) user).collects;
                    loadCallback.loadComplete(topics.contains(topic));
                }else {
                    loadCallback.loadComplete(false);
                }
            });
        }else {
            loadCallback.loadComplete(false);
        }
    }
}
