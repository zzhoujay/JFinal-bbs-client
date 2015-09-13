package zhou.app.jfbs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.zhou.appinterface.callback.LoadCallback;

import zhou.app.jfbs.App;
import zhou.app.jfbs.R;
import zhou.app.jfbs.model.Reply;
import zhou.app.jfbs.model.Topic;
import zhou.app.jfbs.util.NetworkKit;

/**
 * Created by zhou on 15/9/12.
 */
public class ReplyFragment extends Fragment {

    private EditText reply;
    private ImageButton send;
    private String token;
    private String topicId;
    private String quote;
    private LoadCallback<Reply> replyLoadCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(App.TOKEN) && bundle.containsKey(Topic.TOPIC_ID)) {
            token = bundle.getString(App.TOKEN);
            topicId = bundle.getString(Topic.TOPIC_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_reply, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        reply = (EditText) v.findViewById(R.id.reply);
        send = (ImageButton) v.findViewById(R.id.send);

        send.setEnabled(false);

        reply.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(reply.getText())) {
                    send.setEnabled(false);
                } else {
                    send.setEnabled(true);
                }
            }
        });

        send.setOnClickListener(v1 -> NetworkKit.createReply(token, topicId, reply.getText().toString(), quote, result -> {
            if (result.isSuccessful()) {
                if (replyLoadCallback != null) {
                    replyLoadCallback.loadComplete(result.detail.reply);
                }
                reply.setText("");
            } else {
                Toast.makeText(getActivity(), R.string.failure_create_reply, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void setReplyLoadCallback(LoadCallback<Reply> replyLoadCallback) {
        this.replyLoadCallback = replyLoadCallback;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public static ReplyFragment newInstance(String token, String topicId) {
        ReplyFragment replyFragment = new ReplyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(App.TOKEN, token);
        bundle.putString(Topic.TOPIC_ID, topicId);
        replyFragment.setArguments(bundle);
        return replyFragment;
    }
}
