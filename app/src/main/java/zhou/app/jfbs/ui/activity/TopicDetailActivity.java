package zhou.app.jfbs.ui.activity;

import android.os.Bundle;

import com.zhou.appinterface.ui.ToolbarActivity;

import zhou.app.jfbs.R;

/**
 * Created by zzhoujay on 2015/9/5 0005.
 */
public class TopicDetailActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        quickFinish();
        setTitle(R.string.title_topic_detail);
    }
}
