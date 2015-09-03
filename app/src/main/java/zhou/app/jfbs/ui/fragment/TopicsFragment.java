package zhou.app.jfbs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import zhou.app.jfbs.R;
import zhou.app.jfbs.data.TopicsProvider;
import zhou.app.jfbs.ui.adapter.TopicAdapter;

/**
 * Created by zzhoujay on 2015/8/28 0028.
 */
public class TopicsFragment extends Fragment {

    private final static String TAB = "tab";

    private String tab;
    private TopicsProvider provider;
    private TopicAdapter topicAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(TAB)) {
            tab = bundle.getString(TAB);
        }
        provider = new TopicsProvider(tab, 20);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        return v;
    }

    protected void afterInitView() {
        topicAdapter = new TopicAdapter();
    }


}
