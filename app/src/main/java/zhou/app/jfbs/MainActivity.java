package zhou.app.jfbs;

import android.os.Bundle;
import android.util.Log;

import com.zhou.appinterface.ui.ToolbarActivity;
import com.zhou.appinterface.util.LogKit;

import java.util.List;

import zhou.app.jfbs.model.Section;
import zhou.app.jfbs.model.TopicPage;
import zhou.app.jfbs.model.TopicWithReply;
import zhou.app.jfbs.model.User;
import zhou.app.jfbs.model.UserResult;
import zhou.app.jfbs.util.NetworkKit;

public class MainActivity extends ToolbarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        quickFinish();
        getToolbar().setBackgroundColor(getResources().getColor(R.color.material_lightBlue_500));


        /*NetworkKit.sections(result -> {
            List<Section> sections = result.detail;
            LogKit.d("sections", sections);
        });

        NetworkKit.index(1, "gs", 20, result -> {
            TopicPage page = result.detail;
            LogKit.d("page", page);
        });*/

        /*NetworkKit.topic("9debda43e7294e5c9c1026e92e5e27ad", result -> {
            TopicWithReply topicWithReply = result.detail;
            LogKit.d("topicWithReply", topicWithReply);
        });*/

        NetworkKit.userInfo("f9fc3ec0c8a145b1ad7ca28563fea066", result -> {
            UserResult user = result.detail;
            LogKit.d("user", user);
        });

    }

}
