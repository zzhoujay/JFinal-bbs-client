package com.zhou.appinterface.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhou.appinterface.R;
import com.zhou.appinterface.data.DataManager;
import com.zhou.appinterface.data.DataProvider;
import com.zhou.appinterface.data.DataViewer;
import com.zhou.appinterface.model.Model;

import java.util.List;

/**
 * Created by zzhoujay on 2015/8/9 0009.
 * 使用RecyclerView展示数据的Fragment
 */
public class RecyclerViewFragment<T extends Model> extends Fragment implements DataViewer<List<T>> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout failure, empty;
    private TextView failureText, emptyText;
    private DataProvider<List<T>> provider;
    private State state;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.interface_fragment_recycler_view, container, false);
        initView(view);
        afterInitView();
        requestData();
        return view;
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.interface_swipeRefreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.interface_recyclerView);
        failure = (LinearLayout) view.findViewById(R.id.interface_fragment_failure);
        empty = (LinearLayout) view.findViewById(R.id.interface_fragment_empty);
        failureText = (TextView) view.findViewById(R.id.interface_fragment_failure_text);
        emptyText = (TextView) view.findViewById(R.id.interface_fragment_nodata_text);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(this::requestRefresh);
    }

    protected void initData(@NonNull List<T> ts) {

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

    public void setEmptyText(String text) {
        emptyText.setText(text);
    }

    public void setFailureText(String text) {
        failureText.setText(text);
    }

    protected void requestRefresh() {
        if (provider != null) {
            onLoading();
            DataManager.getInstance().update(provider, this::setUpData);
        } else {
            onEmpty();
        }
    }

    protected void requestData() {
        if (provider != null) {
            onLoading();
            DataManager.getInstance().get(provider, (this::setUpData));
        } else {
            onEmpty();
        }
    }

    protected void afterInitView() {

    }

    public void setRefreshing(boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        recyclerView.setLayoutManager(manager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void setProvider(DataProvider<List<T>> provider) {
        this.provider = provider;
    }

    @Override
    public void setUpData(List<T> t) {
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
}
