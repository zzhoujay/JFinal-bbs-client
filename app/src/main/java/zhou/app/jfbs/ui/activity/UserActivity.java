package zhou.app.jfbs.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zhou.appinterface.data.DataManager;

import zhou.app.jfbs.App;
import zhou.app.jfbs.R;
import zhou.app.jfbs.model.User;
import zhou.app.jfbs.model.UserResult;
import zhou.app.jfbs.ui.dialog.NoticeDialog;
import zhou.app.jfbs.ui.fragment.NotificationsFragment;
import zhou.app.jfbs.ui.fragment.TopicsFragment;

/**
 * Created by zhou on 15/9/8.
 */
public class UserActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private TopicsFragment topics, collects;
    private NotificationsFragment notificationsFragment;
    private ImageView icon;
    private TextView name, signature;
    private int[] titles = {R.string.my_topics, R.string.my_collects, R.string.my_notification};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();

        if (!App.isLogin()) {
            NoticeDialog dialog = NoticeDialog.newInstance(getString(R.string.notice), getString(R.string.no_login), notifierId -> {
                finish();
            });
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(), "no_login");
        } else {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading_userdate));
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            DataManager.getInstance().get(App.USER_KEY, user -> {
                if (user != null && user instanceof UserResult) {
                    initData((UserResult) user);
                } else {
                    Toast.makeText(this, R.string.failure_userdata, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initView() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPage);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        icon = (ImageView) findViewById(R.id.icon);
        name = (TextView) findViewById(R.id.name);
        signature = (TextView) findViewById(R.id.signature);

        collapsingToolbarLayout.setBackgroundResource(R.color.material_lightGreen_500);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.my_topics));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.my_collects));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.my_notification));

    }

    private void initData(UserResult userResult) {
        if (userResult == null) {
            return;
        }

        User user = userResult.user;

        Picasso.with(this).load(user.avatar).placeholder(R.drawable.ic_iconfont_tupian).error(R.drawable.ic_iconfont_tupian).into(icon);
        name.setText(user.nickName);

        topics = TopicsFragment.newInstance(userResult.topics);
        collects = TopicsFragment.newInstance(userResult.collects);
        notificationsFragment = NotificationsFragment.newInstance(App.getInstance().getToken());

        Fragment[] fragments = new Fragment[]{topics, collects, notificationsFragment};

        PagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(titles[position]);
            }
        };
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (topics != null)
            topics.setSwipeRefreshLayoutEnable(i == 0);
        if (collects != null)
            collects.setSwipeRefreshLayoutEnable(i == 0);
    }
}