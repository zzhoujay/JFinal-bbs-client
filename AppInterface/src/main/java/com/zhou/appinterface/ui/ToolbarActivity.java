package com.zhou.appinterface.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zhou.appinterface.R;

/**
 * Created by zzhoujay on 2015/8/9 0009.
 * 自带Toolbar的Activity（需要主题设置为AppCompat，并且隐藏原来的ActionBar）
 */
public class ToolbarActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private boolean quickFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_activity_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void quickFinish() {
        quickFinish = true;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setContent(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.interface_activity_content, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (quickFinish && item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
