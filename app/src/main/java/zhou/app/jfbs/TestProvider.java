package zhou.app.jfbs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zhou.appinterface.callback.LoadCallback;
import com.zhou.appinterface.data.DataProvider;

import java.util.ArrayList;
import java.util.List;

import zhou.app.jfbs.model.Text;

/**
 * Created by zzhoujay on 2015/8/10 0010.
 */
public class TestProvider implements DataProvider<List<Text>> {

    private List<Text> texts;
    private int index = 0;

    @Override
    public void persistence() {

    }

    @Nullable
    @Override
    public List<Text> get() {
        return texts;
    }

    @Override
    public void set(@Nullable List<Text> texts) {
        this.texts = texts;
    }

    @Override
    public void loadFromLocal(@NonNull LoadCallback<List<Text>> loadCallback) {
        List<Text> ts = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            ts.add(new Text("this item is " + (index - 20 + i)));
        }
        loadCallback.loadComplete(ts);
    }

    @Override
    public void loadFromNetwork(@NonNull LoadCallback<List<Text>> loadCallback) {
        List<Text> ts = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            ts.add(new Text("this item is " + index));
            index++;
        }
        loadCallback.loadComplete(ts);
    }

    @Override
    public boolean hasLoad() {
        return texts != null;
    }

    @Override
    public boolean needCache() {
        return false;
    }

    @NonNull
    @Override
    public String key() {
        return "text";
    }
}
