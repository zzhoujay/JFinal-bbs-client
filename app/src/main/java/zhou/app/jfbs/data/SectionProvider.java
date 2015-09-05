package zhou.app.jfbs.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zhou.appinterface.callback.LoadCallback;
import com.zhou.appinterface.data.DataProvider;
import com.zhou.appinterface.net.NetworkManager;
import com.zhou.appinterface.util.LogKit;
import com.zhou.appinterface.util.Notifier;

import java.io.File;
import java.util.List;

import zhou.app.jfbs.App;
import zhou.app.jfbs.R;
import zhou.app.jfbs.model.Section;
import zhou.app.jfbs.util.FileKit;
import zhou.app.jfbs.util.NetworkKit;

/**
 * Created by zzhoujay on 2015/8/28 0028.
 * 模块数据提供器
 */
public class SectionProvider implements DataProvider<List<Section>> {

    private List<Section> sections;
    private File file = new File(App.cacheFile(), App.SAVE_SECTIONS);

    @Override
    public void persistence() {
        if (hasLoad()) {
            new Thread() {
                @Override
                public void run() {
                    FileKit.writeObject(file, sections);
                }
            }.start();
        }
    }

    @Nullable
    @Override
    public List<Section> get() {
        return sections;
    }

    @Override
    public void set(@Nullable List<Section> sections,boolean more) {
        this.sections = sections;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadByCache(@NonNull LoadCallback<List<Section>> loadCallback) {
        List<Section> s = null;
        if (file.exists()) {
            try {
                s = (List<Section>) FileKit.readObject(file);
            } catch (Exception e) {
                LogKit.d("loadByCache", "sections", e);
            }
        }
        loadCallback.loadComplete(s);
    }

    @Override
    public void load(@NonNull LoadCallback<List<Section>> loadCallback,boolean more) {
        if (NetworkManager.getInstance().isNetworkConnected()) {
            NetworkKit.sections(listResult -> {
                List<Section> s = null;
                if (listResult.isSuccessful()) {
                    s = listResult.detail;
                } else {
                    LogKit.d("load", "sections,failure,code:" + listResult.code + ",msg:" + listResult.description);
                    App.toast(listResult.description);
                }
                loadCallback.loadComplete(s);
            });
        } else {
            App.toast(R.string.error_network);
            loadCallback.loadComplete(null);
        }
    }

    @Override
    public boolean hasLoad() {
        return sections != null;
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
        return App.SAVE_SECTIONS;
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
