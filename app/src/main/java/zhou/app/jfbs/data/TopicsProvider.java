package zhou.app.jfbs.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zhou.appinterface.callback.LoadCallback;
import com.zhou.appinterface.data.DataProvider;
import com.zhou.appinterface.net.NetworkManager;
import com.zhou.appinterface.util.LogKit;
import com.zhou.appinterface.util.Notifier;
import com.zhou.appinterface.util.Pageable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import zhou.app.jfbs.App;
import zhou.app.jfbs.R;
import zhou.app.jfbs.model.Topic;
import zhou.app.jfbs.util.FileKit;
import zhou.app.jfbs.util.HashKit;
import zhou.app.jfbs.util.NetworkKit;

/**
 * Created by zzhoujay on 2015/8/28 0028.
 * 主题列表提供器
 */
public class TopicsProvider implements DataProvider<List<Topic>> {

    public static final int LAST_PAGE = 0x2233;

    private List<Topic> topics;
    private String tab, key;
    private Pageable pageable;
    private File file;
    private int max;
    private List<Notifier> notifiers;

    public TopicsProvider(String tab, int size) {
        this.tab = tab;
        this.pageable = new Pageable(1, size);
        key = HashKit.md5(tab == null ? "all.cache" : (tab + ".cache"));
        file = new File(App.cacheFile(), key());
        notifiers = new ArrayList<>();
    }

    @Override
    public void persistence() {
        if (hasLoad()) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        FileKit.writeObject(file, topics);
                    } catch (Exception e) {
                        LogKit.d("persistence", "topics", e);
                    }
                }
            }.start();
        }
    }

    @Nullable
    @Override
    public List<Topic> get() {
        return topics;
    }

    @Override
    public void set(@Nullable List<Topic> topics, boolean more) {
        if (more && topics != null) {
            this.topics.addAll(topics);
        } else {
            this.topics = topics;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadByCache(@NonNull LoadCallback<List<Topic>> loadCallback) {
        List<Topic> ts = null;
        if (file.exists()) {
            try {
                ts = (List<Topic>) FileKit.readObject(file);
            } catch (Exception e) {
                LogKit.d("loadByCache", "topics", e);
            }
        }
        loadCallback.loadComplete(ts);
    }

    @Override
    public void load(@NonNull LoadCallback<List<Topic>> loadCallback, boolean more) {
        if (NetworkManager.getInstance().isNetworkConnected()) {
            if (more) {
                pageable.next();
            } else {
                pageable.reset();
            }
            NetworkKit.index(pageable.pageNo, tab, pageable.pageSize, result -> {
                List<Topic> ts = null;
                if (result.isSuccessful()) {
                    ts = result.detail.list;
                    if (result.detail.lastPage||result.detail.list.isEmpty()) {
                        noticeLastPage();
                    }
                } else {
                    pageable.prev();
                    LogKit.d("load", "topics,failure,code:" + result.code + ",msg:" + result.description);
                    App.toast(result.description);
                }
                loadCallback.loadComplete(ts);
            });
        } else {
            App.toast(R.string.error_network);
            loadCallback.loadComplete(null);
        }
    }

    @Override
    public boolean hasLoad() {
        return topics != null;
    }

    @Override
    public boolean needCache() {
        return true;
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
        notifiers.add(notifier);
    }

    @Override
    public void removeNotifier(Notifier notifier) {
        notifiers.remove(notifier);
    }

    @Override
    public void removeAllNotifier() {
        notifiers.clear();
    }

    private void noticeLastPage() {
        for (Notifier notifier : notifiers) {
            notifier.notice(LAST_PAGE);
        }
    }
}
