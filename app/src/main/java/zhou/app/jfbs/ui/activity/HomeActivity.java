package zhou.app.jfbs.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.zhou.appinterface.data.DataManager;
import com.zhou.appinterface.util.LogKit;

import java.util.ArrayList;
import java.util.List;

import zhou.app.jfbs.R;
import zhou.app.jfbs.data.SectionProvider;
import zhou.app.jfbs.model.Section;
import zhou.app.jfbs.ui.fragment.TopicsFragment;

/**
 * Created by zzhoujay on 2015/8/28 0028.
 */
public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SectionProvider sectionProvider;
    private TabLayout tabLayout;
    private List<Fragment> fragments;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        sectionProvider = new SectionProvider();
        fragments = new ArrayList<>();
        initView();
    }

    private void initView() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPage);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        DataManager.getInstance().get(sectionProvider, sections -> {
            if (sections != null) {
                setUpDate(sections);
            } else {
                Toast.makeText(this, R.string.failure_network, Toast.LENGTH_SHORT).show();
            }
        });


        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {

            }
            return true;
        });
    }

    public void setUpDate(List<Section> sections) {

        tabLayout.removeAllTabs();
        fragments.clear();

        Section home = Section.home;
        tabLayout.addTab(tabLayout.newTab().setText(home.name));
        fragments.add(TopicsFragment.newInstance(home.tab));

        for (Section section : sections) {
            tabLayout.addTab(tabLayout.newTab().setText(section.name));
            fragments.add(TopicsFragment.newInstance(section.tab));
        }

        PagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return position==0?home.name:sections.get(position-1).name;
            }
        };

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
