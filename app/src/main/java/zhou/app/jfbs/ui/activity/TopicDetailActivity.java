package zhou.app.jfbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.zhou.appinterface.context.BaseActivity;

import zhou.app.jfbs.App;
import zhou.app.jfbs.R;
import zhou.app.jfbs.model.Topic;
import zhou.app.jfbs.ui.fragment.ReplyFragment;
import zhou.app.jfbs.ui.fragment.TopicDetailFragment;

/**
 * Created by zzhoujay on 2015/9/5 0005.
 */
public class TopicDetailActivity extends BaseActivity {

    private boolean quickFinish;
    private FloatingActionButton fab;
    private String topicId;
    private ReplyFragment replyFragment;
    private View bottomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        bottomView = findViewById(R.id.container_bottom);

        setSupportActionBar(toolbar);

        quickFinish();
        setTitle(R.string.title_topic_detail);

        toolbar.setBackgroundResource(R.color.material_lightGreen_500);
        toolbar.setTitleTextColor(getResources().getColor(R.color.material_lightGreen_50));

        Intent intent = getIntent();
        TopicDetailFragment fragment = null;
        if (intent.hasExtra(Topic.TOPIC)) {
            Topic topic = intent.getParcelableExtra(Topic.TOPIC);
            topicId = topic.id;
            fragment = TopicDetailFragment.newInstance(topic);
        } else if (intent.hasExtra(Topic.TOPIC_ID)) {
            topicId = intent.getStringExtra(Topic.TOPIC_ID);
            fragment = TopicDetailFragment.newInstance(topicId);
        } else {
            topicId = null;
        }

        if (fragment != null) {
            setContent(fragment);
        }

        fab.setImageResource(R.drawable.ic_message_white_48px);

        if (topicId != null && App.isLogin()) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.INVISIBLE);
        }

        fab.setOnClickListener(v->{
            Intent i=new Intent(this,CreateActivity.class);
            i.putExtra(Topic.TOPIC_ID,topicId);
            startActivity(i);
        });

    }

    private void showReplyFragment() {
        if (replyFragment == null) {
            replyFragment = ReplyFragment.newInstance(App.getInstance().getToken(), topicId);
            setBottom(replyFragment);
        } else {
            getSupportFragmentManager().beginTransaction().show(replyFragment).commit();
        }
        fab.setOnClickListener(hiddenListener);
        fab.setImageResource(R.drawable.ic_close_white_48px);
    }

    private void hiddenReplyFragment() {
        getSupportFragmentManager().beginTransaction().hide(replyFragment).commit();
        fab.setOnClickListener(showListener);
        fab.setImageResource(R.drawable.ic_message_white_48px);
    }

    public void quickFinish() {
        quickFinish = true;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setContent(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.activity_content, fragment).commit();
    }

    public void setBottom(Fragment fragment) {
        bottomView.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().add(R.id.container_bottom, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (quickFinish && item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener showListener = v -> showReplyFragment();

    private View.OnClickListener hiddenListener = v -> hiddenReplyFragment();
}
