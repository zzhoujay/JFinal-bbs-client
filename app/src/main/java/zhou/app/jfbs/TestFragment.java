package zhou.app.jfbs;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.zhou.appinterface.ui.RecyclerViewFragment;

import java.util.List;

import zhou.app.jfbs.model.Text;

/**
 * Created by zzhoujay on 2015/8/10 0010.
 */
public class TestFragment extends RecyclerViewFragment<Text> {

    private TestAdapter testAdapter;



    @Override
    protected void afterInitView() {
        testAdapter = new TestAdapter(null);
        setAdapter(testAdapter);
        setLayoutManager(new LinearLayoutManager(getActivity()));
        TestProvider provider = new TestProvider();
        setProvider(provider);

        Log.i("afterInitView", "done");
    }

    @Override
    protected void initData(@NonNull List<Text> texts) {
        testAdapter.setTexts(texts);
        Log.d("initData", "done");
    }
}
