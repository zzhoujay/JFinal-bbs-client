package zhou.app.jfbs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhou.appinterface.data.DataManager;

import zhou.app.jfbs.App;
import zhou.app.jfbs.R;
import zhou.app.jfbs.data.NotificationProvider;
import zhou.app.jfbs.ui.adapter.NotificationAdapter;

/**
 * Created by zhou on 15/9/11.
 */
public class NotificationsFragment extends Fragment {

    private NotificationProvider provider;
    private View empty, failure;
    private TextView emptyText, failureText;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(App.TOKEN)) {
            provider = new NotificationProvider(bundle.getString(App.TOKEN));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        initView(v);
        notificationAdapter = new NotificationAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(notificationAdapter);
        swipeRefreshLayout.setOnRefreshListener(this::refresh);
        init();
        return v;
    }

    private void initView(View v) {
        empty = v.findViewById(R.id.fragment_empty);
        failure = v.findViewById(R.id.fragment_failure);
        emptyText = (TextView) v.findViewById(R.id.fragment_empty_text);
        failureText = (TextView) v.findViewById(R.id.fragment_failure_text);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

        failureText.setText(R.string.text_load_failure);
        emptyText.setText(R.string.text_empty);
    }

    public void refresh() {
        loading();
        DataManager.getInstance().update(provider, notifications -> {
            if (notifications == null) {
                failure();
            } else if (notifications.isEmpty()) {
                empty();
            } else {
                success();
                notificationAdapter.setNotifications(notifications);
            }
        });
    }

    public void init() {
        loading();
        DataManager.getInstance().get(provider, notifications -> {
            if (notifications == null) {
                failure();
            } else if (notifications.isEmpty()) {
                empty();
            } else {
                success();
                notificationAdapter.setNotifications(notifications);
            }
        });
    }

    public void failure() {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setVisibility(View.INVISIBLE);
        empty.setVisibility(View.INVISIBLE);
        failure.setVisibility(View.VISIBLE);
    }

    public void empty() {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setVisibility(View.INVISIBLE);
        empty.setVisibility(View.VISIBLE);
        failure.setVisibility(View.INVISIBLE);
    }

    public void success() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        empty.setVisibility(View.INVISIBLE);
        failure.setVisibility(View.INVISIBLE);
    }

    public void loading() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);
        empty.setVisibility(View.INVISIBLE);
        failure.setVisibility(View.INVISIBLE);
    }

    public static NotificationsFragment newInstance(String token) {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(App.TOKEN, token);
        fragment.setArguments(bundle);
        return fragment;
    }
}
