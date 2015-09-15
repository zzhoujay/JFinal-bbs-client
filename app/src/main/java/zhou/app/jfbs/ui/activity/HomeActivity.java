package zhou.app.jfbs.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zhou.appinterface.context.BaseActivity;
import com.zhou.appinterface.data.DataManager;

import java.util.ArrayList;
import java.util.List;

import zhou.app.jfbs.App;
import zhou.app.jfbs.MainActivity;
import zhou.app.jfbs.R;
import zhou.app.jfbs.data.SectionProvider;
import zhou.app.jfbs.data.UserProvider;
import zhou.app.jfbs.model.Section;
import zhou.app.jfbs.model.Topic;
import zhou.app.jfbs.model.User;
import zhou.app.jfbs.model.UserResult;
import zhou.app.jfbs.ui.dialog.ConfirmDialog;
import zhou.app.jfbs.ui.dialog.NoticeDialog;
import zhou.app.jfbs.ui.fragment.TopicsFragment;
import zhou.app.jfbs.util.UserKit;

/**
 * Created by zzhoujay on 2015/8/28 0028.
 */
public class HomeActivity extends BaseActivity {

    private static final int ID_MENU_SETTING = 0x2233;
    private static final int ID_MENU_ABOUT = 0x3322;
    private static final int ID_MENU_OPTION = 0x3333;

    private DrawerLayout drawerLayout;
    private SectionProvider sectionProvider;
    private TabLayout tabLayout;
    private List<Fragment> fragments;
    private ViewPager viewPager;
    private ImageView icon;
    private TextView name;
    private View head;
    private Menu menu;
    private List<Section> sections;
    private FloatingActionButton createTopic;

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

        head = findViewById(R.id.head);
        icon = (ImageView) head.findViewById(R.id.icon);
        name = (TextView) head.findViewById(R.id.name);
        createTopic = (FloatingActionButton) findViewById(R.id.fab);

        createTopic.setImageResource(R.drawable.ic_mode_edit_white_48px);

        sectionProvider = new SectionProvider();
        DataManager.getInstance().add(sectionProvider);
        fragments = new ArrayList<>();
        initView();

        head.setOnClickListener(v -> {
            if (App.isLogin()) {
                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, QrCodeActivity.class);
                startActivityForResult(intent, QrCodeActivity.REQUEST_CODE);
            }
        });

        createTopic.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateActivity.class);
            intent.putExtra(Topic.TOPIC, true);
            startActivity(intent);
        });

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading_data));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        DataManager.getInstance().get(sectionProvider, sections -> {
            progressDialog.dismiss();
            if (sections != null) {
                setUpDate(sections);
            } else {
                Toast.makeText(this, R.string.failure_network, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPage);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (sections != null && sections.size() + 1 > menuItem.getOrder()) {
                viewPager.setCurrentItem(menuItem.getOrder(), true);
            } else {
                switch (id) {
                    case ID_MENU_SETTING:
                        ConfirmDialog dialog = ConfirmDialog.newInstance(null, getString(R.string.alert_clear_cache), notifierId -> {
                            UserKit.logout();
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                        });
                        dialog.show(getSupportFragmentManager(), "clear_cache");
                        break;
                    case ID_MENU_ABOUT:
                        NoticeDialog noticeDialog = NoticeDialog.newInstance(getString(R.string.menu_about), getString(R.string.content_about), null);
                        noticeDialog.show(getSupportFragmentManager(), "about");
                        break;
                }
            }
            drawerLayout.closeDrawers();
            return true;
        });

        menu = navigationView.getMenu();
    }

    public void setUpDate(List<Section> sections) {
        this.sections = sections;

        tabLayout.removeAllTabs();
        fragments.clear();
        menu.clear();

        Section home = Section.home;
        tabLayout.addTab(tabLayout.newTab().setText(home.name));
        fragments.add(TopicsFragment.newInstance(home.tab));
        MenuItem h = menu.add(0, home.id, 0, home.name);
        h.setIcon(R.drawable.ic_home_white_48px);

        int i = 1;
        for (Section section : sections) {
            tabLayout.addTab(tabLayout.newTab().setText(section.name));
            fragments.add(TopicsFragment.newInstance(section.tab));
            MenuItem item = menu.add(0, section.id, i++, section.name);
            String tab = section.tab;
            if ("gs".equals(tab)) {
                item.setIcon(R.drawable.ic_supervisor_account_white_48px);
            } else if ("news".endsWith(tab)) {
                item.setIcon(R.drawable.ic_dashboard_white_48px);
            } else if ("ask".endsWith(tab)) {
                item.setIcon(R.drawable.ic_question_answer_white_48px);
            } else if ("blog".endsWith(tab)) {
                item.setIcon(R.drawable.ic_description_white_48px);
            } else {
                item.setIcon(R.drawable.ic_mood_white_48px);
            }
        }

//        SubMenu subMenu = menu.addSubMenu(1, ID_MENU_OPTION, 1 + sections.size(), R.string.menu_option);
        MenuItem itemSetting = menu.add(1, ID_MENU_SETTING, i+1, R.string.menu_clear);
        itemSetting.setIcon(R.drawable.ic_delete_white_48px);
        MenuItem itemAbout = menu.add(1, ID_MENU_ABOUT, i+2, R.string.menu_about);
        itemAbout.setIcon(R.drawable.ic_info_white_48px);


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
                return position == 0 ? home.name : sections.get(position - 1).name;
            }
        };

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);

    }

    private void setUpUserData(User user) {
        Picasso.with(this).load(user.avatar).placeholder(R.drawable.ic_account_circle_white_48px).error(R.drawable.ic_account_circle_white_48px).into(icon);
        name.setText(user.nickName);
    }

    private void setDefaultUserData() {
        icon.setImageResource(R.drawable.ic_account_circle_white_48px);
        name.setText(R.string.click_login);
    }

    private void setUser() {
        if (App.isLogin()) {
            DataManager.getInstance().get(App.USER_KEY, result -> {
                if (result != null && result instanceof UserResult) {
                    UserResult userResult = (UserResult) result;
                    setUpUserData(userResult.user);
                } else {
                    Toast.makeText(this, R.string.failure_get_user, Toast.LENGTH_SHORT).show();
                }
            });
            createTopic.setVisibility(View.VISIBLE);
        } else {
            setDefaultUserData();
            createTopic.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUser();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == QrCodeActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            String result = data.getStringExtra(QrCodeActivity.RESULT);
            String[] rs = result.split("@\\|\\|@");
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
