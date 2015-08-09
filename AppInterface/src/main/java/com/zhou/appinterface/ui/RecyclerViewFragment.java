package com.zhou.appinterface.ui;

import android.os.Bundle;
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

/**
 * Created by zzhoujay on 2015/8/9 0009.
 * 使用RecyclerView展示数据的Fragment
 */
public class RecyclerViewFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout loading, failure, nodata;
    private TextView loadingText, failureText, nodataText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.interface_fragment_recycler_view, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        loading = (LinearLayout) view.findViewById(R.id.interface_fragment_loading);
        failure = (LinearLayout) view.findViewById(R.id.interface_fragment_failure);
        nodata = (LinearLayout) view.findViewById(R.id.interface_fragment_nodata);
        loadingText = (TextView) view.findViewById(R.id.interface_fragment_loading_text);
        failureText = (TextView) view.findViewById(R.id.interface_fragment_failure_text);
        nodataText = (TextView) view.findViewById(R.id.interface_fragment_nodata_text);

        swipeRefreshLayout.setOnRefreshListener(this::requestRefresh);
    }

    public void onNoData() {
        swipeRefreshLayout.setVisibility(View.INVISIBLE);
        failure.setVisibility(View.INVISIBLE);
        nodata.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.INVISIBLE);
    }

    public void onFailure() {
        swipeRefreshLayout.setVisibility(View.INVISIBLE);
        failure.setVisibility(View.VISIBLE);
        nodata.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.INVISIBLE);
    }

    public void onLoading() {
        swipeRefreshLayout.setVisibility(View.INVISIBLE);
        failure.setVisibility(View.INVISIBLE);
        nodata.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
    }

    public void onSuccess() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        failure.setVisibility(View.INVISIBLE);
        nodata.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.INVISIBLE);
    }

    public void setNodataText(String text) {
        nodataText.setText(text);
    }

    public void setFailureText(String text) {
        failureText.setText(text);
    }

    public void setLoadingText(String text) {
        loadingText.setText(text);
    }

    protected void requestRefresh() {

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

}
