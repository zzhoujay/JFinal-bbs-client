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
import android.widget.Toast;

import com.zhou.appinterface.data.DataManager;
import com.zhou.appinterface.util.LogKit;

import zhou.app.jfbs.R;
import zhou.app.jfbs.data.TopicsProvider;
import zhou.app.jfbs.ui.adapter.AdvanceAdapter;
import zhou.app.jfbs.ui.adapter.TopicAdapter;

/**
 * Created by zzhoujay on 2015/8/28 0028.
 */
public class TopicsFragment extends Fragment {

    private final static String TAB = "tab";

    private String tab;
    private TopicsProvider provider;
    private View empty, failure, loadMore;
    private TextView emptyText, failureText, loadMoreText;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AdvanceAdapter advanceAdapter;
    private TopicAdapter topicAdapter;
    private LinearLayoutManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(TAB)) {
            tab = bundle.getString(TAB);
        }
        provider = new TopicsProvider(tab, 20);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        loadMore = inflater.inflate(R.layout.layout_load_more, container, false);
        loadMoreText = (TextView) loadMore.findViewById(R.id.fragment_loading_text);
        initView(v);
        init();

//        loadMore.setVisibility(View.GONE);


        swipeRefreshLayout.setOnRefreshListener(this::refresh);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (manager.findLastVisibleItemPosition() == advanceAdapter.getItemCount()-1) {
                        Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();
                        more();
                    }
                    LogKit.i("xxxxxxx",String.format("%d,%d",manager.findLastVisibleItemPosition(),advanceAdapter.getItemCount()));
                    LogKit.i("position",String.format("getItemCount:%d,\nfindFirstCompletelyVisibleItemPosition:%d,\nfindFirstVisibleItemPosition:%d,\nfindLastCompletelyVisibleItemPosition:%d,\nfindLastVisibleItemPosition:%d",manager.getItemCount(),manager.findFirstCompletelyVisibleItemPosition()
                            ,manager.findFirstVisibleItemPosition(),manager.findLastCompletelyVisibleItemPosition(),manager.findLastVisibleItemPosition()));
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

    public void more(){
        DataManager.getInstance().more(provider,topics -> {
            if(topics!=null){
                topicAdapter.addTopics(topics);
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

}
