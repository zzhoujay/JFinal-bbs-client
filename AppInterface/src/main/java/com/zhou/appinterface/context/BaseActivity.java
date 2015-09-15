package com.zhou.appinterface.context;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by zhou on 15/9/15.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().add(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.getInstance().remove(this);
    }
}
