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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zhou.appinterface.data.DataManager;

import zhou.app.jfbs.R;
import zhou.app.jfbs.data.TopicWithRepliesProvider;
import zhou.app.jfbs.model.Topic;
import zhou.app.jfbs.ui.adapter.AdvanceAdapter;
import zhou.app.jfbs.ui.adapter.RepliesAdapter;
import zhou.app.jfbs.ui.weiget.RichText;
import zhou.app.jfbs.util.MarkDownKit;
import zhou.app.jfbs.util.TimeKit;

/**
 * Created by zzhoujay on 2015/9/5 0005.
 */
public class TopicDetailFragment extends Fragment {

    private ImageView icon;
    private TextView name, time, reply, view, title, failureText,emptyText;
    private RichText content;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View failure,empty;
    private TopicWithRepliesProvider provider;
    private AdvanceAdapter advanceAdapter;
    private RepliesAdapter repliesAdapter;
    private Topic topic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(Topic.TOPIC_ID)) {
            provider = new TopicWithRepliesProvider(bundle.getString(Topic.TOPIC_ID));
        }
        if(bundle!=null&&bundle.containsKey(Topic.TOPIC)){
            this.topic=bundle.getParcelable(Topic.TOPIC);
            if(topic!=null){
                provider=new TopicWithRepliesProvider(this.topic.id);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        v.setBackgroundResource(R.color.material_white);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        failure = v.findViewById(R.id.fragment_failure);
        failureText = (TextView) v.findViewById(R.id.fragment_failure_text);

        empty=v.findViewById(R.id.fragment_empty);
        emptyText= (TextView) v.findViewById(R.id.fragment_empty_text);

        failureText.setText(R.string.text_load_failure);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        View head = inflater.inflate(R.layout.layout_topic_detail, container, false);
        initView(head);

        repliesAdapter = new RepliesAdapter();
        advanceAdapter = new AdvanceAdapter(repliesAdapter);
        advanceAdapter.addHeader(head);
        recyclerView.setAdapter(advanceAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        swipeRefreshLayout.setOnRefreshListener(this::refresh);

        if(topic!=null){
            initTopic(topic);
        }

        init();

        return v;
    }

    private void init() {
        if (provider != null) {
            DataManager.getInstance().get(provider, topicWithReply -> {
                if(topicWithReply==null){
                    failure();
                }else if(topicWithReply.isEmpty()){
                    empty();
                }else {
                    initTopic(topicWithReply.topic);
                    repliesAdapter.setReplies(topicWithReply.replies);
                    success();
                }
            });
        }
    }

    private void refresh() {
        if (provider != null) {
            loading();
            DataManager.getInstance().update(provider, topicWithReply -> {
                if(topicWithReply==null){
                    failure();
                }else if(topicWithReply.isEmpty()){
                    empty();
                }else {
                    initTopic(topicWithReply.topic);
                    repliesAdapter.setReplies(topicWithReply.replies);
                    success();
                }
            });
        }
    }


    private void initView(View v) {
        icon = (ImageView) v.findViewById(R.id.icon);
        name = (TextView) v.findViewById(R.id.name);
        time = (TextView) v.findViewById(R.id.time);
        reply = (TextView) v.findViewById(R.id.reply);
        view = (TextView) v.findViewById(R.id.view);
        title = (TextView) v.findViewById(R.id.title);
        content = (RichText) v.findViewById(R.id.content);
    }

    private void initTopic(Topic topic) {
        if (topic != null) {
            Picasso.with(getActivity()).load(topic.avatar).placeholder(R.drawable.ic_iconfont_tupian)
                    .error(R.drawable.ic_iconfont_tupian).into(icon);
            name.setText(topic.nickname);
            time.setText(TimeKit.format(topic.modifyTime == null ? topic.inTime : topic.modifyTime));
            reply.setText(String.format("%d回复", topic.replyCount));
            view.setText(String.format("%d浏览", topic.view));
            title.setText(topic.title);
            content.setRichText(MarkDownKit.conver(topic.content));
        }
    }

    public void failure() {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setVisibility(View.INVISIBLE);
        failure.setVisibility(View.VISIBLE);
        empty.setVisibility(View.INVISIBLE);
    }

    public void success() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        failure.setVisibility(View.INVISIBLE);
        empty.setVisibility(View.INVISIBLE);
    }

    public void loading() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);
        failure.setVisibility(View.INVISIBLE);
        empty.setVisibility(View.INVISIBLE);
    }

    public void empty(){
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setVisibility(View.INVISIBLE);
        failure.setVisibility(View.INVISIBLE);
        empty.setVisibility(View.VISIBLE);
    }

    public static TopicDetailFragment newInstance(Topic topic) {
        TopicDetailFragment topicDetailFragment = new TopicDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Topic.TOPIC, topic);
        topicDetailFragment.setArguments(bundle);
        return topicDetailFragment;
    }

    public static TopicDetailFragment newInstance(String id){
        TopicDetailFragment topicDetailFragment=new TopicDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putString(Topic.TOPIC_ID, id);
        topicDetailFragment.setArguments(bundle);
        return topicDetailFragment;
    }
}
