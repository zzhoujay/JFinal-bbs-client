package zhou.app.jfbs.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.zhou.appinterface.ui.ToolbarActivity;

import zhou.app.jfbs.App;
import zhou.app.jfbs.R;
import zhou.app.jfbs.model.Topic;
import zhou.app.jfbs.ui.dialog.NoticeDialog;
import zhou.app.jfbs.ui.fragment.CreateFragment;

/**
 * Created by zhou on 15/9/13.
 */
public class CreateActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quickFinish();

        Intent intent = getIntent();
        boolean isTopic = intent.getBooleanExtra(Topic.TOPIC, false);
        setTitle(isTopic ? R.string.title_create_topic : R.string.title_create_reply);
        String topicId = null;
        if (intent.hasExtra(Topic.TOPIC_ID)) {
            topicId = intent.getStringExtra(Topic.TOPIC_ID);
        }
        if (!App.isLogin()) {
            NoticeDialog dialog = NoticeDialog.newInstance(getString(R.string.notice), getString(R.string.no_login), notifierId -> {
                finish();
            });
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(), "no_login");
        } else {
            CreateFragment createFragment = CreateFragment.newInstance(isTopic, App.getInstance().getToken(), topicId);
            setContent(createFragment);
        }
    }
}
