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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhou.appinterface.data.DataManager;

import zhou.app.jfbs.R;
import zhou.app.jfbs.data.TopicsProvider;
import zhou.app.jfbs.ui.adapter.AdvanceAdapter;
import zhou.app.jfbs.ui.adapter.TopicAdapter;

/**
 * Created by zzhoujay on 2015/8/28 0028.
 */
public class TopicsFragment extends Fragment implements View.OnClickListener {

    private final static String TAB = "tab";

    private String tab;
    private TopicsProvider provider;
    private View empty, failure, loadMore;
    private TextView emptyText, failureText, loadMoreText;
    private ProgressBar loadMoreProgress;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdvanceAdapter advanceAdapter;
    private TopicAdapter topicAdapter;
    private LinearLayoutManager manager;
    private boolean isLastPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(TAB)) {
            tab = bundle.getString(TAB);
        }
        provider = new TopicsProvider(tab, 20);

        provider.addNotifier(notifierId -> {
            switch (notifierId) {
                case TopicsProvider.LAST_PAGE:
                    loadMoreProgress.setVisibility(View.GONE);
                    loadMoreText.setText(R.string.text_last_page);
                    loadMoreText.setClickable(false);
                    isLastPage = true;
                    break;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        loadMore = inflater.inflate(R.layout.layout_load_more, container, false);
        loadMoreText = (TextView) loadMore.findViewById(R.id.fragment_load_more_text);
        loadMoreProgress = (ProgressBar) loadMore.findViewById(R.id.fragment_load_more_progress);

        loadMoreText.setText(R.string.text_load_more);

        initView(v);

        loadMore.setVisibility(View.GONE);

        init();

        swipeRefreshLayout.setOnRefreshListener(this::refresh);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (manager.findLastVisibleItemPosition() == advanceAdapter.getItemCount() - 1) {
                        more();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });

        return v;
    }

    private void initView(View v) {
        empty = v.findViewById(R.id.fragment_empty);
        failure = v.findViewById(R.id.fragment_failure);
        emptyText = (TextView) v.findViewById(R.id.fragment_empty_text);
        failureText = (TextView) v.findViewById(R.id.fragment_failure_text);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);


        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        manager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(manager);

        topicAdapter = new TopicAdapter();
        advanceAdapter = new AdvanceAdapter(topicAdapter);
        advanceAdapter.addFooter(loadMore);
        recyclerView.setAdapter(advanceAdapter);
    }

    public void refresh() {
        isLastPage = false;
        loading();
        DataManager.getInstance().update(provider, topics -> {
            if (topics == null) {
                failure();
            } else if (topics.isEmpty()) {
                empty();
            } else {
                success();
                topicAdapter.setTopics(topics);
                hiddenLoadMore();
            }
        });
    }

    public void more() {
        DataManager.getInstance().more(provider, topics -> {
            if (topics != null) {
                topicAdapter.setTopics(topics);
            } else {
                loadMoreProgress.setVisibility(View.GONE);
                loadMoreText.setText(R.string.text_load_again);
                loadMoreText.setClickable(true);
                loadMoreText.setOnClickListener(this);
            }
        });
    }

    public void init() {
        loading();
        DataManager.getInstance().get(provider, topics -> {
            if (topics == null) {
                failure();
            } else if (topics.isEmpty()) {
                empty();
            } else {
                success();
                topicAdapter.setTopics(topics);
                hiddenLoadMore();
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

    private void hiddenLoadMore() {
        if (manager.getItemCount() >= manager.findLastVisibleItemPosition() - manager.findFirstVisibleItemPosition() + 1) {
            loadMore.setVisibility(View.VISIBLE);
        } else {
            loadMore.setVisibility(View.GONE);
        }
    }

    public static TopicsFragment newInstance(String tab) {
        TopicsFragment topicsFragment = new TopicsFragment();
        if (tab != null) {
            Bundle bundle = new Bundle();
            bundle.putString(TAB, tab);
            topicsFragment.setArguments(bundle);
        }
        return topicsFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        provider.removeAllNotifier();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_load_more_text:
                loadMoreProgress.setVisibility(View.VISIBLE);
                loadMoreText.setText(R.string.text_load_more);
                loadMoreText.setClickable(false);
                more();
                break;
        }
    }

}
