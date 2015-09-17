package zhou.app.jfbs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.zhou.appinterface.context.ActivityStack;
import com.zhou.appinterface.context.BaseActivity;

import zhou.app.jfbs.ui.activity.HomeActivity;

public class MainActivity extends BaseActivity {

    private FloatingActionButton open;
    private View parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ActivityStack.getInstance().closeOthers();

        open = (FloatingActionButton) findViewById(R.id.fab);

        parent = findViewById(R.id.parent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (App.getInstance().hasInit())
            start();
        else
            new Handler().postDelayed(this::open, 600);
    }

    private void open() {
        int ph = parent.getHeight();
        int pw = parent.getWidth();
        float scale = (float) (Math.sqrt(ph * ph + pw * pw) / open.getHeight());
        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("scaleX", scale);
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("scaleY", scale);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(open, holderX, holderY).setDuration(500);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                start();
            }
        });
        animator.start();
    }

    private void start() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

}
