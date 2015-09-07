package zhou.app.jfbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.zhou.appinterface.ui.ToolbarActivity;

import zhou.app.jfbs.R;
import zhou.app.jfbs.model.Topic;
import zhou.app.jfbs.ui.fragment.TopicDetailFragment;

/**
 * Created by zzhoujay on 2015/9/5 0005.
 */
public class TopicDetailActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        quickFinish();
        setTitle(R.string.title_topic_detail);

        getToolbar().setBackgroundResource(R.color.material_lightGreen_500);
        getToolbar().setTitleTextColor(getResources().getColor(R.color.material_lightGreen_50));

        Intent intent=getIntent();
        TopicDetailFragment fragment=null;
        if(intent.hasExtra(Topic.TOPIC)){
            fragment=TopicDetailFragment.newInstance((Topic) intent.getParcelableExtra(Topic.TOPIC));
        }else if(intent.hasExtra(Topic.TOPIC_ID)){
            fragment=TopicDetailFragment.newInstance(intent.getStringExtra(Topic.TOPIC_ID));
        }

        if(fragment!=null){
            setContent(fragment);
        }

    }
}
