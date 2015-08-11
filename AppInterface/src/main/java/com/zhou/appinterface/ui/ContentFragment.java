package com.zhou.appinterface.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhou.appinterface.R;
import com.zhou.appinterface.data.DataManager;
import com.zhou.appinterface.data.DataProvider;
import com.zhou.appinterface.data.DataViewer;
import com.zhou.appinterface.model.InterfaceModel;

/**
 * Created by zzhoujay on 2015/8/11 0011.
 */
public class ContentFragment<T extends InterfaceModel> extends Fragment implements DataViewer<T> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ScrollView scrollView;
    private LinearLayout failure, empty;
    private TextView failureText, emptyText;
    private State state;
    private DataProvider<T> provider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.interface_fragment_content, container, false);
        initView(v);
        afterInitView(inflater);
        requestSetup();
        return v;
    }

    protected void initView(View v) {
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.interface_swipeRefreshLayout);
        failure = (LinearLayout) v.findViewById(R.id.interface_fragment_failure);
        empty = (LinearLayout) v.findViewById(R.id.interface_fragment_empty);
        failureText = (TextView) v.findViewById(R.id.interface_fragment_failure_text);
        emptyText = (TextView) v.findViewById(R.id.interface_fragment_nodata_text);
        scrollView = (ScrollView) v.findViewById(R.id.interface_scrollview);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(this::requestRefresh);
    }

    protected void afterInitView(LayoutInflater inflater) {

    }

    protected void initData(@NonNull T t) {

    }

    protected void requestRefresh() {
        if (provider != null) {
            onLoading();
            DataManager.getInstance().update(provider, this::setupData);
        } else {
            onEmpty();
        }
    }

    protected void requestSetup() {
        if (provider != null) {
            onLoading();
            DataManager.getInstance().get(provider, this::setupData);
        } else {
            onEmpty();
        }
    }

    public void onEmpty() {
        swipeRefreshLayout.setVisibility(View.INVISIBLE);
        failure.setVisibility(View.INVISIBLE);
        empty.setVisibility(View.INVISIBLE);
        state = State.empty;
    }

    public void onFailure() {
        swipeRefreshLayout.setVisibility(View.INVISIBLE);
        failure.setVisibility(View.VISIBLE);
        empty.setVisibility(View.INVISIBLE);
        state = State.failure;
    }

    public void onLoading() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);
        failure.setVisibility(View.INVISIBLE);
        empty.setVisibility(View.INVISIBLE);
        state = State.loading;
    }

    public void onSuccess() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        failure.setVisibility(View.INVISIBLE);
        empty.setVisibility(View.INVISIBLE);
        state = State.success;
    }


    @Override
    public void setupData(@Nullable T t) {
        if (t == null) {
            onFailure();
        } else if (t.isEmpty()) {
            onEmpty();
        } else {
            onSuccess();
            initData(t);
        }
    }

    @Override
    public void refresh() {
        onLoading();
        requestRefresh();
    }

    @Override
    public State getState() {
        return state;
    }

    public void setContent(View view) {
        scrollView.removeAllViews();
        ScrollView.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        scrollView.addView(view,params);
    }

    public void setProvider(DataProvider<T> provider) {
        this.provider = provider;
    }
}
