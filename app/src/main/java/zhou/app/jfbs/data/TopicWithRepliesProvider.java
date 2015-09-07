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
import zhou.app.jfbs.model.TopicWithReply;
import zhou.app.jfbs.util.FileKit;
import zhou.app.jfbs.util.NetworkKit;

/**
 * Created by zhou on 15/9/7.
 */
public class TopicWithRepliesProvider implements DataProvider<TopicWithReply> {

    private TopicWithReply topicWithReply;
    private File file;
    private String key;
    private String id;

    public TopicWithRepliesProvider(String id) {
        this.id = id;
        file = new File(App.cacheFile(), id);
        key = id;
    }

    @Override
    public void persistence() {
        if (hasLoad()) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        FileKit.writeObject(file, topicWithReply);
                    } catch (Exception e) {
                        LogKit.d("persistence", "save failure", e);
                    }
                }
            }.start();
        }
    }

    @Nullable
    @Override
    public TopicWithReply get() {
        return topicWithReply;
    }

    @Override
    public void set(@Nullable TopicWithReply topicWithReply, boolean more) {
        this.topicWithReply = topicWithReply;
    }

    @Override
    public void loadByCache(@NonNull LoadCallback<TopicWithReply> loadCallback) {
        TopicWithReply twr = null;
        if (file.exists()) {
            try {
                twr = (TopicWithReply) FileKit.readObject(file);
            } catch (Exception e) {
                LogKit.d("loadByCache", "topic with reply", e);
            }
        }
        loadCallback.loadComplete(twr);
    }

    @Override
    public void load(@NonNull LoadCallback<TopicWithReply> loadCallback, boolean more) {
        if (NetworkManager.getInstance().isNetworkConnected()) {
            NetworkKit.topic(id, result -> {
                TopicWithReply twr = null;
                if (result.isSuccessful()) {
                    twr = result.detail;
                } else {
                    LogKit.d("load", "topics with replies,failure,code:" + result.code + ",msg:" + result.description);
                    App.toast(result.description);
                }
                loadCallback.loadComplete(twr);
            });
        } else {
            App.toast(R.string.error_network);
            loadCallback.loadComplete(null);
        }
    }

    @Override
    public boolean hasLoad() {
        return topicWithReply != null;
    }

    @Override
    public boolean needCache() {
        return false;
    }

    @Override
    public boolean clearCache() {
        return file.exists() && file.delete();
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
}
