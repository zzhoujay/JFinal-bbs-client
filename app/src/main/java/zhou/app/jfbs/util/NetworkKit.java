package zhou.app.jfbs.util;

import android.support.annotation.NonNull;

import com.squareup.okhttp.Request;
import com.zhou.appinterface.callback.LoadCallback;
import com.zhou.appinterface.net.NetworkManager;

import java.util.List;

import zhou.app.jfbs.App;
import zhou.app.jfbs.model.Result;
import zhou.app.jfbs.model.Section;
import zhou.app.jfbs.model.Topic;
import zhou.app.jfbs.model.TopicPage;

/**
 * Created by zzhoujay on 2015/8/11 0011.
 */
public class NetworkKit {

    public static void sections(@NonNull LoadCallback<Result<List<Section>>> loadCallback) {
        Request request = new Request.Builder()
                .get().url(App.SECTIONS_URL).build();
        NetworkManager.getInstance().request(request, loadCallback);
    }

    public static void index(int p, String tab, int size, @NonNull LoadCallback<Result<TopicPage>> loadCallback) {
        Request request = new Request.Builder()
                .url(App.INDEX_URL + "?p=" + p + "&tab=" + tab + "&size=" + size).get().build();
        NetworkManager.getInstance().request(request, loadCallback);
    }

    public static void topic(String id, @NonNull LoadCallback<Result<Topic>> loadCallback) {
        Request request = new Request.Builder()
                .url(App.TOPIC_URL + "/" + id).get().build();
        NetworkManager.getInstance().request(request, loadCallback);
    }

}
