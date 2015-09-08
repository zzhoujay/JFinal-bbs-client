package zhou.app.jfbs.ui.activity;

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

import zhou.app.jfbs.R;
import zhou.app.jfbs.ui.fragment.TopicsFragment;

/**
 * Created by zhou on 15/9/8.
 */
public class UserActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private TopicsFragment topicsFragment;
    private ImageView icon;
    private TextView name, signature;

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
    }

    private void initView() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPage);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        icon= (ImageView) findViewById(R.id.icon);
        name= (TextView) findViewById(R.id.name);
        signature= (TextView) findViewById(R.id.signature);

        collapsingToolbarLayout.setBackgroundResource(R.color.material_lightGreen_500);

        tabLayout.addTab(tabLayout.newTab().setText("xxxx"));
        topicsFragment = new TopicsFragment();
        PagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return topicsFragment;
            }

            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "xxxx";
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
        topicsFragment.setSwipeRefreshLayoutEnable(i == 0);
    }
}
