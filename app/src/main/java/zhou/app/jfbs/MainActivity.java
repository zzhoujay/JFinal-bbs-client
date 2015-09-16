package zhou.app.jfbs;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.zhou.appinterface.context.ActivityStack;
import com.zhou.appinterface.context.BaseActivity;
import com.zhou.appinterface.data.DataManager;
import com.zhou.appinterface.util.LogKit;

import java.util.Arrays;

import zhou.app.jfbs.data.UserProvider;
import zhou.app.jfbs.ui.activity.HomeActivity;
import zhou.app.jfbs.ui.activity.QrCodeActivity;
import zhou.app.jfbs.ui.activity.UserActivity;

public class MainActivity extends BaseActivity {

    private FloatingActionButton open;
    private View parent;
    private float scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActivityStack.getInstance().closeOthers();

        open = (FloatingActionButton) findViewById(R.id.fab);

        parent = findViewById(R.id.parent);



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

        /*NetworkKit.userInfo("f9fc3ec0c8a145b1ad7ca28563fea066", result -> {
            UserResult user = result.detail;
            LogKit.d("user", user);
        });

        startActivity(new Intent(this, QrCodeActivity.class));*/

    }

    private void open() {
        int ph = parent.getHeight();
        int pw = parent.getWidth();
        scale = (float) (Math.sqrt(ph * ph + pw * pw) / open.getHeight());
        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("scaleX", scale);
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("scaleY", scale);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(open, holderX, holderY).setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public void qrCode(View view) {
        open();
//        startActivity(new Intent(this, HomeActivity.class));
//        startActivityForResult(new Intent(this, QrCodeActivity.class), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 12 && resultCode == RESULT_OK) {
            String result = data.getStringExtra(QrCodeActivity.RESULT);
            LogKit.d("result", result);
            String[] rs = result.split("@\\|\\|@");
            LogKit.d("jj", Arrays.toString(rs));
            if (rs.length == 2) {
                UserProvider provider = new UserProvider(rs[0]);
                DataManager.getInstance().add(provider);
                App.getInstance().setToken(rs[0]);
                startActivity(new Intent(this, UserActivity.class));
            } else {
                Toast.makeText(this, R.string.error_qr_code, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
